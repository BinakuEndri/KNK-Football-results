package Controller;

import Models.*;
import Repository.LeagueRepository;
import Repository.NationRepository;
import Repository.PlayerRepository;
import Repository.TeamRepository;
import Services.BrowseImage;
import Services.CostumedAlerts;
import Services.ImagesToResources;
import Services.LanguageUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ManagePlayerController extends BaseController implements Initializable {

    @FXML
    private Label birthday;

    @FXML
    private Label photo;

    @FXML
    private Label position;

    @FXML
    private Label team;

    @FXML
    private Label league;

    @FXML
    private Label selectLeague;

    @FXML
    private Label selectTeam;


    @FXML
    private Label name;

    @FXML
    private Label nation;
    @FXML
    private ComboBox<League> choseLeagueToTable;

    @FXML
    private ComboBox<League> chosePlayerLeague;

    @FXML
    private ComboBox<Nation> chosePlayerNation;

    @FXML
    private ComboBox<Team> chosePlayerTeam;

    @FXML
    private TableColumn<Player, Integer> colIdPlayer;

    @FXML
    private TableColumn<Player, String> colNamePlayer;

    @FXML
    private TableColumn<Player, Date> colPlayerBirthday;

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
    private ComboBox<Team> choseTeamToTable;
    private File fileSource;
    private static String  imagePath = ImagesToResources.getImagePath();


    @FXML
    void addPlayer(ActionEvent event) {
        League league = chosePlayerLeague.getValue();
        Team team = chosePlayerTeam.getValue();
        String playerName,playerPosition, imageName;
        playerName = txtPlayerName.getText();
        playerPosition =txtPlayerPosition.getText();
        Date birthday ;
        birthday = Date.valueOf(datePlayerBirthday.getValue());
        imageName = fileSource.getName();
        Nation nation = chosePlayerNation.getValue();
        Path imagePath = fileSource.toPath();
        Player player = new Player(-1,playerName,playerPosition,birthday,nation,imageName);
        Squad squad = new Squad(0,team,null);
            try {
                PlayerRepository.insert(player,squad,team);
                ImagesToResources.imageToResourcesTeam(league.getName(),playerName,imageName,imagePath);
                CostumedAlerts.costumeAlert(Alert.AlertType.CONFIRMATION,
                        "ManagePlayers",
                        "Manage Player",
                        "The Player has been added successfully ");
                fetchData();

            } catch (Exception e) {
                CostumedAlerts.costumeAlert(Alert.AlertType.CONFIRMATION,
                        "ManagePlayer",
                        "Manage Player",
                        "The Player failed to be added");
                throw new RuntimeException(e);
            }
    }


    @FXML
    void browseImagePlayer(ActionEvent event) {
        fileSource = BrowseImage.browseImage(imagePath,fileSource,imagePlayer);

    }
    public void fetchData(){
        try {
            PlayerRepository.fetchToTable(tablePlayer,colIdPlayer,colNamePlayer,colPlayerBirthday,colPlayerLeague,colPlayerNation,colPlayerPos,colPlayerTeam);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void clearPlayer(ActionEvent event) {
        txtPlayerPosition.clear();
        txtPlayerName.clear();
        datePlayerBirthday.setValue(null);
        chosePlayerNation.setValue(null);
        chosePlayerTeam.setValue(null);
        chosePlayerLeague.setValue(null);
        imagePlayer.setImage(null);

    }

    @FXML
    void deletePlayer(ActionEvent event) {
        PlayerRepository.Delete(tablePlayer);
        fetchData();
    }

    @FXML
    void displayFilteredData(ActionEvent event) {
        if(choseLeagueToTable.getValue() == null){
            CostumedAlerts.costumeAlert(Alert.AlertType.WARNING,"Manage Player","Manage Player", "Select a league ");
        }else {

            League league = choseLeagueToTable.getValue();

            try {
                if (choseTeamToTable.getValue() == null) {
                    PlayerRepository.fetchToTableByLeague(tablePlayer,colIdPlayer,colNamePlayer,colPlayerBirthday,colPlayerNation,colPlayerPos,colPlayerTeam,colPlayerLeague,league);
                } else {
                    Team team = choseTeamToTable.getValue();
                    PlayerRepository.fetchToTableByTeam(tablePlayer,colIdPlayer,colNamePlayer,colPlayerBirthday,colPlayerNation,colPlayerPos,colPlayerTeam,colPlayerLeague,team);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void clearTableFilter(){
        choseTeamToTable.setValue(null);
        choseLeagueToTable.setValue(null);
        fetchData();
    }

    @FXML
    void updatePlayer(ActionEvent event) {

    }
    static void setValuesToTeams(ComboBox<League> chosePlayerLeague,ComboBox<Team> chosePlayerTeam){
        chosePlayerLeague.setOnAction(actionEvent -> {
            if(chosePlayerLeague.getValue() !=null){

                chosePlayerTeam.getItems().clear();

                League league = chosePlayerLeague.getValue();
                TeamRepository.setValues(chosePlayerTeam,league);
            }
        });
    }

    public void getDataFromTable(){
        tablePlayer.setRowFactory( tv -> {
            TableRow<Player> myRow = new TableRow<>();
            myRow.setOnMouseClicked( event ->{
                if (event.getClickCount() == 1 && (!myRow.isEmpty())){
                    int myIndex = tablePlayer.getSelectionModel().getSelectedIndex();
                    int id = tablePlayer.getItems().get(myIndex).getId();
                    try {
                        Player player = PlayerRepository.findById(id);
                        String name = player.getName();
                        String image = player.getImage();
                        Date birthday = player.getBirthday();
                        String position = player.getPosition();
                        Nation nation = player.getNationality();
                        League league = tablePlayer.getItems().get(myIndex).getLeague();
                        Team team = tablePlayer.getItems().get(myIndex).getTeam();
                        txtPlayerName.setText(name);
                        datePlayerBirthday.setValue(birthday.toLocalDate());
                        chosePlayerTeam.setValue(team);
                        chosePlayerNation.setValue(nation);
                        txtPlayerPosition.setText(position);
                        chosePlayerLeague.setValue(league);
                        String path = imagePath+"\\"+league+"\\"+name+"\\"+image;
                        this.imagePlayer.setImage( new Image(path));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
            return myRow;
        });
    }


    @Override
    void translateEnglish() {
        Locale currentLocale = new Locale("en");
        ResourceBundle translate = ResourceBundle.getBundle("translations.content", currentLocale);
        birthday.setText(translate.getString("label.birthday"));
        name.setText(translate.getString("label.name"));
        nation.setText(translate.getString("label.nation"));
        league.setText(translate.getString("label.league"));
        team.setText(translate.getString("label.team"));
        photo.setText(translate.getString("label.photo"));
        selectLeague.setText(translate.getString("label.selectLeague"));
        selectTeam.setText(translate.getString("label.selectTeam"));


    }

    @Override
    void translateAlbanian() {
        Locale currentLocale = new Locale("sq");
        ResourceBundle translate = ResourceBundle.getBundle("translations.content", currentLocale);
        birthday.setText(translate.getString("label.birthday"));
        name.setText(translate.getString("label.name"));
        nation.setText(translate.getString("label.nation"));
        league.setText(translate.getString("label.league"));
        team.setText(translate.getString("label.team"));
        photo.setText(translate.getString("label.photo"));
        selectLeague.setText(translate.getString("label.selectLeague"));
        selectTeam.setText(translate.getString("label.selectTeam"));
    }

    public void changeLanguage(){
        LanguageUtil.languageProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Albanian")) {
                translateAlbanian();
            } else {
                translateEnglish();
            }
        });
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setValuesToTeams(this.chosePlayerLeague,this.chosePlayerTeam);
        setValuesToTeams(this.choseLeagueToTable,this.choseTeamToTable);

        NationRepository.setValues(this.chosePlayerNation);
       LeagueRepository.setValues(this.chosePlayerLeague);
       LeagueRepository.setValues(this.choseLeagueToTable);
        fetchData();
        getDataFromTable();
        changeLanguage();
    }
}
