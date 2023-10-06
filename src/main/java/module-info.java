module com.example.arena_tickets_purchasing_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.arena_tickets_purchasing_system to javafx.fxml;
    exports com.example.arena_tickets_purchasing_system;
    exports com.example.arena_tickets_purchasing_system.User;
    opens com.example.arena_tickets_purchasing_system.User to javafx.fxml;
    exports com.example.arena_tickets_purchasing_system.Admin;
    opens com.example.arena_tickets_purchasing_system.Admin to javafx.fxml;
}