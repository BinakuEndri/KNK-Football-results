package Controller;

import Models.Goal;
import Models.Player;
import Models.Team;
import Repository.PlayerRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class GoalController {

    @FXML
    private RadioButton Penalty;

    @FXML
    private ComboBox<Player> choseAssister;

    @FXML
    private ComboBox<Player> choseScorer;

    @FXML
    private TextField minute;

    @FXML
    private RadioButton ownGoal;

    public void setData(Team team){
        choseScorer.getItems().clear();
        choseAssister.getItems().clear();
        PlayerRepository.setValuesByTeam(choseScorer,team);
        PlayerRepository.setValuesByTeam(choseAssister,team);
    }

    public RadioButton getOwnGoal() {
        return ownGoal;
    }

    public void setDataOwnGoal(Team homeTeam, Team awayTeam) {
        choseScorer.getItems().clear();
        choseAssister.getItems().clear();
        PlayerRepository.setValuesByTeam(choseScorer,awayTeam);
        PlayerRepository.setValuesByTeam(choseAssister,homeTeam);


    }
    public Goal getGoal(ComboBox<Team> team){
        Goal goal = new Goal(1,null,null,null,null,null,null,null);
        goal.setMinute(minute.getText());
        goal.setAssisted(choseScorer.getValue());
        goal.setAssisted(choseAssister.getValue());
        goal.setOwngoal(ownGoal.isSelected());
        goal.setPenalty(Penalty.isSelected());
        goal.setTeam(team.getValue());

        return goal;
    }
}
