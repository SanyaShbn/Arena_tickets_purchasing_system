package com.example.arena_tickets_purchasing_system.Admin;

import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.animations.NotificationShower;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;


public class AddPlayerController {

    @FXML
    private AnchorPane MainPane;
    @FXML
    private TextField age;
    @FXML
    private Button exitButton;
    @FXML
    private TextField height;
    @FXML
    private TextField jerseyNumb;
    @FXML
    private TextField name;
    @FXML
    private MenuButton country;
    @FXML
    private MenuItem belarus;
    @FXML
    private MenuItem canada;
    @FXML
    private MenuItem czech;
    @FXML
    private MenuItem finland;
    @FXML
    private MenuItem russia;
    @FXML
    private MenuItem slovakia;
    @FXML
    private MenuItem sweden;
    @FXML
    private MenuItem usa;
    @FXML
    private MenuButton role;
    @FXML
    private TextField seasonsInLeague;
    @FXML
    private TextField seasonsInTeam;
    @FXML
    private Button submitChanges;
    @FXML
    private TextField weight;
    @FXML
    private MenuItem centerForward;
    @FXML
    private MenuItem defenceman;
    @FXML
    private MenuItem goaltender;
    @FXML
    private MenuItem winger;


    AnchorPane back_to_roster;
    @FXML
    void initialize(){
        FXMLLoader add_players_loader = new FXMLLoader();
         add_players_loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("roster.fxml"));
        try {
            back_to_roster = add_players_loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        centerForward.setOnAction(event ->
        {
            role.setText(centerForward.getText());
        });
        winger.setOnAction(event ->
        {
            role.setText(winger.getText());
        });
        defenceman.setOnAction(event ->
        {
            role.setText(defenceman.getText());
        });
        goaltender.setOnAction(event ->
        {
            role.setText(goaltender.getText());
        });
    }

    @FXML
    private void setBelFlag (ActionEvent event) {
        country.setText("Беларусь");
    }
    @FXML
    private void setRusFlag (ActionEvent event) {
        country.setText("Россия");
    }
    @FXML
    private void setUSAFlag (ActionEvent event) {
        country.setText("США");
    }
    @FXML
    private void setCanFlag (ActionEvent event) {
        country.setText("Канада");
    }
    @FXML
    private void setSweFlag (ActionEvent event) {
        country.setText("Швеция");
    }
    @FXML
    private void setFinFlag (ActionEvent event) {
        country.setText("Финляндия");
    }
    @FXML
    private void setCheFlag (ActionEvent event) {
        country.setText("Чехия");
    }
    @FXML
    private void setSloFlag (ActionEvent event) {
        country.setText("Словакия");
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
        backToPreviousPane(back_to_roster);
    }
    @FXML
    private void addNewPlayer (ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            new DatabaseHandler().addNewPlayers(new AdminTeamRosterController.Player(name.getText(), role.getText(),
                    Integer.parseInt(jerseyNumb.getText()), country.getText(), Integer.parseInt(age.getText()),
                    Integer.parseInt(height.getText()), Integer.parseInt(weight.getText()), Integer.parseInt(seasonsInTeam.getText()),
                    Integer.parseInt(seasonsInLeague.getText())));
        }catch (SQLIntegrityConstraintViolationException e){
            new NotificationShower().showSimpleError("Внимание!", "Введённый номер джерси уже занят другим игроком");
        }
    }

}
