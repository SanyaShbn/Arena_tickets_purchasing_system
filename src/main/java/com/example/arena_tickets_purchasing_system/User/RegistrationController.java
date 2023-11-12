package com.example.arena_tickets_purchasing_system.User;
import com.example.arena_tickets_purchasing_system.ArenaTicketsPurchasingSystem;
import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import com.example.arena_tickets_purchasing_system.animations.Error_shaking;
import com.example.arena_tickets_purchasing_system.animations.NotificationShower;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationController {

    @FXML
    private TextArea LoginField;

    @FXML
    private TextArea PasswordField;

    @FXML
    private Button SignUpButton;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private Hyperlink ExitLink;
    AnchorPane new_pane;
    @FXML
    public void initialize() {
        SignUpButton.setOnMouseEntered(event -> {
            SignUpButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #0000FF; -fx-text-fill: #0000FF");
        });
        SignUpButton.setOnMouseExited(event -> {
            SignUpButton.setStyle("-fx-background-color: #0000FF; -fx-border-color: #0000FF; -fx-text-fill: #FFFFFF");
        });
    }
    private void registerUser(String login, String password) throws SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setUser_login(login);
        user.setUser_password(password);
        ResultSet result = dbHandler.getUser(user);

        int numb = 0;

        while(result.next()){
            numb++;
        }

        if(numb > 0){
            Error_shaking login_and_password_shake = new Error_shaking(LoginField, PasswordField);
            login_and_password_shake.executeAnimation();
            new NotificationShower().showSimpleError("Ошибка регистрации!", "Логин или пароль уже используются");
            LoginField.clear();
            PasswordField.clear();

        }
        else{
            dbHandler.signUpUsers(new User(login,password));
            SignUpButton.getScene().getWindow().hide();
            new WindowsOpener("Open_window.fxml");

        }

    }
    @FXML
    private void registrationOfUser (ActionEvent some_event) {
        String login = LoginField.getText().trim();
        String password = PasswordField.getText().trim();
        if(!LoginField.getText().isEmpty() && !PasswordField.getText().isEmpty()){
            try {
                registerUser(login, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            new NotificationShower().showSimpleError("Ошибка регистрации!", "Введите логин и пароль");
        }
    }

    @FXML
    private void goToOpenWindowPage (ActionEvent some_event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("Open_window.fxml"));
        MainPane.getChildren().clear();
        try {
            new_pane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainPane.getChildren().add(new_pane);
    }
}
