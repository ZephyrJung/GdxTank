/**
 *
 */
package org.b3log.tank.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.b3log.tank.model.common.Position;

@Slf4j
public class SerializationClientHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object obj)
            throws Exception {
        if (obj instanceof Position) {
            Position position = (Position) obj;
            log.debug("From Server:> {}", position);
        }

    }

}
