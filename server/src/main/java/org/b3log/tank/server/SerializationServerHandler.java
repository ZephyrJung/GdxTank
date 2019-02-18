package org.b3log.tank.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.b3log.tank.model.common.Position;

@Slf4j
public class SerializationServerHandler extends SimpleChannelInboundHandler<Object> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object obj)
            throws Exception {
        if (obj instanceof Position) {
            Position position = (Position) obj;
            ctx.writeAndFlush(position);
            log.debug("From Client:> {}", position);
        }
    }
}
