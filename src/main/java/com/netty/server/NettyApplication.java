package com.netty.server;


import com.netty.server.netty.handler.NettyWebSocketChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

import static io.netty.channel.ChannelOption.SO_BACKLOG;

/**
 * @author huangxin
 */
@PropertySource(value= "classpath:/nettyserver.properties")
@SpringBootApplication
public class NettyApplication {

	@Value("${tcp.port}")
	private int tcpPort;

	@Value("${boss.thread.count}")
	private int bossCount;

	@Value("${worker.thread.count}")
	private int workerCount;

	@Value("${so.keepalive}")
	private boolean keepAlive;

	@Value("${so.backlog}")
	private int backlog;

	@Bean(name = "serverBootstrap")
	public ServerBootstrap bootstrap() {
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup(), workerGroup())
				.channel(NioServerSocketChannel.class)
				.handler(new LoggingHandler(LogLevel.DEBUG))
				.childHandler(nettyWebSocketChannelInitializer);
        b.option(ChannelOption.SO_KEEPALIVE, keepAlive).option(SO_BACKLOG, backlog);
		return b;
	}

	@Resource
	private NettyWebSocketChannelInitializer nettyWebSocketChannelInitializer;

	@Bean(name = "bossGroup", destroyMethod = "shutdownGracefully")
	public NioEventLoopGroup bossGroup() {
		return new NioEventLoopGroup(bossCount);
	}

	@Bean(name = "workerGroup", destroyMethod = "shutdownGracefully")
	public NioEventLoopGroup workerGroup() {
		return new NioEventLoopGroup(workerCount);
	}

	@Bean(name = "tcpSocketAddress")
	public InetSocketAddress tcpPort() {
		return new InetSocketAddress(tcpPort);
	}

	public static void main(String[] args) throws Exception{
		ConfigurableApplicationContext context = SpringApplication.run(NettyApplication.class, args);
		TCPServer tcpServer = context.getBean(TCPServer.class);
		tcpServer.start();
	}
}
