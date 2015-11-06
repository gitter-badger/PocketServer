package com.pocketserver.event.entity.vehicle;

import com.pocketserver.event.Cancellable;
import com.pocketserver.event.Event;

public class VehicleRideEvent extends Event implements Cancellable {
    private boolean cancelled;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
