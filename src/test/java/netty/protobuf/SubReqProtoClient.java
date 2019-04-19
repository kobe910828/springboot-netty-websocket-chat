package netty.protobuf;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author xin.huang
 * @version v1.0
 * @date 2019/3/7 15:25
 */
public class SubReqProtoClient {
    public static void main(String[] args)throws Exception{
        int port=15444;
        new SubReqProtoClient().bind(port, "127.0.0.1");
    }
    private void bind(int port,String host)throws Exception{
        //配置客户端NIO线程池
        EventLoopGroup workGroup=new NioEventLoopGroup();
        try{
            io.netty.bootstrap.Bootstrap b=new io.netty.bootstrap.Bootstrap();
            b.group(workGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.TCP_NODELAY,true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new ProtobufVarint32FrameDecoder())
                            .addLast(new ProtobufDecoder(SubscribeRespProto.SubscribeResp.getDefaultInstance()))
                            .addLast(new ProtobufVarint32LengthFieldPrepender())
                            .addLast(new ProtobufEncoder())
                            .addLast(new SubReqProtoClientHandler());
                }
            });
            //发起异步连接操作
            ChannelFuture f=b.connect(host,port).sync();
            //等待客户端链路关闭
            f.channel().closeFuture().sync();
        }finally {
            //释放NIO 线程组
            workGroup.shutdownGracefully();
        }
    }
}
