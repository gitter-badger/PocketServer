package com.pocketserver.net.netty.channel;

import com.google.inject.Guice;
import com.pocketserver.net.netty.decoder.PacketDecoder;
import com.pocketserver.net.netty.encoder.PacketEncoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

import java.util.logging.Logger;

public class PacketChannelInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast(new PacketEncoder());
        pipeline.addLast(new PacketDecoder());
        pipeline.addLast(new PacketAdapter(Logger.getGlobal()));
    }
}