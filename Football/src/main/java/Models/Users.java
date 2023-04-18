package Models;

import java.util.Date;

public class Users {
    private int id;
    private String fullname;
    private String username;
    private String password;
    private Date Date_Created;
    private Date Date_Modified;
    private Boolean role;

    public Users(int id, String fullname, String username, String password, Date date_Created, Date date_Modified, Boolean role) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        Date_Created = date_Created;
        Date_Modified = date_Modified;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDate_Created() {
        return Date_Created;
    }

    public void setDate_Created(Date date_Created) {
        Date_Created = date_Created;
    }

    public Date getDate_Modified() {
        return Date_Modified;
    }

    public void setDate_Modified(Date date_Modified) {
        Date_Modified = date_Modified;
    }

    public Boolean getRole() {
        return role;
    }

    public void setRole(Boolean role) {
        this.role = role;
    }
}
