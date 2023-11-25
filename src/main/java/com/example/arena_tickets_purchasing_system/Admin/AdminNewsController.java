package com.example.arena_tickets_purchasing_system.Admin;

import com.example.arena_tickets_purchasing_system.ArenaTicketsPurchasingSystem;
import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import com.example.arena_tickets_purchasing_system.animations.NotificationShower;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.arena_tickets_purchasing_system.Constant.*;

public class AdminNewsController implements Initializable {

    @FXML
    private AnchorPane MainPane;
    @FXML
    private TableView<News> table;
    @FXML
    private TableColumn<News, String> contentsNews;
    @FXML
    private TableColumn<News, String> dateNews;
    @FXML
    private TableColumn<News, String> timeNews;
    @FXML
    private TableColumn<News, Integer> id;
    @FXML
    private Button delNews;
    @FXML
    private Button exitButton;
    @FXML
    private Button addNews;
    @FXML
    private Button updateInfo;
    ObservableList<News> news_list = FXCollections.observableArrayList();
    AnchorPane new_pane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        exitButton.setOnMouseEntered(event ->{
            exitButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #00BFFF; -fx-text-fill: #00BFFF");
        });
        exitButton.setOnMouseExited(event ->{
            exitButton.setStyle("-fx-background-color: #00BFFF; -fx-border-color: #00BFFF; -fx-text-fill: #000000");
        });
        addNews.setOnMouseEntered(event ->{
            addNews.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #00BFFF; -fx-text-fill: #00BFFF");
        });
        addNews.setOnMouseExited(event ->{
            addNews.setStyle("-fx-background-color: #00BFFF; -fx-border-color: #00BFFF; -fx-text-fill: #000000");
        });
        delNews.setOnMouseEntered(event ->{
            delNews.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #00BFFF; -fx-text-fill: #00BFFF");
        });
        delNews.setOnMouseExited(event ->{
            delNews.setStyle("-fx-background-color: #00BFFF; -fx-border-color: #00BFFF; -fx-text-fill: #000000");
        });
        updateInfo.setOnMouseEntered(event ->{
            updateInfo.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #00BFFF; -fx-text-fill: #00BFFF");
        });
        updateInfo.setOnMouseExited(event ->{
            updateInfo.setStyle("-fx-background-color: #00BFFF; -fx-border-color: #00BFFF; -fx-text-fill: #000000");
        });
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateNews.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeNews.setCellValueFactory(new PropertyValueFactory<>("time"));
        contentsNews.setCellValueFactory(new PropertyValueFactory<>("contents"));
        try {
            loadFromDataBase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadFromDataBase() throws SQLException, ClassNotFoundException {

        String select = "SELECT * FROM " + NEWS_TABLE;
        PreparedStatement prStr = new DatabaseHandler().getDbConnection("news").prepareStatement(select);
        ResultSet result = prStr.executeQuery();
        try {
            while (result.next()) {
                int ID = result.getInt(NEWS_ID);
                String  date = result.getString(PUBLISHING_DATE);
                String time = result.getString(PUBLISHING_TIME);
                String contents = result.getString(CONTESTS);
                news_list.add(new News(ID, date, time, contents));
            }
        } catch (SQLException e) {
            new RuntimeException(e);
        }
        table.setItems(news_list);
    }

    @FXML
    private void addNews(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ArenaTicketsPurchasingSystem.class.getResource("add_news.fxml"));
        try {
            new_pane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainPane.getChildren().clear();
        MainPane.getChildren().add(new_pane);
    }

    @FXML
    private void delNewsFromTable(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            News news = table.getSelectionModel().getSelectedItem();
            if(news == null){throw new RuntimeException();}
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Внимание!");
            alert.setContentText("Вы уверены, что хотите удалить выбранный новостной пост из базы данных?");
            Optional<ButtonType> confirm = alert.showAndWait();
            if(confirm.get() == ButtonType.OK) {
                String delete = "DELETE FROM " + NEWS_TABLE + " WHERE idNews = " + news.getId();
                PreparedStatement prStr = new DatabaseHandler().getDbConnection("news").prepareStatement(delete);
                prStr.executeUpdate();
                new NotificationShower().showSimpleNotification("Уведомление", "Запись успешно удалена из базы данных");
                updateInfo();
            }
        } catch (Exception e) {
            new NotificationShower().showSimpleError("Ошибка!", "Выберите новостной пост для удаления!");
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
        news_list.removeAll();
        try {
            loadFromDataBase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static class News implements Serializable{

        private final SimpleIntegerProperty id;
        private final SimpleStringProperty date;
        private final SimpleStringProperty time;
        private final SimpleStringProperty contents;

        public News(int id, String date, String time, String contents) {

            this.id = new SimpleIntegerProperty(id);
            this.date = new SimpleStringProperty(date);
            this.time = new SimpleStringProperty(time);
            this.contents = new SimpleStringProperty(contents);
        }

        public void setId(int id){this.id.set(id);}
        public int getId() {return id.get();}
        public void setDate(String date){this.date.set(date);}
        public String getDate() {return date.get();}
        public void setTime(String time){
            this.time.set(time);
        }
        public String getTime() {return time.get();}
        public void setContents(String contents){
            this.contents.set(contents);
        }
        public String getContents() {
            return contents.get();
        }

    }
}

