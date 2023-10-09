package com.example.arena_tickets_purchasing_system;

import com.example.arena_tickets_purchasing_system.Admin.Admin;
import com.example.arena_tickets_purchasing_system.Admin.AdminMatchesWindowController;
import com.example.arena_tickets_purchasing_system.Admin.AdminTicketsController;
import com.example.arena_tickets_purchasing_system.User.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import static com.example.arena_tickets_purchasing_system.Constant.MATCHES_TABLE;

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

    public Connection getDbConnectionForAdminTickets() throws ClassNotFoundException, SQLException {
        String connection_to_database = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbNameForAdminTickets;

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

    public void addNewMatches(AdminMatchesWindowController.Match match) throws SQLException, ClassNotFoundException{
        String insert = "INSERT INTO " + Constant.MATCHES_TABLE + "(" + Constant.MATCH_ID + "," +
                Constant.MATCHES_DATE + "," + Constant.MATCHES_TIME + "," + Constant.OPP_TEAM + "," + Constant.TICKETS_AMOUNT +
        "," + Constant.MATCH_TYPE +  ")" + "VALUES(?,?,?,?,?,?)";


        PreparedStatement prStr = getDbConnectionForMatches().prepareStatement(insert);
        prStr.setInt(1, match.getId());
        prStr.setString(2, match.getDate());
        prStr.setString(3, match.getTime());
        prStr.setString(4, match.getOpponent());
        prStr.setInt(5, match.getAmount());
        prStr.setString(6, match.getType());

        prStr.executeUpdate();

    }

    public void addNewAdminTickets(AdminTicketsController.MatchTickets match_tickets) throws SQLException, ClassNotFoundException{
        String insert = "INSERT INTO " + Constant.ADMIN_TICKETS_TABLE +
                "(id_Match,Tickets_amount,VIP,Sector_A,Sector_B,Sector_C,Sector_D,Sector_E,Sector_F,Sector_G,Sector_H,Sector_I)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";


        PreparedStatement prStr = getDbConnectionForAdminTickets().prepareStatement(insert);
        prStr.setInt(1, match_tickets.getId());
        prStr.setInt(2, match_tickets.getAmount());
        prStr.setInt(3, match_tickets.getVipSector());
        prStr.setInt(4, match_tickets.getSectorA());
        prStr.setInt(5, match_tickets.getSectorB());
        prStr.setInt(6, match_tickets.getSectorC());
        prStr.setInt(7, match_tickets.getSectorD());
        prStr.setInt(8, match_tickets.getSectorE());
        prStr.setInt(9, match_tickets.getSectorF());
        prStr.setInt(10, match_tickets.getSectorG());
        prStr.setInt(11, match_tickets.getSectorH());
        prStr.setInt(12, match_tickets.getSectorI());

        prStr.executeUpdate();

    }
}
