package com.master.analyze.analyeBean;

import com.master.gm.service.analyze.LoginLogoutService;

import java.util.Map;

public abstract class AbstractTable {
    public abstract StringBuilder startupLoad(LoginLogoutService service);
    public abstract StringBuilder fetchFromLogsAndCheckClear(LoginLogoutService service);
    public abstract Map toGraphicJsonStr(String value);
    public abstract ResultBean getJsonStrByPageSizeAndPageIndex(int pageSize, int pageIndex);
    public abstract String getTableName();
    public abstract String printState();
    public abstract String getTableStr();
    public abstract void clearTable();
}
