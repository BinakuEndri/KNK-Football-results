package Controller;

import Models.Users;
import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

public class LoginController {

    @FXML
    private Button loginbtn;

    @FXML
    private Label passwodLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Label usernameLabel;

    @FXML
    void login(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            Users user =UserService.login(username,password);
            if(user == null){
                //
            }else{
                Parent root = null;
                try {
                   Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
                    preferences.putBoolean("loggedIn",true);

                    root = FXMLLoader.load(getClass().getResource("AdminDashboard.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root,1200,700);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
