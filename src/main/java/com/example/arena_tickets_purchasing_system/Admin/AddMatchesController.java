package com.example.arena_tickets_purchasing_system.Admin;

import com.example.arena_tickets_purchasing_system.ArenaTicketsPurchasingSystem;
import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.animations.NotificationShower;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.example.arena_tickets_purchasing_system.Constant.*;
import static com.example.arena_tickets_purchasing_system.Constant.MATCH_TYPE;


public class AddMatchesController {

    @FXML
    private AnchorPane MainPane;

    @FXML
    private TextField amount;

    @FXML
    private DatePicker date;

    @FXML
    private Button exitButton;

    @FXML
    private RadioButton awayRadioButton;

    @FXML
    private RadioButton homeRadioButton;


    @FXML
    private MenuButton opponent;

    @FXML
    private Button submitChanges;

    @FXML
    private TextField time;


    AnchorPane back_to_admin_matches;
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
        homeRadioButton.setSelected(true);
        FXMLLoader add_matches_loader = new FXMLLoader();
        add_matches_loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("admin_matches.fxml"));
        try {
            back_to_admin_matches = add_matches_loader.load();
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
    private void backToAdminMatches (ActionEvent event) {
        backToPreviousPane(back_to_admin_matches);
    }
    @FXML
    private void awayRadioButtonAction (ActionEvent event) {
        homeRadioButton.setSelected(false);
        amount.setVisible(false);
    }
    @FXML
    private void homeRadioButtonAction (ActionEvent event) {
        awayRadioButton.setSelected(false);
        amount.setVisible(true);
    }
    @FXML
    private void addNewMatch (ActionEvent event) throws SQLException, ClassNotFoundException {
        ArrayList<String> dates_list = new ArrayList<>();
        String select = "SELECT * FROM " + MATCHES_TABLE;
        PreparedStatement prStr = new DatabaseHandler().getDbConnection( "matches").prepareStatement(select);
        ResultSet result = prStr.executeQuery();
        try {
            while (result.next()) {
                String date = result.getString(MATCHES_DATE);
                dates_list.add(date);
            }
        } catch (SQLException e) {
            new RuntimeException(e);
        }

        String type_match; int tickets_amount; boolean check = false;
        try {
        if(homeRadioButton.isSelected()){
            type_match  = homeRadioButton.getText();
            tickets_amount = Integer.parseInt(amount.getText());
        }
        else {
            type_match = awayRadioButton.getText();
            tickets_amount = 0;
        }
        for(String dates : dates_list){
            if(String.valueOf(date.getValue()).equals(dates)){
                check = true; break;
            }
        }
            if(date.getValue() == null){
                new NotificationShower().showWarning("Внимание!","Выберите дату матча!");
            }
            else if(date.getValue().isBefore(LocalDate.now())){
                new NotificationShower().showWarning("Внимание!","Выберите корректную дату матча!");
            }
            else if(check){
                new NotificationShower().showWarning("Внимание!","В этот день уже есть игра! Выберите корректную дату матча");
            }
            else if(time.getText().length() != 5 || !String.valueOf(time.getText().toCharArray()[2]).equals(":")
                    || (Integer.parseInt(String.valueOf(time.getText().toCharArray()[3])) > 5)
                    || (Integer.parseInt(String.valueOf(time.getText().toCharArray()[0])) > 2)){
                new NotificationShower().showWarning("Внимание!","Проверьте корректность ввода времени матча!");
            }
            else if(tickets_amount < 0 || tickets_amount > 15000) {
                new NotificationShower().showWarning("Внимание!", "Проверьте корректность ввода количества билетов на матч!");
            }
            else if(opponent.getText().equals("соперник")){
                new NotificationShower().showWarning("Внимание!","Выберите соперника!");
            }else {
                boolean final_check = true;
                for(char letter: time.getText().toCharArray()){
                    if(Character.isAlphabetic(letter)){
                        new NotificationShower().showWarning("Внимание!","Проверьте корректность ввода времени матча!");
                        final_check = false;break;
                    }
                }
                if(final_check) {
                    new DatabaseHandler().addNewMatches(new AdminMatchesWindowController.Match(String.valueOf(date.getValue()),
                            time.getText(), type_match, opponent.getText(), tickets_amount));
                }
            }
        } catch(NumberFormatException e){
            new NotificationShower().showWarning("Внимание!","Проверьте корректность ввода данных");
        }finally {
           date.cancelEdit();time.clear();homeRadioButton.setSelected(true);amount.setVisible(true);awayRadioButton.setSelected(false);opponent.setText("соперник");amount.clear();
        }
    }
    @FXML
    private void setSPA (ActionEvent event) {
        opponent.setText("Спартак");
    }
    @FXML
    private void setSKA (ActionEvent event) {
        opponent.setText("СКА");
    }
    @FXML
    private void setCSKA (ActionEvent event) {
        opponent.setText("ЦСКА");
    }
    @FXML
    private void setDNM (ActionEvent event) {
        opponent.setText("Динамо Мск");
    }
    @FXML
    private void setLOK (ActionEvent event) {
        opponent.setText("Локомотив");
    }
    @FXML
    private void setAKB (ActionEvent event) {
        opponent.setText("Ак барс");
    }
    @FXML
    private void setAVG (ActionEvent event) {
        opponent.setText("Авангард");
    }
    @FXML
    private void setMMG (ActionEvent event) {
        opponent.setText("Металлург");
    }
    @FXML
    private void setAVT (ActionEvent event) {
        opponent.setText("Автомобилист");
    }

}
