package netty.protocol.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author xin.huang
 * @version v1.0
 * @date 2019/3/11 13:38
 */
public class HttpFileServer {
    private static final String DEFAULT_URL = "/src/test/java/netty/";

    public void run(final int port, final String url) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast("http-decoder",
                                    new HttpRequestDecoder()); // 请求解码器
                            ch.pipeline().addLast("http-aggregator",
                                    new HttpObjectAggregator(65536));// 目的是将多个消息对象转换为单一的request或者response对象
                            ch.pipeline().addLast("http-encoder",
                                    new HttpResponseEncoder());//响应编码器
                            ch.pipeline().addLast("http-chunked",
                                    new ChunkedWriteHandler());//目的是支持异步大文件传输（）
                            ch.pipeline().addLast("fileServerHandler",
                                    new HttpFileServerHandler(url));// 业务逻辑
                        }
                    });
            ChannelFuture future = b.bind("192.168.158.111", port).sync();
            System.out.println("HTTP文件目录服务器启动，网址是 : " + "http://192.168.158.111:"
                    + port + url);
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        new HttpFileServer().run(port, DEFAULT_URL);
    }
}
