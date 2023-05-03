package Models;

import java.sql.Date;

public class Player {
    private int id;
    private String name;
    private String position;
    private Date birthday;
    private Nation nationality;
    private String image;

    public Player(int id, String name, String position, Date birthday, Nation nationality, String image) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.birthday = birthday;
        this.nationality = nationality;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Nation getNationality() {
        return nationality;
    }

    public void setNationality(Nation nationality) {
        this.nationality = nationality;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private League league;
    private Team team;

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
