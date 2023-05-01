package Repository;

import Models.League;
import Models.LeagueTeams;
import Models.Squad;
import Models.Team;
import Services.ConnectionUtil;

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
        SquadRepository.insert(squad);
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
}
