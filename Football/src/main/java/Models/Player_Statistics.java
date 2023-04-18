package Models;

public class Player_Statistics {
    private int id;
    private Player player_id;
    private int goal;
    private int assist;
    private int minutes_played;
    private int yellow_cards;
    private int red_cards;

    public Player_Statistics(int id, Player player_id, int goal, int assist, int minutes_played, int yellow_cards, int red_cards) {
        this.id = id;
        this.player_id = player_id;
        this.goal = goal;
        this.assist = assist;
        this.minutes_played = minutes_played;
        this.yellow_cards = yellow_cards;
        this.red_cards = red_cards;
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

    public int getMinutes_played() {
        return minutes_played;
    }

    public void setMinutes_played(int minutes_played) {
        this.minutes_played = minutes_played;
    }

    public int getYellow_cards() {
        return yellow_cards;
    }

    public void setYellow_cards(int yellow_cards) {
        this.yellow_cards = yellow_cards;
    }

    public int getRed_cards() {
        return red_cards;
    }

    public void setRed_cards(int red_cards) {
        this.red_cards = red_cards;
    }
}
