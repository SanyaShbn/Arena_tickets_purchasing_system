package com.example.arena_tickets_purchasing_system.User;

import com.example.arena_tickets_purchasing_system.WindowsOpener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    @FXML
    private MenuButton MenuButton;

    @FXML
    private MenuItem MenuItem;
    @FXML
    void initialize(){
        MenuItem news_item = new MenuItem("Новости");
        MenuItem tickets_item = new MenuItem("Матчи");
        MenuItem team_roster_item = new MenuItem("Команда");
        MenuButton.getItems().addAll(news_item, tickets_item, team_roster_item);

        tickets_item.setOnAction(event ->
        {
            MenuButton.getScene().getWindow().hide();
            WindowsOpener matches_window = new WindowsOpener("matches.fxml");
        });

    }

}
