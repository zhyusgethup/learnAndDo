package com.master.analyze.analyeBean;

import java.util.Map;

public interface ColumnInit {
    abstract void init(Map<String,Object> longCache, Map<String, Object> shortCache);
}
