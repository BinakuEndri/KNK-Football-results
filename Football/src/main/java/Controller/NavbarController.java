package Controller;

import Models.League;
import Repository.LeagueRepository;
import Services.ImagesToResources;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NavbarController implements Initializable {



    @FXML
    private VBox vbox;

    static void fetchToNavbar(VBox vbox){

        try {


            for (League league: LeagueRepository.getAllLeagues()) {
                Button leagueButton = new Button();
                leagueButton.setText(league.getName());
                leagueButton.getStylesheets().add("file:///C:/Users/PC-SYSTEMS/IdeaProjects/KNK-Football-results/Football/src/main/resources/css/button.css");
                leagueButton.getStyleClass().add("leaguebutton");
                ImageView imageView = new ImageView();
                imageView.setFitHeight(40);
                imageView.setFitWidth(40);
                imageView.setImage(new Image(ImagesToResources.getImagePath()+"\\"+league.getName()+"\\"+league.getLeague_logo()));
                leagueButton.setGraphic(imageView);
                vbox.getChildren().add(leagueButton);
                leagueButton.setMinWidth(300);


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchToNavbar(vbox);
    }
}