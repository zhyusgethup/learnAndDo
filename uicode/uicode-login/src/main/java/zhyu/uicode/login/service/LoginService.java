package zhyu.uicode.login.service;

import zhyu.common.vo.Result;

import java.util.Map;

public interface LoginService {
    public Result loginByPassword(Map<String, Object> param);
}
