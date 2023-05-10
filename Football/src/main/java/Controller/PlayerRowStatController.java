package Controller;

import Models.Player;
import Models.PlayerStatistics;
import Repository.PlayerStatisticsRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;

public class PlayerRowStatController {
    @FXML
    private Label playerGoals;

    @FXML
    private ImageView playerImage;

    @FXML
    private Label playerName;

    @FXML
    private ImageView playerNation;

    @FXML
    private Label playerPosition;

    @FXML
    private ImageView playerTeam;

    public void setDataS(Player player, Image playerImage, Image playerTeam,Image playerNation){
        this.playerName.setText(player.getName());
        this.playerImage.setImage(playerImage);
        this.playerTeam.setImage(playerTeam);
        this.playerNation.setImage(playerNation);
        this.playerPosition.setText(player.getPosition());

        try {
            PlayerStatistics playerStatistics = PlayerStatisticsRepository.getPlayerStatisticsByPlayer(player);
            this.playerGoals.setText(String.valueOf(playerStatistics.getGoal()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void setDataA(Player player, Image playerImage, Image playerTeam,Image playerNation){
        this.playerName.setText(player.getName());
        this.playerImage.setImage(playerImage);
        this.playerTeam.setImage(playerTeam);
        this.playerNation.setImage(playerNation);
        this.playerPosition.setText(player.getPosition());

        try {
            PlayerStatistics playerStatistics = PlayerStatisticsRepository.getPlayerStatisticsByPlayer(player);
            this.playerGoals.setText(String.valueOf(playerStatistics.getAssist()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
