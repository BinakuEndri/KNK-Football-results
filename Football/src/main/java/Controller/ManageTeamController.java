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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
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
    private File fileSource ;
    private static String imagePath;
    public void setValues(){
        try {
            for (League league : LeagueRepository.getAllLeagues()) {
                choseLeague.getItems().add(league);
            }

        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
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

        } catch (Exception e) {
            CostumedAlerts.costumeAlert(Alert.AlertType.CONFIRMATION,
                    "ManageTeams",
                    "Manage Teams",
                    "The Team failed to be added");
            throw new RuntimeException(e);
        }
    }

    @FXML
    void browseImage(ActionEvent event) {
       fileSource = BrowseImage.browseImage(imagePath,fileSource,teamImage);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setValues();
    }
}
