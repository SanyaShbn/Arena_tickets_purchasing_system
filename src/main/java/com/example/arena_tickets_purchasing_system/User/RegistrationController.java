package com.example.arena_tickets_purchasing_system.User;
import com.example.arena_tickets_purchasing_system.ArenaTicketsPurchasingSystem;
import com.example.arena_tickets_purchasing_system.Constant;
import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import com.example.arena_tickets_purchasing_system.animations.Error_shaking;
import com.example.arena_tickets_purchasing_system.animations.NotificationShower;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationController {

    @FXML
    private TextArea LoginField;

    @FXML
    private PasswordField PasswordField;
    @FXML
    private TextArea shownPassword;
    @FXML
    private ImageView eyeImage;

    @FXML
    private Button SignUpButton;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private Hyperlink ExitLink;
    AnchorPane new_pane;
    @FXML
    public void initialize() {
        SignUpButton.setDefaultButton(true);
        shownPassword.setVisible(false);
        SignUpButton.setOnMouseEntered(event -> {
            SignUpButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #0000FF; -fx-text-fill: #0000FF");
        });
        SignUpButton.setOnMouseExited(event -> {
            SignUpButton.setStyle("-fx-background-color: #0000FF; -fx-border-color: #0000FF; -fx-text-fill: #FFFFFF");
        });
        eyeImage.setOnMousePressed(event ->{
            if(!shownPassword.isVisible()) {
                shownPassword.setVisible(true);
                eyeImage.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\eye_crossed.png"));
                shownPassword.setText(PasswordField.getText());
                PasswordField.setVisible(false);
                PasswordField.clear();
            }
            else{
                shownPassword.setVisible(false);
                eyeImage.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\eye.png"));
                PasswordField.setText(shownPassword.getText());
                PasswordField.setVisible(true);
                shownPassword.clear();
            }
        });
    }
    private void registerUser(String login, String password) throws SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        boolean check = false;
        String select_users = "SELECT * FROM " + Constant.USERS_TABLE;
        PreparedStatement prStrUsers = dbHandler.getDbConnection("users").prepareStatement(select_users);
        ResultSet result_users = prStrUsers.executeQuery();
        while (result_users.next()){
            if (login.equals(result_users.getString("User_login"))
                    || password.equals(result_users.getString("User_password"))) {
                check = true;
                break;
            }
        }
        if (check) {
            Error_shaking login_and_password_shake;
            if(PasswordField.isVisible()) {
                login_and_password_shake = new Error_shaking(LoginField, PasswordField);
            }
            else{
                login_and_password_shake = new Error_shaking(LoginField, shownPassword);
            }
            Error_shaking image_shaking = new Error_shaking(eyeImage);
            image_shaking.executeSingleAnimation();
            login_and_password_shake.executeAnimation();
            new NotificationShower().showSimpleError("Ошибка регистрации!", "Логин или пароль уже используются");
            LoginField.clear();
            PasswordField.clear();
            shownPassword.clear();

        }
        else{
            ResultSet check_admin = dbHandler.getAdmin(login, password);
            if(check_admin.next()){
                Error_shaking login_and_password_shake;
                if(PasswordField.isVisible()) {
                    login_and_password_shake = new Error_shaking(LoginField, PasswordField);
                }
                else{
                    login_and_password_shake = new Error_shaking(LoginField, shownPassword);
                }
                Error_shaking image_shaking = new Error_shaking(eyeImage);
                image_shaking.executeSingleAnimation();
                login_and_password_shake.executeAnimation();
                new NotificationShower().showSimpleError("Ошибка регистрации!", "Логин или пароль уже используются");
                LoginField.clear();
                PasswordField.clear();
                shownPassword.clear();
            }
            else {
                new NotificationShower().showSimpleNotification("Уведомление", "Вы успешно зарегестрированы");
                dbHandler.signUpUsers(new User(login, password));
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

    }
    @FXML
    private void registrationOfUser (ActionEvent some_event) {
        if(LoginField.getText().isEmpty() || (PasswordField.isVisible() && PasswordField.getText().isEmpty())
                || (shownPassword.isVisible() && shownPassword.getText().isEmpty())){
            Error_shaking login_and_password_shake;
            if(PasswordField.isVisible()) {
                login_and_password_shake = new Error_shaking(LoginField, PasswordField);
            }
            else{
                login_and_password_shake = new Error_shaking(LoginField, shownPassword);
            }
            Error_shaking image_shaking = new Error_shaking(eyeImage);
            image_shaking.executeSingleAnimation();
            login_and_password_shake.executeAnimation();
            new NotificationShower().showSimpleError("Ошибка регистрации!", "Введите логин и пароль");
            PasswordField.clear();shownPassword.clear();LoginField.clear();
        }
        else {
            try {
                String login = LoginField.getText().trim();
                String password;
                if(PasswordField.isVisible()){
                    password = PasswordField.getText().trim();
                }else{
                    password = shownPassword.getText().trim();
                }
                registerUser(login, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
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
