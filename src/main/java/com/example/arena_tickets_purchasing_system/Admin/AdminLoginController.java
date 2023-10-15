package com.example.arena_tickets_purchasing_system.Admin;

import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import com.example.arena_tickets_purchasing_system.animations.Error_shaking;
import com.example.arena_tickets_purchasing_system.animations.NotificationShower;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;

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
    private Button SignUpButton;
    @FXML
    private void signInAdmin() throws SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();

        String login = LoginField.getText();
        String password = PasswordField.getText();

        Admin admin = new Admin(login, password);

        dbHandler.getAdmin(admin);
        loginAdmin(login, password);
    }
    private void loginAdmin(String login, String password) throws SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        Admin admin = new Admin();
        admin.setAdmin_login(login);
        admin.setAdmin_password(password);
        ResultSet result = dbHandler.getAdmin(admin);

        int numb = 0;

        while(result.next()){
            numb++;
        }

        if(numb >= 1){
            SignInButton.getScene().getWindow().hide();
            WindowsOpener main_menu_window = new WindowsOpener("admin_home_page.fxml");
        }
        else{
            Error_shaking login_and_password_shake = new Error_shaking(LoginField, PasswordField);
            login_and_password_shake.executeAnimation();
            new NotificationShower().showSimpleError("Ошибка входа!", "Неверный логин или пароль");
        }

    }
    @FXML
    private void goToAdminRegistrationPage (ActionEvent some_event) {
        SignInButton.getScene().getWindow().hide();
        WindowsOpener registration_window = new WindowsOpener("admin_registration.fxml");
    }
}
