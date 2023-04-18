package Models;

public class Coach {
    private int id;
    private Nation nationality;
    private String name;
    private int age;

    public Coach(int id, Nation nationality, String name, int age) {
        this.id = id;
        this.nationality = nationality;
        this.name = name;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
