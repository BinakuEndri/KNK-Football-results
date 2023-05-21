package Controller;

import Models.*;
import Repository.NationRepository;
import Repository.PlayerRepository;
import Repository.SquadRepository;
import Services.ImagesToResources;
import Services.LanguageUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class TeamDetailsController extends BaseController{

    @FXML
    private Label assists;

    @FXML
    private ImageView coachImage;

    @FXML
    private Label coachName;

    @FXML
    private ImageView coachNationFlag;

    @FXML
    private Label goals;

    @FXML
    private Label image;

    @FXML
    private Label name;

    @FXML
    private Label nation;

    @FXML
    private Label players;

    @FXML
    private Label position;

    @FXML
    private ImageView teamLogo;

    @FXML
    private Label teamName;

    @FXML
    private VBox vbox1;



    public void setData(League league, Team team) {
        Image image= new Image(ImagesToResources.getImagePath()+"\\"+league.getName()+"\\"+team.getName()+"\\"+team.getLogo());
        try {
            Squad squad = SquadRepository.findIdByTeam(team);
            Coach coach = squad.getCoach_id();
            teamLogo.setImage(image);
            teamName.setText(team.getName());
            if(coach != null) {
                Image cImage = new Image(ImagesToResources.getImagePath() + "\\" + league.getName() + "\\" + coach.getName() + "\\" + coach.getImage());
                coachImage.setImage(cImage);
                coachName.setText(coach.getName());
                Nation nation = NationRepository.findById(coach.getNationality().getId());
                Image nImage = new Image(ImagesToResources.getImagePath() + "\\Nations\\" + nation.getFlag());
                coachNationFlag.setImage(nImage);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            ObservableList<Player> players = PlayerRepository.getAllPlayerByTeam(team);
            if(!players.isEmpty()) {
                for (int i = 0; i < players.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("playerRow.fxml"));
                    GridPane gridPane = fxmlLoader.load();
                    PlayerRowController playerRowController = fxmlLoader.getController();
                    this.vbox1.getChildren().add(gridPane);
                    Image playerNation = new Image(ImagesToResources.getImagePath() + "\\Nations\\" + players.get(i).getNationality().getFlag());
                    Image playerImage = new Image(ImagesToResources.getImagePath() + "\\" + league.getName() + "\\" + players.get(i).getName() + "\\" + players.get(i).getImage());
                    playerRowController.setData(players.get(i), playerImage, playerNation);
                }
            }else{
              //
            }
        }catch(Exception e){
                throw new RuntimeException(e);
            }
        }


    @Override
    void translateEnglish() {
        Locale currentLocale = new Locale("en");
        ResourceBundle translate = ResourceBundle.getBundle("translations.content", currentLocale);
        name.setText(translate.getString("label.name"));
        assists.setText(translate.getString("label.assists"));
        position.setText(translate.getString("label.position"));
        image.setText(translate.getString("label.image"));
        nation.setText(translate.getString("label.nation"));
        goals.setText(translate.getString("label.goals"));
        players.setText(translate.getString("label.players"));

    }

    @Override
    void translateAlbanian() {
        Locale currentLocale = new Locale("sq");
        ResourceBundle translate = ResourceBundle.getBundle("translations.content", currentLocale);
        name.setText(translate.getString("label.name"));
        assists.setText(translate.getString("label.assists"));
        position.setText(translate.getString("label.position"));
        image.setText(translate.getString("label.image"));
        nation.setText(translate.getString("label.nation"));
        goals.setText(translate.getString("label.goals"));
        players.setText(translate.getString("label.players"));

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
}
