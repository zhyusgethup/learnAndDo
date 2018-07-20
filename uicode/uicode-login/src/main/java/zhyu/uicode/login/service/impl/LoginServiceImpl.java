package zhyu.uicode.login.service.impl;

import zhyu.common.dao.AbstractService;
import zhyu.common.vo.Result;
import zhyu.uicode.login.service.LoginService;

import java.util.Map;

public class LoginServiceImpl  extends AbstractService implements
        LoginService {

    @Override
    public Result loginByPassword(Map<String, Object> param) {
        Map<String, Object> one = this.template.selectOne("login" +
                ".checkAccountAndPassword",param);
        if(one != null && one.size() > 0) {
            String userId = one.get("USER_ID").toString();
            Map<String, Object> info = this.template.selectOne("login" +
                    ".getUserInfoById",userId);
            return null;
        }else {
            return Result.getFailResult("登陆失败");
        }
    }
}
