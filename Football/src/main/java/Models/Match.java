package Models;

import java.util.Date;

public class Match {
    private int id;
    private Team home_team_id;
    private Team away_team_id;
    private Date match_date;

    public Match(int id, Team home_team_id, Team away_team_id, Date match_date) {
        this.id = id;
        this.home_team_id = home_team_id;
        this.away_team_id = away_team_id;
        this.match_date = match_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Team getHome_team_id() {
        return home_team_id;
    }

    public void setHome_team_id(Team home_team_id) {
        this.home_team_id = home_team_id;
    }

    public Team getAway_team_id() {
        return away_team_id;
    }

    public void setAway_team_id(Team away_team_id) {
        this.away_team_id = away_team_id;
    }

    public Date getMatch_date() {
        return match_date;
    }

    public void setMatch_date(Date match_date) {
        this.match_date = match_date;
    }
}
