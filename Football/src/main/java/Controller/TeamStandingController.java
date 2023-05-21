package Controller;

import Models.League;
import Models.Standings;
import Models.Team;
import Repository.TeamRepository;
import Services.ImagesToResources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TeamStandingController {


    @FXML
    private Label GoalsForStanding;

    @FXML
    private Label gameLostStanding;

    @FXML
    private Label gamesDrawnStanding;

    @FXML
    private Label gamesPlayed;

    @FXML
    private Label gamesWonStanding;

    @FXML
    private Label goalsAgainstStanding;

    @FXML
    private ImageView posImage;

    @FXML
    private ImageView teamImageStanding;

    @FXML
    private Label teamNameStanding;

    @FXML
    private Label teamPointStanding;



    private String imagePath = ImagesToResources.getImagePath();

    public void setData(Standings standings,Image img){
        String teamName  = standings.getTeam_id().getName();
        String  teamLogo = standings.getTeam_id().getLogo();

        String gamesplayed = String.valueOf(standings.getMatches_played());
        String gamesWon = String.valueOf(standings.getWins());
        String gameDrawn = String.valueOf(standings.getDraws());
        String gamesLost = String.valueOf(standings.getLoses());
        String goalsFor = String.valueOf(standings.getGoals_for());
        String goalsAgainst = String.valueOf(standings.getGoals_against());
        String teamPoints = String.valueOf(standings.getPoints());


       Image image = new Image(imagePath + "\\" +
                standings.getLeague_id().getName() +"\\"+
                teamName + "\\" +teamLogo);
        posImage.setImage(img);
        teamImageStanding.setImage(image);
        teamNameStanding.setText(teamName);
        teamPointStanding.setText(teamPoints);
        gamesPlayed.setText(gamesplayed);
        gamesWonStanding.setText(gamesWon);
        gameLostStanding.setText(gamesLost);
        gamesDrawnStanding.setText(gameDrawn);
        goalsAgainstStanding.setText(goalsAgainst);
        GoalsForStanding.setText(goalsFor);
        Team team = standings.getTeam_id();
        League league = standings.getLeague_id();
        teamNameStanding.setUserData(team);

        teamNameStanding.setOnMouseClicked( event ->{

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("teamDetails.fxml"));
            Parent root = null;
            try {

                Team team1 = (Team) teamNameStanding.getUserData();
                root = loader.load();

                TeamDetailsController teamDetailsController = loader.getController();
                teamDetailsController.setData(league,team1);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1200, 700);
                stage.setScene(scene);
                stage.show();

            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
