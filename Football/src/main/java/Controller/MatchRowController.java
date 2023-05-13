package Controller;

import Models.League;
import Models.Match;
import Models.MatchStatistics;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MatchRowController {

    @FXML
    private Label awayTeamGoals;

    @FXML
    private ImageView awayTeamLogo;

    @FXML
    private Label awayTeamName;

    @FXML
    private Label homeTeamGoals;

    @FXML
    private ImageView homeTeamLogo;

    @FXML
    private Label homeTeamName;

    @FXML
    private ImageView leagueLogo;

    @FXML
    private Label leagueName;

    @FXML
    private Label matchDate;

    public void setData(Match match, Image homeTeam, Image awayTeam , Image leagueLogo, MatchStatistics matchStatistics, League league){
        this.leagueLogo.setImage(leagueLogo);
        this.homeTeamLogo.setImage(homeTeam);
        this.awayTeamLogo.setImage(awayTeam);
        this.homeTeamName.setText(match.getHome_team_id().getName());
        this.awayTeamName.setText(match.getAway_team_id().getName());
        this.matchDate.setText(String.valueOf(match.getMatch_date()));
        this.homeTeamGoals.setText(String.valueOf(matchStatistics.getHome_team_goals()));
        this.awayTeamGoals.setText(String.valueOf(matchStatistics.getAway_team_goals()));
        this.leagueName.setText(league.getName());

    }


}
