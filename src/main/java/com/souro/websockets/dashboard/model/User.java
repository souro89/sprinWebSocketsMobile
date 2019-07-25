package com.souro.websockets.dashboard.model;

import org.springframework.stereotype.Repository;


public class User {

    private int id;

    private String name;

    private String segment;

    private String error;

    public User(){

    }

    public User(int id, String name, String segment, String error) {
        this.id = id;
        this.name = name;
        this.segment = segment;
        this.error = error;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", segment='" + segment + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
