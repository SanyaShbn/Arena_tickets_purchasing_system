package com.example.arena_tickets_purchasing_system.Admin;

import com.example.arena_tickets_purchasing_system.ArenaTicketsPurchasingSystem;
import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.animations.NotificationShower;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static com.example.arena_tickets_purchasing_system.Constant.*;



public class AddTicketsController {

    @FXML
    private AnchorPane MainPane;

    @FXML
    private TextField amount;

    @FXML
    private MenuButton id;

    @FXML
    private TextField secA;

    @FXML
    private TextField secB;

    @FXML
    private TextField secC;

    @FXML
    private TextField secD;

    @FXML
    private TextField secE;

    @FXML
    private TextField secF;

    @FXML
    private TextField secG;

    @FXML
    private TextField secH;

    @FXML
    private TextField secI;

    @FXML
    private TextField vip;
    @FXML
    private Button submitChanges;

    @FXML
    private Button exitButton;

    AnchorPane back_to_admin_tickets;
    @FXML
    void initialize(){

        submitChanges.setOnMouseEntered(event ->{
            submitChanges.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #00BFFF; -fx-text-fill: #00BFFF");
        });
        submitChanges.setOnMouseExited(event ->{
            submitChanges.setStyle("-fx-background-color: #00BFFF; -fx-border-color: #00BFFF; -fx-text-fill: #000000");
        });
        exitButton.setOnMouseEntered(event ->{
            exitButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #00BFFF; -fx-text-fill: #00BFFF");
        });
        exitButton.setOnMouseExited(event ->{
            exitButton.setStyle("-fx-background-color: #00BFFF; -fx-border-color: #00BFFF; -fx-text-fill: #000000");
        });
        String select = "SELECT * FROM " + MATCHES_TABLE;
        try {
            PreparedStatement prStr = new DatabaseHandler().getDbConnection( "matches").prepareStatement(select);
            ResultSet result = prStr.executeQuery();
            while (result.next()) {
                String value = String.valueOf(result.getInt(MATCH_ID));
                String tickets_amount = String.valueOf(result.getInt(TICKETS_AMOUNT));
                String select_tickets = "SELECT * FROM " + ADMIN_TICKETS_TABLE + " WHERE id_Match = " + value;
                PreparedStatement prStrTickets = new DatabaseHandler().getDbConnection( "tickets").prepareStatement(select_tickets);
                ResultSet result_for_tickets = prStrTickets.executeQuery();
                if((Integer.parseInt(tickets_amount)) != 0 && !result_for_tickets.next()) {
                    MenuItem ID = new MenuItem(value);
                    id.getItems().addAll(ID);
                    ID.setOnAction(event ->
                    {
                        id.setText(value);
                        amount.setText(tickets_amount);
                    });
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        FXMLLoader admin_tickets_loader = new FXMLLoader();
        admin_tickets_loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("tickets.fxml"));
        try {
            back_to_admin_tickets = admin_tickets_loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void backToPreviousPane(Node node) {
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
    private void backToAdminTickets (ActionEvent event) {
        backToPreviousPane(back_to_admin_tickets);
    }

    @FXML
    private void addTickets (ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            if(Integer.parseInt(amount.getText()) != (Integer.parseInt(vip.getText()) + Integer.parseInt(secA.getText()) + Integer.parseInt(secB.getText()) + Integer.parseInt(secC.getText())
            + Integer.parseInt(secD.getText()) + Integer.parseInt(secE.getText()) + Integer.parseInt(secF.getText()) + Integer.parseInt(secG.getText()) + Integer.parseInt(secH.getText())
            + Integer.parseInt(secI.getText()))){
                new NotificationShower().showWarning("Внимание!","Убедитесь, что сумма всех билетов на конкретные сектора равна общему количеству доступных билетов");
            }
            else {
                new DatabaseHandler().addNewAdminTickets(new AdminTicketsController.MatchTickets(Integer.parseInt(id.getText()), Integer.parseInt(amount.getText()),
                        Integer.parseInt(vip.getText()), Integer.parseInt(secA.getText()), Integer.parseInt(secB.getText()), Integer.parseInt(secC.getText()),
                        Integer.parseInt(secD.getText()), Integer.parseInt(secE.getText()), Integer.parseInt(secF.getText()), Integer.parseInt(secG.getText()),
                        Integer.parseInt(secH.getText()), Integer.parseInt(secI.getText())));
            }
        }catch(NumberFormatException e){
            new NotificationShower().showWarning("Внимание!","Проверьте корректность ввода данных");
        }finally {
            id.setText("id");amount.clear();vip.clear();secA.clear();secB.clear();secC.clear();secD.clear();
            secE.clear();secF.clear();secG.clear();secH.clear();secI.clear();
        }
    }

}
