package Controller;

import Models.League;
import Repository.LeagueRepository;
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

public class ManageLeagueController implements Initializable {

    @FXML
    private TableColumn<League, Integer> colLeagueId;

    @FXML
    private TableColumn<League, String> colLeagueName;

    @FXML
    private TableColumn<League, Integer> colLeagueNumberOfTeams;

    @FXML
    private ImageView leaguePhoto;

    @FXML
    private TableView<League> tableLeagues;

    @FXML
    private TextField txtLeagueId;

    @FXML
    private TextField txtLeagueName;

    private File filesrc;

    private String imagePath = ImagesToResources.getImagePath();



    @FXML
    void addLeague(ActionEvent event){
        int leagueId;
        String leagueName, imageName;
        leagueId = Integer.parseInt(txtLeagueId.getText());
        leagueName = txtLeagueName.getText();
        imageName = filesrc.getName();
        Path imagePath = filesrc.toPath();
        League league = new League(leagueId,leagueName,imageName);
        try {
            LeagueRepository.insert(league);
            ImagesToResources.imageToResources(leagueName,imageName,imagePath);
            CostumedAlerts.costumeAlert(Alert.AlertType.CONFIRMATION,
                    "ManageLeagues",
                    "Manage Leagues",
                    "The League has been added successfully ");
            fetchData();

        } catch (Exception e) {
            CostumedAlerts.costumeAlert(Alert.AlertType.CONFIRMATION,
                    "ManageLeagues",
                    "Manage Leagues",
                    "The League failed to be added");
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void deleteLeague(ActionEvent actionEvent){
        LeagueRepository.Delete(tableLeagues);
        fetchData();
    }

    @FXML
    public void updateLeague(ActionEvent actionEvent){
        int index = tableLeagues.getSelectionModel().getSelectedIndex();
        String imageName = tableLeagues.getItems().get(index).getLeague_logo();
        String leaguename = tableLeagues.getItems().get(index).getName();
        File file = new File(ImagesToResources.getImagePath()+"\\"+leaguename+"\\"+imageName);
        Path path = file.toPath();
        if(filesrc != null) {
             imageName = filesrc.getName();
             path = filesrc.toPath();
        }
        String name = txtLeagueName.getText();
        name.trim();
        League league = new League(1,name,imageName);
        LeagueRepository.Update(tableLeagues,league,path);
        fetchData();
        clearLeagueFields(actionEvent);
    }
    @FXML
    public void clearLeagueFields(ActionEvent actionEvent){
        leaguePhoto.setImage(null);
        txtLeagueName.clear();
        txtLeagueId.clear();
    }

    public void fetchData(){
        try {
            LeagueRepository.fetchToTable(tableLeagues,colLeagueId,colLeagueName,colLeagueNumberOfTeams);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void getDataFromTable(){
        tableLeagues.setRowFactory( tv -> {
            TableRow<League> myRow = new TableRow<>();
            myRow.setOnMouseClicked( event ->{
                if (event.getClickCount() == 1 && (!myRow.isEmpty())){
                    int myIndex = tableLeagues.getSelectionModel().getSelectedIndex();
                    int id = tableLeagues.getItems().get(myIndex).getId();
                    try {
                        League league = LeagueRepository.findById(id);
                        String name = league.getName();
                        String image = league.getLeague_logo();
                        txtLeagueId.setText(String.valueOf(league.getId()));
                        txtLeagueName.setText(league.getName());
                        String path = imagePath +"\\"+name+"\\"+image;
                        this.leaguePhoto.setImage( new Image(path));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
            return myRow;
        });
    }
    @FXML
    void browseImage(ActionEvent event){
        filesrc = BrowseImage.browseImage(imagePath,filesrc,leaguePhoto);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchData();
        getDataFromTable();
    }

}
