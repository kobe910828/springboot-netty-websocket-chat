package netty.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author xin.huang
 * @version v1.0
 * @date 2019/3/2 16:45
 */
public class NettyServer {
    /**
     * 开启服务的方法
     */
    public void StartNetty(){

        /*创建两个EventLoop的组，EventLoop 这个相当于一个处理线程，
         是Netty接收请求和处理IO请求的线程。不理解的话可以百度NIO图解*/
        /*
        相关资料：NioEventLoopGroup是一个处理I/O操作的多线程事件循环。
        Netty为不同类型的传输提供了各种EventLoopGroup实现。
        在本例中，我们正在实现一个服务器端应用程序，因此将使用两个NioEventLoopGroup。
        第一个，通常称为“boss”，接受传入的连接。
        第二个，通常称为“worker”，当boss接受连接并注册被接受的连接到worker时，处理被接受连接的流量。
        使用了多少线程以及如何将它们映射到创建的通道取决于EventLoopGroup实现，甚至可以通过构造函数进行配置。
        */
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            //1、创建启动类
            ServerBootstrap bootstrap = new ServerBootstrap();
            //2、配置启动参数等
            /*设置循环线程组，前者用于处理客户端连接事件，后者用于处理网络IO(server使用两个参数这个)
             public ServerBootstrap group(EventLoopGroup group)
             public ServerBootstrap group(EventLoopGroup parentGroup, EventLoopGroup childGroup)
             */
            //bootstrap.group(boss,worker);
            /*设置选项
              参数：Socket的标准参数（key，value），可自行百度
              eg:
              bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
             bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
              */
            //bootstrap.option(ChannelOption.SO_BACKLOG, 1024);

            //用于构造socketchannel工厂
            //bootstrap.channel(NioServerSocketChannel.class);
            /*
              传入自定义客户端Handle（服务端在这里搞事情）
             */
            bootstrap.group(boss, worker).option(ChannelOption.SO_BACKLOG, 1024)
            .channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    // 注册handler
                    ch.pipeline().addLast(new NettyServerHandler());
                }
            });

            // 监听端口，开始接收进来的连接
            int port = 9999; //服务端口
            ChannelFuture future = bootstrap.bind(port).sync();


            // 阻塞方法，等待服务端链路关闭之后main函数才退出。
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

}
