package com.master.gm.elaGener.oop.act;

import com.master.gm.elaGener.gener.AccGen;
import com.master.gm.elaGener.gener.ServerIdGen;
import com.master.gm.elaGener.oop.Server;
import com.master.gm.elaGener.oop.User;
import com.master.gm.elaGener.oop.action.Action;
import com.master.gm.elaGener.oop.action.Adjust;
import com.master.gm.elaGener.oop.action.CreAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//要求200个玩家在3天内对10个服务器进行注册(82,66,52)
public class SimpleAct {
    private int userCount;
    private int serverCount;
    private Action node1;

    private List<User> users;
    private List<Server> servers;
    public SimpleAct(int userCount, int serverCount) {
        this.userCount = userCount;
        this.serverCount = serverCount;
        users = new ArrayList<>();
        servers = new ArrayList<>();
        init();
    }
    private List<CreAction.StandCreAdjust> adjusts;

    Random r = new Random();
    public  Server randomServer(){
        if(null == servers || servers.size() == 0)
            return null;
        return servers.get(r.nextInt(servers.size()));
    }

    public void adjust(){
        CreAction.StandCreAdjust adjust = null;
        for (int i = 0; i < users.size(); i++) {
            if(i < 82){
                adjust = adjusts.get(0);
            }else if(i < 148){
                adjust = adjusts.get(1);
            }else {
                adjust = adjusts.get(2);
            }
            Server server = randomServer();
            User user = users.get(i);
            Action action = new CreAction();
            adjust.base(user, server);
            adjust.adjust(user, server);
            action.action(user,server);
        }
    }


    public void initAdjust(){
        adjusts = new ArrayList<>();
        CreAction.StandCreAdjust s1 = new CreAction.StandCreAdjust();
        s1.setCre_role_time(1551801600000L);
        CreAction.StandCreAdjust s2 = new CreAction.StandCreAdjust();
        s2.setCre_role_time(1551888000000L);
        CreAction.StandCreAdjust s3 = new CreAction.StandCreAdjust();
        s3.setCre_role_time(1551974400000L);
        adjusts.add(s1);
        adjusts.add(s2);
        adjusts.add(s3);
    }

    public void init(){
        for (String s : AccGen.getAcc(userCount)) {
            users.add(new User(s));
        }
        for (String serverId : ServerIdGen.getServerIds(serverCount)) {
            Server server = new Server(serverId);
            server.setSer_cre_time(1551801600000L);
            servers.add(server);
        }
        initAdjust();
    }

    public static void main(String[] args) {
        new SimpleAct(200,10).adjust();
    }
}
