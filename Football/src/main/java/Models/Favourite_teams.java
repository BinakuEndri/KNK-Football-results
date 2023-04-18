package Models;

public class Favourite_teams {
    private Users user_id;
    private Team team_id;

    public Favourite_teams(Users user_id, Team team_id) {
        this.user_id = user_id;
        this.team_id = team_id;
    }

    public Users getUser_id() {
        return user_id;
    }

    public void setUser_id(Users user_id) {
        this.user_id = user_id;
    }

    public Team getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Team team_id) {
        this.team_id = team_id;
    }
}
