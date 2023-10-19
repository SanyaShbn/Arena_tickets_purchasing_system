package com.example.arena_tickets_purchasing_system;

import com.example.arena_tickets_purchasing_system.Admin.*;
import com.example.arena_tickets_purchasing_system.User.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import static com.example.arena_tickets_purchasing_system.Constant.MATCHES_TABLE;

public class DatabaseHandler extends Config{
    Connection dbConnection;

    public Connection getDbConnection(String dbName) throws ClassNotFoundException, SQLException {
        String connection_to_database = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connection_to_database, dbUser, dbPassword);
        return dbConnection;
    }
    public void signUpUsers(User user) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Constant.USERS_TABLE + "(" + Constant.USERS_LOGIN + "," +
                Constant.USERS_PASSWORD + ")" + "VALUES(?,?)";


        PreparedStatement prStr = getDbConnection(dbNameForUsers).prepareStatement(insert);
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
            prStr = getDbConnection(dbNameForUsers).prepareStatement(select);
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


        PreparedStatement prStr = getDbConnection(dbNameForAdmins).prepareStatement(insert);
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
            prStr = getDbConnection(dbNameForAdmins).prepareStatement(select);
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


        PreparedStatement prStr = getDbConnection(dbNameForMatches).prepareStatement(insert);
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


        PreparedStatement prStr = getDbConnection(dbNameForAdminTickets).prepareStatement(insert);
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
    public void addNewPlayers(AdminTeamRosterController.Player team_roster) throws SQLException, ClassNotFoundException{
        String insert = "INSERT INTO " + Constant.PLAYERS_TABLE + "(" + Constant.PLAYER_NAME + "," +
                Constant.ROLE + "," + Constant.JERSEY + "," + Constant.NATION + "," +
                Constant.AGE + "," + Constant.HEIGHT + "," + Constant.WEIGHT + "," + Constant.TEAM
                + "," + Constant.LEAGUE + ")" + "VALUES(?,?,?,?,?,?,?,?,?)";

        PreparedStatement prStr = getDbConnection(dbNameForPlayers).prepareStatement(insert);
        prStr.setString(1, team_roster.getName());
        prStr.setString(2, team_roster.getRole());
        prStr.setInt(3, team_roster.getNumber());
        prStr.setString(4, team_roster.getNationality());
        prStr.setInt(5, team_roster.getAge());
        prStr.setInt(6, team_roster.getHeight());
        prStr.setInt(7, team_roster.getWeight());
        prStr.setInt(8, team_roster.getSeasonsTeam());
        prStr.setInt(9, team_roster.getSeasonsLeague());

        prStr.executeUpdate();

    }

    public void addNews(AdminNewsController.News news) throws SQLException, ClassNotFoundException{
        String insert = "INSERT INTO " + Constant.NEWS_TABLE + "(" + Constant.NEWS_ID + "," +
                Constant.PUBLISHING_DATE + "," + Constant.PUBLISHING_TIME + "," + Constant.CONTESTS +  ")"
                + "VALUES(?,?,?,?)";

        PreparedStatement prStr = getDbConnection(dbNameForNews).prepareStatement(insert);
        prStr.setInt(1, news.getId());
        prStr.setString(2, news.getDate());
        prStr.setString(3, news.getTime());
        prStr.setString(4, news.getContents());

        prStr.executeUpdate();

    }
    public void updateAdminTickets(int amount, String column_name, int id){
        try {
            String select = "SELECT * FROM " + Constant.ADMIN_TICKETS_TABLE + " WHERE id_Match = "
                    + id;
            PreparedStatement prStrSelect = new DatabaseHandler().getDbConnection("tickets").prepareStatement(select);
            ResultSet result = prStrSelect.executeQuery();
            if (result.next()) {
                int updated_amount = result.getInt(2) - amount;
                int updated_column_amount = 0;
                switch (column_name) {
                    case "Sector_VIP":
                        updated_column_amount = result.getInt(3) - amount;
                        column_name = "VIP";
                        break;
                    case "Sector_A":
                        updated_column_amount = result.getInt(4) - amount;
                        break;
                    case "Sector_B":
                        updated_column_amount = result.getInt(5) - amount;
                        break;
                    case "Sector_C":
                        updated_column_amount = result.getInt(6) - amount;
                        break;
                    case "Sector_D":
                        updated_column_amount = result.getInt(7) - amount;
                        break;
                    case "Sector_E":
                        updated_column_amount = result.getInt(8) - amount;
                        break;
                    case "Sector_F":
                        updated_column_amount = result.getInt(9) - amount;
                        break;
                    case "Sector_G":
                        updated_column_amount = result.getInt(10) - amount;
                        break;
                    case "Sector_H":
                        updated_column_amount = result.getInt(11) - amount;
                        break;
                    case "Sector_I":
                        updated_column_amount = result.getInt(12) - amount;
                        break;
                }
                String update = "UPDATE " + Constant.ADMIN_TICKETS_TABLE + " SET Tickets_amount = " +
                        updated_amount + "," + column_name + " = " + updated_column_amount + " WHERE id_Match = " + id;
                PreparedStatement prStrUpdate = null;
                prStrUpdate = new DatabaseHandler().getDbConnection("tickets").prepareStatement(update);
                prStrUpdate.executeUpdate();
                }
            } catch(SQLException e){
                throw new RuntimeException(e);
            } catch(ClassNotFoundException e){
                throw new RuntimeException(e);
        }
    }
}
