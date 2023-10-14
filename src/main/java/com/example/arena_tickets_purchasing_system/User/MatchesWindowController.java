package com.example.arena_tickets_purchasing_system.User;

import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
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

public class MatchesWindowController {

    @FXML
    private AnchorPane MainPane;
    @FXML
    private Button BuyTicketButton;

    @FXML
    private MenuButton MenuButton;

    @FXML
    private TextArea SheduleTextArea;

    AnchorPane main_menu;
    @FXML
    void initialize(){
        MenuItem main_menu_item = new MenuItem("Главная");
        MenuItem news_item = new MenuItem("Новости");
        MenuItem team_roster_item = new MenuItem("Команда");
        MenuButton.getItems().addAll(main_menu_item, news_item, team_roster_item);
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

        main_menu_item.setOnAction(event ->
        {
          goToNewPane(main_menu);
        });

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
}
