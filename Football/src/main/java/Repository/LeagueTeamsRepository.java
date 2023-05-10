package Repository;

import Models.League;
import Models.LeagueTeams;
import Models.Squad;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeagueTeamsRepository {

    public static void insert(LeagueTeams leagueTeams) throws SQLException {
        String sql = "INSERT INTO league_teams (league_id,team_id) " +
                "Values (?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, leagueTeams.getLeague_id().getId());
        statement.setInt(2, leagueTeams.getTeam_id().getId());
        statement.executeUpdate();

    }

    public static LeagueTeams findByLeague(League league) throws SQLException {
        String sql = "Select * from league_teams where league_id = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,league.getId());
        ResultSet result = statement.executeQuery();
        while (result.next()){
            LeagueTeams leagueTeams = new LeagueTeams(LeagueRepository.findById(result.getInt("league_id")),
                    TeamRepository.findById(result.getInt("team_id")));
            return leagueTeams;
        }
        return null;
    }
}
