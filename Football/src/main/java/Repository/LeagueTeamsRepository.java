package Repository;

import Models.LeagueTeams;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
