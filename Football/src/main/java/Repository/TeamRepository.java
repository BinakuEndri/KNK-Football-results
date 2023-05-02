package Repository;

import Models.*;
import Services.ConnectionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamRepository {
    public static void insert(Team team, League league) throws SQLException {
        String sql = "INSERT INTO team (name,stadium,year,logo) " +
                "Values (?,?,?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,team.getName());
        statement.setString(2,team.getStadium());
        statement.setString(3,team.getYear());
        statement.setString(4,team.getLogo());
        statement.executeUpdate();

        team.setId(TeamRepository.findId(team));
        LeagueTeams league_team = new LeagueTeams(league,team);
        LeagueTeamsRepository.insert(league_team);
        Squad squad = new Squad(1,team,null);
        SquadRepository.insertByTeam(squad);
        Standings standing = new Standings(1,team,league,0,0,0,0,0,0,0);
        StandingsRepository.insertByTeam(standing);
    }
    public static int findId(Team team) throws SQLException {
        String sql = "Select id from team where name=? and stadium =? and year= ? and logo=?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,team.getName());
        statement.setString(2,team.getStadium());
        statement.setString(3,team.getYear());
        statement.setString(4,team.getLogo());
        ResultSet result = statement.executeQuery();
        if(result.next()){
            int id  = result.getInt("id");
            return id;
        }
        else {
            return -1;
        }
    }

    public static void fetchToTable(TableView<Team> table,
                                    TableColumn<Team, Integer> colId, TableColumn<Team,String > colName,
                                    TableColumn<Team, String> colImage, TableColumn<Team,String> colStadium,
                                    TableColumn<Team, String> colYear, TableColumn<Team, League> colLeague
    ) throws SQLException {
        ObservableList<Team> teams = FXCollections.observableArrayList();
        String sql = "Select t.id, t.name ,t.stadium ,t.year, t.logo, l.id as leagueId from Team t\n" +
                "inner join league_teams lt on lt.team_id = t.id\n" +
                "inner join league l on l.id = lt.league_id;";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result  = statement.executeQuery();

        while (result.next()){
            Team team = new Team(1,null,null, null,null);
            team.setId(result.getInt("id"));
            team.setName(result.getString("name"));
            team.setYear(result.getString("year"));
            team.setStadium(result.getString("stadium"));
            team.setLogo(result.getString("logo"));
            team.setLeague(LeagueRepository.findById(result.getInt("leagueId")));

            teams.add(team);
        }
        table.setItems(teams);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("logo"));
        colStadium.setCellValueFactory(new PropertyValueFactory<>("stadium"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colLeague.setCellValueFactory(new PropertyValueFactory<>("league"));

    }
    public static void fetchToTableByLeague(TableView<Team> table,
                                    TableColumn<Team, Integer> colId, TableColumn<Team,String > colName,
                                    TableColumn<Team, String> colImage, TableColumn<Team,String> colStadium,
                                    TableColumn<Team, String> colYear, TableColumn<Team, League> colLeague, League league
    ) throws SQLException {

        ObservableList<Team> teams = FXCollections.observableArrayList();
        String sql = "Select t.id, t.name ,t.stadium ,t.year, t.logo, l.id as leagueId from Team t\n" +
                "inner join league_teams lt on lt.team_id = t.id\n" +
                "inner join league l on l.id = lt.league_id Where l.id = ?;";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,league.getId());
        ResultSet result  = statement.executeQuery();

        while (result.next()){
            Team team = new Team(1,null,null, null,null);
            team.setId(result.getInt("id"));
            team.setName(result.getString("name"));
            team.setYear(result.getString("year"));
            team.setStadium(result.getString("stadium"));
            team.setLogo(result.getString("logo"));
            team.setLeague(LeagueRepository.findById(result.getInt("leagueId")));

            teams.add(team);
        }
        table.setItems(teams);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("logo"));
        colStadium.setCellValueFactory(new PropertyValueFactory<>("stadium"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colLeague.setCellValueFactory(new PropertyValueFactory<>("league"));

    }

    public static Team findById(int teamId) throws SQLException {
        String sql = "Select * from team where id = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,teamId);
        ResultSet result = statement.executeQuery();
        while (result.next()){
            Team team = new Team(result.getInt("id"),
                                result.getString("name"),
                                result.getString("stadium"),
                                result.getString("year"),
                                result.getString("logo"));
            return team;
        }
        return null;
    }
}
