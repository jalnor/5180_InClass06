package com.example.gameon.inclass06;

import java.io.Serializable;

public class Messages implements Serializable {

    String username;
    String message;
    String created_at;

    public Messages(){ }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
                "username='" + username + '\'' +
                ", message='" + message + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}