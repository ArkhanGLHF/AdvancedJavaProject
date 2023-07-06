package com.example.projetv0;

public class Session {
    private String name = "";
    private String password = "";
    private String role = "Guest";

    public void disconnect(){
        name = null;
        password = null;
        role = null;
    }
}
