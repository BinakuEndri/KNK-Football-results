package Controller;

import Models.League;
import Repository.LeagueRepository;
import Services.ImagesToResources;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddLeagueController implements Initializable {
    @FXML
    private Button btnAddLeague;

    @FXML
    private Button btnBrowseImage;

    @FXML
    private Button btnClearLeague;

    @FXML
    private Button btnDeleteLeague;

    @FXML
    private Button btnLaLiga;

    @FXML
    private Button btnLaLiga1;

    @FXML
    private Button btnLaLiga11;

    @FXML
    private Button btnLaLiga12;

    @FXML
    private Button btnPremierLeague;

    @FXML
    private Button btnPremierLeague1;

    @FXML
    private Button btnSeriaA;

    @FXML
    private Button btnSuperLiga;

    @FXML
    private Button btnUpdateLeague;

    @FXML
    private TableColumn<League, Integer> colLeagueId;

    @FXML
    private TableColumn<League, String> colLeagueName;

    @FXML
    private TableColumn<League, Integer> colLeagueNumberOfTeams;

    @FXML
    private TableColumn<League, String> colLeagueImage;

    @FXML
    private ImageView leaguePhoto;

    @FXML
    private TableView<League> tableLeagues;

    @FXML
    private TextField txtLeagueId;

    @FXML
    private TextField txtLeagueName;


    private File filesrc;

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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "The League has been added successfully ",
                    ButtonType.CLOSE);
            alert.showAndWait();
            fetchData();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "The League failed to be added ",
                    ButtonType.CLOSE);
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }
    public void fetchData(){
        try {
            LeagueRepository.fetchToTable(tableLeagues,colLeagueId,colLeagueName,colLeagueImage);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void deleteLeague(ActionEvent actionEvent){
        LeagueRepository.Delete(tableLeagues);
        fetchData();
    }
    FileChooser fileChooser = new FileChooser();
    public void getDataFromTable(){
        tableLeagues.setRowFactory( tv -> {
            TableRow<League> myRow = new TableRow<>();
            myRow.setOnMouseClicked( event ->{
                if (event.getClickCount() == 1 && (!myRow.isEmpty())){
                   int myIndex = tableLeagues.getSelectionModel().getSelectedIndex();
                    String id = String.valueOf(tableLeagues.getItems().get(myIndex).getId());
                    String name = tableLeagues.getItems().get(myIndex).getName();
                    String image = tableLeagues.getItems().get(myIndex).getLeague_logo();
                    txtLeagueId.setText(id);
                    txtLeagueName.setText(name);
                    String path = "C:\\Users\\PC-SYSTEMS\\IdeaProjects\\KNK-Football-results\\Football\\src\\main\\resources\\images\\"+name+"\\"+image;
                    this.leaguePhoto.setImage( new Image(path));
                }
            });
            return myRow;
        });
    }
    @FXML
    void browseImage(ActionEvent event){
        fileChooser.setInitialDirectory(new File("C:\\Users\\PC-SYSTEMS\\Desktop\\fiek"));
        this.filesrc = fileChooser.showOpenDialog(new Stage());
        if(this.filesrc != null) {
            Image image = new Image(this.filesrc.getAbsolutePath());
            this.leaguePhoto.setImage(image);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchData();
        getDataFromTable();
    }
}
