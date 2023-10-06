package com.example.arena_tickets_purchasing_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MatchesWindowController {

    @FXML
    private Button BuyTicketButton;

    @FXML
    private MenuButton MenuButton;

    @FXML
    private TextArea SheduleTextArea;

    @FXML
    void initialize(){
        MenuItem main_menu_item = new MenuItem("Главная");
        MenuItem news_item = new MenuItem("Новости");
        MenuItem team_roster_item = new MenuItem("Команда");
        MenuButton.getItems().addAll(main_menu_item, news_item, team_roster_item);
        main_menu_item.setOnAction(event ->
        {
            MenuButton.getScene().getWindow().hide();
            WindowsOpener main_menu_window = new WindowsOpener("main_menu.fxml");
        });

    }

}
