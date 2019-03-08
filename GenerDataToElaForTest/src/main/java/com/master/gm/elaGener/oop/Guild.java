package com.master.gm.elaGener.oop;

import java.util.HashSet;
import java.util.Set;

public class Guild {
    private int id;
    private String creater;
    private Set<String> members;

    public Guild(int id, String creater) {
        this.id = id;
        this.creater = creater;
        members = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Set<String> getMembers() {
        return members;
    }

    public void setMembers(Set<String> members) {
        this.members = members;
    }
}
