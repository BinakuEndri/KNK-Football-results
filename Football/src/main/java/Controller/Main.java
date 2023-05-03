package Controller;

import Services.ImagesToResources;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                Main.class.getResource("managePlayer.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load(),1200,700);
        stage.setTitle("FootyScore");
        stage.setScene(scene);
        Image image =new Image(ImagesToResources.getImagePath()+"\\logo.png");
        stage.setResizable(false);
        stage.getIcons().add(image);
        stage.show();
    }
}
