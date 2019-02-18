package org.b3log.tank.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.b3log.tank.model.common.GameData;

/**
 * @author : yu.zhang
 * Date : 2019/2/17 3:50 PM
 * Email : zephyrjung@126.com
 **/
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameClient extends Thread {
    private String host = "localhost";
    private int port = 8080;
    private Channel channel;

    @Override
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SerializationClientHandlerInitializer());
            channel = bootstrap.connect(host, port).sync().channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.error("client error:{}", e);
        } finally {
            group.shutdownGracefully();
        }
    }

    public void notifyServer(GameData gameData) {
        if (channel == null) {
            log.error("Channel not ready!");
            return;
        }
        channel.writeAndFlush(gameData);
    }
}
