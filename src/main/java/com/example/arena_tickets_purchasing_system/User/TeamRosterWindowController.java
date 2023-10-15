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

public class TeamRosterWindowController {

    @FXML
    private AnchorPane MainPane;
    @FXML
    private MenuButton MenuButton;

    @FXML
    private MenuItem MainMenuItem;
    @FXML
    private MenuItem MatchesItem;
    @FXML
    private MenuItem TicketsItem;

    AnchorPane main_menu;
    @FXML
    void initialize(){
        FXMLLoader main_menu_loader = new FXMLLoader();
        main_menu_loader.setLocation((getClass().getResource("main_menu.fxml")));
        try {
            main_menu = main_menu_loader.load();
            String select = "SELECT * FROM " + NEWS_TABLE;
            PreparedStatement prStr = null;
            prStr = new DatabaseHandler().getDbConnection("news").prepareStatement(select);
            ResultSet result = prStr.executeQuery();
            double Y = 0;
            while (result.next() && Y < 750) {
                TextArea news_area = new TextArea();
                news_area.setPrefWidth(590);
                news_area.setPrefHeight(30);
                news_area.setLayoutX(478);
                news_area.setLayoutY(500 + Y);
                news_area.setText(result.getString(PUBLISHING_DATE) + " "
                        + result.getString(PUBLISHING_TIME) + " " + result.getString(CONTESTS));
                Y = Y + 50;
                main_menu.getChildren().add(news_area);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    private void backToMainMenu (ActionEvent some_event) {goToNewPane(main_menu);}

    @FXML
    private void viewUserTickets (ActionEvent some_event) {
        MainPane.getScene().getWindow().hide();
        new WindowsOpener("user_tickets.fxml");;
    }

    @FXML
    private void viewMatches (ActionEvent some_event) {
        MainPane.getScene().getWindow().hide();
        new WindowsOpener("matches.fxml");;
    }

}
