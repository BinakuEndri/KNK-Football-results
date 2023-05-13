package Controller;

import Models.League;
import Repository.LeagueRepository;
import Services.ImagesToResources;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class LeagueButtonController {
    @FXML
    private Button leagueBtn;
    @FXML
    private ImageView leagueImage;

    private boolean isLoading = false;



    public void setData(League league){
        leagueBtn.setText(league.getName());
        Image image = new Image(ImagesToResources.getImagePath() +"\\"+league.getName()+"\\"+league.getLeague_logo());
        leagueImage.setImage(image);
        leagueBtn.setUserData(league);
        leagueBtn.setOnAction( actionEvent -> {
            try {
                if (isLoading) {
                    return;
                }
                isLoading = true;
                leagueBtn.setDisable(true); // Disable the button during loading


                League league1 = (League) leagueBtn.getUserData();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("leagueStandings.fxml"));
                Parent root = loader.load();
                LeagueStandingsController leagueStandingsController = loader.getController();
                VBox vbox =leagueStandingsController.getVbox1();
                leagueStandingsController.setLeague(league1);
                leagueStandingsController.addTeamStandings(vbox);
                leagueStandingsController.setLeagueNameText(league1.getName());

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root,1200,700);
                stage.setScene(scene);
                stage.show();

            }catch (IOException e){
                throw new RuntimeException(e);
            } finally {
                isLoading = false;
                leagueBtn.setDisable(false); // Re-enable the button after loading
            }

        });
    }

}
