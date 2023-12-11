package com.example.arena_tickets_purchasing_system.User;

import com.example.arena_tickets_purchasing_system.ArenaTicketsPurchasingSystem;
import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import com.example.arena_tickets_purchasing_system.animations.ButtonsSkin;
import com.example.arena_tickets_purchasing_system.animations.Error_shaking;
import com.example.arena_tickets_purchasing_system.animations.NotificationShower;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OpeningWindowController {

    @FXML
    private TextArea LoginField;

    @FXML
    private PasswordField PasswordField;
    @FXML
    private Button SignInButton;

    @FXML
    private AnchorPane MainPane;
    @FXML
    private TextArea ShownPassword;

    @FXML
    private Hyperlink SignUpLink;

    @FXML
    private ImageView eyeImage;
    AnchorPane registration, main_page, admin_main_page;
    @FXML
    public void initialize() {
        SignInButton.setDefaultButton(true);
        ShownPassword.setVisible(false);
        SignInButton.setOnMouseEntered(event ->{
            SignInButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #0000FF; -fx-text-fill: #0000FF");
        });
        SignInButton.setOnMouseExited(event ->{
            SignInButton.setStyle("-fx-background-color: #0000FF; -fx-border-color: #0000FF; -fx-text-fill: #FFFFFF");
        });
        eyeImage.setOnMousePressed(event ->{
           if(!ShownPassword.isVisible()) {
               ShownPassword.setVisible(true);
               eyeImage.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\eye_crossed.png"));
               ShownPassword.setText(PasswordField.getText());
               PasswordField.setVisible(false);
               PasswordField.clear();
           }
           else{
               ShownPassword.setVisible(false);
               eyeImage.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\eye.png"));
               PasswordField.setText(ShownPassword.getText());
               PasswordField.setVisible(true);
               ShownPassword.clear();
           }
        });
        FXMLLoader registration_loader = new FXMLLoader();
        registration_loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("registration.fxml"));
        FXMLLoader main_page_loader = new FXMLLoader();
        main_page_loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("main_menu.fxml"));
        try {
            registration = registration_loader.load();
            main_page = main_page_loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    private void signInUser() throws SQLException{
        if(LoginField.getText().isEmpty() || (PasswordField.isVisible() && PasswordField.getText().isEmpty())
        || (ShownPassword.isVisible() && ShownPassword.getText().isEmpty())){
            Error_shaking login_and_password_shake;
            if(PasswordField.isVisible()) {
               login_and_password_shake = new Error_shaking(LoginField, PasswordField);
            }
            else{
                login_and_password_shake = new Error_shaking(LoginField, ShownPassword);
            }
            Error_shaking image_shaking = new Error_shaking(eyeImage);
            image_shaking.executeSingleAnimation();
            login_and_password_shake.executeAnimation();
            new NotificationShower().showSimpleError("Ошибка входа!", "Введите логин и пароль!");
            PasswordField.clear();ShownPassword.clear();LoginField.clear();
        }
        else {
            DatabaseHandler dbHandler = new DatabaseHandler();

            String login = LoginField.getText();
            String password;
            if(PasswordField.isVisible()) {
                password = PasswordField.getText();
            }else{
                password = ShownPassword.getText();
            }
            User user = new User(login, password);

            dbHandler.getUser(user);
            loginUser(login, password);
        }
    }
    @FXML
    private void goToNewPane(Node node) {
        MainPane.getChildren().clear();
        MainPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(2500));
        if(node == main_page) {
            ft.setNode(node);
            ft.setFromValue(0.1);
            ft.setToValue(1);
            ft.setCycleCount(1);
            ft.setAutoReverse(false);
        }
        else {
            ft.setNode(node);
            ft.setFromValue(1);
            ft.setToValue(1);
            ft.setCycleCount(1);
            ft.setAutoReverse(false);
        }
        ft.play();
    }
    private void loginUser(String login, String password) throws SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setUser_login(login);
        user.setUser_password(password);
        ResultSet result = dbHandler.getUser(user);

        int numb = 0;

        while (result.next()) {
            numb++;
        }

        if (numb >= 1) {
            user.writeUserIntoFile(user);
            goToNewPane(main_page);
        } else {
            ResultSet result_admin = dbHandler.getAdmin(login, password);
            if(result_admin.next()){
                new NotificationShower().showSimpleNotification("Добро пожаловать","Выполнен вход в качестве администратора");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("admin_home_page.fxml"));
                MainPane.getChildren().clear();
                try {
                    admin_main_page = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                MainPane.getChildren().clear();
                MainPane.getChildren().add(admin_main_page);
            }
            else {
                Error_shaking login_and_password_shake;
                if(PasswordField.isVisible()) {
                    login_and_password_shake = new Error_shaking(LoginField, PasswordField);
                }
                else{
                    login_and_password_shake = new Error_shaking(LoginField, ShownPassword);
                }
                Error_shaking image_shaking = new Error_shaking(eyeImage);
                image_shaking.executeSingleAnimation();
                login_and_password_shake.executeAnimation();
                new NotificationShower().showSimpleError("Ошибка входа!", "Неверный логин или пароль");
                LoginField.clear();
                PasswordField.clear();
                ShownPassword.clear();
            }
        }

    }
    @FXML
    private void goToRegistrationPage (ActionEvent some_event) {
        goToNewPane(registration);
    }

}
