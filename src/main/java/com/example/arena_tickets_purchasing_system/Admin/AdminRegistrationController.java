package com.example.arena_tickets_purchasing_system.Admin;

import com.example.arena_tickets_purchasing_system.ArenaTicketsPurchasingSystem;
import com.example.arena_tickets_purchasing_system.Constant;
import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.User.User;
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
import java.util.Optional;

public class AdminRegistrationController {

    @FXML
    private TextArea LoginField;

    @FXML
    private PasswordField PasswordField;
    @FXML
    private PasswordField CurrentPasswordField;

    @FXML
    private Button SignUpButton;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private Hyperlink ExitLink;

    @FXML
    private ImageView eyeImage;
    @FXML
    private RadioButton changeLogin;

    @FXML
    private RadioButton changePassword;
    @FXML
    private ImageView eyeImage1;

    @FXML
    private TextArea shownPassword;
    @FXML
    private TextArea shownCurrentPassword;
    AnchorPane new_pane;

    @FXML
    public void initialize() {
        changeLogin.setSelected(true);
        changePassword.setSelected(true);
        SignUpButton.setDefaultButton(true);
        shownPassword.setVisible(false);
        shownCurrentPassword.setVisible(false);
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
        eyeImage1.setOnMousePressed(event -> {
            if (!shownCurrentPassword.isVisible()) {
                shownCurrentPassword.setVisible(true);
                eyeImage1.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\eye_crossed.png"));
                shownCurrentPassword.setText(CurrentPasswordField.getText());
                CurrentPasswordField.setVisible(false);
                CurrentPasswordField.clear();
            } else {
                shownCurrentPassword.setVisible(false);
                eyeImage1.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\eye.png"));
                CurrentPasswordField.setText(shownCurrentPassword.getText());
                CurrentPasswordField.setVisible(true);
                shownCurrentPassword.clear();
            }
        });
    }
    private void registerAdmin(String login, String password) throws SQLException, ClassNotFoundException {
        String select = "SELECT * FROM " + Constant.ADMINS_TABLE;
        DatabaseHandler dbHandler = new DatabaseHandler();
        PreparedStatement prStr = dbHandler.getDbConnection("admins").prepareStatement(select);
        ResultSet result = prStr.executeQuery();
        while (result.next()) {
            if (result.getString("Admin_password").equals(password) || result.getString("Admin_login").equals(login)) {
                Error_shaking login_and_password_shake;
                if(PasswordField.isVisible() && CurrentPasswordField.isVisible()){
                    login_and_password_shake = new Error_shaking(LoginField, PasswordField, CurrentPasswordField);
                }else if(!PasswordField.isVisible() && CurrentPasswordField.isVisible()){
                    login_and_password_shake = new Error_shaking(LoginField, CurrentPasswordField, shownPassword);
                }
                else if(PasswordField.isVisible() && !CurrentPasswordField.isVisible()){
                    login_and_password_shake = new Error_shaking(LoginField, PasswordField, shownCurrentPassword);
                }
                else{
                    login_and_password_shake = new Error_shaking(LoginField, shownPassword, shownCurrentPassword);
                }
                Error_shaking image_shaking = new Error_shaking(eyeImage1, eyeImage);
                image_shaking.executeAnimation();
                login_and_password_shake.executeTripleAnimation();
                new NotificationShower().showSimpleError("Ошибка!", "Введённые данные совпадают с текущими! Введите новые данные");
                LoginField.clear();
                PasswordField.clear();
                CurrentPasswordField.clear();
                shownPassword.clear();
                shownCurrentPassword.clear();
            } else {
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
                    if(PasswordField.isVisible() && CurrentPasswordField.isVisible()){
                        login_and_password_shake = new Error_shaking(LoginField, PasswordField, CurrentPasswordField);
                    }else if(!PasswordField.isVisible() && CurrentPasswordField.isVisible()){
                        login_and_password_shake = new Error_shaking(LoginField, CurrentPasswordField, shownPassword);
                    }
                    else if(PasswordField.isVisible() && !CurrentPasswordField.isVisible()){
                        login_and_password_shake = new Error_shaking(LoginField, PasswordField, shownCurrentPassword);
                    }
                    else{
                        login_and_password_shake = new Error_shaking(LoginField, shownPassword, shownCurrentPassword);
                    }
                    Error_shaking image_shaking = new Error_shaking(eyeImage1, eyeImage);
                    image_shaking.executeAnimation();
                    login_and_password_shake.executeTripleAnimation();
                    new NotificationShower().showSimpleError("Ошибка!", "Введённый логин или пароль уже используются одним из пользователей!");
                    LoginField.clear();
                    PasswordField.clear();
                    CurrentPasswordField.clear();
                    shownPassword.clear();
                    shownCurrentPassword.clear();
                } else {
                    if ((CurrentPasswordField.isVisible() && CurrentPasswordField.getText().equals(result.getString("Admin_password"))) ||
                            (shownCurrentPassword.isVisible() && shownCurrentPassword.getText().equals(result.getString("Admin_password")))) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText("Внимание!");
                        alert.setContentText("Вы уверены, что хотите окончательно изменить данные учётной записи?");
                        Optional<ButtonType> confirm = alert.showAndWait();
                        if (confirm.get() == ButtonType.OK) {
                            if(!changePassword.isSelected()){
                                password = result.getString("Admin_password");
                            }
                            if(!changeLogin.isSelected()){
                                login = result.getString("Admin_login");
                            }
                            dbHandler.signUpAdmins(login, password);
                            new NotificationShower().showSimpleNotification("Уведомление", "Данные аккаунта успешно изменены");
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("Open_window.fxml"));
                            MainPane.getChildren().clear();
                            try {
                                new_pane = loader.load();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            MainPane.getChildren().add(new_pane);
                        } else {
                            LoginField.clear();
                            PasswordField.clear();
                            CurrentPasswordField.clear();
                            shownPassword.clear();
                            shownCurrentPassword.clear();
                        }
                    } else {
                        Error_shaking login_and_password_shake;
                        if(PasswordField.isVisible() && CurrentPasswordField.isVisible()){
                            login_and_password_shake = new Error_shaking(LoginField, PasswordField, CurrentPasswordField);
                        }else if(!PasswordField.isVisible() && CurrentPasswordField.isVisible()){
                            login_and_password_shake = new Error_shaking(LoginField, CurrentPasswordField, shownPassword);
                        }
                        else if(PasswordField.isVisible() && !CurrentPasswordField.isVisible()){
                            login_and_password_shake = new Error_shaking(LoginField, PasswordField, shownCurrentPassword);
                        }
                        else{
                            login_and_password_shake = new Error_shaking(LoginField, shownPassword, shownCurrentPassword);
                        }
                        Error_shaking image_shaking = new Error_shaking(eyeImage1, eyeImage);
                        image_shaking.executeAnimation();
                        login_and_password_shake.executeTripleAnimation();
                        new NotificationShower().showSimpleError("Ошибка!", "Вы ввели неверный текущий пароль для подтверждения!");
                        LoginField.clear();
                        PasswordField.clear();
                        CurrentPasswordField.clear();
                        shownPassword.clear();
                        shownCurrentPassword.clear();
                    }
                }

            }
        }

    }
    @FXML
    private void registrationOfAdmin (ActionEvent some_event) {
        String login = LoginField.getText().trim();
        String password = PasswordField.getText().trim();
        if(((LoginField.isVisible() && LoginField.getText().isEmpty()) || (PasswordField.isVisible() && PasswordField.getText().isEmpty()))
                || (shownPassword.isVisible() && shownPassword.getText().isEmpty()) || (CurrentPasswordField.isVisible() && CurrentPasswordField.getText().isEmpty())
                || (shownCurrentPassword.isVisible() && shownCurrentPassword.getText().isEmpty())){
            Error_shaking login_and_password_shake;
            if(PasswordField.isVisible() && CurrentPasswordField.isVisible()){
                login_and_password_shake = new Error_shaking(LoginField, PasswordField, CurrentPasswordField);
            }else if(!PasswordField.isVisible() && CurrentPasswordField.isVisible()){
                login_and_password_shake = new Error_shaking(LoginField, CurrentPasswordField, shownPassword);
            }
            else if(PasswordField.isVisible() && !CurrentPasswordField.isVisible()){
                login_and_password_shake = new Error_shaking(LoginField, PasswordField, shownCurrentPassword);
            }
            else{
                login_and_password_shake = new Error_shaking(LoginField, shownPassword, shownCurrentPassword);
            }
            Error_shaking image_shaking = new Error_shaking(eyeImage1, eyeImage);
            image_shaking.executeAnimation();
            login_and_password_shake.executeTripleAnimation();
            new NotificationShower().showSimpleError("Ошибка!", "Заполните поля!");
            LoginField.clear();
            PasswordField.clear();
            CurrentPasswordField.clear();
            shownPassword.clear();
            shownCurrentPassword.clear();
        }
        else {
            try {
                registerAdmin(login, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    private void goToAdminHomePage(ActionEvent some_event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("admin_home_page.fxml"));
        MainPane.getChildren().clear();
        try {
            new_pane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainPane.getChildren().add(new_pane);
    }
    @FXML
    private void changeLoginAction (ActionEvent event) {
        if(!changeLogin.isSelected()){
            LoginField.clear();
            LoginField.setVisible(false);
        }
        else{
            LoginField.setVisible(true);
        }
    }
    @FXML
    private void changePasswordAction (ActionEvent event) {
        if(!changePassword.isSelected()){
            PasswordField.clear();
            shownPassword.clear();
            if(PasswordField.isVisible()) {
                PasswordField.setVisible(false);
            }
            if(shownPassword.isVisible()) {
                shownPassword.setVisible(false);
            }
            eyeImage.setVisible(false);
        }
        else{
            eyeImage.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\eye.png"));
            eyeImage.setVisible(true);
            PasswordField.setVisible(true);
        }
    }
}
