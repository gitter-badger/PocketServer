package com.pocketserver;

public class Permission {
    private final String permission;
    private boolean allowed = true;

    public Permission(String permission, boolean allowed) {
        this.permission = permission;
        this.allowed = allowed;
    }

    public String getPermission() {
        return permission;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }
}
