package com.example.arena_tickets_purchasing_system.Admin;


import com.example.arena_tickets_purchasing_system.ArenaTicketsPurchasingSystem;
import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.User.PlayerCardController;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.arena_tickets_purchasing_system.Constant.*;

public class AdminTeamRosterController implements Initializable {

    @FXML
    private AnchorPane MainPane;

    @FXML
    private TableView<Player> table;

    @FXML
    private TableColumn<Player, Integer> ageOfPlayer;

    @FXML
    private TableColumn<Player, Integer> heightOfPlayer;

    @FXML
    private TableColumn<Player, Integer> jerseyNumb;

    @FXML
    private TableColumn<Player, String> nationalityOfPlayer;

    @FXML
    private TableColumn<Player, String> passportName;

    @FXML
    private TableColumn<Player, String> roleOfPlayer;

    @FXML
    private TableColumn<Player, Integer> seasonsInLeague;

    @FXML
    private TableColumn<Player, Integer> seasonsInTeam;
    @FXML
    private TableColumn<Player, Integer> weightOfPlayer;

    @FXML
    private Button delPlayer;
    @FXML
    private Button exitButton;
    @FXML
    private Button addPlayer;
    @FXML
    private Button updateInfo;

    ObservableList<Player> list_of_players = FXCollections.observableArrayList();
    AnchorPane new_pane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        exitButton.setOnMouseEntered(event ->{
            exitButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #00BFFF; -fx-text-fill: #00BFFF");
        });
        exitButton.setOnMouseExited(event ->{
            exitButton.setStyle("-fx-background-color: #00BFFF; -fx-border-color: #00BFFF; -fx-text-fill: #000000");
        });
        passportName.setCellValueFactory(new PropertyValueFactory<>("name"));
        roleOfPlayer.setCellValueFactory(new PropertyValueFactory<>("role"));
        jerseyNumb.setCellValueFactory(new PropertyValueFactory<>("number"));
        nationalityOfPlayer.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        ageOfPlayer.setCellValueFactory(new PropertyValueFactory<>("age"));
        heightOfPlayer.setCellValueFactory(new PropertyValueFactory<>("height"));
        weightOfPlayer.setCellValueFactory(new PropertyValueFactory<>("weight"));
        seasonsInTeam.setCellValueFactory(new PropertyValueFactory<>("seasonsTeam"));
        seasonsInLeague.setCellValueFactory(new PropertyValueFactory<>("seasonsLeague"));

        try {
            loadFromDataBase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public void loadFromDataBase() throws SQLException, ClassNotFoundException {
        String select = "SELECT * FROM " + PLAYERS_TABLE;
        PreparedStatement prStr = new DatabaseHandler().getDbConnection( "players").prepareStatement(select);
        ResultSet result = prStr.executeQuery();
        try {
            while (result.next()) {
                String name = result.getString(PLAYER_NAME);
                String role = result.getString(ROLE);
                int jersey = result.getInt(JERSEY);
                String nationality = result.getString(NATION);
                int age = result.getInt(AGE);
                int height = result.getInt(HEIGHT);
                int weight = result.getInt(WEIGHT);
                int team = result.getInt(TEAM);
                int league = result.getInt(LEAGUE);
                list_of_players.add(new Player(name, role, jersey, nationality, age, height, weight, team, league));
            }
        } catch (SQLException e) {
            new RuntimeException(e);
        }
        table.setItems(list_of_players);
    }

    @FXML
    private void addPlayer(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("add_player.fxml"));
        try {
            new_pane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainPane.getChildren().clear();
        MainPane.getChildren().add(new_pane);
    }

    @FXML
    private void delPlayerFromTable(ActionEvent event) {
        try {
        Player players = table.getSelectionModel().getSelectedItem();
        String delete = "DELETE FROM " + PLAYERS_TABLE + " WHERE Jersey_numb = " + players.getNumber();
        PreparedStatement prStr = null;
        prStr = new DatabaseHandler().getDbConnection("players").prepareStatement(delete);
        prStr.executeUpdate();
        updateInfo();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void updateTable(ActionEvent event) {
        updateInfo();
    }

    @FXML
    private void backToAdminHomePage (ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("admin_home_page.fxml"));
        try {
            new_pane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainPane.getChildren().clear();
        MainPane.getChildren().add(new_pane);
    }
    private void updateInfo()
    {
        table.getItems().clear();
        list_of_players.removeAll();
        try {
            loadFromDataBase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Player{

        private final SimpleStringProperty name;
        private final SimpleStringProperty role;
        private final SimpleIntegerProperty number;
        private final SimpleStringProperty nationality;
        private final SimpleIntegerProperty age;
        private final SimpleIntegerProperty height;
        private final SimpleIntegerProperty weight;
        private final SimpleIntegerProperty seasonsTeam;
        private final SimpleIntegerProperty seasonsLeague;
        public Player(String player_name, String player_role, int player_numb, String nation, int player_age, int player_height,
        int player_weight, int season_team, int season_league) {
            this.name = new SimpleStringProperty(player_name);
            this.role = new SimpleStringProperty(player_role);
            this.number = new SimpleIntegerProperty(player_numb);
            this.nationality = new SimpleStringProperty (nation);
            this.age = new SimpleIntegerProperty(player_age);
            this.height = new SimpleIntegerProperty(player_height);
            this.weight = new SimpleIntegerProperty(player_weight);
            this.seasonsTeam = new SimpleIntegerProperty(season_team);
            this.seasonsLeague = new SimpleIntegerProperty(season_league);
        }

        public void setName(String name){
            this.name.set(name);
        }
        public String getName() {
            return name.get();
        }

        public void setRole(String role){
            this.role.set(role);
        }
        public String getRole() {
            return role.get();
        }

        public void setNumber(int number){
            this.number.set(number);
        }
        public int getNumber() {
            return number.get();
        }

        public void setNationality(String nationality){this.nationality.set(nationality);}
        public String getNationality() {return nationality.get();}

        public void setAge(int age){
            this.age.set(age);
        }
        public int getAge() {
            return age.get();
        }

        public void setHeight(int height){
            this.height.set(height);
        }
        public int getHeight() {return height.get();}

        public void setWeight(int weight){
            this.weight.set(weight);
        }
        public int getWeight() {return weight.get();}
        public void setSeasonsTeam(int s_team){
            this.seasonsTeam.set(s_team);
        }
        public int getSeasonsTeam() {return seasonsTeam.get();}
        public void setSeasonsLeague(int s_league){
            this.seasonsLeague.set(s_league);
        }
        public int getSeasonsLeague() {return seasonsLeague.get();}

    }
}

