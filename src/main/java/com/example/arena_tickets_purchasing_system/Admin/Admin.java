package com.example.arena_tickets_purchasing_system.Admin;

public class Admin {
    public String admin_login;
    public String admin_password;

    public Admin(String admin_login, String admin_password ){
        this.admin_login = admin_login;
        this.admin_password = admin_password;

    }
    public Admin() {

    }
    public void setAdmin_login(String user_login) {
        this.admin_login = user_login;
    }
    public String getAdmin_login(){
        return admin_login;
    }

    public void setAdmin_password(String admin_password ) {
        this.admin_password  = admin_password ;
    }
    public String getAdmin_password(){
        return admin_password ;
    }

}
