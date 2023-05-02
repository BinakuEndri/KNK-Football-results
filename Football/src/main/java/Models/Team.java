package Models;

import java.util.Date;

public class Team {
    private int id;
    private String name;
    private String stadium;
    private String year;

    private League league;

    private String logo;
    public Team(int id, String name, String stadium, String year, String logo) {
        this.id = id;
        this.name = name;
        this.stadium = stadium;
        this.year = year;
        this.logo = logo;
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

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }
}
