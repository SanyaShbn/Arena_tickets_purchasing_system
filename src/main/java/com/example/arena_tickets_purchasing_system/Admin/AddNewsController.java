package com.example.arena_tickets_purchasing_system.Admin;

import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class AddNewsController {

    @FXML
    private AnchorPane MainPane;

    @FXML
    private TextField contents;
    @FXML
    private TextField id;

    @FXML
    private TextField date;

    @FXML
    private Button exitButton;

    @FXML
    private Button submitChanges;

    @FXML
    private TextField time;


    AnchorPane back_to_club_news;
    @FXML
    void initialize(){
        FXMLLoader add_news_loader = new FXMLLoader();
        add_news_loader.setLocation((getClass().getResource("club_news.fxml")));
        try {
            back_to_club_news = add_news_loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void backToPreviousPane(Node node) {
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
    private void backToClubNews (ActionEvent event) {
        backToPreviousPane(back_to_club_news);
    }
    @FXML
    private void addNews (ActionEvent event) throws SQLException, ClassNotFoundException {
        new DatabaseHandler().addNews(new AdminNewsController.News(Integer.parseInt(id.getText()), date.getText(),
                time.getText(), contents.getText()));
    }
}

