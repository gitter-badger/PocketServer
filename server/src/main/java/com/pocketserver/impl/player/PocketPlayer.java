package com.pocketserver.impl.player;

import java.net.InetSocketAddress;

import com.pocketserver.impl.entity.living.PocketLivingEntity;
import com.pocketserver.impl.net.packets.message.MessagePacket;
import com.pocketserver.player.GameMode;
import com.pocketserver.player.Player;

import io.netty.channel.ChannelHandlerContext;

public class PocketPlayer extends PocketLivingEntity implements Player {

    private ChannelHandlerContext ctx;
    private final InetSocketAddress address;
    private GameMode gameMode = GameMode.SURVIVAL;
    private String name;

    public PocketPlayer(int entityId, InetSocketAddress address) {
        super(entityId);
        this.address = address;
    }

    @Override
    public void sendMessage(String message) {
        new MessagePacket(message).sendLogin(ctx, address);
    }

    @Override
    public GameMode getGameMode() {
        return gameMode;
    }

    @Override
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    @Override
    public void chat(String message) {

    }

    @Override
    public InetSocketAddress getAddress() {
        return address;
    }

    public ChannelHandlerContext getChannelContext() {
        return ctx;
    }

    @Override
    public String getName() {
        return name;
    }

    public Player setName(String name) {
        this.name = name;
        return this;
    }

}
