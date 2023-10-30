package com.example.arena_tickets_purchasing_system.User;

import com.example.arena_tickets_purchasing_system.Admin.AdminTeamRosterController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class PlayerCardController {

    @FXML
    private Label age;

    @FXML
    private ImageView flag;

    @FXML
    private Label height;

    @FXML
    private Label name;
    @FXML
    private Text number;

    @FXML
    private Label role;

    @FXML
    private Label seasonsInLeague;

    @FXML
    private Label seasonsInTeam;

    @FXML
    private Label weight;
    private AdminTeamRosterController.Player Player;

    @FXML
    void initialize() {
      age.setText(String.valueOf(Player.getAge()));
      height.setText(String.valueOf(Player.getHeight()));
      weight.setText(String.valueOf(Player.getWeight()));
      name.setText(Player.getName());
      role.setText(Player.getRole());
      number.setText(String.valueOf(Player.getNumber()));
      seasonsInTeam.setText(String.valueOf(Player.getSeasonsTeam()));
      seasonsInLeague.setText(String.valueOf(Player.getSeasonsLeague()));
      setFlag(Player.getNationality(), flag);
    }
    public void setPlayer(AdminTeamRosterController.Player player){
        Player = player;
    }
    public AdminTeamRosterController.Player getPlayer(){
        return Player;
    }
    private ImageView setFlag (String nationality, ImageView flag) {
        switch (nationality){
            case "Беларусь": flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\belarus.png"));break;
            case "Россия": flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\Russia.png"));break;
            case "США": flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\Usa.png"));break;
            case "Канада": flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\canada.png"));break;
            case "Швеция": flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\Sweden.png")); break;
            case "Финляндия": flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\finland.png"));break;
            case "Чехия": flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\Czech.png"));break;
            case "Словакия": flag.setImage(new Image("D:\\Уник\\Arena_tickets_purchasing_system\\src\\main\\java\\com\\example\\arena_tickets_purchasing_system\\Images\\Slovakia.png"));break;
        }
        return flag;
    }
}
