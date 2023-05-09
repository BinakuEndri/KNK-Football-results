package Controller;

import Models.League;
import Repository.LeagueRepository;
import Services.ImagesToResources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NavbarController implements Initializable {

    @FXML
    private Button homeBtn;
    @FXML
    private VBox vbox;



    public void fetchToNavbar(VBox vbox){

        try {
            for (League league: LeagueRepository.getAllLeagues()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("leagueButton.fxml"));
                HBox hBox = fxmlLoader.load();
                LeagueButtonController leagueButtonController = fxmlLoader.getController();
                leagueButtonController.setData(league);

                vbox.getChildren().add(hBox);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchToNavbar(vbox);
    }
}