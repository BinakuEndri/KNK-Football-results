package Controller;

import Models.*;
import Repository.LeagueTeamsRepository;
import Repository.MatchRepository;
import Repository.MatchStatisticsRepository;
import Services.ImagesToResources;
import Services.LanguageUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class LandingController extends BaseController implements Initializable {


    @FXML
    private VBox vbox;

    @FXML
    private DatePicker datePicker;
    @FXML
    private Label dashboard;
    @FXML
    private Label signOut;
    @FXML
    private Label login;

    @FXML
    private Label choseDate;


    private boolean loggedIn;

    private  void iterateToArray(VBox vbox, ObservableList<Match> matches){
        for (int i = 0; i < matches.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("matchRow.fxml"));

            VBox vBox = null;
            try {
                vBox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            MatchRowController matchRowController = fxmlLoader.getController();
            Match match = matches.get(i);
            Team homeTeam = match.getHome_team_id();
            Team awayTeam = match.getAway_team_id();
            LeagueTeams leagueTeams = null;
            try {
                leagueTeams = LeagueTeamsRepository.findByLeague(homeTeam);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            League league = leagueTeams.getLeague_id();
            Image imageHome = new Image(ImagesToResources.getImagePath() + "\\" + league.getName() + "\\" + homeTeam.getName() + "\\" + homeTeam.getLogo());
            Image imageAway = new Image(ImagesToResources.getImagePath() + "\\" + league.getName() + "\\" + awayTeam.getName() + "\\" + awayTeam.getLogo());
            Image leagueLogo = new Image(ImagesToResources.getImagePath() + "\\" + league.getName() + "\\" + league.getLeague_logo());

            MatchStatistics matchStatistics = null;
            try {
                matchStatistics = MatchStatisticsRepository.findByMatch(match);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            matchRowController.setData(match, imageHome, imageAway, leagueLogo, matchStatistics, league);
            vbox.getChildren().add(vBox);
        }
    }
    public void fetchToVBox(VBox vbox){
            datePicker.setOnAction(actionEvent -> {

            ObservableList<Match> matches;
            if(datePicker.getValue() != null) {
            Date date = Date.valueOf(datePicker.getValue());

                try {
                    matches = MatchRepository.getAllMatchesByDate(date);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                vbox.getChildren().clear();

                iterateToArray(vbox, matches);
            }
            else {
                vbox.getChildren().clear();
                fetchToVBox(vbox);
            }
            });
            if(datePicker.getValue() == null) {
                ObservableList<Match> matches;
                try {
                    matches = MatchRepository.getAllMatchesToday();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                vbox.getChildren().clear();

                iterateToArray(vbox, matches);

            }

    }

    public  void toDashboard(){
        this.dashboard.setOnMouseClicked(actionEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("AdminDashboard.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root,1200,700);
            stage.setScene(scene);
            stage.show();
        });
    }
    private void signOut(){
        this.signOut.setOnMouseClicked(event -> {
            try {
                Preferences prefs = Preferences.userNodeForPackage(LoginController.class);
                prefs.clear();
                Parent root = FXMLLoader.load(getClass().getResource("landing.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root,1200,700);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }
    private  void login(){
        this.login.setOnMouseClicked(event -> {
            try {
                Preferences prefs = Preferences.userNodeForPackage(LoginController.class);
                prefs.clear();
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root,1200,700);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchToVBox(vbox);
        Preferences prefs = Preferences.userNodeForPackage(LoginController.class);
        loggedIn = prefs.getBoolean("loggedIn",false);
        dashboard.setVisible(loggedIn);
        signOut.setVisible(loggedIn);
        login.setVisible(!loggedIn);
        toDashboard();
        signOut();
        login();
        changeLanguage();
    }

    @Override
    void translateEnglish() {
        Locale currentLocale = new Locale("en");
        ResourceBundle translate = ResourceBundle.getBundle("translations.content", currentLocale);

        login.setText(translate.getString("login"));
        dashboard.setText(translate.getString("dashboard"));
        signOut.setText(translate.getString("signOut"));
        choseDate.setText(translate.getString("choseDate"));

    }

    @Override
    void translateAlbanian() {
        Locale currentLocale = new Locale("sq");
        ResourceBundle translate = ResourceBundle.getBundle("translations.content", currentLocale);

        login.setText(translate.getString("login"));
        dashboard.setText(translate.getString("dashboard"));
        signOut.setText(translate.getString("signOut"));
        choseDate.setText(translate.getString("choseDate"));
    }

   private void changeLanguage(){
       LanguageUtil.languageProperty().addListener((observable, oldValue, newValue) -> {
           if (newValue.equals("Albanian")) {
               translateAlbanian();
           } else {
               translateEnglish();
           }
       });
    }
}
