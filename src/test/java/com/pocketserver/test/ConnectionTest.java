package com.pocketserver.test;

import com.jayway.awaitility.Awaitility;
import com.pocketserver.PocketServer;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class ConnectionTest {
    @Test
    public void testConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PocketServer.main(new String[]{});
            }
        });
        Awaitility.await().forever().untilFalse(new AtomicBoolean(PocketServer.getServer().isRunning()));
    }
}
