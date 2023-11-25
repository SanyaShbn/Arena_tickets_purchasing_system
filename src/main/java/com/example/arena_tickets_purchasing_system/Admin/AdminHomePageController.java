package com.example.arena_tickets_purchasing_system.Admin;

import com.example.arena_tickets_purchasing_system.ArenaTicketsPurchasingSystem;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import com.example.arena_tickets_purchasing_system.animations.ButtonsSkin;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
public class AdminHomePageController {

    public static String name;

    @FXML
    private AnchorPane MainPane;
    @FXML
    private Button Matches;

    @FXML
    private Button Roster;

    @FXML
    private Button Show;

    @FXML
    private Button Tickets;

    @FXML
    private Button exitButton;


    @FXML
    private Button changeAcc;


    AnchorPane new_pane;

    @FXML
    public void initialize() {
        Matches.setSkin(new ButtonsSkin(Matches));
        Roster.setSkin(new ButtonsSkin(Roster));
        Show.setSkin(new ButtonsSkin(Show));
        Tickets.setSkin(new ButtonsSkin(Tickets));
        changeAcc.setSkin(new ButtonsSkin(changeAcc));
        exitButton.setOnMouseEntered(event ->{
            exitButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #00BFFF; -fx-text-fill: #00BFFF");
        });
        exitButton.setOnMouseExited(event ->{
            exitButton.setStyle("-fx-background-color: #00BFFF; -fx-border-color: #00BFFF; -fx-text-fill: #000000");
        });

    }

    @FXML
    private void viewEditedMatches(ActionEvent some_event) {
        setNewPane("admin_matches.fxml");
    }

    @FXML
    private void viewAdminTickets(ActionEvent some_event) {
        setNewPane("tickets.fxml");
    }

    @FXML
    private void teamRoster(ActionEvent some_event) {
        setNewPane("roster.fxml");
    }

    @FXML
    private void newsShow(ActionEvent some_event) {setNewPane("club_news.fxml");}

    private void setNewPane (String file_name) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource(file_name));
        MainPane.getChildren().clear();
        try {
            new_pane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainPane.getChildren().add(new_pane);
    }
    @FXML
    private void backToOpeningWindow(ActionEvent event) {
        setNewPane("Open_window.fxml");
    }

    @FXML
    private void editAdminAccount(ActionEvent event) {
        setNewPane("admin_registration.fxml");
    }
}

