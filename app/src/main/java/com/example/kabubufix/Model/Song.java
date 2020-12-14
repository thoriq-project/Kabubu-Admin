package com.example.kabubufix.Model;

public class Song {

  String sid, name, lagu;

  public Song () {}

    public Song(String sid, String name, String lagu) {
        this.sid = sid;
        this.name = name;
        this.lagu = lagu;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLagu() {
        return lagu;
    }

    public void setLagu(String lagu) {
        this.lagu = lagu;
    }
}
