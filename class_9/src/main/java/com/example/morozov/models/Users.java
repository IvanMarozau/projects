package com.example.morozov.models;

import java.io.Serializable;

public class Users implements Serializable {
    private String login;


    public Users(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
