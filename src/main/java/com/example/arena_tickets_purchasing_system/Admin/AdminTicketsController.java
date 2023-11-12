package com.example.arena_tickets_purchasing_system.Admin;


import com.example.arena_tickets_purchasing_system.ArenaTicketsPurchasingSystem;
import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import com.example.arena_tickets_purchasing_system.animations.NotificationShower;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class AdminTicketsController implements Initializable {


    @FXML
    private AnchorPane MainPane;
    @FXML
    private TableView<MatchTickets> table;

    @FXML
    private TableColumn<MatchTickets, Integer> amountTickets;

    @FXML
    private TableColumn<MatchTickets, Integer> idMatch;

    @FXML
    private TableColumn<MatchTickets, Integer> vipSector;

    @FXML
    private TableColumn<MatchTickets, Integer> sectorA;

    @FXML
    private TableColumn<MatchTickets, Integer> sectorB;

    @FXML
    private TableColumn<MatchTickets, Integer> sectorC;

    @FXML
    private TableColumn<MatchTickets, Integer> sectorD;

    @FXML
    private TableColumn<MatchTickets, Integer> sectorE;

    @FXML
    private TableColumn<MatchTickets, Integer> sectorF;

    @FXML
    private TableColumn<MatchTickets, Integer> sectorG;

    @FXML
    private TableColumn<MatchTickets, Integer> sectorH;

    @FXML
    private TableColumn<MatchTickets, Integer> sectorI;

    @FXML
    private Button delInfo;

    @FXML
    private Button exitButton;

    @FXML
    private Button updateInfo;

    @FXML
    private Button addTickets;


    ObservableList<MatchTickets> list_of_tickets = FXCollections.observableArrayList();
    AnchorPane new_pane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        exitButton.setOnMouseEntered(event ->{
            exitButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #00BFFF; -fx-text-fill: #00BFFF");
        });
        exitButton.setOnMouseExited(event ->{
            exitButton.setStyle("-fx-background-color: #00BFFF; -fx-border-color: #00BFFF; -fx-text-fill: #000000");
        });
        idMatch.setCellValueFactory(new PropertyValueFactory<>("id"));
        amountTickets.setCellValueFactory(new PropertyValueFactory<>("amount"));
        vipSector.setCellValueFactory(new PropertyValueFactory<>("vipSector"));
        sectorA.setCellValueFactory(new PropertyValueFactory<>("sectorA"));
        sectorB.setCellValueFactory(new PropertyValueFactory<>("sectorB"));
        sectorC.setCellValueFactory(new PropertyValueFactory<>("sectorC"));
        sectorD.setCellValueFactory(new PropertyValueFactory<>("sectorD"));
        sectorE.setCellValueFactory(new PropertyValueFactory<>("sectorE"));
        sectorF.setCellValueFactory(new PropertyValueFactory<>("sectorF"));
        sectorG.setCellValueFactory(new PropertyValueFactory<>("sectorG"));
        sectorH.setCellValueFactory(new PropertyValueFactory<>("sectorH"));
        sectorI.setCellValueFactory(new PropertyValueFactory<>("sectorI"));


        try {
            loadFromDataBase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public void loadFromDataBase() throws SQLException, ClassNotFoundException {
        String select = "SELECT * FROM " + ADMIN_TICKETS_TABLE;
        PreparedStatement prStr = new DatabaseHandler().getDbConnection("tickets").prepareStatement(select);
        ResultSet result = prStr.executeQuery();
        try {
            while (result.next()) {
                int ID = result.getInt("id_Match");
                int  amount = result.getInt("Tickets_amount");
                int  vip = result.getInt("VIP");
                int  A = result.getInt("Sector_A");
                int  B = result.getInt("Sector_B");
                int  C = result.getInt("Sector_C");
                int  D = result.getInt("Sector_D");
                int  E = result.getInt("Sector_E");
                int  F = result.getInt("Sector_F");
                int  G = result.getInt("Sector_G");
                int  H = result.getInt("Sector_H");
                int  I = result.getInt("Sector_I");
                list_of_tickets.add(new MatchTickets(ID, amount, vip, A, B, C, D, E, F, G, H, I));
            }
        } catch (SQLException e) {
            new RuntimeException(e);
        }
        table.setItems(list_of_tickets);
    }

    @FXML
    private void addTickets(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("add_admin_tickets.fxml"));
        try {
            new_pane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainPane.getChildren().clear();
        MainPane.getChildren().add(new_pane);
    }

    @FXML
    private void delTicketsFromTable(ActionEvent event) {
        try{
            MatchTickets tickets = table.getSelectionModel().getSelectedItem();
            String delete = "DELETE FROM " + ADMIN_TICKETS_TABLE + " WHERE id_Match = " + tickets.getId();
            PreparedStatement prStr = new DatabaseHandler().getDbConnection("tickets").prepareStatement(delete);
            prStr.executeUpdate();
            new NotificationShower().showSimpleNotification("Уведомление", "Запись успешно удалена из базы данных");
            updateInfo();

        }
        catch(Exception ex)
        {
            new NotificationShower().showSimpleError("Ошибка!", "Выберите билеты для удаления!");
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
        list_of_tickets.removeAll();
        try {
            loadFromDataBase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static class MatchTickets{

        private final SimpleIntegerProperty id;
        private final SimpleIntegerProperty amount;
        private final SimpleIntegerProperty vipSector;
        private final SimpleIntegerProperty sectorA;
        private final SimpleIntegerProperty sectorB;
        private final SimpleIntegerProperty sectorC;
        private final SimpleIntegerProperty sectorD;
        private final SimpleIntegerProperty sectorE;
        private final SimpleIntegerProperty sectorF;
        private final SimpleIntegerProperty sectorG;
        private final SimpleIntegerProperty sectorH;
        private final SimpleIntegerProperty sectorI;


        public MatchTickets(int id_of_match, int tickets_amount, int vip_sector, int sector_A, int sector_B, int sector_C,
                            int sector_D, int sector_E, int sector_F, int sector_G, int sector_H, int sector_I) {
            this.id = new SimpleIntegerProperty(id_of_match);
            this.amount = new SimpleIntegerProperty(tickets_amount);
            this.vipSector = new SimpleIntegerProperty(vip_sector);
            this.sectorA = new SimpleIntegerProperty (sector_A);
            this.sectorB = new SimpleIntegerProperty(sector_B);
            this.sectorC = new SimpleIntegerProperty(sector_C);
            this.sectorD = new SimpleIntegerProperty(sector_D);
            this.sectorE = new SimpleIntegerProperty(sector_E);
            this.sectorF = new SimpleIntegerProperty(sector_F);
            this.sectorG = new SimpleIntegerProperty(sector_G);
            this.sectorH = new SimpleIntegerProperty(sector_H);
            this.sectorI = new SimpleIntegerProperty(sector_I);
        }
        public void setId(int id){
            this.id.set(id);
        }
        public int getId() {
            return id.get();
        }

        public void setAmount(int amount){
            this.amount.set(amount);
        }
        public int getAmount() {
            return amount.get();
        }

        public void setVipSector(int amount){
            this.vipSector.set(amount);
        }
        public int getVipSector() {
            return vipSector.get();
        }
        public void setSectorA(int amount){
            this.sectorA.set(amount);
        }
        public int getSectorA() {
            return sectorA.get();
        }

        public void setSectorB(int amount){
            this.sectorB.set(amount);
        }
        public int getSectorB() {
            return sectorB.get();
        }

        public void setSectorC(int amount){
            this.sectorC.set(amount);
        }
        public int getSectorC() {
            return sectorC.get();
        }
        public void setSectorD(int amount){
            this.sectorD.set(amount);
        }
        public int getSectorD() {
            return sectorD.get();
        }
        public void setSectorE(int amount){
            this.sectorE.set(amount);
        }
        public int getSectorE() {
            return sectorE.get();
        }
        public void setSectorF(int amount){
            this.sectorF.set(amount);
        }
        public int getSectorF() {
            return sectorF.get();
        }
        public void setSectorG(int amount){
            this.sectorG.set(amount);
        }
        public int getSectorG() {
            return sectorG.get();
        }
        public void setSectorH(int amount){
            this.sectorH.set(amount);
        }
        public int getSectorH() {
            return sectorH.get();
        }
        public void setSectorI(int amount){
            this.sectorI.set(amount);
        }
        public int getSectorI() {
            return sectorI.get();
        }

    }
}
