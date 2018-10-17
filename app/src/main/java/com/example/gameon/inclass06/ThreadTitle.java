package com.example.gameon.inclass06;

import android.widget.Button;

import java.io.Serializable;

public class ThreadTitle implements Serializable {

    String title;
    String button;


    public ThreadTitle() {

    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ThreadTitle{" +
                "title='" + title + '\'' +
                ", button='" + button + '\'' +
                '}';
    }
}
