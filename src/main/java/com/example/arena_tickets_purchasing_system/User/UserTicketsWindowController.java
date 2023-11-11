package com.example.arena_tickets_purchasing_system.User;

import com.example.arena_tickets_purchasing_system.*;
import com.example.arena_tickets_purchasing_system.Admin.AdminMatchesWindowController;
import com.example.arena_tickets_purchasing_system.Admin.AdminTicketsController;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static com.example.arena_tickets_purchasing_system.Constant.*;

public class UserTicketsWindowController {
    @FXML
    private MenuItem MainMenuItem;

    @FXML
    private AnchorPane MainPane;
    @FXML
    private AnchorPane TicketsPane;

    @FXML
    private MenuItem MatchesItem;

    @FXML
    private MenuButton MenuButton;

    @FXML
    private MenuItem TeamItem;

    private User User = new User().readUserFromFile();
    AnchorPane new_pane;
    @FXML
    void initialize() {
        updateWindow();
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
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("main_menu.fxml"));
        try {
            new_pane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainPane.getChildren().clear();
        MainPane.getChildren().add(new_pane);
    }

    @FXML
    private void viewMatches (ActionEvent some_event) {
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

    @FXML
    private void viewTeamRoster (ActionEvent some_event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("user_roster.fxml"));
        MainPane.getChildren().clear();
        try {
            new_pane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainPane.getChildren().add(new_pane);
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

    private void updateWindow () {
        TicketsPane.getChildren().clear();
        String select = "SELECT * FROM " + USERS_TICKETS_TABLE + " WHERE LoginUsers =?";
        PreparedStatement prStr;
        try {
            prStr = new DatabaseHandler().getDbConnection("users_tickets").prepareStatement(select);
            prStr.setString(1, User.getUser_login());
            ResultSet result = prStr.executeQuery();
            double Y = 0;
            while (result.next()) {
                String matches_select = "SELECT * FROM " + MATCHES_TABLE + " WHERE idMatches =?";
                String tickets_select = "SELECT * FROM " + ADMIN_TICKETS_TABLE + " WHERE id_Match =?";
                PreparedStatement prStrMatch;
                prStrMatch = new DatabaseHandler().getDbConnection("matches").prepareStatement(matches_select);
                prStrMatch.setString(1, String.valueOf(result.getInt("id_Match")));
                ResultSet result_matches = prStrMatch.executeQuery();
                    while (result_matches.next()) {
                        AdminMatchesWindowController.Match match = new AdminMatchesWindowController.Match(result_matches.getInt(MATCH_ID), result_matches.getString(MATCHES_DATE),
                                result_matches.getString(MATCHES_TIME), result_matches.getString(MATCH_TYPE), result_matches.getString(OPP_TEAM), result_matches.getInt(TICKETS_AMOUNT));
                        AdminTicketsController.MatchTickets user_tickets = new AdminTicketsController.MatchTickets(result.getInt("id_Match"), result.getInt("Tickets_amount"), result.getInt("Sector_VIP"),
                                result.getInt("Sector_A"), result.getInt("Sector_B"), result.getInt("Sector_C"), result.getInt("Sector_D"),
                                result.getInt("Sector_E"), result.getInt("Sector_F"), result.getInt("Sector_G"), result.getInt("Sector_H"),
                                result.getInt("Sector_I"));
                        AnchorPane tickets_view = (AnchorPane) setTicketsView(match, user_tickets);
                        tickets_view.setLayoutX(50);
                        tickets_view.setLayoutY(50 + Y);
                        Y = Y + 100;
                        if ((215 + Y) >= 800) {
                            Y = 0;
                        }
                        ImageView cancel_image = new ImageView("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\cross.png");
                        cancel_image.setFitWidth(20);
                        cancel_image.setFitHeight(20);
                        MenuItem cancelItem = new MenuItem("Отменить покупку", cancel_image);
                        cancelItem.setOnAction(event -> {
                            String delete = "DELETE FROM " + Constant.USERS_TICKETS_TABLE + " WHERE LoginUsers =? AND id_Match =?";
                            String update_matches = "UPDATE " + Constant.MATCHES_TABLE + " SET Tickets_Amount =?" + " WHERE idMatches =?";
                            String update_tickets = "UPDATE " + Constant.ADMIN_TICKETS_TABLE + " SET Tickets_amount =?, VIP =?, Sector_A =?, Sector_B =?, Sector_C =?, Sector_D =?," +
                                    " Sector_E =?, Sector_F =?, Sector_G =?, Sector_H =?, Sector_I =? WHERE id_Match =?";
                            PreparedStatement prStrDelete;
                            PreparedStatement prStrUpdateMatches;
                            PreparedStatement prStrUpdateTickets;
                            try {
                                PreparedStatement prStrTickets;
                                prStrTickets = new DatabaseHandler().getDbConnection("tickets").prepareStatement(tickets_select);
                                prStrTickets.setString(1, String.valueOf(user_tickets.getId()));
                                ResultSet result_tickets = prStrTickets.executeQuery();
                                if (result_tickets.next()) {
                                    AdminTicketsController.MatchTickets tickets = new AdminTicketsController.MatchTickets(result_tickets.getInt("id_Match"), result_tickets.getInt("Tickets_amount"), result_tickets.getInt("VIP"),
                                            result_tickets.getInt("Sector_A"), result_tickets.getInt("Sector_B"), result_tickets.getInt("Sector_C"), result_tickets.getInt("Sector_D"),
                                            result_tickets.getInt("Sector_E"), result_tickets.getInt("Sector_F"), result_tickets.getInt("Sector_G"), result_tickets.getInt("Sector_H"),
                                            result_tickets.getInt("Sector_I"));

                                    prStrDelete = new DatabaseHandler().getDbConnection("users_tickets").prepareStatement(delete);
                                    prStrUpdateMatches = new DatabaseHandler().getDbConnection("matches").prepareStatement(update_matches);
                                    prStrUpdateTickets = new DatabaseHandler().getDbConnection("tickets").prepareStatement(update_tickets);

                                    prStrDelete.setString(1, User.getUser_login());
                                    prStrDelete.setString(2, String.valueOf(user_tickets.getId()));
                                    prStrUpdateMatches.setString(1, String.valueOf(match.getAmount() + user_tickets.getAmount()));
                                    prStrUpdateMatches.setString(2, String.valueOf(user_tickets.getId()));
                                    prStrUpdateTickets.setString(1, String.valueOf(tickets.getAmount() + user_tickets.getAmount()));
                                    prStrUpdateTickets.setString(2, String.valueOf(tickets.getVipSector() + user_tickets.getVipSector()));
                                    prStrUpdateTickets.setString(3, String.valueOf(tickets.getSectorA() + user_tickets.getSectorA()));
                                    prStrUpdateTickets.setString(4, String.valueOf(tickets.getSectorB() + user_tickets.getSectorB()));
                                    prStrUpdateTickets.setString(5, String.valueOf(tickets.getSectorC() + user_tickets.getSectorC()));
                                    prStrUpdateTickets.setString(6, String.valueOf(tickets.getSectorD() + user_tickets.getSectorD()));
                                    prStrUpdateTickets.setString(7, String.valueOf(tickets.getSectorE() + user_tickets.getSectorE()));
                                    prStrUpdateTickets.setString(8, String.valueOf(tickets.getSectorF() + user_tickets.getSectorF()));
                                    prStrUpdateTickets.setString(9, String.valueOf(tickets.getSectorG() + user_tickets.getSectorG()));
                                    prStrUpdateTickets.setString(10, String.valueOf(tickets.getSectorH() + user_tickets.getSectorH()));
                                    prStrUpdateTickets.setString(11, String.valueOf(tickets.getSectorI() + user_tickets.getSectorI()));
                                    prStrUpdateTickets.setString(12, String.valueOf(user_tickets.getId()));

                                    prStrDelete.executeUpdate();
                                    prStrUpdateMatches.executeUpdate();
                                    prStrUpdateTickets.executeUpdate();
                            }} catch(SQLException e){
                                throw new RuntimeException(e);
                            } catch(ClassNotFoundException e){
                                throw new RuntimeException(e);
                            }
                            updateWindow();
                        });
                        ContextMenu contextMenu = new ContextMenu(cancelItem);
                        tickets_view.setOnContextMenuRequested(e -> contextMenu.show(tickets_view, e.getScreenX(), e.getScreenY()));
                        TicketsPane.getChildren().add(tickets_view);

                    }
                }

        } catch (SQLException e) {
            new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(TicketsPane.getChildren().size() == 0){
            Text notification = new Text("Вы ещё не приобрели ни одного билета");
            notification.setStyle("-fx-font-size: 36; -fx-fill: #FFFFFF;");
            notification.setLayoutX(450); notification.setLayoutY(300);
            TicketsPane.getChildren().add(notification);
        }
    }
}
