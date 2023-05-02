package Controller;

import Models.League;
import Models.Team;
import Repository.LeagueRepository;
import Repository.TeamRepository;
import Services.BrowseImage;
import Services.CostumedAlerts;
import Services.ImagesToResources;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManageTeamController implements Initializable {

    @FXML
    private Button btnAddTeam;

    @FXML
    private Button btnBrowse;

    @FXML
    private ChoiceBox<League> choseLeague;

    @FXML
    private ImageView teamImage;

    @FXML
    private TextField txtStadiumName;

    @FXML
    private TextField txtTeamName;

    @FXML
    private TextField txtTeamYear;
    @FXML
    private TableColumn<Team, Integer> colId;

    @FXML
    private TableColumn<Team, League> colLeague;

    @FXML
    private TableColumn<Team, String> colLogo;

    @FXML
    private TableColumn<Team, String> colName;

    @FXML
    private TableColumn<Team, String> colStadium;

    @FXML
    private TableColumn<Team, String> colYear;
    @FXML
    private TableView<Team> teamTable;
    @FXML
    private ChoiceBox<League> choseLeagueToTable;
    private File fileSource ;
    private static String imagePath = ImagesToResources.getImagePath();



    @FXML
    void addTeam(ActionEvent event) {
       League league = choseLeague.getValue();
        int leagueId;
        String teamName,teamYear,teamStadium, imageName;
        teamName = txtTeamName.getText();
        teamStadium =txtStadiumName.getText();
        teamYear = txtTeamYear.getText();
        imageName = fileSource.getName();
        Path imagePath = fileSource.toPath();
        Team team = new Team(-1,teamName,teamStadium,teamYear,imageName);
        try {
            TeamRepository.insert(team,league);
            ImagesToResources.imageToResourcesTeam(league.getName(),teamName,imageName,imagePath);
            CostumedAlerts.costumeAlert(Alert.AlertType.CONFIRMATION,
                    "ManageTeams",
                    "Manage Teams",
                    "The Team has been added successfully ");
            fetchData();

        } catch (Exception e) {
            CostumedAlerts.costumeAlert(Alert.AlertType.CONFIRMATION,
                    "ManageTeams",
                    "Manage Teams",
                    "The Team failed to be added");
            throw new RuntimeException(e);
        }
    }

    public void fetchData(){
        try {
            TeamRepository.fetchToTable(teamTable,colId,colName,colLogo,colStadium,colYear,colLeague);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void getDataFromTable(){
        teamTable.setRowFactory( tv -> {
            TableRow<Team> myRow = new TableRow<>();
            myRow.setOnMouseClicked( event ->{
                if (event.getClickCount() == 1 && (!myRow.isEmpty())){
                    int myIndex = teamTable.getSelectionModel().getSelectedIndex();
                    String id = String.valueOf(teamTable.getItems().get(myIndex).getId());
                    String name = teamTable.getItems().get(myIndex).getName();
                    String image = teamTable.getItems().get(myIndex).getLogo();
                    String year = teamTable.getItems().get(myIndex).getYear();
                    League league = teamTable.getItems().get(myIndex).getLeague();
                    txtStadiumName.setText(id);
                    txtTeamName.setText(name);
                    txtTeamYear.setText(year);
                    choseLeague.setValue(league);
                    String path = imagePath+"\\"+league+"\\"+name+"\\"+image;
                    this.teamImage.setImage( new Image(path));
                }
            });
            return myRow;
        });
    }


    @FXML
    void browseImage(ActionEvent event) {
       fileSource = BrowseImage.browseImage(imagePath,fileSource,teamImage);
    }

    @FXML
    void clearTeam(ActionEvent event) {

    }

    @FXML
    void deleteTeam(ActionEvent event) {

    }

    @FXML
    void updateTeam(ActionEvent event) {

    }
    @FXML
    void fetchFilteredData(ActionEvent event){
        League league = choseLeagueToTable.getValue();
        try {
            TeamRepository.fetchToTableByLeague(teamTable,colId,colName,colLogo,colStadium,colYear,colLeague,league);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LeagueRepository.setValues(this.choseLeague);
        LeagueRepository.setValues(this.choseLeagueToTable);

        fetchData();
        getDataFromTable();
    }
}
