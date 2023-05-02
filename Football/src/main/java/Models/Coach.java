package Models;

import java.sql.Date;

public class Coach {
    private int id;
    private Nation nationality;
    private String name;
    private Date birthday;

    public Coach(int id, Nation nationality, String name, Date birthday) {
        this.id = id;
        this.nationality = nationality;
        this.name = name;
        this.birthday = birthday;
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
}
