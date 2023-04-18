package Models;

import java.util.Date;

public class Team {
    private int id;
    private String name;
    private String stadium;
    private Date year;

    public Team(int id, String name, String stadium, Date year) {
        this.id = id;
        this.name = name;
        this.stadium = stadium;
        this.year = year;
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

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }
}
