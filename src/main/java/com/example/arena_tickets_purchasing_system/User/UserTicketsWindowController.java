package com.example.arena_tickets_purchasing_system.User;

import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.arena_tickets_purchasing_system.Constant.*;
import static com.example.arena_tickets_purchasing_system.Constant.CONTESTS;

public class UserTicketsWindowController {
    @FXML
    private MenuItem MainMenuItem;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private MenuItem MatchesItem;

    @FXML
    private MenuButton MenuButton;

    @FXML
    private MenuItem TeamItem;
    private User User = new User().readUserFromFile();
    @FXML
    void initialize() {
        String select = "SELECT * FROM " + USERS_TICKETS_TABLE + " WHERE LoginUsers =?";
        PreparedStatement prStr = null;
        try {
            prStr = new DatabaseHandler().getDbConnection("users_tickets").prepareStatement(select);
            prStr.setString(1, User.getUser_login());
            ResultSet result = prStr.executeQuery();
            double Y = 0;
            while (result.next()) {
                String matches_select = "SELECT * FROM " + MATCHES_TABLE + " WHERE idMatches =?";
                PreparedStatement prStrMatch = null;
                prStrMatch = new DatabaseHandler().getDbConnection("matches").prepareStatement(matches_select);
                prStrMatch.setString(1, String.valueOf(result.getInt("id_Match")));
                ResultSet result_matches = prStrMatch.executeQuery();
                while(result_matches.next()){
                    String sectors_info = "";
                    if(result.getInt("Sector_VIP") != 0){
                        sectors_info += "VIP_Сектор - " + result.getInt("Sector_VIP") + " ";
                    }
                    if(result.getInt("Sector_A") != 0){
                        sectors_info += "Сектор A - " + result.getInt("Sector_A") + " ";
                    }
                    if(result.getInt("Sector_B") != 0){
                        sectors_info += "Сектор B - " + result.getInt("Sector_B") + " ";
                    }
                    if(result.getInt("Sector_C") != 0){
                        sectors_info += "Сектор C - " + result.getInt("Sector_C") + " ";
                    }
                    if(result.getInt("Sector_D") != 0){
                        sectors_info += "Сектор D - " + result.getInt("Sector_D") + " ";
                    }
                    if(result.getInt("Sector_E") != 0){
                        sectors_info += "Сектор E - " + result.getInt("Sector_E") + " ";
                    }
                    if(result.getInt("Sector_F") != 0){
                        sectors_info += "Сектор F - " + result.getInt("Sector_F") + " ";
                    }
                    if(result.getInt("Sector_G") != 0){
                        sectors_info += "Сектор G - " + result.getInt("Sector_G") + " ";
                    }
                    if(result.getInt("Sector_H") != 0){
                        sectors_info += "Сектор H - " + result.getInt("Sector_H") + " ";
                    }
                    if(result.getInt("Sector_I") != 0){
                        sectors_info += "Сектор I - " + result.getInt("Sector_I") + " ";
                    }
                    TextArea match_info = new TextArea();
                    match_info.setPrefWidth(590);
                    match_info.setPrefHeight(30);
                    match_info.setLayoutX(478);
                    match_info.setLayoutY(500 + Y);
                    match_info.setText("Информация о билете:" + result_matches.getInt(MATCH_ID) + " "
                            + result_matches.getString(MATCHES_DATE) + " " + result_matches.getString(MATCHES_TIME) + " " + result_matches.getString(OPP_TEAM)
                    + " " + result_matches.getInt(TICKETS_AMOUNT) + " " + result_matches.getString(MATCH_TYPE) + "\n" + sectors_info + "Всего - " + result.getInt("Tickets_amount"));
                    Y = Y + 50;
                    MainPane.getChildren().add(match_info);
                }
            }

        } catch (SQLException e) {
            new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void goToNewPane(Node node) {
        MainPane.getChildren().clear();
        MainPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(2500));
        ft.setNode(node);
        ft.setFromValue(2);
        ft.setToValue(2);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
    @FXML
    private void backToMainMenu (ActionEvent some_event) {
        MainPane.getScene().getWindow().hide();
        new WindowsOpener("main_menu.fxml");
    }

    @FXML
    private void viewMatches (ActionEvent some_event) {
        MainPane.getScene().getWindow().hide();
        new WindowsOpener("matches.fxml");
    }

    @FXML
    private void viewTeamRoster (ActionEvent some_event) {
        MainPane.getScene().getWindow().hide();
        new WindowsOpener("user_roster.fxml");
    }

}
