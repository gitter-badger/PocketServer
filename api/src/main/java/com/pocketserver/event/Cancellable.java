package com.pocketserver.event;

public interface Cancellable {
    boolean isCancelled();

    void setCancelled(boolean cancelled);
}
