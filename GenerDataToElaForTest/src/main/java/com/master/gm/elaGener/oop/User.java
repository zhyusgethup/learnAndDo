package com.master.gm.elaGener.oop;

public class User {
    private String acc;
    private volatile boolean access;

    public User(String acc) {
        this.acc = acc;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }

    @Override
    public String toString() {
        return "User{" +
                "acc='" + acc + '\'' +
                ", access=" + access +
                '}';
    }
}
