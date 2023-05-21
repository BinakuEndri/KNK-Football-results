package Controller;

import Models.League;
import Repository.LeagueRepository;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class NavbarController implements Initializable {

    @FXML
    private Button homeBtn;
    @FXML
    private VBox vbox;
    @FXML


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
    private void toLandingPage(){
        homeBtn.setOnAction(actionEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("landing.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root,1200,700);
            stage.setScene(scene);
            stage.show();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchToNavbar(vbox);
        toLandingPage();
    }
}