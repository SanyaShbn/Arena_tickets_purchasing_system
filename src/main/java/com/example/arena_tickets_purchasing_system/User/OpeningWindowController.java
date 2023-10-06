package com.example.arena_tickets_purchasing_system.User;

import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.User.User;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import com.example.arena_tickets_purchasing_system.animations.Error_shaking;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

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
    private Text ErrorMessage;
    @FXML
    private Hyperlink AdminLink;
    @FXML
    void initialize() {

        ErrorMessage.setVisible(false);
        SignUpButton.setOnAction(event -> {
            SignInButton.getScene().getWindow().hide();
            WindowsOpener registration_window = new WindowsOpener("registration.fxml");
        });

        SignInButton.setOnAction(event -> {
            try {
                signInUser();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        AdminLink.setOnAction(event -> {
            AdminLink.getScene().getWindow().hide();
            WindowsOpener registration_window = new WindowsOpener("admin_login.fxml");
        });
    }
    private void signInUser() throws SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();

        String login = LoginField.getText();
        String password = PasswordField.getText();

        User user = new User(login, password);

        dbHandler.getUser(user);
        loginUser(login, password);
    }

    //private void setEnterEvent(Parent root){
     //   root.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
      //    if(event.getCode() == KeyCode.ENTER){
           //   System.out.println("Нажата Enter");
         // }
      //  });
   // }
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
            SignInButton.getScene().getWindow().hide();
            WindowsOpener main_menu_window = new WindowsOpener("main_menu.fxml");
        }
        else{
            Error_shaking login_and_password_shake = new Error_shaking(LoginField, PasswordField);
            login_and_password_shake.executeAnimation();
            ErrorMessage.setVisible(true);
        }

    }

}
