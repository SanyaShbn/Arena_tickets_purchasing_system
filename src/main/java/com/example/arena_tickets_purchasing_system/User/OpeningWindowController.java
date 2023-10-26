package com.example.arena_tickets_purchasing_system.User;

import com.example.arena_tickets_purchasing_system.ArenaTicketsPurchasingSystem;
import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import com.example.arena_tickets_purchasing_system.animations.Error_shaking;
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
    private Button SignUpButton;
    @FXML
    private Hyperlink AdminLink;
    @FXML
    private AnchorPane MainPane;

    AnchorPane registration, main_page;
    @FXML
    public void initialize() {
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
    private void signInUser() throws SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();

        String login = LoginField.getText();
        String password = PasswordField.getText();

        User user = new User(login, password);

        dbHandler.getUser(user);
        loginUser(login, password);
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

        while(result.next()){
            numb++;
        }

        if(numb >= 1){
            user.writeUserIntoFile(user);
            goToNewPane(main_page);
        }
        else{
            Error_shaking login_and_password_shake = new Error_shaking(LoginField, PasswordField);
            login_and_password_shake.executeAnimation();
            new NotificationShower().showSimpleError("Ошибка входа!", "Неверный логин или пароль");
        }

    }
    @FXML
    private void goToRegistrationPage (ActionEvent some_event) {
        goToNewPane(registration);
    }
    @FXML
    private void goToAdminLogin (ActionEvent some_event) {
        AdminLink.getScene().getWindow().hide();
        WindowsOpener registration_window = new WindowsOpener("admin_login.fxml");
    }

}
