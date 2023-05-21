package Controller;

import Services.LanguageUtil;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class AdminNavbarController extends BaseController implements Initializable {

    @FXML
    private Button landing;

    @FXML
    private Button manageClub;

    @FXML
    private Button manageCoach;

    @FXML
    private Button manageLeague;

    @FXML
    private Button manageMatches;

    @FXML
    private Button managePlayers;

    @FXML
    private Button signOut;

    @FXML
    private ChoiceBox choseLanguage;


    @FXML
    void changeSceneClub(ActionEvent event) {
        try {
            changeScene("manageTeam.fxml",event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void changeSceneCoach(ActionEvent event) {
        try {
            changeScene("manageCoach.fxml",event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void changeSceneLeague(ActionEvent event) {
        try {
            changeScene("manageLeague.fxml",event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void changeSceneMatches(ActionEvent event) {
        try {
            changeScene("addMatch.fxml",event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void changeScenePlayers(ActionEvent event) {
        try {
            changeScene("managePlayer.fxml",event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void backToLanding(ActionEvent event) {
        try {
            changeScene("landing.fxml",event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void signOut(ActionEvent event) {
        try {
            Preferences prefs = Preferences.userNodeForPackage(LoginController.class);
            prefs.clear();
            changeScene("landing.fxml",event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    void changeScene(String fxmlFile,ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1200,700);
        stage.setScene(scene);
        stage.show();
    }

    public void setLanguage(Event event) {
        if (choseLanguage.getValue() == "Albanian") {
            try {
                translateAlbanian();
            } catch (Exception e) {
                System.out.println(e);
            }
            LanguageUtil.setLanguage("Albanian");
        } else {
            try {
                translateEnglish();
            } catch (Exception e) {
                System.out.println(e);
            }
            LanguageUtil.setLanguage("English");
        }
    }

    @Override
    void translateEnglish() {
        Locale currentLocale = new Locale("en");
        ResourceBundle translate = ResourceBundle.getBundle("translations.content", currentLocale);

        manageMatches.setText(translate.getString("button.manageMatches"));
        manageLeague.setText(translate.getString("button.manageLeague"));
        manageCoach.setText(translate.getString("button.manageCoach"));
        manageClub.setText(translate.getString("button.manageClub"));
        managePlayers.setText(translate.getString("button.managePlayers"));
        landing.setText(translate.getString("button.landing"));
        signOut.setText(translate.getString("button.signOut"));
    }

    @Override
    void translateAlbanian() {

        Locale currentLocale = new Locale("sq");
        ResourceBundle translate = ResourceBundle.getBundle("translations.content", currentLocale);

        manageMatches.setText(translate.getString("button.manageMatches"));
        manageLeague.setText(translate.getString("button.manageLeague"));
        manageCoach.setText(translate.getString("button.manageCoach"));
        manageClub.setText(translate.getString("button.manageClub"));
        managePlayers.setText(translate.getString("button.managePlayers"));
        landing.setText(translate.getString("button.landing"));
        signOut.setText(translate.getString("button.signOut"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choseLanguage.setValue(LanguageUtil.getLanguage());
        this.setLanguage(null);
        choseLanguage.getItems().addAll("English", "Albanian");
        choseLanguage.setOnAction(this::setLanguage);
    }
}
