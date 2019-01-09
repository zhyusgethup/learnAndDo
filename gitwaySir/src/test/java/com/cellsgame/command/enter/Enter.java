package com.cellsgame.command.enter;

import com.cellsgame.gateway.core.Connection;

import java.util.Map;

public interface Enter {

    void start(Connection connection, int cmd, Map map);
}
