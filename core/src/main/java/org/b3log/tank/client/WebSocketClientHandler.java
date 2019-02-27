package org.b3log.tank.client;

import com.alibaba.fastjson.JSON;
import com.badlogic.gdx.Game;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.b3log.tank.core.GdxTank;
import org.b3log.tank.model.common.GameData;

import java.util.Map;

/**
 * @author : yu.zhang
 * Date : 2019/2/26 2:23 PM
 * Email : zephyrjung@126.com
 **/
@Data
@Slf4j
public class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {
    WebSocketClientHandshaker handshaker;
    ChannelPromise handshakeFuture;

    public void handlerAdded(ChannelHandlerContext ctx) {
        this.handshakeFuture = ctx.newPromise();
    }

    public ChannelFuture handshakeFuture() {
        return this.handshakeFuture;
    }

    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead0  " + this.handshaker.isHandshakeComplete());
        Channel ch = ctx.channel();
        FullHttpResponse response;
        if (!this.handshaker.isHandshakeComplete()) {
            try {
                response = (FullHttpResponse) msg;
                //握手协议返回，设置结束握手
                this.handshaker.finishHandshake(ch, response);
                //设置成功
                this.handshakeFuture.setSuccess();
                System.out.println("WebSocket Client connected! response headers[sec-websocket-extensions]:{}" + response.headers());
            } catch (WebSocketHandshakeException var7) {
                FullHttpResponse res = (FullHttpResponse) msg;
                String errorMsg = String.format("WebSocket Client failed to connect,status:%s,reason:%s", res.status(), res.content().toString(CharsetUtil.UTF_8));
                this.handshakeFuture.setFailure(new Exception(errorMsg));
            }
        } else if (msg instanceof FullHttpResponse) {
            response = (FullHttpResponse) msg;
            //this.listener.onFail(response.status().code(), response.content().toString(CharsetUtil.UTF_8));
            throw new IllegalStateException("Unexpected FullHttpResponse (getStatus=" + response.status() + ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        } else {
            WebSocketFrame frame = (WebSocketFrame) msg;
            if (frame instanceof TextWebSocketFrame) {
                TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
                //this.listener.onMessage(textFrame.text());
                Map map = JSON.parseObject(textFrame.text(), Map.class);
                GdxTank gdxTank = GdxTank.getInstance();
                gdxTank.updateGameDataMap(map);
            } else if (frame instanceof BinaryWebSocketFrame) {
                log.debug("binary web socket frame");
            } else if (frame instanceof PongWebSocketFrame) {
                log.debug("pong web socket frame");
            } else if (frame instanceof CloseWebSocketFrame) {
                ch.close();
            }else{
                log.debug(frame.toString());
            }

        }
    }
}
