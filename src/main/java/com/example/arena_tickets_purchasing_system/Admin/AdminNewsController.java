package com.example.arena_tickets_purchasing_system.Admin;


import com.example.arena_tickets_purchasing_system.Config;
import com.example.arena_tickets_purchasing_system.DatabaseHandler;
import com.example.arena_tickets_purchasing_system.WindowsOpener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.arena_tickets_purchasing_system.Constant.*;

public class AdminNewsController implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
        addNews.getScene().getWindow().hide();
        new WindowsOpener("add_news.fxml");
    }

    @FXML
    private void delNewsFromTable(ActionEvent event) throws SQLException, ClassNotFoundException {

        News news = table.getSelectionModel().getSelectedItem();
        String delete = "DELETE FROM " + NEWS_TABLE + " WHERE idNews = " + news.getId();
        PreparedStatement prStr = new DatabaseHandler().getDbConnection("news").prepareStatement(delete);
        try {
            prStr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        updateInfo();

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

