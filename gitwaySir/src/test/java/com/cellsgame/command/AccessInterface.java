package com.cellsgame.command;

import com.cellsgame.gateway.core.Connection;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class AccessInterface {
    private int cmd;
    private Map<String, Object> data;

    public int getCmd() {
        return  cmd;
    }
    public Map getData() {
        return  data;
    }
    public AccessInterface( String cmd, Map<String, Object> data) {
        this.cmd = Integer.valueOf(cmd);
        this.data = data;
    }

    public AccessInterface( int cmd, Map<String, Object> data) {
        this.cmd = cmd;
        this.data = data;
    }


    public AccessInterface(int cmd) {
        this.cmd = cmd;
        this.data = new HashMap<>();
    }

    public AccessInterface( String cmd) {
        this.cmd = Integer.valueOf(cmd);
        this.data = new HashMap<>();
    }
}
