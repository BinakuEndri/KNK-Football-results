package Controller;

import Models.League;
import Models.Standings;
import Repository.StandingsRepository;
import Services.ImagesToResources;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LeagueStandingsController implements Initializable {
    @FXML
    private VBox vbox1;
    @FXML
    private Button leagueName;

    private League league;

    public LeagueStandingsController(League league){
        this.league =league;
    }

    public void addTeamStandings(VBox vbox1){
        if(league != null) {
            try {
                ObservableList<Standings> standings = StandingsRepository.getAllStandings(this.league);
                for (int i = 0; i < standings.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("teamStanding.fxml"));
                    HBox hbox = fxmlLoader.load();
                    TeamStandingController teamStandingController = fxmlLoader.getController();
                    int number = i + 1;
                    Image image = new Image(ImagesToResources.getImagePath() + "\\" + "Position" + "\\" + number + ".png");
                    teamStandingController.setData(standings.get(i), image);

                    vbox1.getChildren().add(hbox);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTeamStandings(vbox1);
        leagueName.setText(league.getName());
    }
}
