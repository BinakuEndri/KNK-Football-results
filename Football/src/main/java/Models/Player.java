package Models;

import java.sql.Date;

public class Player {
    private int id;
    private String name;
    private String position;
    private Date birthday;
    private Nation nationality;

    public Player(int id, String name, String position, Date birthday, Nation nationality) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.birthday = birthday;
        this.nationality = nationality;
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

    public void setAge(Date birthday) {
        this.birthday = birthday;
    }

    public Nation getNationality() {
        return nationality;
    }

    public void setNationality(Nation nationality) {
        this.nationality = nationality;
    }
}
