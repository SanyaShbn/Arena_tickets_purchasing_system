package com.example.arena_tickets_purchasing_system.User;

import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static com.example.arena_tickets_purchasing_system.Constant.*;

public class TeamRosterWindowController {

    @FXML
    private AnchorPane MainPane;
    @FXML
    private MenuButton MenuButton;

    @FXML
    private MenuItem MainMenuItem;
    @FXML
    private MenuItem MatchesItem;
    @FXML
    private MenuItem TicketsItem;
    @FXML
    void initialize() {
        String select = "SELECT * FROM " + PLAYERS_TABLE;
        PreparedStatement prStr = null;
        try {

            prStr = new DatabaseHandler().getDbConnection("players").prepareStatement(select);
            ResultSet result = prStr.executeQuery();
            double Y = 0;
            double X = 0;
            while (result.next()) {
                AnchorPane player_info = new AnchorPane();
                ImageView player_image = new ImageView();
                ImageView flag = new ImageView();
                flag.setFitHeight(20);flag.setFitWidth(35);
                setFlag(result.getString(NATION), flag);
                Text name = new Text();
                name.setText("Имя - " + result.getString(PLAYER_NAME));
                name.setLayoutX(0);
                name.setLayoutY(155);
                Text role = new Text();
                role.setText("Амплуа - " + result.getString(ROLE));
                role.setLayoutX(0);
                role.setLayoutY(165);
                Text numb = new Text();
                numb.setText("Номер - " +result.getInt(JERSEY));
                numb.setLayoutX(0);
                numb.setLayoutY(175);
                Text age = new Text();
                age.setText("Возраст - " + result.getInt(AGE));
                age.setLayoutX(0);
                age.setLayoutY(185);
                Text height = new Text();
                height.setLayoutX(0);
                height.setLayoutY(195);
                height.setText("Рост, см - " + result.getInt(HEIGHT));
                Text weight = new Text();
                weight.setLayoutX(0);
                weight.setLayoutY(205);
                weight.setText("Вес, кг - " + result.getInt(WEIGHT));
                Text team = new Text();
                team.setLayoutX(0);
                team.setLayoutY(215);
                team.setText("Сезонов в команде - " + result.getInt(TEAM));
                Text league = new Text();
                league.setText("Сезонов в лиге - " + result.getInt(LEAGUE));
                league.setLayoutX(0);
                league.setLayoutY(225);
                player_image.setFitWidth(140);
                player_image.setFitHeight(140);
                player_image.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\player_card.png"));
                player_info.setPrefWidth(200);
                player_info.setPrefHeight(250);
                player_info.setLayoutX(15 + X);
                player_info.setLayoutY(240+ Y);
                X = X + 250;
                if(player_info.getLayoutX() > 1200) {
                    Y = Y + 250;
                    X = 0;
                }
                player_info.getChildren().addAll(player_image, flag, name, role , age, height, weight, numb, team, league);

                MainPane.getChildren().add(player_info);
            }

        } catch (SQLException e) {
            new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void backToMainMenu (ActionEvent some_event) {
        MainPane.getScene().getWindow().hide();
        new WindowsOpener("main_menu.fxml");}

    @FXML
    private void viewUserTickets (ActionEvent some_event) {
        MainPane.getScene().getWindow().hide();
        new WindowsOpener("user_tickets.fxml");
    }
    private ImageView setFlag (String nationality, ImageView flag) {
      switch (nationality){
          case "Беларусь": flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\belarus.png"));break;
          case "Россия": flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\Russia.png"));break;
          case "США": flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\Usa.png"));break;
          case "Канада": flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\canada.png"));break;
          case "Швеция": flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\Sweden.png")); break;
          case "Финляндия": flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\finland.png"));break;
          case "Чехия": flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\Czech.png"));break;
          case "Словакия": flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\Slovakia.png"));break;
      }
        return flag;
    }
    @FXML
    private void viewMatches (ActionEvent some_event) {
        MainPane.getScene().getWindow().hide();
        new WindowsOpener("matches.fxml");
    }
}
