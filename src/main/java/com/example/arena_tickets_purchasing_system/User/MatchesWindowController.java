package com.example.arena_tickets_purchasing_system.User;

import com.example.arena_tickets_purchasing_system.Admin.AdminMatchesWindowController;
import com.example.arena_tickets_purchasing_system.ArenaTicketsPurchasingSystem;
import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import com.example.arena_tickets_purchasing_system.animations.NotificationShower;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.example.arena_tickets_purchasing_system.Constant.*;

public class MatchesWindowController implements Initializable {

    @FXML
    private AnchorPane MainPane;

    @FXML
    private TableView<AdminMatchesWindowController.Match> table;

    @FXML
    private TableColumn<AdminMatchesWindowController.Match, Integer> idMatch;

    @FXML
    private TableColumn<AdminMatchesWindowController.Match, String> dateMatch;

    @FXML
    private TableColumn<AdminMatchesWindowController.Match, String> timeMatch;

    @FXML
    private TableColumn<AdminMatchesWindowController.Match, Integer> amountTickets;

    @FXML
    private TableColumn<AdminMatchesWindowController.Match, String> opponentTeam;
    @FXML
    private TableColumn<AdminMatchesWindowController.Match, String> typeMatch;
    @FXML
    private MenuButton filterMatches;
    @FXML
    private Button bookTickets;
    @FXML
    private Button updateTable;
    @FXML
    private MenuButton MenuButton;
    @FXML
    private MenuItem MainMenuItem;
    @FXML
    private MenuItem TeamItem;
    @FXML
    private MenuItem TicketsItem;
    @FXML
    private MenuButton DateMenuButton;

    @FXML
    private MenuButton TypeMenuButton;
    @FXML
    private MenuButton OpponentMenuButton;
    AnchorPane new_pane;

    ObservableList<AdminMatchesWindowController.Match> list_of_matches = FXCollections.observableArrayList();
    ObservableList<AdminMatchesWindowController.Match> filtered_list = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookTickets.setOnMouseExited(event ->{
            bookTickets.setStyle("-fx-background-color: #00BFFF; -fx-border-color: #00BFFF; -fx-text-fill: #000000");
        });
        bookTickets.setOnMouseEntered(event ->{
            bookTickets.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #00BFFF; -fx-text-fill: #00BFFF");
        });
        filterMatches.setOnMouseEntered(event ->{
            filterMatches.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #00BFFF; -fx-text-fill: #00BFFF");
        });
        filterMatches.setOnMouseExited(event ->{
            filterMatches.setStyle("-fx-background-color: #00BFFF; -fx-border-color: #00BFFF; -fx-text-fill: #000000");
        });
        updateTable.setOnMouseEntered(event ->{
            updateTable.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #00BFFF; -fx-text-fill: #00BFFF");
        });
        updateTable.setOnMouseExited(event ->{
            updateTable.setStyle("-fx-background-color: #00BFFF; -fx-border-color: #00BFFF; -fx-text-fill: #000000");
        });
        DateMenuButton.setOnMouseEntered(event ->{
            DateMenuButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #00BFFF; -fx-text-fill: #00BFFF");
        });
        DateMenuButton.setOnMouseExited(event ->{
           DateMenuButton.setStyle("-fx-background-color: #00BFFF; -fx-border-color: #00BFFF; -fx-text-fill: #000000");
        });
        OpponentMenuButton.setOnMouseEntered(event ->{
           OpponentMenuButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #00BFFF; -fx-text-fill: #00BFFF");
        });
        OpponentMenuButton.setOnMouseExited(event ->{
           OpponentMenuButton.setStyle("-fx-background-color: #00BFFF; -fx-border-color: #00BFFF; -fx-text-fill: #000000");
        });
        TypeMenuButton.setOnMouseEntered(event ->{
            TypeMenuButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #00BFFF; -fx-text-fill: #00BFFF");
        });
        TypeMenuButton.setOnMouseExited(event ->{
            TypeMenuButton.setStyle("-fx-background-color: #00BFFF; -fx-border-color: #00BFFF; -fx-text-fill: #000000");
        });
        String select = "SELECT * FROM " + MATCHES_TABLE;
        try {
            PreparedStatement prStr = new DatabaseHandler().getDbConnection( "matches").prepareStatement(select);
            ResultSet result = prStr.executeQuery();
            while (result.next()) {
                boolean flag_date = false; boolean flag_opponents = false;
                MenuItem sorted_dates = new MenuItem(result.getString(MATCHES_DATE));
                MenuItem sorted_opponents = new MenuItem(result.getString(OPP_TEAM));
                for(MenuItem item : DateMenuButton.getItems()) {
                    if (item.getText().equals(sorted_dates.getText())) { flag_date = true;break;}
                }
                if(flag_date == false){
                    DateMenuButton.getItems().addAll(sorted_dates);
                    sorted_dates.setOnAction(event -> {
                        int i = 0;
                       while(i < filtered_list.size()) {
                            if (!filtered_list.get(i).getDate().substring(4).equals(sorted_dates.getText())) {
                                filtered_list.remove(filtered_list.get(i));
                            }
                            else{i++;}
                        }
                       if(filtered_list.isEmpty()){
                           new NotificationShower().showSimpleNotification("Уведомление","Подходящих матчей не найдено");
                       }
                        table.setItems(filtered_list);
                    });
                }
                for(MenuItem item : OpponentMenuButton.getItems()) {
                    if (item.getText().equals(sorted_opponents.getText())) { flag_opponents = true;break;}
                }
                if(flag_opponents == false) {
                    OpponentMenuButton.getItems().addAll(sorted_opponents);
                    sorted_opponents.setOnAction(event -> {
                        int i = 0;
                        while(i < filtered_list.size()) {
                            if (!filtered_list.get(i).getOpponent().equals(sorted_opponents.getText())) {
                                filtered_list.remove(filtered_list.get(i));
                            }
                            else{i++;}
                        }
                        if(filtered_list.isEmpty()){
                            new NotificationShower().showSimpleNotification("Уведомление","Подходящих матчей не найдено");
                        }
                        table.setItems(filtered_list);
                    });
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        idMatch.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateMatch.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeMatch.setCellValueFactory(new PropertyValueFactory<>("time"));
        opponentTeam.setCellValueFactory(new PropertyValueFactory<>("opponent"));
        amountTickets.setCellValueFactory(new PropertyValueFactory<>("amount"));
        typeMatch.setCellValueFactory(new PropertyValueFactory<>("type"));

        try {
            loadFromDataBase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadFromDataBase() throws SQLException, ClassNotFoundException {
        String select = "SELECT * FROM " + MATCHES_TABLE;
        PreparedStatement prStr = new DatabaseHandler().getDbConnection( "matches").prepareStatement(select);
        ResultSet result = prStr.executeQuery();
        try {
            while (result.next()) {
                DateTimeFormatter dtfInput = DateTimeFormatter.ofPattern("u-M-d", Locale.ENGLISH);
                DateTimeFormatter dtfOutput = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH);
                int ID = result.getInt(MATCH_ID);
                String date = LocalDate.parse(result.getString(MATCHES_DATE), dtfInput).format(dtfOutput).toUpperCase() + " " + result.getString(MATCHES_DATE);
                String time = result.getString(MATCHES_TIME);
                String opponent = result.getString(OPP_TEAM);
                int tickets = result.getInt(TICKETS_AMOUNT);
                String type = result.getString(MATCH_TYPE);
                list_of_matches.add(new AdminMatchesWindowController.Match(ID, date, time, type, opponent, tickets));
                filtered_list.add(new AdminMatchesWindowController.Match(ID, date, time, type, opponent, tickets));
            }
        } catch (SQLException e) {
            new RuntimeException(e);
        }
        table.setItems(list_of_matches);
    }
    @FXML
    private void filterByTypeHome(ActionEvent event) {
        int i = 0;
        while(i < filtered_list.size()){
            if(!filtered_list.get(i).getType().equals("Домашний")){
                filtered_list.remove(filtered_list.get(i));
            }
            else{i++;}
        }
        if(filtered_list.isEmpty()){
            new NotificationShower().showSimpleNotification("Уведомление","Подходящих матчей не найдено");
        }
        table.setItems(filtered_list);
    }
    @FXML
    private void filterByTypeGuest(ActionEvent event) {
        int i = 0;
        while(i < filtered_list.size()){
            if(!filtered_list.get(i).getType().equals("Гостевой")){
                filtered_list.remove(filtered_list.get(i));
            }
            else{i++;}
        }
        if(filtered_list.isEmpty()){
            new NotificationShower().showSimpleNotification("Уведомление","Подходящих матчей не найдено");
        }
        table.setItems(filtered_list);
    }
    @FXML
    private void updateTable(ActionEvent event) {
        updateInfo();
    }

    private void updateInfo()
    {
        table.getItems().clear();
        list_of_matches.clear();
        filtered_list.clear();
        try {
            loadFromDataBase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void bookTickets(ActionEvent event) {
        AdminMatchesWindowController.Match match = table.getSelectionModel().getSelectedItem();
        if(match == null){
            new NotificationShower().showSimpleError("Ошибка покупки!", "Выберите матч, билеты на который хотели бы приобрести");
        }else
        if(match.getType().equals("Гостевой")){
            new NotificationShower().showWarning("Внимание!", "Выбранный матч является гостевым. Вы не можете приобрести билет в нашем городе");
        }else if(match.getAmount() == 0){
            new NotificationShower().showWarning("Внимание!", "На данный матч все билеты уже раскуплены");
        }
        else{
            new WindowsOpener("booking.fxml", match);
        }

    }
    @FXML
    private void backToMainMenu (ActionEvent some_event) {
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
    private void viewUserTickets (ActionEvent some_event) {
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

}
