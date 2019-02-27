package org.b3log.tank.server;

import com.badlogic.gdx.math.MathUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.b3log.tank.model.common.GameData;

import java.util.Map;

@Slf4j
public class GameServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object obj)
            throws Exception {
        if (obj instanceof GameData) {
            GameData gameData = (GameData) obj;
            GameServer.GAME_DATA_MAP.put(gameData.getPlayerId(), gameData);
            ctx.writeAndFlush(GameServer.GAME_DATA_MAP);
            log.debug("From Client:> {}", gameData);
        }
    }
}
