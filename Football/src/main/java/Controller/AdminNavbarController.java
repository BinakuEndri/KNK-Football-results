package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;

public class AdminNavbarController{
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

}
