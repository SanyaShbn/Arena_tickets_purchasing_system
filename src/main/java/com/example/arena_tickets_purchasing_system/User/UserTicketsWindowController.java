package com.example.arena_tickets_purchasing_system.User;

import com.example.arena_tickets_purchasing_system.Admin.AdminMatchesWindowController;
import com.example.arena_tickets_purchasing_system.Admin.AdminTeamRosterController;
import com.example.arena_tickets_purchasing_system.Admin.AdminTicketsController;
import com.example.arena_tickets_purchasing_system.ArenaTicketsPurchasingSystem;
import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.arena_tickets_purchasing_system.Constant.*;
import static com.example.arena_tickets_purchasing_system.Constant.CONTESTS;

public class UserTicketsWindowController {
    @FXML
    private MenuItem MainMenuItem;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private MenuItem MatchesItem;

    @FXML
    private MenuButton MenuButton;

    @FXML
    private MenuItem TeamItem;
    private User User = new User().readUserFromFile();
    @FXML
    void initialize() {
        String select = "SELECT * FROM " + USERS_TICKETS_TABLE + " WHERE LoginUsers =?";
        PreparedStatement prStr = null;
        try {
            prStr = new DatabaseHandler().getDbConnection("users_tickets").prepareStatement(select);
            prStr.setString(1, User.getUser_login());
            ResultSet result = prStr.executeQuery();
            double Y = 0;
            while (result.next()) {
                String matches_select = "SELECT * FROM " + MATCHES_TABLE + " WHERE idMatches =?";
                PreparedStatement prStrMatch = null;
                prStrMatch = new DatabaseHandler().getDbConnection("matches").prepareStatement(matches_select);
                prStrMatch.setString(1, String.valueOf(result.getInt("id_Match")));
                ResultSet result_matches = prStrMatch.executeQuery();
                while(result_matches.next()){
                    AnchorPane tickets_view = (AnchorPane) setTicketsView(new AdminMatchesWindowController.Match(result_matches.getInt(MATCH_ID), result_matches.getString(MATCHES_DATE),
                                    result_matches.getString(MATCHES_TIME), result_matches.getString(MATCH_TYPE), result_matches.getString(OPP_TEAM), result_matches.getInt(TICKETS_AMOUNT)),
                            new AdminTicketsController.MatchTickets(result.getInt("id_Match"), result.getInt("Tickets_amount"), result.getInt("Sector_VIP"),
                                    result.getInt("Sector_A"), result.getInt("Sector_B"), result.getInt("Sector_C"), result.getInt("Sector_D"),
                                    result.getInt("Sector_E"), result.getInt("Sector_F"), result.getInt("Sector_G"), result.getInt("Sector_H"),
                                    result.getInt("Sector_I")));
                    tickets_view.setLayoutX(50);
                    tickets_view.setLayoutY(215 + Y);
                    Y = Y + 100;
                    MainPane.getChildren().add(tickets_view);
                }
            }

        } catch (SQLException e) {
            new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
    @FXML
    private void backToMainMenu (ActionEvent some_event) {
        MainPane.getScene().getWindow().hide();
        new WindowsOpener("main_menu.fxml");
    }

    @FXML
    private void viewMatches (ActionEvent some_event) {
        MainPane.getScene().getWindow().hide();
        new WindowsOpener("matches.fxml");
    }

    @FXML
    private void viewTeamRoster (ActionEvent some_event) {
        MainPane.getScene().getWindow().hide();
        new WindowsOpener("user_roster.fxml");
    }
    public Node setTicketsView (AdminMatchesWindowController.Match match, AdminTicketsController.MatchTickets tickets){
        AnchorPane ticket_view;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("ticket_view.fxml"));
        TicketsViewController controller = new TicketsViewController();
        controller.setTickets(tickets);
        controller.setMatch(match);
        loader.setController(controller);
        try {
            ticket_view = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ticket_view;
    }
}
