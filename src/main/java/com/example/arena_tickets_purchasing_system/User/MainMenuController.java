package com.example.arena_tickets_purchasing_system.User;

import com.example.arena_tickets_purchasing_system.Admin.AdminNewsController;
import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.arena_tickets_purchasing_system.Constant.*;

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
    void initialize(){

        ScrollPane.setPannable(true);
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
