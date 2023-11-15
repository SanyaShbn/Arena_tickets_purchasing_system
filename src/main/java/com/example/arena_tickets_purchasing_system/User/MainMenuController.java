package com.example.arena_tickets_purchasing_system.User;

import com.example.arena_tickets_purchasing_system.ArenaTicketsPurchasingSystem;
import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ScrollPane;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.arena_tickets_purchasing_system.Constant.*;
import static com.example.arena_tickets_purchasing_system.Constant.CONTESTS;


public class MainMenuController {

    @FXML
    private AnchorPane MainPane;
    @FXML
    private MenuButton MenuButton;
    @FXML
    private MenuItem MenuItem;
    @FXML
    private ScrollPane ScrollPane;
    @FXML
    private MenuItem MatchesItem;
    @FXML
    private MenuItem TeamItem;
    @FXML
    private MenuItem TicketsItem;

    AnchorPane new_pane;
    @FXML
    void initialize() {
        String select = "SELECT * FROM " + NEWS_TABLE;
        PreparedStatement prStr = null;
        try {

            prStr = new DatabaseHandler().getDbConnection("news").prepareStatement(select);
            ResultSet result = prStr.executeQuery();
            double Y = 0;
            while (result.next()) {
                Circle date_circle = new Circle(30, Color.AZURE);
                Text date_time = new Text(result.getString(PUBLISHING_DATE) + "\n    " + result.getString(PUBLISHING_TIME));
                date_time.setStyle("-fx-font-size: 10;");
                date_circle.setLayoutX(335);date_circle.setLayoutY(480 + Y);
                date_time.setLayoutX(310);date_time.setLayoutY(484 + Y);
                AnchorPane news_area = new AnchorPane();
                news_area.setPrefWidth(590);
                news_area.setPrefHeight(60);
                news_area.setLayoutX(300);
                news_area.setLayoutY(450 + Y);
                Text news = new Text(result.getString(CONTESTS));
                news.setFont(Font.font("Bodoni MT Black"));
                news.setStyle("-fx-font-size: 16;");news_area.setStyle("-fx-background-color: #FFFFFF;");
                news.setLayoutX(400);
                news.setLayoutY(480 + Y);
                Y = Y + 70;
                MainPane.getChildren().addAll(news_area, date_circle, date_time, news);
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
    private void viewMatches (ActionEvent some_event) {
        FXMLLoader matches_loader = new FXMLLoader();
        matches_loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("matches.fxml"));
        MainPane.getChildren().clear();
        try {
            new_pane = matches_loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainPane.getChildren().add(new_pane);
    }

    @FXML
    private void viewUserTickets (ActionEvent some_event) {
        FXMLLoader user_tickets_loader = new FXMLLoader();
        user_tickets_loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("user_tickets.fxml"));
        MainPane.getChildren().clear();
        try {
            new_pane = user_tickets_loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainPane.getChildren().add(new_pane);
    }

    @FXML
    private void viewTeamRoster (ActionEvent some_event) {
        FXMLLoader roster_loader = new FXMLLoader();
        roster_loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("user_roster.fxml"));
        MainPane.getChildren().clear();
        try {
            new_pane = roster_loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainPane.getChildren().add(new_pane);
    }
     @FXML
    private void backToOpeningWindow (ActionEvent event) {
        FXMLLoader open_window_loader = new FXMLLoader();
        open_window_loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("Open_window.fxml"));
        MainPane.getChildren().clear();
        try {
            new_pane = open_window_loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainPane.getChildren().add(new_pane);
    }
}
