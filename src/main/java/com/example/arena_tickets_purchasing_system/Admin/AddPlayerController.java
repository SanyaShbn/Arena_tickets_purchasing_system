package com.example.arena_tickets_purchasing_system.Admin;

import com.example.arena_tickets_purchasing_system.DatabaseHandler;
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
    private TextField nation;

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


    AnchorPane back_to_admin_matches;
    @FXML
    void initialize(){
        FXMLLoader add_players_loader = new FXMLLoader();
        add_players_loader.setLocation((getClass().getResource("roster.fxml")));
        try {
            back_to_admin_matches = add_players_loader.load();
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
    private void addNewPlayer (ActionEvent event) throws SQLException, ClassNotFoundException {
        new DatabaseHandler().addNewPlayers(new AdminTeamRosterController.Player(name.getText(), role.getText(),
                Integer.parseInt(jerseyNumb.getText()), nation.getText(), Integer.parseInt(age.getText()),
                Integer.parseInt(height.getText()), Integer.parseInt(weight.getText()), Integer.parseInt(seasonsInTeam.getText()),
                Integer.parseInt(seasonsInLeague.getText())));
    }

}
