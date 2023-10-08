package com.example.arena_tickets_purchasing_system.Admin;

import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import javafx.animation.FadeTransition;
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

import static com.example.arena_tickets_purchasing_system.Constant.MATCHES_TABLE;

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
    private TextField id;

    @FXML
    private TextField opponent;

    @FXML
    private Button submitChanges;

    @FXML
    private TextField time;

    @FXML
    private TextField type;

    AnchorPane back_to_admin_matches;
    @FXML
    void initialize(){
        FXMLLoader add_matches_loader = new FXMLLoader();
        add_matches_loader.setLocation((getClass().getResource("admin_matches.fxml")));
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
    private void addNewMatch (ActionEvent event) throws SQLException, ClassNotFoundException {
        new DatabaseHandler().addNewMatches(new AdminMatchesWindowController.Match(Integer.parseInt(id.getText()), date.getText(),
                time.getText(), opponent.getText(), Integer.parseInt(amount.getText()), type.getText()));
    }

}
