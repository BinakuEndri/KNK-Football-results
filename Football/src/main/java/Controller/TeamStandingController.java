package Controller;

import Models.Standings;
import Services.ImagesToResources;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    }
}
