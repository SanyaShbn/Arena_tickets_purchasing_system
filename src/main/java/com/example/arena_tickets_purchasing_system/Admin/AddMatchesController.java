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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;


public class AddMatchesController {

    @FXML
    private AnchorPane MainPane;

    @FXML
    private TextField amount;

    @FXML
    private TextField date;

    @FXML
    private Button exitButton;

    @FXML
    private RadioButton awayRadioButton;

    @FXML
    private RadioButton homeRadioButton;

    @FXML
    private TextField id;

    @FXML
    private TextField opponent;

    @FXML
    private Button submitChanges;

    @FXML
    private TextField time;


    AnchorPane back_to_admin_matches;
    @FXML
    void initialize(){
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
        String type_match; int tickets_amount;
        try {
        if(homeRadioButton.isSelected()){
            type_match  = homeRadioButton.getText();
            tickets_amount = Integer.parseInt(amount.getText());
        }
        else {
            type_match = awayRadioButton.getText();
            System.out.println(type_match);
            tickets_amount = 0;
        }
            new DatabaseHandler().addNewMatches(new AdminMatchesWindowController.Match(Integer.parseInt(id.getText()), date.getText(),
                    time.getText(), type_match, opponent.getText(), tickets_amount));
        } catch(NumberFormatException e){
            new NotificationShower().showWarning("Внимание!","Проверьте корректность ввода данных");
        }
    }

}
