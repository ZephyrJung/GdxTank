package org.b3log.tank.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.b3log.tank.model.common.Position;

/**
 * 客户端 channel
 *
 * @author waylau.com
 * @date 2015-2-26
 */
@Slf4j
public class SimpleChatClientHandler extends SimpleChannelInboundHandler<Position> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Position msg) throws Exception {
        log.debug("position received:{}", msg);
    }
}
