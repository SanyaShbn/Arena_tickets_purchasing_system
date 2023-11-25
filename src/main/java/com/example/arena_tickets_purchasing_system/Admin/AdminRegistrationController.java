package com.example.arena_tickets_purchasing_system.Admin;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRegistrationController {

    @FXML
    private TextArea LoginField;

    @FXML
    private javafx.scene.control.PasswordField PasswordField;

    @FXML
    private Button SignUpButton;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private Hyperlink ExitLink;

    @FXML
    private ImageView eyeImage;

    @FXML
    private TextArea shownPassword;
    AnchorPane new_pane;

    @FXML
    public void initialize() {
        shownPassword.setVisible(false);
        SignUpButton.setOnMouseEntered(event -> {
            SignUpButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #0000FF; -fx-text-fill: #0000FF");
        });
        SignUpButton.setOnMouseExited(event -> {
            SignUpButton.setStyle("-fx-background-color: #0000FF; -fx-border-color: #0000FF; -fx-text-fill: #FFFFFF");
        });
        eyeImage.setOnMousePressed(event ->{
            shownPassword.setVisible(true);
            eyeImage.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\eye_crossed.png"));
            shownPassword.setText(PasswordField.getText());
            PasswordField.setVisible(false);
        });
        eyeImage.setOnMouseReleased(event ->{
            eyeImage.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\eye.png"));
            PasswordField.setVisible(true);
            shownPassword.clear();
            shownPassword.setVisible(false);
        });
    }
    private void registerAdmin(String login, String password) throws SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getAdmin(login, password);

        int numb = 0;

        while(result.next()){
            numb++;
        }

        if(numb > 0){
            Error_shaking login_and_password_shake = new Error_shaking(LoginField, PasswordField);
            login_and_password_shake.executeAnimation();
            new NotificationShower().showSimpleError("Ошибка регистрации!", "Вы ввели свои предыдущие логин и пароль! Введите новые данные");
            LoginField.clear();
            PasswordField.clear();
        }
        else{
            Admin new_admin = Admin.getInstance(login, password);
            dbHandler.signUpAdmins(new_admin);
            new NotificationShower().showSimpleNotification("Уведомление", "Вы успешно зарегестрированы");
            SignUpButton.getScene().getWindow().hide();
            WindowsOpener open_window = new WindowsOpener("admin_login.fxml");

        }

    }
    @FXML
    private void registrationOfAdmin (ActionEvent some_event) {
        String login = LoginField.getText().trim();
        String password = PasswordField.getText().trim();
        if(!LoginField.getText().isEmpty() && !PasswordField.getText().isEmpty()){
            try {
                registerAdmin(login, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            Error_shaking login_and_password_shake = new Error_shaking(LoginField, PasswordField);
            login_and_password_shake.executeAnimation();
            new NotificationShower().showSimpleError("Ошибка регистрации!", "Введите логин и пароль");
        }
    }
    @FXML
    private void goToAdminLoginPage (ActionEvent some_event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("admin_login.fxml"));
        MainPane.getChildren().clear();
        try {
            new_pane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainPane.getChildren().add(new_pane);
    }
}
