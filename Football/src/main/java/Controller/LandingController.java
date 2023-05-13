package Controller;

import Models.*;
import Repository.LeagueRepository;
import Repository.LeagueTeamsRepository;
import Repository.MatchRepository;
import Repository.MatchStatisticsRepository;
import Services.ImagesToResources;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LandingController implements Initializable {


    @FXML
    private VBox vbox;

    @FXML
    private DatePicker datePicker;

    private  void iterateToArray(VBox vbox, ObservableList<Match> matches){
        for (int i = 0; i < matches.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("matchRow.fxml"));

            VBox vBox = null;
            try {
                vBox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            MatchRowController matchRowController = fxmlLoader.getController();
            Match match = matches.get(i);
            Team homeTeam = match.getHome_team_id();
            Team awayTeam = match.getAway_team_id();
            LeagueTeams leagueTeams = null;
            try {
                leagueTeams = LeagueTeamsRepository.findByLeague(homeTeam);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            League league = leagueTeams.getLeague_id();
            Image imageHome = new Image(ImagesToResources.getImagePath() + "\\" + league.getName() + "\\" + homeTeam.getName() + "\\" + homeTeam.getLogo());
            Image imageAway = new Image(ImagesToResources.getImagePath() + "\\" + league.getName() + "\\" + awayTeam.getName() + "\\" + awayTeam.getLogo());
            Image leagueLogo = new Image(ImagesToResources.getImagePath() + "\\" + league.getName() + "\\" + league.getLeague_logo());

            MatchStatistics matchStatistics = null;
            try {
                matchStatistics = MatchStatisticsRepository.findByMatch(match);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            matchRowController.setData(match, imageHome, imageAway, leagueLogo, matchStatistics, league);
            vbox.getChildren().add(vBox);
        }
    }
    public void fetchToVBox(VBox vbox){
            datePicker.setOnAction(actionEvent -> {

            ObservableList<Match> matches;
            if(datePicker.getValue() != null) {
            Date date = Date.valueOf(datePicker.getValue());

                try {
                    matches = MatchRepository.getAllMatchesByDate(date);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                vbox.getChildren().clear();

                iterateToArray(vbox, matches);
            }
            else {
                vbox.getChildren().clear();
                fetchToVBox(vbox);
            }
            });
            if(datePicker.getValue() == null) {
                ObservableList<Match> matches;
                try {
                    matches = MatchRepository.getAllMatchesToday();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                vbox.getChildren().clear();

                iterateToArray(vbox, matches);

            }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchToVBox(vbox);
    }
}
