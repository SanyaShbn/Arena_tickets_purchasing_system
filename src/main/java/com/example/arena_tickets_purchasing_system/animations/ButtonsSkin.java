package com.example.arena_tickets_purchasing_system.animations;
import javafx.scene.control.Button;
import javafx.scene.control.skin.ButtonSkin;
public class ButtonsSkin extends ButtonSkin {

    public ButtonsSkin(Button button) {
        super(button);
    button.setOnMouseEntered(event -> button.setStyle("-fx-background-radius: 20; -fx-background-color: #FFFFFF; -fx-border-radius: 20; -fx-border-color: #FF4500; -fx-text-fill: #FF4500"));
    button.setOnMouseExited(event -> button.setStyle("-fx-background-radius: 20; -fx-background-color: #FF4500; -fx-border-radius: 20; -fx-border-color: #FF4500; -fx-text-fill: #000000"));
    }
}
