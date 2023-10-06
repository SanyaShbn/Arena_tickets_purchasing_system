package com.example.arena_tickets_purchasing_system.User;

public class User {
    public String user_login;
    public String user_password;

    public User(String user_login, String user_password){
        this.user_login = user_login;
        this.user_password = user_password;

    }
    public User() {

    }
    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }
    public String getUser_login(){
        return user_login;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
    public String getUser_password(){
        return user_password;
    }

}
