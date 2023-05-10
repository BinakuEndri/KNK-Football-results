package Controller;

import Models.League;
import Models.Standings;
import Repository.StandingsRepository;
import Services.ImagesToResources;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class LeagueStandingsController {
    @FXML
    private VBox vbox1;
    @FXML
    private Button leagueName;

    @FXML
    private Button topScorers;
    @FXML
    private Button topAssists;

    private League league;
    @FXML
    private ImageView leagueImage;

    public VBox getVbox1() {
        return vbox1;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public void setLeagueNameText(String leagueName) {
        this.leagueName.setText(leagueName);
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
            Image imageLeague = new Image(ImagesToResources.getImagePath() + "\\" + league.getName() + "\\" +league.getLeague_logo());
            leagueImage.setImage(imageLeague);
            topScorers.setOnAction( actionEvent -> {
                topScorers.setUserData(league);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("topScorer.fxml"));
                Parent root;
                try {
                    root = fxmlLoader.load();
                    TopScorerController topScorerController = fxmlLoader.getController();
                    topScorerController.setDataScorer(league);
                   // Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Stage stage = new Stage();
                    Image image = new Image(ImagesToResources.getImagePath()+"\\"+"logo.png");
                    stage.getIcons().add(image);
                    Scene scene = new Scene(root,1100,550);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            topAssists.setOnAction(actionEvent -> {
                topAssists.setUserData(league);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("topScorer.fxml"));
                Parent root;
                try {
                    root = fxmlLoader.load();
                    TopScorerController topScorerController = fxmlLoader.getController();
                    topScorerController.setDataAssister(league);
                   // Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Stage stage = new Stage();
                    Image image = new Image(ImagesToResources.getImagePath()+"\\"+"logo.png");
                    stage.getIcons().add(image);
                    Scene scene = new Scene(root,1100,550);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

}
