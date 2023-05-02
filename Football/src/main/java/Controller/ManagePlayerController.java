package Controller;

import Models.League;
import Models.Nation;
import Models.Player;
import Models.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.sql.Date;

public class ManagePlayerController {


    @FXML
    private ChoiceBox<League> choseLeagueToTable;

    @FXML
    private ChoiceBox<League> chosePlayerLeague;

    @FXML
    private ChoiceBox<Nation> chosePlayerNation;

    @FXML
    private ChoiceBox<Team> chosePlayerTeam;

    @FXML
    private TableColumn<Player, Integer> colIdPlayer;

    @FXML
    private TableColumn<Player, String> colNamePlayer;

    @FXML
    private TableColumn<Player, Date> colPlayerBirthday;

    @FXML
    private TableColumn<Player, String> colPlayerFlag;

    @FXML
    private TableColumn<Player, String> colPlayerImage;

    @FXML
    private TableColumn<Player, League> colPlayerLeague;

    @FXML
    private TableColumn<Player, Nation> colPlayerNation;

    @FXML
    private TableColumn<Player, String> colPlayerPos;

    @FXML
    private TableColumn<Player, Team> colPlayerTeam;

    @FXML
    private DatePicker datePlayerBirthday;

    @FXML
    private ImageView imagePlayer;

    @FXML
    private TableView<Player> tablePlayer;

    @FXML
    private TextField txtPlayerName;

    @FXML
    private TextField txtPlayerPosition;

    @FXML
    void addPlayer(ActionEvent event) {

    }

    @FXML
    void browseImagePlayer(ActionEvent event) {

    }

    @FXML
    void clearPlayer(ActionEvent event) {

    }

    @FXML
    void deletePlayer(ActionEvent event) {

    }

    @FXML
    void displayFilteredData(ActionEvent event) {

    }

    @FXML
    void updatePlayer(ActionEvent event) {

    }

}
