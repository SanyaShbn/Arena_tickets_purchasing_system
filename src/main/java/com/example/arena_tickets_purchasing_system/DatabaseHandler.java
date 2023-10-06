package com.example.arena_tickets_purchasing_system;

import com.example.arena_tickets_purchasing_system.Admin.Admin;
import com.example.arena_tickets_purchasing_system.User.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DatabaseHandler extends Config{
    Connection dbConnection;

    public Connection getDbConnectionForUsers() throws ClassNotFoundException, SQLException {
        String connection_to_database = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbNameForUsers;

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connection_to_database, dbUser, dbPassword);
        return dbConnection;
    }
    public Connection getDbConnectionForAdmins() throws ClassNotFoundException, SQLException {
        String connection_to_database = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbNameForAdmins;

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connection_to_database, dbUser, dbPassword);
        return dbConnection;
    }
    public Connection getDbConnectionForMatches() throws ClassNotFoundException, SQLException {
        String connection_to_database = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbNameForMatches;

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connection_to_database, dbUser, dbPassword);
        return dbConnection;
    }

    public void signUpUsers(User user) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Constant.USERS_TABLE + "(" + Constant.USERS_LOGIN + "," +
                Constant.USERS_PASSWORD + ")" + "VALUES(?,?)";


        PreparedStatement prStr = getDbConnectionForUsers().prepareStatement(insert);
        prStr.setString(1, user.getUser_login());
        prStr.setString(2, user.getUser_password());

        prStr.executeUpdate();
    }

    public ResultSet getUser(User user){
        ResultSet result = null;

        String select = "SELECT * FROM " + Constant.USERS_TABLE + " WHERE " + Constant.USERS_LOGIN + "=? AND " +
                Constant.USERS_PASSWORD + "=?";

        PreparedStatement prStr = null;
        try {
            prStr = getDbConnectionForUsers().prepareStatement(select);
            prStr.setString(1, user.getUser_login());
            prStr.setString(2, user.getUser_password());

            result = prStr.executeQuery();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
       }catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
    }
        return result;
    }
    public void signUpAdmins(Admin admin) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Constant.ADMINS_TABLE + "(" + Constant.ADMINS_LOGIN + "," +
                Constant.ADMINS_PASSWORD + ")" + "VALUES(?,?)";


        PreparedStatement prStr = getDbConnectionForAdmins().prepareStatement(insert);
        prStr.setString(1, admin.getAdmin_login());
        prStr.setString(2, admin.getAdmin_password());

        prStr.executeUpdate();
    }

    public ResultSet getAdmin(Admin admin){
        ResultSet result = null;

        String select = "SELECT * FROM " + Constant.ADMINS_TABLE + " WHERE " + Constant.ADMINS_LOGIN + "=? AND " +
                Constant.ADMINS_PASSWORD + "=?";

        PreparedStatement prStr = null;
        try {
            prStr = getDbConnectionForAdmins().prepareStatement(select);
            prStr.setString(1, admin.getAdmin_login());
            prStr.setString(2, admin.getAdmin_password());

            result = prStr.executeQuery();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
