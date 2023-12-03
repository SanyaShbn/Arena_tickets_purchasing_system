package com.example.arena_tickets_purchasing_system.User;

import com.example.arena_tickets_purchasing_system.Admin.AdminMatchesWindowController;
import com.example.arena_tickets_purchasing_system.Admin.AdminTicketsController;
import com.example.arena_tickets_purchasing_system.Config;
import com.example.arena_tickets_purchasing_system.Constant;
import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.animations.NotificationShower;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

import static com.example.arena_tickets_purchasing_system.Constant.ADMIN_TICKETS_TABLE;
import static com.example.arena_tickets_purchasing_system.Constant.MATCHES_TABLE;

public class BookTicketsController {
    @FXML
    private Button Exit;

    @FXML
    private AnchorPane MainPane;
    @FXML
    private Label AmountLabel;

    @FXML
    private Label DateLabel;

    @FXML
    private MenuButton SectorsMenuButton;

    @FXML
    private Label TimeLabel;

    @FXML
    private Label TypeLabel;

    @FXML
    private TextField allTickets;

    @FXML
    private TextField amountToBuy;

    @FXML
    private Label OppLabel;

    @FXML
    private Button submitChanges;
    @FXML
    private MenuItem SectorA;

    @FXML
    private MenuItem SectorB;

    @FXML
    private MenuItem SectorC;

    @FXML
    private MenuItem SectorD;

    @FXML
    private MenuItem SectorE;

    @FXML
    private MenuItem SectorF;

    @FXML
    private MenuItem SectorG;

    @FXML
    private MenuItem SectorH;

    @FXML
    private MenuItem SectorI;

    @FXML
    private MenuItem SectorVIP;
    private AdminMatchesWindowController.Match Match;
    private User User = new User().readUserFromFile();
    AdminTicketsController.MatchTickets Ticket = new AdminTicketsController.MatchTickets(0, 0, 0, 0,
            0, 0,0 , 0, 0, 0, 0,0);
    @FXML
    void initialize() {
        submitChanges.setDefaultButton(true);
        submitChanges.setOnMouseEntered(event ->{
            submitChanges.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #00BFFF; -fx-text-fill: #00BFFF");
        });
        submitChanges.setOnMouseExited(event ->{
            submitChanges.setStyle("-fx-background-color: #00BFFF; -fx-border-color: #00BFFF; -fx-text-fill: #000000");
        });
        Exit.setOnMouseEntered(event ->{
            Exit.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #00BFFF; -fx-text-fill: #00BFFF");
        });
        Exit.setOnMouseExited(event ->{
            Exit.setStyle("-fx-background-color: #00BFFF; -fx-border-color: #00BFFF; -fx-text-fill: #000000");
        });
        OppLabel.setText(Match.getOpponent());
        TypeLabel.setText(Match.getType());
        TimeLabel.setText(Match.getTime());
        DateLabel.setText(Match.getDate());
        AmountLabel.setText(String.valueOf(Match.getAmount()));
        String select = "SELECT * FROM " + ADMIN_TICKETS_TABLE + " WHERE id_Match = " + Match.getId();
        PreparedStatement prStr = null;
        try {
            prStr = new DatabaseHandler().getDbConnection("tickets").prepareStatement(select);
            ResultSet result = prStr.executeQuery();
            if(result.next()) {
                Ticket.setId(result.getInt("id_Match"));
                Ticket.setAmount(result.getInt("Tickets_amount"));
                Ticket.setVipSector(result.getInt("VIP"));
                Ticket.setSectorA(result.getInt("Sector_A"));
                Ticket.setSectorB(result.getInt("Sector_B"));
                Ticket.setSectorC(result.getInt("Sector_C"));
                Ticket.setSectorD(result.getInt("Sector_D"));
                Ticket.setSectorE(result.getInt("Sector_E"));
                Ticket.setSectorF(result.getInt("Sector_F"));
                Ticket.setSectorG(result.getInt("Sector_G"));
                Ticket.setSectorH(result.getInt("Sector_H"));
                Ticket.setSectorI(result.getInt("Sector_I"));

            }
            else{
                new NotificationShower().showSimpleNotification("Уведомление!", "Билетов на данный матч нет в продаже");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void setVIPSector (ActionEvent some_event) {
        SectorsMenuButton.setText(SectorVIP.getText());
        String select = "SELECT * FROM " + ADMIN_TICKETS_TABLE + " WHERE id_Match = " + Match.getId();
        PreparedStatement prStr = null;
        try {
            prStr = new DatabaseHandler().getDbConnection("tickets").prepareStatement(select);
            ResultSet result = prStr.executeQuery();
            if (result.next()) {
                Ticket.setVipSector(result.getInt("VIP"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        allTickets.setText(String.valueOf(Ticket.getVipSector()));
        allTickets.setEditable(false);
    }
    @FXML
    private void setSectorA (ActionEvent some_event) {
        SectorsMenuButton.setText(SectorA.getText());
        String select = "SELECT * FROM " + ADMIN_TICKETS_TABLE + " WHERE id_Match = " + Match.getId();
        PreparedStatement prStr = null;
        try {
            prStr = new DatabaseHandler().getDbConnection("tickets").prepareStatement(select);
            ResultSet result = prStr.executeQuery();
            if (result.next()) {
                Ticket.setSectorA(result.getInt("Sector_A"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        allTickets.setText(String.valueOf(Ticket.getSectorA()));
        allTickets.setEditable(false);
    }
    @FXML
    private void setSectorB (ActionEvent some_event) {
        SectorsMenuButton.setText(SectorB.getText());
        String select = "SELECT * FROM " + ADMIN_TICKETS_TABLE + " WHERE id_Match = " + Match.getId();
        PreparedStatement prStr = null;
        try {
            prStr = new DatabaseHandler().getDbConnection("tickets").prepareStatement(select);
            ResultSet result = prStr.executeQuery();
            if (result.next()) {
                Ticket.setSectorB(result.getInt("Sector_B"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        allTickets.setText(String.valueOf(Ticket.getSectorB()));
        allTickets.setEditable(false);
    }
    @FXML
    private void setSectorC (ActionEvent some_event) {
        SectorsMenuButton.setText(SectorC.getText());
        String select = "SELECT * FROM " + ADMIN_TICKETS_TABLE + " WHERE id_Match = " + Match.getId();
        PreparedStatement prStr = null;
        try {
            prStr = new DatabaseHandler().getDbConnection("tickets").prepareStatement(select);
            ResultSet result = prStr.executeQuery();
            if (result.next()) {
                Ticket.setSectorC(result.getInt("Sector_C"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        allTickets.setText(String.valueOf(Ticket.getSectorC()));
        allTickets.setEditable(false);
    }
    @FXML
    private void setSectorD (ActionEvent some_event) {
        SectorsMenuButton.setText(SectorD.getText());
        String select = "SELECT * FROM " + ADMIN_TICKETS_TABLE + " WHERE id_Match = " + Match.getId();
        PreparedStatement prStr = null;
        try {
            prStr = new DatabaseHandler().getDbConnection("tickets").prepareStatement(select);
            ResultSet result = prStr.executeQuery();
            if (result.next()) {
                Ticket.setSectorD(result.getInt("Sector_D"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        allTickets.setText(String.valueOf(Ticket.getSectorD()));
        allTickets.setEditable(false);
    }
    @FXML
    private void setSectorE (ActionEvent some_event) {
        SectorsMenuButton.setText(SectorE.getText());
        String select = "SELECT * FROM " + ADMIN_TICKETS_TABLE + " WHERE id_Match = " + Match.getId();
        PreparedStatement prStr = null;
        try {
            prStr = new DatabaseHandler().getDbConnection("tickets").prepareStatement(select);
            ResultSet result = prStr.executeQuery();
            if (result.next()) {
                Ticket.setSectorE(result.getInt("Sector_E"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        allTickets.setText(String.valueOf(Ticket.getSectorE()));
        allTickets.setEditable(false);
    }
    @FXML
    private void setSectorF (ActionEvent some_event) {
        SectorsMenuButton.setText(SectorF.getText());
        String select = "SELECT * FROM " + ADMIN_TICKETS_TABLE + " WHERE id_Match = " + Match.getId();
        PreparedStatement prStr = null;
        try {
            prStr = new DatabaseHandler().getDbConnection("tickets").prepareStatement(select);
            ResultSet result = prStr.executeQuery();
            if (result.next()) {
                Ticket.setSectorF(result.getInt("Sector_F"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        allTickets.setText(String.valueOf(Ticket.getSectorF()));
        allTickets.setEditable(false);
    }
    @FXML
    private void setSectorG (ActionEvent some_event) {
        SectorsMenuButton.setText(SectorG.getText());
        String select = "SELECT * FROM " + ADMIN_TICKETS_TABLE + " WHERE id_Match = " + Match.getId();
        PreparedStatement prStr = null;
        try {
            prStr = new DatabaseHandler().getDbConnection("tickets").prepareStatement(select);
            ResultSet result = prStr.executeQuery();
            if (result.next()) {
                Ticket.setSectorG(result.getInt("Sector_G"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        allTickets.setText(String.valueOf(Ticket.getSectorG()));
        allTickets.setEditable(false);
    }
    @FXML
    private void setSectorH (ActionEvent some_event) {
        SectorsMenuButton.setText(SectorH.getText());
        String select = "SELECT * FROM " + ADMIN_TICKETS_TABLE + " WHERE id_Match = " + Match.getId();
        PreparedStatement prStr = null;
        try {
            prStr = new DatabaseHandler().getDbConnection("tickets").prepareStatement(select);
            ResultSet result = prStr.executeQuery();
            if (result.next()) {
                Ticket.setSectorH(result.getInt("Sector_H"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        allTickets.setText(String.valueOf(Ticket.getSectorH()));
        allTickets.setEditable(false);
    }
    @FXML
    private void setSectorI (ActionEvent some_event) {
        SectorsMenuButton.setText(SectorI.getText());
        String select = "SELECT * FROM " + ADMIN_TICKETS_TABLE + " WHERE id_Match = " + Match.getId();
        PreparedStatement prStr = null;
        try {
            prStr = new DatabaseHandler().getDbConnection("tickets").prepareStatement(select);
            ResultSet result = prStr.executeQuery();
            if (result.next()) {
                Ticket.setSectorI(result.getInt("Sector_I"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        allTickets.setText(String.valueOf(Ticket.getSectorI()));
        allTickets.setEditable(false);
    }
    @FXML
    private void submitChanges (ActionEvent some_event) {
        try {
            boolean flag = false;
            String users_tickets = amountToBuy.getText();
            String column_name = "Sector_" + SectorsMenuButton.getText();
            if (Integer.parseInt(users_tickets) > Integer.parseInt(allTickets.getText()) || users_tickets.equals("0")) {
                new NotificationShower().showSimpleError("Внимание!", "Вы ввели недопустимое количество билетов для покупки");
                amountToBuy.clear();
            }
            else {
                    String select = "SELECT * FROM " + Constant.USERS_TICKETS_TABLE + " WHERE LoginUsers =?";
                    PreparedStatement prStrSelect = new DatabaseHandler().getDbConnection("users_tickets").prepareStatement(select);
                    prStrSelect.setString(1, User.getUser_login());
                    ResultSet result = prStrSelect.executeQuery();
                    while(result.next()) {
                        if (result.getInt(3) == Ticket.getId()) {
                            int bought_tickets = result.getInt("Tickets_amount") + Integer.parseInt(users_tickets);
                            int sector_bought_tickets = result.getInt(column_name) + Integer.parseInt(amountToBuy.getText());
                            String insert = "UPDATE " + Constant.USERS_TICKETS_TABLE + " SET Tickets_amount = " + bought_tickets
                                    + "," + column_name + " = " + sector_bought_tickets + " WHERE idUsersTickets =?";
                            PreparedStatement prStrUpdate = null;
                            try {
                                prStrUpdate = new DatabaseHandler().getDbConnection("users_tickets").prepareStatement(insert);
                                prStrUpdate.setString(1, String.valueOf(result.getInt(1)));
                                prStrUpdate.executeUpdate();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            flag = true;
                            break;
                        }
                     }
                    if(flag != true)
                        {
                            int bought_tickets = Integer.parseInt(users_tickets);
                            String insert = "INSERT INTO " + Constant.USERS_TICKETS_TABLE +
                                    "(LoginUsers,id_Match,Tickets_amount," + column_name + ")"
                                    + "VALUES(?,?,?,?)";

                            PreparedStatement prStrInsert = null;
                            try {
                                prStrInsert = new DatabaseHandler().getDbConnection("users_tickets").prepareStatement(insert);
                                prStrInsert.setString(1, User.getUser_login());
                                prStrInsert.setInt(2, Ticket.getId());
                                prStrInsert.setInt(3, bought_tickets);
                                prStrInsert.setInt(4, Integer.parseInt(amountToBuy.getText()));
                                prStrInsert.executeUpdate();
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            } catch (ClassNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                new DatabaseHandler().updateAdminTickets(Integer.parseInt(amountToBuy.getText()), column_name, Ticket.getId());
                new DatabaseHandler().updateMatches(Integer.parseInt(amountToBuy.getText()), Ticket.getId());
                AmountLabel.setText(String.valueOf(Integer.parseInt(AmountLabel.getText()) - Integer.parseInt(amountToBuy.getText())));
                new NotificationShower().showSimpleNotification("Уведомление", "Покупка успешно осуществлена");
                amountToBuy.clear();
                SectorsMenuButton.setText("Сектор");
                allTickets.clear();
            }
        } catch (NumberFormatException e) {
        new NotificationShower().showSimpleError("Внимание!", "Вы ввели недопустимое количество билетов для покупки");
            amountToBuy.clear();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void closeWindow (ActionEvent some_event) {
        MainPane.getScene().getWindow().hide();
    }
    public void setMatch(AdminMatchesWindowController.Match match){
        Match = match;
    }
    public AdminMatchesWindowController.Match getMatch(){
        return Match;
    }
    public void setUser(User user){
        User = user;
    }
    public User getUser(){
        return User;
    }
}
