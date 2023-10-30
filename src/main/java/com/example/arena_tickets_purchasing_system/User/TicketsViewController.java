package com.example.arena_tickets_purchasing_system.User;

import com.example.arena_tickets_purchasing_system.Admin.AdminMatchesWindowController;
import com.example.arena_tickets_purchasing_system.Admin.AdminTeamRosterController;
import com.example.arena_tickets_purchasing_system.Admin.AdminTicketsController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TicketsViewController {

    @FXML
    private Label date;
    @FXML
    private Label opponent;

    @FXML
    private TextField sectorsInfo;
    @FXML
    private Label time;
    private AdminTicketsController.MatchTickets Tickets;
    private AdminMatchesWindowController.Match Match;

    @FXML
    void initialize() {
      date.setText(Match.getDate());
      time.setText(Match.getTime());
      opponent.setText(Match.getOpponent());
        String sectors_info = "";
        if(Tickets.getVipSector() != 0){
            sectors_info += "VIP_Сектор - " + Tickets.getVipSector() + " ";
        }
        if(Tickets.getSectorA() != 0){
            sectors_info += "Сектор A - " + Tickets.getSectorA() + " ";
        }
        if(Tickets.getSectorB() != 0){
            sectors_info += "Сектор B - " + Tickets.getSectorB() + " ";
        }
        if(Tickets.getSectorC() != 0){
            sectors_info += "Сектор C - " + Tickets.getSectorC() + " ";
        }
        if(Tickets.getSectorD() != 0){
            sectors_info += "Сектор D - " + Tickets.getSectorD() + " ";
        }
        if(Tickets.getSectorE() != 0){
            sectors_info += "Сектор E - " + Tickets.getSectorE() + " ";
        }
        if(Tickets.getSectorF() != 0){
            sectors_info += "Сектор F - " + Tickets.getSectorF() + " ";
        }
        if(Tickets.getSectorG() != 0){
            sectors_info += "Сектор G - " + Tickets.getSectorG() + " ";
        }
        if(Tickets.getSectorH() != 0){
            sectors_info += "Сектор H - " + Tickets.getSectorH() + " ";
        }
        if(Tickets.getSectorI() != 0){
            sectors_info += "Сектор I - " + Tickets.getSectorI() + " ";
        }
        sectorsInfo.setText(sectors_info + "Всего - " + Tickets.getAmount());
    }
    public void setTickets(AdminTicketsController.MatchTickets tickets){
        Tickets = tickets;
    }
    public AdminTicketsController.MatchTickets getTickets(){
        return Tickets;
    }
    public void setMatch(AdminMatchesWindowController.Match match){
        Match = match;
    }
    public AdminMatchesWindowController.Match getMatch(){
        return Match;
    }
}
