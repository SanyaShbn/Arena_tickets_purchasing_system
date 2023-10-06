package com.example.arena_tickets_purchasing_system.Admin;

import com.example.arena_tickets_purchasing_system.WindowsOpener;
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
import java.net.URL;
import java.util.ResourceBundle;

public class AdminHomePageController {

    public static String name;

    @FXML
    private Label AdminId;
    @FXML
    private AnchorPane MainPane;
    @FXML
    private Button Matches;

    @FXML
    private Button Settings;

    @FXML
    private Button Show;

    @FXML
    private Button Tickets;


    AnchorPane match, news_page , settings_page, tickets_page;

    @FXML
    public void initialize() {
        AdminId.setText(name);
        FXMLLoader matches_loader = new FXMLLoader();
        matches_loader.setLocation((getClass().getResource("admin_matches.fxml")));
        try {
            match = matches_loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void viewEditedMatches (ActionEvent some_event) {
        setNewPane(match);
    }

    @FXML
    private void matchesAndTicketsSettings (ActionEvent some_event) {
        setNewPane(settings_page);
    }

    @FXML
    private void newsShow (ActionEvent some_event) {
        setNewPane(news_page);
    }
    private void setNewPane(Node node) {
        MainPane.getChildren().clear();
        MainPane.getChildren().add(node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
    private void closeStage() {
        ((Stage) AdminId.getScene().getWindow()).close();
    }
    void loadWindow(String loc, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

