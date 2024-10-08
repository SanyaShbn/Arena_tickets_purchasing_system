package com.example.arena_tickets_purchasing_system.Admin;

import com.example.arena_tickets_purchasing_system.ArenaTicketsPurchasingSystem;
import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.animations.NotificationShower;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

public class AddNewsController {

    @FXML
    private AnchorPane MainPane;

    @FXML
    private TextField contents;

    @FXML
    private Button exitButton;

    @FXML
    private Button submitChanges;


    AnchorPane back_to_club_news;
    @FXML
    void initialize(){
        submitChanges.setDefaultButton(true);
        submitChanges.setOnMouseEntered(event ->{
            submitChanges.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #00BFFF; -fx-text-fill: #00BFFF");
        });
        submitChanges.setOnMouseExited(event ->{
            submitChanges.setStyle("-fx-background-color: #00BFFF; -fx-border-color: #00BFFF; -fx-text-fill: #000000");
        });
        exitButton.setOnMouseEntered(event ->{
            exitButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #00BFFF; -fx-text-fill: #00BFFF");
        });
        exitButton.setOnMouseExited(event ->{
            exitButton.setStyle("-fx-background-color: #00BFFF; -fx-border-color: #00BFFF; -fx-text-fill: #000000");
        });
        FXMLLoader add_news_loader = new FXMLLoader();
        add_news_loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("club_news.fxml"));
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
    private void addNews (ActionEvent event) throws ClassNotFoundException {
        boolean check = false;
        String alphabet = "abcdefghijklmnopqrstuvwxwz";
        for(char letter : contents.getText().toCharArray()){
           for(char english_letter : alphabet.toCharArray()){
               if(letter == english_letter){
                   check = true; break;
               }
           }
        }
        if(!check) {
           new DatabaseHandler().addNews(new AdminNewsController.News(String.valueOf(LocalDate.now()),
                   String.valueOf(Calendar.getInstance().getTime()).substring(11, 16), contents.getText()));
        }
        else {
            new NotificationShower().showWarning("Внимание!","Содержание должно быть написано на русском языке!");
        }
        contents.clear();
    }
}

