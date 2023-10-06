package com.example.arena_tickets_purchasing_system.Admin;

import com.example.arena_tickets_purchasing_system.Admin.Admin;
import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import com.example.arena_tickets_purchasing_system.animations.Error_shaking;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRegistrationController {

    @FXML
    private TextArea LoginField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button SignUpButton;
    @FXML
    private Text RegistrationFailedMessage;

    @FXML
    void initialize() {

        RegistrationFailedMessage.setVisible(false);
        SignUpButton.setOnAction(event -> {
            String login = LoginField.getText().trim();
            String password = PasswordField.getText().trim();
            if(!login.equals(" ") && !password.equals(" ")){
                try {
                    registerAdmin(login, password);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                System.out.println("Ошибка регистрации!");
            }
        });
    }

    private void registerAdmin(String login, String password) throws SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        Admin admin = new Admin();
        admin.setAdmin_login(login);
        admin.setAdmin_password(password);
        ResultSet result = dbHandler.getAdmin(admin);

        int numb = 0;

        while(result.next()){
            numb++;
        }

        if(numb > 0){
            Error_shaking login_and_password_shake = new Error_shaking(LoginField, PasswordField);
            login_and_password_shake.executeAnimation();
            RegistrationFailedMessage.setVisible(true);

        }
        else{
            dbHandler.signUpAdmins(new Admin(login,password));
            SignUpButton.getScene().getWindow().hide();
            WindowsOpener open_window = new WindowsOpener("admin_login.fxml");

        }

    }

}

