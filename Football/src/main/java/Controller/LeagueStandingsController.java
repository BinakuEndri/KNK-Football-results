package Controller;

import Models.League;
import Models.Standings;
import Repository.StandingsRepository;
import Services.ImagesToResources;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;


public class LeagueStandingsController {
    @FXML
    private VBox vbox1;
    @FXML
    private Button leagueName;

    @FXML
    private Button topScorers;
    @FXML
    private Button topAssists;

    private League league;
    @FXML
    private ImageView leagueImage;

    @FXML
    private Label dashboard;
    @FXML
    private Label signOut;
    @FXML
    private Label login;
    private boolean loggedIn;

    public VBox getVbox1() {
        return vbox1;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public void setLeagueNameText(String leagueName) {
        this.leagueName.setText(leagueName);
    }

    public void loggedIn(){
        Preferences prefs = Preferences.userNodeForPackage(LoginController.class);
        loggedIn = prefs.getBoolean("loggedIn",false);
        dashboard.setVisible(loggedIn);
        signOut.setVisible(loggedIn);
        login.setVisible(!loggedIn);
    }
    public void toDashboard(){
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
    public void signOut(){
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
    public  void login(){
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

    public void addTeamStandings(VBox vbox1){
        if(league != null) {
            try {
                ObservableList<Standings> standings = StandingsRepository.getAllStandings(this.league);
                for (int i = 0; i < standings.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("teamStanding.fxml"));
                    HBox hbox = fxmlLoader.load();
                    TeamStandingController teamStandingController = fxmlLoader.getController();
                    int number = i + 1;
                    Image image = new Image(ImagesToResources.getImagePath() + "\\" + "Position" + "\\" + number + ".png");
                    teamStandingController.setData(standings.get(i), image);

                    vbox1.getChildren().add(hbox);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Image imageLeague = new Image(ImagesToResources.getImagePath() + "\\" + league.getName() + "\\" +league.getLeague_logo());
            leagueImage.setImage(imageLeague);
            topScorers.setOnAction( actionEvent -> {
                topScorers.setUserData(league);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("topScorer.fxml"));
                Parent root;
                try {
                    root = fxmlLoader.load();
                    TopScorerController topScorerController = fxmlLoader.getController();
                    topScorerController.setDataScorer(league);
                   // Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Stage stage = new Stage();
                    Image image = new Image(ImagesToResources.getImagePath()+"\\"+"logo.png");
                    stage.getIcons().add(image);
                    Scene scene = new Scene(root,1100,550);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            topAssists.setOnAction(actionEvent -> {
                topAssists.setUserData(league);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("topScorer.fxml"));
                Parent root;
                try {
                    root = fxmlLoader.load();
                    TopScorerController topScorerController = fxmlLoader.getController();
                    topScorerController.setDataAssister(league);
                   // Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Stage stage = new Stage();
                    Image image = new Image(ImagesToResources.getImagePath()+"\\"+"logo.png");
                    stage.getIcons().add(image);
                    Scene scene = new Scene(root,1100,550);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

}
