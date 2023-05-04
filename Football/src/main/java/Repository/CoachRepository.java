package Repository;

import Models.*;
import Services.ConnectionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class CoachRepository {
    public static void insert(Coach coach, Squad squad, Team team) throws SQLException{
        String sql = "INSERT INTO coach (name,birthday,nationality,image) " +
                "Values (?,?,?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,coach.getName());
        statement.setDate(2,coach.getBirthday());
        statement.setInt(3,coach.getNationality().getId());
        statement.setString(4,coach.getImage());
        statement.executeUpdate();

        coach.setId(findIdByData(coach));
        squad.setId(SquadRepository.findIdByTeam(team).getId());
        SquadRepository.insertByCoach(squad,coach);



    }
    public static Coach findById(int squadId) throws SQLException {
        String sql = "Select * from coach where id = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,squadId);
        ResultSet result = statement.executeQuery();
        while (result.next()){
            Coach coach = new Coach(result.getInt("id"),
                    result.getString("name"),
                    result.getDate("birthday"),
                    NationRepository.findById(result.getInt("nationality")),
                    result.getString("image")
                    );
            return coach;
        }
        return null;
    }

    public static void fetchToTable(TableView<Coach> tableCoach, TableColumn<Coach, Integer> colIdCoach,
                                    TableColumn<Coach, String> colNameCoach, TableColumn<Coach, Date> colCoachBirthday,
                                    TableColumn<Coach, Nation> colCoachNation, TableColumn<Coach, League> colCoachLeague,
                                    TableColumn<Coach, Team> colCoachTeam) throws SQLException{
        ObservableList<Coach> coaches = FXCollections.observableArrayList();
        String sql = "Select c.id, c.name , c.birthday, l.id as leagueName, n.id as nationName, t.id as teamName From coach c\n" +
                "                Inner join squad s on s.coach_id = c.id \n" +
                "                Inner join team t on t.id = s.team_id\n" +
                "                Inner join nation n on n.id = c.nationality\n" +
                "                Inner join league_teams lt on lt.team_id = t.id\n" +
                "                Inner join league l on l.id = lt.league_id;";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result  = statement.executeQuery();

        while (result.next()){
            dataToList(coaches,result);
        }
        objectToTable(tableCoach,colIdCoach,colNameCoach,colCoachBirthday,colCoachNation,colCoachTeam,colCoachLeague,coaches);


    }




    public static void fetchToTableByLeague(TableView<Coach> tableCoach, TableColumn<Coach, Integer> colIdCoach,
                                            TableColumn<Coach, String> colNameCoach, TableColumn<Coach, Date> colCoachBirthday,
                                            TableColumn<Coach, Nation> colCoachNation, TableColumn<Coach, Team> colCoachTeam,
                                            TableColumn<Coach, League> colCoachLeague, League league) throws SQLException{
        ObservableList<Coach> coaches = FXCollections.observableArrayList();
        String sql = "Select c.id, c.name, c.birthday, l.id as leagueName , n.id as nationName, t.id as teamName From coach c\n" +
                "                Inner join squad s on s.coach_id = c.id \n" +
                "                Inner join team t on t.id = s.team_id\n" +
                "                Inner join nation n on n.id = c.nationality\n" +
                "                Inner join league_teams lt on lt.team_id = t.id\n" +
                "                Inner join league l on l.id = lt.league_id " +
                "                Where l.id = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,league.getId());
        ResultSet result  = statement.executeQuery();
        while (result.next()){
            dataToList(coaches,result);
        }
        objectToTable(tableCoach,colIdCoach,colNameCoach,colCoachBirthday,colCoachNation,colCoachTeam,colCoachLeague,coaches);

    }

    public static void fetchToTableByTeam(TableView<Coach> tableCoach, TableColumn<Coach, Integer> colIdCoach,
                                          TableColumn<Coach, String> colNameCoach, TableColumn<Coach, Date> colCoachBirthday,
                                          TableColumn<Coach, Nation> colCoachNation, TableColumn<Coach, Team> colCoachTeam,
                                          TableColumn<Coach, League> colCoachLeague, Team team) throws SQLException{
        ObservableList<Coach> coaches = FXCollections.observableArrayList();
        String sql = "Select c.id, c.name, c.birthday, l.id as leagueName, n.id as nationName , t.id as teamName From coach c\n" +
                "                Inner join squad s on s.coach_id = c.id \n" +
                "                Inner join team t on t.id = s.team_id\n" +
                "                Inner join nation n on n.id = c.nationality\n" +
                "                Inner join league_teams lt on lt.team_id = t.id\n" +
                "                Inner join league l on l.id = lt.league_id " +
                "                Where t.id = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,team.getId());
        ResultSet result  = statement.executeQuery();
        while (result.next()){
            dataToList(coaches,result);
        }
        objectToTable(tableCoach,colIdCoach,colNameCoach,colCoachBirthday,colCoachNation,colCoachTeam,colCoachLeague,coaches);

    }

    private static void objectToTable(TableView<Coach> tableCoach, TableColumn<Coach, Integer> colIdCoach,
                                      TableColumn<Coach, String> colNameCoach, TableColumn<Coach, Date> colCoachBirthday,
                                      TableColumn<Coach, Nation> colCoachNation, TableColumn<Coach, Team> colCoachTeam,
                                      TableColumn<Coach, League> colCoachLeague, ObservableList<Coach> coaches) {
        tableCoach.setItems(coaches);
        colIdCoach.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNameCoach.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCoachBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        colCoachNation.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        colCoachTeam.setCellValueFactory(new PropertyValueFactory<>("team"));
        colCoachLeague.setCellValueFactory(new PropertyValueFactory<>("league"));
    }

    private static void dataToList(ObservableList<Coach> coaches, ResultSet result) throws SQLException {
        Coach coach = new Coach(1,null,null, null,null);
        coach.setId(result.getInt("id"));
        coach.setName(result.getString("name"));
        coach.setNationality(NationRepository.findById(result.getInt("nationName")));
        coach.setBirthday(result.getDate("birthday"));
        coach.setTeam(TeamRepository.findById(result.getInt("teamName")));
        coach.setLeague(LeagueRepository.findById(result.getInt("leagueName")));

        coaches.add(coach);
    }

    public static void Delete(TableView<Coach> tableCoach) {

    }





    private static int findIdByData(Coach coach) throws SQLException {
        String sql = "Select * from coach where name=? and birthday= ? and nationality=? and image = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,coach.getName());
        statement.setDate(2,coach.getBirthday());
        statement.setInt(3,coach.getNationality().getId());
        statement.setString(4,coach.getImage());
        ResultSet result = statement.executeQuery();
        if(result.next()){
            int id  = result.getInt("id");
            return id;
        }
        else {
            return -1;
        }
    }
}
