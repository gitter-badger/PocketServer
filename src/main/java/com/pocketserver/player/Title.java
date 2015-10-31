package com.pocketserver.player;

import com.pocketserver.net.PacketManager;
import com.pocketserver.net.packets.message.TitlePacket;

public class Title {
    private final String topLine; //TODO RENAME THESE
    private final String bottomLine;
    private final short fadeDelay;

    public Title(String topLine, String bottomLine, short fadeDelay) {
        this.topLine = topLine;
        this.bottomLine = bottomLine;
        this.fadeDelay = fadeDelay;
    }

    public String getTopLine() {
        return topLine;
    }

    public String getBottomLine() {
        return bottomLine;
    }

    public short getFadeDelay() {
        return fadeDelay;
    }

    public void send() {
        TitlePacket packet = new TitlePacket();
        packet.encode(packet.createDatagramPacket());
    }
}
