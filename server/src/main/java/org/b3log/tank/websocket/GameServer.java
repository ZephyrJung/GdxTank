package org.b3log.tank.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.b3log.tank.model.common.GameData;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : yu.zhang
 * Date : 2019/2/26 2:20 PM
 * Email : zephyrjung@126.com
 * https://blog.csdn.net/u010939285/article/details/81231221
 **/
@Service
public class GameServer {
    public static final Map<String, GameData> GAME_DATA_MAP = new ConcurrentHashMap<String, GameData>();

    @PostConstruct
    public static void serviceStart() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup wokerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, wokerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new WebSocketChannelInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(8080)).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            wokerGroup.shutdownGracefully();
        }
    }
}
