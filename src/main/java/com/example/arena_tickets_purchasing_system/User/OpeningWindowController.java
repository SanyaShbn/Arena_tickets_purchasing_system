package com.example.arena_tickets_purchasing_system.User;

import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.User.User;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import com.example.arena_tickets_purchasing_system.animations.Error_shaking;
import com.example.arena_tickets_purchasing_system.animations.NotificationShower;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.arena_tickets_purchasing_system.Constant.*;

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

    AnchorPane main_menu;
    @FXML
    void initialize() {
        FXMLLoader main_menu_loader = new FXMLLoader();
        main_menu_loader.setLocation((getClass().getResource("main_menu.fxml")));
        try {
            main_menu = main_menu_loader.load();
            String select = "SELECT * FROM " + NEWS_TABLE;
            PreparedStatement prStr = null;
            try {

                prStr = new DatabaseHandler().getDbConnection("news").prepareStatement(select);
                ResultSet result = prStr.executeQuery();
                double Y = 0;

                while (result.next() && Y < 750) {
                    TextArea news_area = new TextArea();
                    news_area.setPrefWidth(590);
                    news_area.setPrefHeight(30);
                    news_area.setLayoutX(478);
                    news_area.setLayoutY(500 + Y);
                    news_area.setText(result.getString(PUBLISHING_DATE) + " "
                            + result.getString(PUBLISHING_TIME) + " " + result.getString(CONTESTS));
                    Y = Y + 50;
                    main_menu.getChildren().add(news_area);
                }

            } catch (SQLException e) {
                new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

    @FXML
    private void goToNewPane(Node node) {
        MainPane.getChildren().clear();
        MainPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(2500));
        ft.setNode(node);
        ft.setFromValue(2);
        ft.setToValue(2);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
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
            goToNewPane(main_menu);
        }
        else{
            Error_shaking login_and_password_shake = new Error_shaking(LoginField, PasswordField);
            login_and_password_shake.executeAnimation();
        }

    }

}
