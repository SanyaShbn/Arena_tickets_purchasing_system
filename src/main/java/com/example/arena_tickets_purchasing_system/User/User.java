package com.example.arena_tickets_purchasing_system.User;

import java.io.*;
import java.util.ArrayList;

public class User implements Serializable{
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

    public void writeUserIntoFile(User user) {
        try {
            FileOutputStream fos = new FileOutputStream("Users.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
            oos.close();
            fos.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public User readUserFromFile() {
        User user;
        try {
            FileInputStream fis = new FileInputStream("Users.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            user = (User) ois.readObject();
            ois.close();
        } catch (IOException e) {
            throw new RuntimeException();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
