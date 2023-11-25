package com.example.arena_tickets_purchasing_system.Admin;

import com.example.arena_tickets_purchasing_system.ArenaTicketsPurchasingSystem;
import com.example.arena_tickets_purchasing_system.Constant;
import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import com.example.arena_tickets_purchasing_system.animations.Error_shaking;
import com.example.arena_tickets_purchasing_system.animations.NotificationShower;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminLoginController {

    @FXML
    private TextArea LoginField;

    @FXML
    private PasswordField PasswordField;
    @FXML
    private Button SignInButton;

    @FXML
    private AnchorPane MainPane;
    @FXML
    private Hyperlink ExitLink;

    @FXML
    private Hyperlink SignUpLink;

    @FXML
    private TextArea ShownPassword;
    @FXML
    private ImageView eyeImage;


    AnchorPane registration, admin_home_page, open_window;
    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        ShownPassword.setVisible(false);
        eyeImage.setOnMousePressed(event ->{
            ShownPassword.setVisible(true);
            eyeImage.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\eye_crossed.png"));
            ShownPassword.setText(PasswordField.getText());
            PasswordField.setVisible(false);
        });
        eyeImage.setOnMouseReleased(event ->{
            eyeImage.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\eye.png"));
            PasswordField.setVisible(true);
            ShownPassword.clear();
            ShownPassword.setVisible(false);
        });
        String select = "SELECT * FROM " + Constant.ADMINS_TABLE;
        PreparedStatement prStr = new DatabaseHandler().getDbConnection("admins").prepareStatement(select);
        ResultSet result = prStr.executeQuery();

        if(result.next()){
            SignUpLink.setText("Изменить данные учётной записи");
            SignUpLink.setLayoutX(650);
        }
        SignInButton.setOnMouseEntered(event ->{
            SignInButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #0000FF; -fx-text-fill: #0000FF");
        });
        SignInButton.setOnMouseExited(event ->{
            SignInButton.setStyle("-fx-background-color: #0000FF; -fx-border-color: #0000FF; -fx-text-fill: #FFFFFF");
        });
        FXMLLoader registration_loader = new FXMLLoader();
        FXMLLoader admin_home_page_loader = new FXMLLoader();
        registration_loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("admin_registration.fxml"));
        admin_home_page_loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("admin_home_page.fxml"));
        try {
            registration = registration_loader.load();
            admin_home_page = admin_home_page_loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void signInAdmin() throws SQLException, ClassNotFoundException {
        if(LoginField.getText().isEmpty() || PasswordField.getText().isEmpty()){
            Error_shaking login_and_password_shake = new Error_shaking(LoginField, PasswordField);
            login_and_password_shake.executeAnimation();
            new NotificationShower().showSimpleError("Ошибка входа!", "Введите логин и пароль!");
        }
        else {
            DatabaseHandler dbHandler = new DatabaseHandler();

            String login = LoginField.getText();
            String password = PasswordField.getText();

            dbHandler.getAdmin(login, password);
            loginAdmin(login, password);
        }
    }
    private void loginAdmin(String login, String password) throws SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getAdmin(login, password);
        int numb = 0;
        while (result.next()) {
            numb++;
        }

        if (numb >= 1) {
            goToNewPane(admin_home_page);
        } else {
            Error_shaking login_and_password_shake = new Error_shaking(LoginField, PasswordField);
            login_and_password_shake.executeAnimation();
            new NotificationShower().showSimpleError("Ошибка входа!", "Неверный логин или пароль");
            LoginField.clear();
            PasswordField.clear();
        }

    }
    @FXML
    private void goToNewPane(Node node) {
        MainPane.getChildren().clear();
        MainPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(2500));
        if(node == admin_home_page){
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
    @FXML
    private void goToAdminRegistrationPage (ActionEvent some_event) {;
       goToNewPane(registration);
    }
    @FXML
    private void goBackToOpenWindow (ActionEvent some_event) {;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("Open_window.fxml"));
        MainPane.getChildren().clear();
        try {
            open_window = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainPane.getChildren().add(open_window);
    }
}
