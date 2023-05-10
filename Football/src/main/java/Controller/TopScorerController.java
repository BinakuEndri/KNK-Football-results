package Controller;

import Models.*;
import Repository.LeagueTeamsRepository;
import Repository.PlayerRepository;
import Repository.SquadPlayerRepository;
import Repository.StandingsRepository;
import Services.ImagesToResources;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TopScorerController {

    @FXML
    private Label goals;

    @FXML
    private ImageView leagueLogo;

    @FXML
    private Label leagueName;

    @FXML
    private Label topScorers;
    @FXML
    private VBox vbox1;


    public void setDataScorer(League league) {
        this.goals.setText("Goals");
        this.topScorers.setText("Top Scorers");
        this.leagueName.setText(league.getName());
        Image imageLeague = new Image(ImagesToResources.getImagePath() + "\\" + league.getName() + "\\" +league.getLeague_logo());


        this.leagueLogo.setImage(imageLeague);
        if (league != null) {
            try {
                ObservableList<Player> players = PlayerRepository.getAllPlayerByLeagueGoals(league);
                for (int i = 0; i < players.size(); i++) {
                    Player player = players.get(i);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("playerRowStat.fxml"));
                    GridPane gridPane = fxmlLoader.load();
                    PlayerRowStatController playerRowStatController = fxmlLoader.getController();
                    Image imagePlayer = new Image(ImagesToResources.getImagePath() + "\\" + league.getName() + "\\" + player.getName()+ "\\" +player.getImage());
                    Image imageNation = new Image(ImagesToResources.getImagePath() + "\\" + "Nations"  + "\\" + player.getNationality().getFlag());
                    Team team = PlayerRepository.getPlayerTeam(player);
                    Image imageTeam = new Image(ImagesToResources.getImagePath() + "\\" + league.getName() + "\\" + team.getName()+ "\\" +team.getLogo());
                    playerRowStatController.setDataS(player,imagePlayer,imageTeam,imageNation);

                    vbox1.getChildren().add(gridPane);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setDataAssister(League league) {
        this.goals.setText("Assists");
        this.topScorers.setText("Top Assists");
        this.leagueName.setText(league.getName());
        Image imageLeague = new Image(ImagesToResources.getImagePath() + "\\" + league.getName() + "\\" +league.getLeague_logo());

        this.leagueLogo.setImage(imageLeague);
        if (league != null) {
            try {
                ObservableList<Player> players = PlayerRepository.getAllPlayerByLeagueAssist(league);
                for (int i = 0; i < players.size(); i++) {
                    Player player = players.get(i);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("playerRowStat.fxml"));
                    GridPane gridPane = fxmlLoader.load();
                    PlayerRowStatController playerRowStatController = fxmlLoader.getController();
                    int number = i + 1;
                    Image imagePlayer = new Image(ImagesToResources.getImagePath() + "\\" + league.getName() + "\\" + player.getName()+ "\\" +player.getImage());
                    Image imageNation = new Image(ImagesToResources.getImagePath() + "\\" + "Nations"  + "\\" + player.getNationality().getFlag());
                    Team team = PlayerRepository.getPlayerTeam(player);
                    Image imageTeam = new Image(ImagesToResources.getImagePath() + "\\" + league.getName() + "\\" + team.getName()+ "\\" +team.getLogo());
                    playerRowStatController.setDataA(player,imagePlayer,imageTeam,imageNation);

                    vbox1.getChildren().add(gridPane);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
