package Controller;

import Models.*;
import Repository.*;
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

public class ManageCoachController extends BaseController implements Initializable {


    @FXML
    private ComboBox<League> choseLeagueToTable;

    @FXML
    private ComboBox<League> choseCoachLeague;

    @FXML
    private ComboBox<Nation> choseCoachNation;

    @FXML
    private ComboBox<Team> choseCoachTeam;

    @FXML
    private TableColumn<Coach, Integer> colIdCoach;

    @FXML
    private TableColumn<Coach, String> colNameCoach;

    @FXML
    private TableColumn<Coach, Date> colCoachBirthday;

    @FXML
    private TableColumn<Coach, League> colCoachLeague;

    @FXML
    private TableColumn<Coach, Nation> colCoachNation;

    @FXML
    private TableColumn<Coach, Team> colCoachTeam;

    @FXML
    private DatePicker dateCoachBirthday;

    @FXML
    private ImageView imageCoach;

    @FXML
    private TableView<Coach> tableCoach;

    @FXML
    private TextField txtCoachName;


    @FXML
    private ComboBox<Team> choseTeamToTable;
    private File fileSource;
    private static String  imagePath = ImagesToResources.getImagePath();


    @FXML
    void addCoach(ActionEvent event) {
        League league = choseCoachLeague.getValue();
        Team team = choseCoachTeam.getValue();
        String coachName,imageName;
        coachName = txtCoachName.getText();
        Date birthday ;
        birthday = Date.valueOf(dateCoachBirthday.getValue());
        imageName = fileSource.getName();
        Nation nation = choseCoachNation.getValue();
        Path imagePath = fileSource.toPath();
        Coach coach = new Coach(-1,coachName,birthday,nation,imageName);
        Squad squad = new Squad(0,team,null);
        try {
            CoachRepository.insert(coach,squad,team);
            ImagesToResources.imageToResourcesTeam(league.getName(),coachName,imageName,imagePath);
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
    void browseImageCoach(ActionEvent event) {
        fileSource = BrowseImage.browseImage(imagePath,fileSource,imageCoach);

    }
    public void fetchData(){
        try {
            CoachRepository.fetchToTable(tableCoach,colIdCoach,colNameCoach,colCoachBirthday,colCoachNation,colCoachLeague,colCoachTeam);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void clearCoach(ActionEvent event) {
        txtCoachName.clear();
        dateCoachBirthday.setValue(null);
        choseCoachLeague.setValue(null);
        choseCoachNation.setValue(null);
        choseCoachTeam.setValue(null);
        imageCoach.setImage(null);

    }

    @FXML
    void deleteCoach(ActionEvent event) {
        CoachRepository.Delete(tableCoach);
        fetchData();
    }

    @FXML
    void displayFilteredData(ActionEvent event) {
        if(choseLeagueToTable.getValue() == null){
            CostumedAlerts.costumeAlert(Alert.AlertType.WARNING,"Manage Coach","Manage Coach", "Select a league ");
        }else {

            League league = choseLeagueToTable.getValue();

            try {
                if (choseTeamToTable.getValue() == null) {
                    CoachRepository.fetchToTableByLeague(tableCoach,colIdCoach,colNameCoach,colCoachBirthday,colCoachNation,colCoachTeam,colCoachLeague,league);
                } else {
                    Team team = choseTeamToTable.getValue();
                    CoachRepository.fetchToTableByTeam(tableCoach,colIdCoach,colNameCoach,colCoachBirthday,colCoachNation,colCoachTeam,colCoachLeague,team);
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
    void updateCoach(ActionEvent event) {

    }
    static void setValuesToTeams(ComboBox<League>  choseCoachLeague,ComboBox<Team> choseCoachTeam){
        choseCoachLeague.setOnAction(actionEvent -> {
            if(choseCoachLeague.getValue() !=null){

                choseCoachTeam.getItems().clear();

                League league = choseCoachLeague.getValue();
                TeamRepository.setValues(choseCoachTeam,league);
            }
        });
    }

    public void getDataFromTable(){
        tableCoach.setRowFactory( tv -> {
            TableRow<Coach> myRow = new TableRow<>();
            myRow.setOnMouseClicked( event ->{
                if (event.getClickCount() == 1 && (!myRow.isEmpty())){
                    int myIndex = tableCoach.getSelectionModel().getSelectedIndex();
                    int id = tableCoach.getItems().get(myIndex).getId();
                    try {
                        Coach coach = CoachRepository.findById(id);
                        String name = coach.getName();
                        String image = coach.getImage();
                        Date birthday = coach.getBirthday();
                        Nation nation = coach.getNationality();
                        League league = tableCoach.getItems().get(myIndex).getLeague();
                        Team team = tableCoach.getItems().get(myIndex).getTeam();
                        txtCoachName.setText(name);
                        dateCoachBirthday.setValue(birthday.toLocalDate());
                        choseCoachTeam.setValue(team);
                        choseCoachNation.setValue(nation);
                        choseCoachLeague.setValue(league);
                        String path = imagePath+"\\"+league+"\\"+name+"\\"+image;
                        this.imageCoach.setImage( new Image(path));
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



    }

    @Override
    void translateAlbanian() {
        Locale currentLocale = new Locale("sq");
        ResourceBundle translate = ResourceBundle.getBundle("translations.content", currentLocale);

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
        setValuesToTeams(this.choseCoachLeague,this.choseCoachTeam);
        setValuesToTeams(this.choseLeagueToTable,this.choseTeamToTable);

        NationRepository.setValues(this.choseCoachNation);
        LeagueRepository.setValues(this.choseCoachLeague);
        LeagueRepository.setValues(this.choseLeagueToTable);
        fetchData();
        getDataFromTable();
    }
}
