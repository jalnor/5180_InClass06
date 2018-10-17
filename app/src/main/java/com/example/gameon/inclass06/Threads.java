package com.example.gameon.inclass06;

import java.io.Serializable;

public class Threads implements Serializable {

    String ufname;
    String ulname;
    String uid;
    String id;
    String title;
    String createdAt;

    public Threads() {
    }

    public String getUfname() {
        return ufname;
    }

    public void setUfname(String ufname) {
        this.ufname = ufname;
    }

    public String getUlname() {
        return ulname;
    }

    public void setUlname(String ulname) {
        this.ulname = ulname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Threads{" +
                "ufname='" + ufname + '\'' +
                ", ulname='" + ulname + '\'' +
                ", uid='" + uid + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
