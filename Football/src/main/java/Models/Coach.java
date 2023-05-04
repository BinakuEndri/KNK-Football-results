package Models;

import java.sql.Date;

public class Coach {
    private int id;
    private Nation nationality;
    private String name;
    private Date birthday;
    private String image;

    public Coach(int id, String name, Date birthday,Nation nationality,String image) {
        this.id = id;
        this.nationality = nationality;
        this.name = name;
        this.birthday = birthday;
        this.image= image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Nation getNationality() {
        return nationality;
    }

    public void setNationality(Nation nationality) {
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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
