package org.b3log.tank.websocket;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.b3log.tank.model.common.GameData;

/**
 * @author : yu.zhang
 * Date : 2019/2/26 2:22 PM
 * Email : zephyrjung@126.com
 **/
@Slf4j
public class WebSocketHandle extends SimpleChannelInboundHandler<Object> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("channelActive");
    }

    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof TextWebSocketFrame) {
            String text = ((TextWebSocketFrame) msg).text();
            log.debug("收到信息：{}", text);
            GameData gameData = JSON.parseObject(text, GameData.class);
            GameServer.GAME_DATA_MAP.put(gameData.getPlayerId(), gameData);
            ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(GameServer.GAME_DATA_MAP)));
        } else if (msg instanceof BinaryWebSocketFrame) {
            log.debug("收到二进制信息：{}", ((BinaryWebSocketFrame) msg).content().readableBytes());
            BinaryWebSocketFrame binaryWebSocketFrame = new BinaryWebSocketFrame(Unpooled.buffer().writeBytes("xxx".getBytes()));
            ctx.channel().writeAndFlush(binaryWebSocketFrame);
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.debug("channelUnregistered");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("channelInactive");
    }
}
