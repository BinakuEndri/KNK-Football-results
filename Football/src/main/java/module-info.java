module com.example.football {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.football to javafx.fxml;
    exports com.example.football;
}