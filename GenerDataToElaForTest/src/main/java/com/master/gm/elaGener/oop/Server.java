package com.master.gm.elaGener.oop;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Server {
    private String serverId;
    private long ser_cre_time;
    private Map<String, UserVO> users;

    public long getSer_cre_time() {
        return ser_cre_time;
    }

    public void setSer_cre_time(long ser_cre_time) {
        this.ser_cre_time = ser_cre_time;
    }

    private Set<String> regeterUser;
    private Set<String> onlineUser;
    //公会id,
    private Map<Integer,Guild> guildMap;

    public Server(String serverId) {
        this.serverId = serverId;
        users = new HashMap<>();
        guildMap = new HashMap<>();
        regeterUser = new HashSet<>();
        onlineUser = new HashSet<>();
    }

    public Set<String> getRegeterUser() {
        return regeterUser;
    }

    public void setRegeterUser(Set<String> regeterUser) {
        this.regeterUser = regeterUser;
    }

    public Set<String> getOnlineUser() {
        return onlineUser;
    }

    public void setOnlineUser(Set<String> onlineUser) {
        this.onlineUser = onlineUser;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public UserVO getUser(String acc){
        return users.get(acc);
    }


    @Override
    public String toString() {
        return "Server{" +
                "serverId='" + serverId + '\'' +
                ", users=" + users +
                ", regeterUser=" + regeterUser +
                ", onlineUser=" + onlineUser +
                ", guildMap=" + guildMap +
                '}';
    }

    public Map<String, UserVO> getUsers() {
        return users;
    }

    public void setUsers(Map<String, UserVO> users) {
        this.users = users;
    }

    public void setGuildMap(Map<Integer, Guild> guildMap) {
        this.guildMap = guildMap;
    }

    public String getServerId() {
        return serverId;
    }


    public Map<Integer, Guild> getGuildMap() {
        return guildMap;
    }

    public static class UserVO {
        private String acc;
        private long loginTime;
        private long logoutTime;
        private int guildId;
        private long cre_role_time;
        private int nearInst;
        private int point;

        public Map<Integer, Integer> getGood() {
            return good;
        }

        public void setGood(Map<Integer, Integer> good) {
            this.good = good;
        }

        private Map<Integer, Integer> good;
        public int getPoint() {
            return point;
        }

        public UserVO(String acc) {
            this.acc = acc;
            good = new HashMap<>();
        }

        @Override
        public String toString() {
            return "UserVO{" +
                    "acc='" + acc + '\'' +
                    ", loginTime=" + loginTime +
                    ", logoutTime=" + logoutTime +
                    ", guildId=" + guildId +
                    ", cre_role_time=" + cre_role_time +
                    ", nearInst=" + nearInst +
                    ", point=" + point +
                    ", good=" + good +
                    '}';
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public String getAcc() {
            return acc;
        }

        public void setAcc(String acc) {
            this.acc = acc;
        }

        public long getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(long loginTime) {
            this.loginTime = loginTime;
        }

        public long getLogoutTime() {
            return logoutTime;
        }

        public int getNearInst() {
            return nearInst;
        }

        public void setNearInst(int nearInst) {
            this.nearInst = nearInst;
        }

        public void setLogoutTime(long logoutTime) {
            this.logoutTime = logoutTime;
        }

        public int getGuildId() {
            return guildId;
        }

        public void setGuildId(int guildId) {
            this.guildId = guildId;
        }

        public long getCre_role_time() {
            return cre_role_time;
        }

        public void setCre_role_time(long cre_role_time) {
            this.cre_role_time = cre_role_time;
        }
    }
}
