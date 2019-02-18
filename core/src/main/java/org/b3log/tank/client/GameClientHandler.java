/**
 *
 */
package org.b3log.tank.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.b3log.tank.core.GdxTank;
import org.b3log.tank.model.common.GameData;

import java.util.Map;

@Slf4j
public class GameClientHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object obj)
            throws Exception {
        if (obj instanceof Map) {
            Map<String, GameData> gameDataMap = (Map) obj;
            log.debug("From Server:> {}", obj);

            GdxTank gdxTank = GdxTank.getInstance();
            gdxTank.updateGameDataMap(gameDataMap);
        }
    }
}
