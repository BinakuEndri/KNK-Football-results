package Models;

public class PlayerStatistics {
    private int id;
    private Player player_id;
    private int goal;
    private int assist;


    public PlayerStatistics(int id, Player player_id, int goal, int assist) {
        this.id = id;
        this.player_id = player_id;
        this.goal = goal;
        this.assist = assist;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(Player player_id) {
        this.player_id = player_id;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getAssist() {
        return assist;
    }

    public void setAssist(int assist) {
        this.assist = assist;
    }
}
