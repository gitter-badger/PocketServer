package com.pocketserver;

public interface Permissible {

    boolean hasPermission(String permission);

    void setPermission(String permission, boolean value);

}
