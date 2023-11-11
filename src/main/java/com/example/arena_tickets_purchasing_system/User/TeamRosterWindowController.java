package com.example.arena_tickets_purchasing_system.User;

import com.example.arena_tickets_purchasing_system.Admin.AdminTeamRosterController;
import com.example.arena_tickets_purchasing_system.ArenaTicketsPurchasingSystem;
import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
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

    AnchorPane new_pane;
    @FXML
    void initialize() {
        String select = "SELECT * FROM " + PLAYERS_TABLE;
        PreparedStatement prStr = null;
        try {

            prStr = new DatabaseHandler().getDbConnection("players").prepareStatement(select);
            ResultSet result = prStr.executeQuery();
            double Y = 230;
            double X = 15;
            while (result.next()) {

                AnchorPane player_card = (AnchorPane) setPlayerCard(new AdminTeamRosterController.Player(result.getString(PLAYER_NAME),
                        result.getString(ROLE), result.getInt(JERSEY), result.getString(NATION), result.getInt(AGE),
                        result.getInt(HEIGHT), result.getInt(WEIGHT), result.getInt(TEAM), result.getInt(LEAGUE)));
                player_card.setLayoutX(X);
                player_card.setLayoutY(Y);
                X = X + 250;
                if (player_card.getLayoutX() > 1100) {
                    Y = Y + 220;
                    X = 15;
                }
                MainPane.getChildren().add(player_card);
            }

        } catch (SQLException e) {
            new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void backToMainMenu(ActionEvent some_event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("main_menu.fxml"));
        MainPane.getChildren().clear();
        try {
            new_pane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainPane.getChildren().clear();
        MainPane.getChildren().add(new_pane);
    }

    @FXML
    private void viewUserTickets(ActionEvent some_event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("user_tickets.fxml"));
        MainPane.getChildren().clear();
        try {
            new_pane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainPane.getChildren().add(new_pane);
    }

    private ImageView setFlag(String nationality, ImageView flag) {
        switch (nationality) {
            case "Беларусь":
                flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\belarus.png"));
                break;
            case "Россия":
                flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\Russia.png"));
                break;
            case "США":
                flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\Usa.png"));
                break;
            case "Канада":
                flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\canada.png"));
                break;
            case "Швеция":
                flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\Sweden.png"));
                break;
            case "Финляндия":
                flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\finland.png"));
                break;
            case "Чехия":
                flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\Czech.png"));
                break;
            case "Словакия":
                flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\Slovakia.png"));
                break;
        }
        return flag;
    }

    @FXML
    private void viewMatches(ActionEvent some_event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("matches.fxml"));
        MainPane.getChildren().clear();
        try {
            new_pane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainPane.getChildren().add(new_pane);
    }
    public Node setPlayerCard (AdminTeamRosterController.Player player){
        AnchorPane player_card;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("player_card.fxml"));
        PlayerCardController controller = new PlayerCardController();
        controller.setPlayer(player);
        loader.setController(controller);
        try {
            player_card = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return player_card;
    }
}

