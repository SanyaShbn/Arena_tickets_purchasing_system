package com.example.arena_tickets_purchasing_system;

import com.example.arena_tickets_purchasing_system.Admin.AdminMatchesWindowController;
import com.example.arena_tickets_purchasing_system.User.BookTicketsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowsOpener {
    String file_name;

    public WindowsOpener(String name){
        file_name = name;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((getClass().getResource(file_name)));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        if(!name.equals("booking.fxml")) {
            stage.setMaximized(true);
        }

    }
    public WindowsOpener(String name, AdminMatchesWindowController.Match match){
        file_name = name;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((getClass().getResource(file_name)));
        BookTicketsController controller = new BookTicketsController();
        controller.setMatch(match);
        loader.setController(controller);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
