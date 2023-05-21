package Controller;

import Models.League;
import Repository.LeagueRepository;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class NavbarController extends BaseController implements Initializable {

    @FXML
    private Button homeBtn;
    @FXML
    private VBox vbox;
    @FXML
    private ChoiceBox choseLanguage;



    public void fetchToNavbar(VBox vbox){

        try {
            for (League league: LeagueRepository.getAllLeagues()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(Main.class.getResource("leagueButton.fxml"));
                HBox hBox = fxmlLoader.load();
                LeagueButtonController leagueButtonController = fxmlLoader.getController();
                leagueButtonController.setData(league);

                vbox.getChildren().add(hBox);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void toLandingPage(){
        homeBtn.setOnAction(actionEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("landing.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root,1200,700);
            stage.setScene(scene);
            stage.show();
        });
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

        homeBtn.setText(translate.getString("button.home"));
    }

    @Override
    void translateAlbanian() {

        Locale currentLocale = new Locale("sq");
        ResourceBundle translate = ResourceBundle.getBundle("translations.content", currentLocale);

        homeBtn.setText(translate.getString("button.home"));

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchToNavbar(vbox);
        toLandingPage();
        choseLanguage.setValue(LanguageUtil.getLanguage());
        this.setLanguage(null);
        choseLanguage.getItems().addAll("English", "Albanian");
        choseLanguage.setOnAction(this::setLanguage);
    }

}