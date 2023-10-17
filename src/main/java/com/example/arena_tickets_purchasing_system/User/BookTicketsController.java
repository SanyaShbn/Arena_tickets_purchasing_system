package com.example.arena_tickets_purchasing_system.User;

import com.example.arena_tickets_purchasing_system.Admin.AdminMatchesWindowController;
import com.example.arena_tickets_purchasing_system.Admin.AdminTicketsController;
import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.animations.NotificationShower;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static com.example.arena_tickets_purchasing_system.Constant.ADMIN_TICKETS_TABLE;


public class BookTicketsController {

    @FXML
    private AnchorPane MainPane;
    @FXML
    private Label AmountLabel;

    @FXML
    private Label DateLabel;

    @FXML
    private MenuButton SectorsMenuButton;

    @FXML
    private Label TimeLabel;

    @FXML
    private Label TypeLabel;

    @FXML
    private TextField allTickets;

    @FXML
    private TextField amountToBuy;

    @FXML
    private Label OppLabel;

    @FXML
    private Button submitChanges;
    @FXML
    private MenuItem SectorA;

    @FXML
    private MenuItem SectorB;

    @FXML
    private MenuItem SectorC;

    @FXML
    private MenuItem SectorD;

    @FXML
    private MenuItem SectorE;

    @FXML
    private MenuItem SectorF;

    @FXML
    private MenuItem SectorG;

    @FXML
    private MenuItem SectorH;

    @FXML
    private MenuItem SectorI;

    @FXML
    private MenuItem SectorVIP;

    private AdminMatchesWindowController.Match Match;
    AdminTicketsController.MatchTickets Ticket = new AdminTicketsController.MatchTickets(0, 0, 0, 0,
            0, 0,0 , 0, 0, 0, 0,0);
    @FXML
    void initialize() {
        OppLabel.setText(Match.getOpponent());
        TypeLabel.setText(Match.getType());
        TimeLabel.setText(Match.getTime());
        DateLabel.setText(Match.getDate());
        AmountLabel.setText(String.valueOf(Match.getAmount()));
        String select = "SELECT * FROM " + ADMIN_TICKETS_TABLE + " WHERE id_Match = " + Match.getId();
        PreparedStatement prStr = null;
        try {
            prStr = new DatabaseHandler().getDbConnection("tickets").prepareStatement(select);
            ResultSet result = prStr.executeQuery();
            if(result.next()) {
                Ticket.setId(result.getInt("id_Match"));
                Ticket.setAmount(result.getInt("Tickets_amount"));
                Ticket.setVipSector(result.getInt("VIP"));
                Ticket.setSectorA(result.getInt("Sector_A"));
                Ticket.setSectorB(result.getInt("Sector_B"));
                Ticket.setSectorC(result.getInt("Sector_C"));
                Ticket.setSectorD(result.getInt("Sector_D"));
                Ticket.setSectorE(result.getInt("Sector_E"));
                Ticket.setSectorF(result.getInt("Sector_F"));
                Ticket.setSectorG(result.getInt("Sector_G"));
                Ticket.setSectorH(result.getInt("Sector_H"));
                Ticket.setSectorI(result.getInt("Sector_I"));

            }
            else{
                new NotificationShower().showSimpleNotification("Уведомление!", "Билетов на данный матч нет в продаже");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void setVIPSector (ActionEvent some_event) {
        allTickets.setText(String.valueOf(Ticket.getVipSector()));
    }
    @FXML
    private void setSectorA (ActionEvent some_event) {
        allTickets.setText(String.valueOf(Ticket.getSectorA()));
    }
    @FXML
    private void setSectorB (ActionEvent some_event) {
        allTickets.setText(String.valueOf(Ticket.getSectorB()));
    }
    @FXML
    private void setSectorC (ActionEvent some_event) {
        allTickets.setText(String.valueOf(Ticket.getSectorC()));
    }
    @FXML
    private void setSectorD (ActionEvent some_event) {
        allTickets.setText(String.valueOf(Ticket.getSectorD()));
    }
    @FXML
    private void setSectorE (ActionEvent some_event) {
        allTickets.setText(String.valueOf(Ticket.getSectorE()));
    }
    @FXML
    private void setSectorF (ActionEvent some_event) {
        allTickets.setText(String.valueOf(Ticket.getSectorF()));
    }
    @FXML
    private void setSectorG (ActionEvent some_event) {
        allTickets.setText(String.valueOf(Ticket.getSectorG()));
    }
    @FXML
    private void setSectorH (ActionEvent some_event) {
        allTickets.setText(String.valueOf(Ticket.getSectorH()));
    }
    @FXML
    private void setSectorI (ActionEvent some_event) {
        allTickets.setText(String.valueOf(Ticket.getSectorI()));
    }
    @FXML
    private void submitChanges (ActionEvent some_event){
        MainPane.getScene().getWindow().hide();
    }
    public void setMatch(AdminMatchesWindowController.Match match){
        Match = match;
    }
    public AdminMatchesWindowController.Match getMatch(){
        return Match;
    }
}
