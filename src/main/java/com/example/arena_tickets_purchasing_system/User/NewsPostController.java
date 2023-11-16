package com.example.arena_tickets_purchasing_system.User;
import com.example.arena_tickets_purchasing_system.Admin.AdminNewsController;
import com.example.arena_tickets_purchasing_system.Admin.AdminTeamRosterController;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
public class NewsPostController {
    @FXML
    private AnchorPane NewsPostPane;

    @FXML
    private Text contents;

    @FXML
    private Text date_fill;

    @FXML
    private Text time_fill;

    private AdminNewsController.News News;

    @FXML
    void initialize() {
        date_fill.setText(String.valueOf(News.getDate()));
        time_fill.setText(News.getTime());
        contents.setText(News.getContents());
    }
    public void setNewsPost(AdminNewsController.News news){
        News = news;
    }
    public AdminNewsController.News getNewsPost(){
        return News;
    }

}
