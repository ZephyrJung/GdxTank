package org.b3log.tank.client;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.b3log.tank.model.common.GameData;

import java.net.URI;

/**
 * @author : yu.zhang
 * Date : 2019/2/26 2:22 PM
 * Email : zephyrjung@126.com
 **/
@Slf4j
public class WebSocketClient extends Thread {
    private Channel channel;
//    private static final String SERVER_URI = "ws://hitbug.cn:80/ws";
    private static final String SERVER_URI = "ws://127.0.0.1:8080/ws";

    @Override
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap boot = new Bootstrap();
            boot.option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_BACKLOG, 1024 * 1024 * 10)
                    .group(group)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline p = socketChannel.pipeline();
                            p.addLast(new HttpClientCodec(), new HttpObjectAggregator(1024 * 1024 * 10));
                            p.addLast("hookedHandler", new WebSocketClientHandler());
                        }
                    });
            URI websocketURI = new URI(SERVER_URI);
            HttpHeaders httpHeaders = new DefaultHttpHeaders();
            //进行握手
            WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(websocketURI, WebSocketVersion.V13, null, true, httpHeaders);
            log.debug("connect");
            channel = boot.connect(websocketURI.getHost(), websocketURI.getPort()).sync().channel();
            WebSocketClientHandler handler = (WebSocketClientHandler) channel.pipeline().get("hookedHandler");
            handler.setHandshaker(handshaker);
            handshaker.handshake(channel);
            //阻塞等待是否握手成功
            handler.handshakeFuture().sync();
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
        log.debug("text send");
        TextWebSocketFrame frame = new TextWebSocketFrame(JSON.toJSONString(gameData));
        channel.writeAndFlush(frame);
    }
}
