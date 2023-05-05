package Models;

import java.util.Date;

public class Goal {
    private int id;
    private Match game;
    private Team team;
    private Player scored;
    private Player assisted;
    private String minute;
    private Boolean owngoal;
    private Boolean penalty;

    public Goal(int id, Match game, Team team, Player scored, Player assisted, String minute, Boolean owngoal, Boolean penalty) {
        this.id = id;
        this.game = game;
        this.team = team;
        this.scored = scored;
        this.assisted = assisted;
        this.minute = minute;
        this.owngoal = owngoal;
        this.penalty = penalty;
    }

    public Match getGame() {
        return game;
    }

    public void setGame(Match game) {
        this.game = game;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Player getScored() {
        return scored;
    }

    public void setScored(Player scored) {
        this.scored = scored;
    }

    public Player getAssisted() {
        return assisted;
    }

    public void setAssisted(Player assisted) {
        this.assisted = assisted;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public Boolean getOwngoal() {
        return owngoal;
    }

    public void setOwngoal(Boolean owngoal) {
        this.owngoal = owngoal;
    }

    public Boolean getPenalty() {
        return penalty;
    }

    public void setPenalty(Boolean penalty) {
        this.penalty = penalty;
    }
}
