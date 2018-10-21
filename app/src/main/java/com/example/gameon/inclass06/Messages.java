/*
Assignment HW#6
Page Messages.java
Authors Jarrod Norris, Abinandaraj Rajendran, Carrie Mao
 */
package com.example.gameon.inclass06;

import java.io.Serializable;

public class Messages implements Serializable {

    String user_fname;
    String user_lname;
    String user_id;
    String id;
    String message;
    String created_at;



    public Messages(){ }

    public Messages(String user_fname, String user_lname, String user_id, String id, String message, String created_at) {
        this.user_fname = user_fname;
        this.user_lname = user_lname;
        this.user_id = user_id;
        this.id = id;
        this.message = message;
        this.created_at = created_at;
    }

    public String getUser_fname() {
        return user_fname;
    }

    public void setUser_fname(String user_fname) {
        this.user_fname = user_fname;
    }

    public String getUser_lname() {
        return user_lname;
    }

    public void setUser_lname(String user_lname) {
        this.user_lname = user_lname;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }


    @Override
    public String toString() {
        return "Messages{" +
                "user_fname='" + user_fname + '\'' +
                ", user_lname='" + user_lname + '\'' +
                ", user_id='" + user_id + '\'' +
                ", id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}

/*
 "user_fname": "Bob",
            "user_lname": "Smith",
            "user_id": "1",
            "id": "218",
            "message": "hello",
            "created_at": "2018-10-20 23:40:33"
 */