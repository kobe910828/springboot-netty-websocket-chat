package com.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.net.InetSocketAddress;


@Component
public class TCPServer {

    @Resource
    private ServerBootstrap serverBootstrap;

    @Resource
    private InetSocketAddress tcpPort;

    private Channel serverChannel;

    public void start() throws Exception {
        serverChannel =  serverBootstrap.bind(tcpPort).sync().channel().closeFuture().sync().channel();
    }

    @PreDestroy
    public void stop() throws Exception {
        serverChannel.close();
        serverChannel.parent().close();
    }

}