package com.example.arena_tickets_purchasing_system.Admin;

public class Admin {
    private static Admin instance;
    public String admin_login;
    public String admin_password;
    private Admin(String login, String password){
        admin_login = login;
        admin_password = password;
    }
    public static Admin getInstance(String login, String password){
        if(instance == null) {
            instance = new Admin(login, password);
        }
        return instance;
    }


}
