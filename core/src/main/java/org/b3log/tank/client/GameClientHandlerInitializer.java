package org.b3log.tank.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class GameClientHandlerInitializer extends
        ChannelInitializer<Channel> {

    private static final int MAX_OBJECT_SIZE = 1024 * 1024;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE,
                ClassResolvers.weakCachingConcurrentResolver(this.getClass()
                        .getClassLoader())));
        pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new GameClientHandler());
    }
}