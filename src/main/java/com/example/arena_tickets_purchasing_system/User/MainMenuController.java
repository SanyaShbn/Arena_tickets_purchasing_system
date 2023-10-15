package com.example.arena_tickets_purchasing_system.User;

import com.example.arena_tickets_purchasing_system.WindowsOpener;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ScrollPane;

import javafx.util.Duration;



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
        MainPane.getScene().getWindow().hide();
        new WindowsOpener("matches.fxml");;
    }

    @FXML
    private void viewUserTickets (ActionEvent some_event) {
        MainPane.getScene().getWindow().hide();
        new WindowsOpener("user_tickets.fxml");;
    }

    @FXML
    private void viewTeamRoster (ActionEvent some_event) {
        MainPane.getScene().getWindow().hide();
        new WindowsOpener("user_roster.fxml");;
    }
}
