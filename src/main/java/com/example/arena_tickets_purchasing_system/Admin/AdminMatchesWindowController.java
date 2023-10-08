package com.example.arena_tickets_purchasing_system.Admin;


import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.arena_tickets_purchasing_system.Constant.*;

public class AdminMatchesWindowController implements Initializable {

    @FXML
    private TableView<Match> table;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Match, Integer> idMatch;

    @FXML
    private TableColumn<Match, String> dateMatch;

    @FXML
    private TableColumn<Match, String> timeMatch;

    @FXML
    private TableColumn<Match, Integer> amountTickets;

    @FXML
    private TableColumn<Match, String> opponentTeam;
    @FXML
    private TableColumn<Match, String> typeMatch;
    @FXML
    private Button addMatch;

    @FXML
    private Button delMatch;

    @FXML
    private Button sortMatches;

    @FXML
    private Button updateInfo;

    ObservableList<Match> list_of_matches = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
        PreparedStatement prStr = new DatabaseHandler().getDbConnectionForMatches().prepareStatement(select);
        ResultSet result = prStr.executeQuery();
        try {
            while (result.next()) {
                int ID = result.getInt(MATCH_ID);
                String  date = result.getString(MATCHES_DATE);
                String time = result.getString(MATCHES_TIME);
                String opponent = result.getString(OPP_TEAM);
                int tickets = result.getInt(TICKETS_AMOUNT);
                String type = result.getString(MATCH_TYPE);
                list_of_matches.add(new Match(ID, date, time, type, tickets, opponent));
            }
        } catch (SQLException e) {
            new RuntimeException(e);
        }
        table.setItems(list_of_matches);
    }

    @FXML
    private void addMatch(ActionEvent event) {
        addMatch.getScene().getWindow().hide();
        new WindowsOpener("add_match.fxml");
    }

    @FXML
    private void delMatchFromTable(ActionEvent event) {
        try{
        Match match = table.getSelectionModel().getSelectedItem();
        String delete = "DELETE FROM " + MATCHES_TABLE + " WHERE idMatches = " + match.getId();
        PreparedStatement prStr = new DatabaseHandler().getDbConnectionForMatches().prepareStatement(delete);
        prStr.executeUpdate();
        //if(все успешно){
        //AlertMaker.showNotification("Successful", "Movie Deleted",AlertMaker.image_movie_frame);
        //updateInfo();
        // }
        updateInfo();
        // else
           //AlertMaker.showNotification("Error","Movie is already scheduled to run", AlertMaker.image_cross);
        }
        catch(Exception ex)
        {
           //AlertMaker.showNotification("Error","Not Selected movie", AlertMaker.image_cross);
        }
    }
    @FXML
    private void updateTable(ActionEvent event) {
        updateInfo();
    }

    @FXML
    private void backToAdminHomePage (ActionEvent event) {
        exitButton.getScene().getWindow().hide();
        new WindowsOpener("admin_home_page.fxml");
    }
    private void updateInfo()
    {
        table.getItems().clear();
        list_of_matches.removeAll();
        try {
            loadFromDataBase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    // метод по переходу на отдельную страницу для добавления матчей

    public static class Match{

        private final SimpleIntegerProperty id;
        private final SimpleStringProperty date;
        private final SimpleStringProperty time;
        private final SimpleStringProperty type;
        private final SimpleIntegerProperty amount;
        private final SimpleStringProperty opponent;

        public Match(int id_of_match, String match_date, String match_time, String match_type, int tickets_amount, String opponent) {
            this.id = new SimpleIntegerProperty(id_of_match);
            this.date = new SimpleStringProperty(match_date);
            this.time = new SimpleStringProperty(match_time);
            this.type = new SimpleStringProperty (match_type);
            this.amount = new SimpleIntegerProperty(tickets_amount);
            this.opponent = new SimpleStringProperty(opponent);
        }

        public void setId(int id){
            this.id.set(id);
        }
        public int getId() {
            return id.get();
        }

        public void setDate(String date){
            this.date.set(date);
        }
        public String getDate() {
            return date.get();
        }

        public void setTime(String time){
            this.time.set(time);
        }
        public String getTime() {
            return time.get();
        }

        public void setType(String type){
            this.type.set(type);
        }
        public String getType() {return type.get();}

        public void setAmount(int amount){
            this.amount.set(amount);
        }
        public int getAmount() {
            return amount.get();
        }

        public void setOpponent(String opponent){
            this.opponent.set(opponent);
        }
        public String getOpponent() {
            return opponent.get();
        }
    }
}

