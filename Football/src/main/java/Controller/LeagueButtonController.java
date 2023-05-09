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



    public void setData(League league){
        leagueBtn.setText(league.getName());
        Image image = new Image(ImagesToResources.getImagePath() +"\\"+league.getName()+"\\"+league.getLeague_logo());
        leagueImage.setImage(image);
        leagueBtn.setUserData(league);
        leagueBtn.setOnAction( actionEvent -> {
            try {


                League league1 = (League) leagueBtn.getUserData();
                System.out.println("Button clicked for league: " + league1.getName());
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("leagueStandings.fxml"));
                LeagueStandingsController leagueStandingsController = new LeagueStandingsController(league1);
                loader.setController(leagueStandingsController);
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root,1200,700);
                stage.setScene(scene);
                stage.show();

            }catch (IOException e){
                throw new RuntimeException(e);
            }

        });

    }


}
