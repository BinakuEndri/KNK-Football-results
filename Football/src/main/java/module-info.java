module com.example.football {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.prefs;


    opens Models;
    opens Controller;
    opens com.example.football to javafx.fxml;
    exports Controller;
}