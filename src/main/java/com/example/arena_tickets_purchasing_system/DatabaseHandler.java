package com.example.arena_tickets_purchasing_system;

import com.example.arena_tickets_purchasing_system.Admin.*;
import com.example.arena_tickets_purchasing_system.User.User;
import com.example.arena_tickets_purchasing_system.animations.NotificationShower;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.format.DateTimeParseException;
import java.util.Locale;

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
        String delete = "DELETE FROM " + Constant.ADMINS_TABLE;
        String insert = "INSERT INTO " + Constant.ADMINS_TABLE + "(" + Constant.ADMINS_LOGIN + "," +
                Constant.ADMINS_PASSWORD + ")" + "VALUES(?,?)";

        PreparedStatement prStrDelete = getDbConnection(dbNameForAdmins).prepareStatement(delete);
        PreparedStatement prStrInsert = getDbConnection(dbNameForAdmins).prepareStatement(insert);
        prStrInsert.setString(1, admin.admin_login);
        prStrInsert.setString(2, admin.admin_password);

        prStrDelete.executeUpdate();
        prStrInsert.executeUpdate();
    }

    public ResultSet getAdmin(String login, String password){
        ResultSet result = null;

        String select = "SELECT * FROM " + Constant.ADMINS_TABLE + " WHERE " + Constant.ADMINS_LOGIN + "=? AND " +
                Constant.ADMINS_PASSWORD + "=?";

        PreparedStatement prStr = null;
        try {
            prStr = getDbConnection(dbNameForAdmins).prepareStatement(select);
            prStr.setString(1, login);
            prStr.setString(2, password);

            result = prStr.executeQuery();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void addNewMatches(AdminMatchesWindowController.Match match) throws ClassNotFoundException{
        String insert = "INSERT INTO " + Constant.MATCHES_TABLE + "(" + Constant.MATCHES_DATE + "," + Constant.MATCHES_TIME + "," + Constant.OPP_TEAM + "," + Constant.TICKETS_AMOUNT +
        "," + Constant.MATCH_TYPE +  ")" + "VALUES(?,?,?,?,?)";

        try {
            PreparedStatement prStr = getDbConnection(dbNameForMatches).prepareStatement(insert);
            prStr.setString(1, match.getDate());
            prStr.setString(2, match.getTime());
            prStr.setString(3, match.getOpponent());
            prStr.setInt(4, match.getAmount());
            prStr.setString(5, match.getType());

            prStr.executeUpdate();
            new NotificationShower().showSimpleNotification("Уведомление","Матч успешно добавлен в базу данных");
        }catch (SQLIntegrityConstraintViolationException e){
            new NotificationShower().showWarning("Внимание!","Проверьте корректность ввода данных. Возможно матч с введённым id уже существует");
        }catch(NumberFormatException e){
            new NotificationShower().showWarning("Внимание!","Проверьте корректность ввода данных");
        }catch(SQLException e){
            new NotificationShower().showWarning("Внимание!","Проверьте корректность ввода данных");
        }catch(DateTimeParseException e){
            new NotificationShower().showWarning("Внимание!","Проверьте корректность ввода данных");
        }

    }

    public void addNewAdminTickets(AdminTicketsController.MatchTickets match_tickets) throws ClassNotFoundException{
        String insert = "INSERT INTO " + Constant.ADMIN_TICKETS_TABLE +
                "(id_Match,Tickets_amount,VIP,Sector_A,Sector_B,Sector_C,Sector_D,Sector_E,Sector_F,Sector_G,Sector_H,Sector_I)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

       try{
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
        new NotificationShower().showSimpleNotification("Уведомление","Информация о билетах успешно добавлена в базу данных");
        }catch (SQLIntegrityConstraintViolationException e){
           new NotificationShower().showWarning("Внимание!","Проверьте корректность ввода данных");
        }catch(SQLException e){
           new NotificationShower().showWarning("Внимание!","Проверьте корректность ввода данных");
        }
    }
    public void addNewPlayers(AdminTeamRosterController.Player team_roster) throws ClassNotFoundException{
        String insert = "INSERT INTO " + Constant.PLAYERS_TABLE + "(" + Constant.PLAYER_NAME + "," +
                Constant.ROLE + "," + Constant.JERSEY + "," + Constant.NATION + "," +
                Constant.AGE + "," + Constant.HEIGHT + "," + Constant.WEIGHT + "," + Constant.TEAM
                + "," + Constant.LEAGUE + ")" + "VALUES(?,?,?,?,?,?,?,?,?)";

        try {
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
        new NotificationShower().showSimpleNotification("Уведомление","Игрок успешно добавлен в базу данных");
        }catch (SQLIntegrityConstraintViolationException e){
            new NotificationShower().showWarning("Внимание!","Проверьте корректность ввода данных. Возможно введённый номер джерси уже занят другим игроком");
        } catch(SQLException e){
            new NotificationShower().showWarning("Внимание!","Проверьте корректность ввода данных. Возможно введённый номер джерси уже занят другим игроком");
        }
    }

    public void addNews(AdminNewsController.News news) throws ClassNotFoundException{
        String insert = "INSERT INTO " + Constant.NEWS_TABLE + "(" + Constant.PUBLISHING_DATE + "," + Constant.PUBLISHING_TIME + "," + Constant.CONTESTS +  ")"
                + "VALUES(?,?,?)";

        try{
        PreparedStatement prStr = getDbConnection(dbNameForNews).prepareStatement(insert);
        prStr.setString(1, news.getDate());
        prStr.setString(2, news.getTime());
        prStr.setString(3, news.getContents());

        prStr.executeUpdate();
            new NotificationShower().showSimpleNotification("Уведомление","Новостной пост добавлен в базу данных");
        }catch (SQLIntegrityConstraintViolationException e){
            new NotificationShower().showWarning("Внимание!","Проверьте корректность ввода данных. Возможно пост с введённым id уже существует");
        } catch(SQLException e){
            new NotificationShower().showWarning("Внимание!","Проверьте корректность ввода данных. Возможно пост с введённым id уже существует");
        }

    }
    public void updateAdminTickets(int amount, String column_name, int id){
        try {
            String select = "SELECT * FROM " + Constant.ADMIN_TICKETS_TABLE + " WHERE id_Match = "
                    + id;
            PreparedStatement prStrSelect = new DatabaseHandler().getDbConnection(dbNameForAdminTickets).prepareStatement(select);
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
    public void updateMatches(int amount, int id){
        try {
            String select = "SELECT * FROM " + Constant.MATCHES_TABLE + " WHERE idMatches = "
                    + id;
            PreparedStatement prStrSelect = new DatabaseHandler().getDbConnection(dbNameForMatches).prepareStatement(select);
            ResultSet result = prStrSelect.executeQuery();
            if (result.next()) {
                int updated_amount = result.getInt(5) - amount;
                String update = "UPDATE " + Constant.MATCHES_TABLE + " SET Tickets_amount = " +
                        updated_amount + " WHERE idMatches = " + id;
                PreparedStatement prStrUpdate = null;
                prStrUpdate = new DatabaseHandler().getDbConnection(dbNameForMatches).prepareStatement(update);
                prStrUpdate.executeUpdate();
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        } catch(ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
