package com.master.analyze.analyeBean;

import com.master.gm.service.analyze.LoginLogoutService;
import com.master.gm.service.analyze.impl.LoginLogoutServiceImpl;

import java.util.Map;

/***
 * 该接口定义每一个列值的算法,
 */
public interface ColumnArithmetic {
    public Map<String,String> getValue(Map<String, Object> cache, Row[] rows, LoginLogoutService service);
}
