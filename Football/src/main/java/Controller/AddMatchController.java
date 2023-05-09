package Controller;

import Models.*;
import Repository.GoalRepository;
import Repository.LeagueRepository;
import Repository.MatchRepository;
import Repository.TeamRepository;
import Services.CostumedAlerts;
import Services.ImagesToResources;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class AddMatchController implements Initializable {

    @FXML
    private VBox vbox1;
    @FXML
    private VBox vbox2;

    @FXML
    private ComboBox<Team> choseAwayTeam;

    @FXML
    private ComboBox<Team> choseHomeTeam;

    @FXML
    private ComboBox<League> choseLeagueMatch;

    @FXML
    private TextField fieldAwayTeamGoal;

    @FXML
    private TextField fieldHomeTeamGoal;

    @FXML
    private ImageView imageAwayTeam;

    @FXML
    private ImageView imageHomeTeam;

    @FXML
    private Label txtAwayTeamName;

    @FXML
    private Label txtHomeTeamName;
    @FXML
    private DatePicker choseDate;

    @FXML
    private TextField txtAwayCorners;

    @FXML
    private TextField txtAwayFouls;

    @FXML
    private TextField txtAwayPossesion;

    @FXML
    private TextField txtAwayRedCard;

    @FXML
    private TextField txtAwayShots;

    @FXML
    private TextField txtAwayYellowCard;

    @FXML
    private TextField txtHomeCorner;

    @FXML
    private TextField txtHomeFouls;

    @FXML
    private TextField txtHomePossesion;

    @FXML
    private TextField txtHomeRedCard;

    @FXML
    private TextField txtHomeShots;

    @FXML
    private TextField txtHomeYellowCard;

    private String imagePath = ImagesToResources.getImagePath();

    private Team team ;

    static void setValuesToHomeTeam(ComboBox<League>  choseLeagueMatch,ComboBox<Team> choseHomeTeam,ComboBox<Team> choseAwayTeam){
        choseLeagueMatch.setOnAction(actionEvent -> {
            if(choseLeagueMatch.getValue() !=null){

                choseHomeTeam.getItems().clear();
                choseAwayTeam.getItems().clear();

                League league = choseLeagueMatch.getValue();
                TeamRepository.setValues(choseHomeTeam,league);
            }
        });
    }

    static void setValuesFromHome(ComboBox<League> choseLeagueMatch,ComboBox<Team> choseAwayTeam,
                                  ComboBox<Team> choseHomeTeam,Label txtTeamName, ImageView imageTeam ,String imagePath){
        choseHomeTeam.setOnAction(actionEvent -> {
            if(choseHomeTeam.getValue() !=null){
                choseAwayTeam.getItems().clear();
                Team team = choseHomeTeam.getValue();
                League league = choseLeagueMatch.getValue();
                TeamRepository.setValuesAwayTeam(choseAwayTeam,league,team);
                txtTeamName.setText(choseHomeTeam.getValue().getName());
                Image image = new Image(imagePath+ "\\"+choseLeagueMatch.getValue().getName() + "\\"+ choseHomeTeam.getValue().getName()+"\\"+choseHomeTeam.getValue().getLogo());
                imageTeam.setImage(image);
            }else{
                txtTeamName.setText(null);
                imageTeam.setImage(null);
            }
        });
    }

    static void setValuesFromAway(ComboBox<Team> choseTeam,ComboBox<League> choseLeagueMatch,Label txtTeamName, ImageView imageTeam ,String imagePath){
        choseTeam.setOnAction(actionEvent -> {
            if(choseTeam.getValue() != null){
                txtTeamName.setText(choseTeam.getValue().getName());
                Image image = new Image(imagePath+ "\\"+choseLeagueMatch.getValue().getName() + "\\"+ choseTeam.getValue().getName()+"\\"+choseTeam.getValue().getLogo());
                imageTeam.setImage(image);
            }else{
                txtTeamName.setText(null);
                imageTeam.setImage(null);
            }
        });
    }
    private void addGoals(TextField fieldTeamGoal, VBox vbox1,ComboBox<Team> homeTeam , ComboBox<Team> awayTeam){
        fieldTeamGoal.setOnKeyTyped(actionEvent -> {
            String text  = fieldTeamGoal.getText();
            if(text != null && !text.isEmpty()){
                vbox1.getChildren().clear();
                int numberOfGoals = Integer.parseInt(text);
                try {
                    for (int i = 0; i < numberOfGoals; i++) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("Goal.fxml"));
                        VBox vbox = fxmlLoader.load();
                        GoalController goalController = fxmlLoader.getController();
                        vbox.getProperties().put("fxmlLoader",fxmlLoader);
                        vbox1.getChildren().add(vbox);
                        RadioButton ownGoal =  goalController.getOwnGoal();

                        goalController.setData(homeTeam.getValue());

                        ownGoal.setOnAction(actionEvent1 -> {
                            if(ownGoal.isSelected()){
                                 goalController.setDataOwnGoal(homeTeam.getValue(),awayTeam.getValue());
                            }else{
                                goalController.setData(homeTeam.getValue());

                            }
                        });
                    }
                }
                catch (IOException e){

                }
            }
            else {
                vbox1.getChildren().clear();

            }
        });
    }
     @FXML
     void addMatch(ActionEvent actionEvent){

         if(choseLeagueMatch.getValue() == null || choseHomeTeam.getValue() == null ||
                 choseAwayTeam.getValue() == null || choseDate.getValue() == null ||
                 fieldHomeTeamGoal.getText().isEmpty() || fieldAwayTeamGoal.getText().isEmpty() ||
                 txtHomePossesion.getText().isEmpty() || txtAwayPossesion.getText().isEmpty() ||
                 txtHomeShots.getText().isEmpty() || txtAwayShots.getText().isEmpty() ||
                 txtHomeCorner.getText().isEmpty() || txtAwayCorners.getText().isEmpty() ||
                 txtHomeYellowCard.getText().isEmpty() || txtAwayYellowCard.getText().isEmpty() ||
                 txtHomeRedCard.getText().isEmpty() || txtAwayRedCard.getText().isEmpty() ||
                 txtHomeFouls.getText().isEmpty() || txtAwayFouls.getText().isEmpty()){

             CostumedAlerts.costumeAlert(Alert.AlertType.WARNING,
                     "Add Match",
                     "Missing Fields",
                     "Please fill out all required fields before submitting the form.");
             return;
         }

        League league = choseLeagueMatch.getValue();
        Team homeTeam = choseHomeTeam.getValue();
        Team awayTeam = choseAwayTeam.getValue();
        Date matchDate = Date.valueOf(choseDate.getValue());
        Match match = new Match(-1,homeTeam,awayTeam,matchDate);
        int goalsHome = Integer.parseInt(fieldHomeTeamGoal.getText());
        int goalsAway = Integer.parseInt(fieldAwayTeamGoal.getText());
        double possessionHome = Double.parseDouble(txtHomePossesion.getText());
        double possessionAway = Double.parseDouble(txtAwayPossesion.getText());
        int shotsHome = Integer.parseInt(txtHomeShots.getText());
        int shotsAway = Integer.parseInt(txtAwayShots.getText());
        int cornersHome = Integer.parseInt(txtHomeCorner.getText());
        int cornersAway = Integer.parseInt(txtAwayCorners.getText());
        int yellowHome = Integer.parseInt(txtHomeYellowCard.getText());
        int yellowAway = Integer.parseInt(txtAwayYellowCard.getText());
        int redHome = Integer.parseInt(txtHomeRedCard.getText());
        int redAway = Integer.parseInt(txtAwayRedCard.getText());
        int foulsHome = Integer.parseInt(txtHomeFouls.getText());
        int foulsAway = Integer.parseInt(txtAwayFouls.getText());

        MatchStatistics matchStatistics = new MatchStatistics(-1,null,goalsHome,goalsAway,
                possessionHome,possessionAway,shotsHome,shotsAway,
                cornersHome,cornersAway,foulsHome,foulsAway,
                yellowHome,yellowAway,redHome,redAway);

        try {
            MatchRepository.insert(match,league,matchStatistics);
            match.setId(MatchRepository.findIdByData(match));
            List<Goal> homeGoals = getGoals(vbox1,choseHomeTeam,match);
            List<Goal> awayGoals = getGoals(vbox2,choseAwayTeam,match);
            // Store the goals in the database
            addGoal(homeGoals);
            addGoal(awayGoals);
            CostumedAlerts.costumeAlert(Alert.AlertType.CONFIRMATION,
                    "ManagePlayers",
                    "Manage Player",
                    "The Player has been added successfully ");

        }catch (SQLException e){
            CostumedAlerts.costumeAlert(Alert.AlertType.CONFIRMATION,
                    "ManagePlayer",
                    "Manage Player",
                    "The Player failed to be added");
            throw new RuntimeException(e);

        }

    }

    static List<Goal> getGoals(VBox vbox,ComboBox<Team> choseTeam, Match match){
        List<Goal> goals = new ArrayList<>();
        for (Node node : vbox.getChildren()) {
            if (node instanceof VBox) {
                FXMLLoader fxmlLoader = (FXMLLoader) node.getProperties().get("fxmlLoader");
                GoalController goalController = fxmlLoader.getController();
                Goal goal = goalController.getGoal(choseTeam);
                goal.setGame(match);
                goals.add(goal);
            }
        }
        return goals;
    }
    static void addGoal(List<Goal> goals){
        for (Goal goal : goals) {
            try {
                GoalRepository.insert(goal);
            }
            catch (SQLException e){

            }
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LeagueRepository.setValues(this.choseLeagueMatch);
        addGoals(fieldHomeTeamGoal,vbox1, choseHomeTeam,choseAwayTeam);
        addGoals(fieldAwayTeamGoal,vbox2,choseAwayTeam,choseHomeTeam);

        setValuesToHomeTeam(choseLeagueMatch,choseHomeTeam,choseAwayTeam);
        setValuesFromHome(choseLeagueMatch,choseAwayTeam,choseHomeTeam,txtHomeTeamName,imageHomeTeam,imagePath);
        setValuesFromAway(choseAwayTeam,choseLeagueMatch,txtAwayTeamName,imageAwayTeam,imagePath);

    }
}
