package com.example.arena_tickets_purchasing_system.User;

import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.User.User;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import com.example.arena_tickets_purchasing_system.animations.Error_shaking;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

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
    void initialize() {

            SignUpButton.setOnAction(event -> {
                String login = LoginField.getText().trim();
                String password = PasswordField.getText().trim();
                if(!login.equals(" ") && !password.equals(" ")){
                    try {
                        registerUser(login, password);
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

        }
        else{
            dbHandler.signUpUsers(new User(login,password));
            SignUpButton.getScene().getWindow().hide();
            WindowsOpener open_window = new WindowsOpener("Open_window.fxml");

        }

    }

}

