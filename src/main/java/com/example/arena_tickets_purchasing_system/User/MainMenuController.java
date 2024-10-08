package com.example.arena_tickets_purchasing_system.User;

import com.example.arena_tickets_purchasing_system.Admin.AdminNewsController;
import com.example.arena_tickets_purchasing_system.Admin.AdminTeamRosterController;
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
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    @FXML
    private MenuItem ExitItem;

    AnchorPane new_pane;
    @FXML
    void initialize() {
        String select = "SELECT * FROM " + NEWS_TABLE;
        PreparedStatement prStr = null;
        try {
            prStr = new DatabaseHandler().getDbConnection("news").prepareStatement(select);
            ResultSet result = prStr.executeQuery();
            double Y = 450;
            double X = 375;
            while (result.next()) {
                if(Y < 850) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate date = LocalDate.parse(result.getString(PUBLISHING_DATE), formatter);
                    if(date.isEqual(LocalDate.now()) || date.isBefore(LocalDate.now()) ) {
                        AnchorPane news_post = (AnchorPane) setNewsPost(new AdminNewsController.News(result.getInt(NEWS_ID), result.getString(PUBLISHING_DATE),
                                result.getString(PUBLISHING_TIME), result.getString(CONTESTS)));
                        news_post.setLayoutX(X);
                        news_post.setLayoutY(Y);
                        Y = Y + 70;
                        MainPane.getChildren().add(news_post);
                    }
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
    public Node setNewsPost (AdminNewsController.News news){
        AnchorPane news_post;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("news_post.fxml"));
        NewsPostController controller = new NewsPostController();
        controller.setNewsPost(news);
        loader.setController(controller);
        try {
            news_post = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return news_post;
    }
}
