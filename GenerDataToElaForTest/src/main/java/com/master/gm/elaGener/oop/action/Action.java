package com.master.gm.elaGener.oop.action;

import com.alibaba.fastjson.JSONObject;
import com.master.gm.elaGener.log.ela.bean.BaseELog;
import com.master.gm.elaGener.oop.Server;
import com.master.gm.elaGener.oop.User;
import com.master.gm.elaGener.utils.ElUtil;

import java.util.HashMap;
import java.util.Map;

/***
 * 行为, 连接玩家,服务器,公会,日志,
 * 并规范了顺序的行动;
 * 或者行动链条
 */
public abstract class Action {
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    protected Adjust adjust;
    protected Action before;
    protected Action next;
    protected BaseELog log;
    protected Map<String,Object> params;
    public Action end(){
        Action action = this;
        while(null != action.before){
            action = action.before;
        }
        return action;
    }

    public Action next(Action action){
        this.next = action;
        action.before = this;
        return next;
    }
    public Adjust getAdjust() {
        return adjust;
    }

    public void setAdjust(Adjust adjust) {
        this.adjust = adjust;
        this.adjust.setAction(this);
    }

    public Action getBefore() {
        return before;
    }

    public void setBefore(Action before) {
        this.before = before;
    }

    public Action getNext() {
        return next;
    }

    public void setNext(Action next) {
        this.next = next;
    }

    public BaseELog getLog() {
        return log;
    }

    public void setLog(BaseELog log) {
        this.log = log;
    }

    abstract public void gener(User user, Server server);

    public Action() {
        params = new HashMap<>();
    }

    public void action(User user, Server server) {
        Action action = this;
        while(null != action){
            try {
                action.gener(user, server);
                action.log.setServer(server.getServerId());
                JSONObject data = action.log.getData();
                ElUtil.sendMsgToEl(data);
                System.out.println(action.name + ":ok");
            }catch (ActionException e){
                System.out.println(action.name + ":wrong:" + e.getMessage());
                break;
            }
            action = action.next;
        }
    }
}
