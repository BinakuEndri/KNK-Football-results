package Models;

public class Squad {
    private int id;
    private Team team_id;
    private Coach coach_id;

    public Squad(int id, Team team_id, Coach coach_id) {
        this.id = id;
        this.team_id = team_id;
        this.coach_id = coach_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Team getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Team team_id) {
        this.team_id = team_id;
    }

    public Coach getCoach_id() {
        return coach_id;
    }

    public void setCoach_id(Coach coach_id) {
        this.coach_id = coach_id;
    }
}
