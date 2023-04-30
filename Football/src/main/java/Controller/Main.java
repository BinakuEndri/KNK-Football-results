package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                Main.class.getResource("admindashboard.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load(),1200,700);
        stage.setTitle("FootyScore");
        stage.setScene(scene);
        stage.show();
    }
}
