package com.example.arena_tickets_purchasing_system.animations;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;

public class NotificationShower {

    public void showWarning(String text, String message)
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(text);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void showSimpleError(String text, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(text);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showSimpleNotification(String text, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(text);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
