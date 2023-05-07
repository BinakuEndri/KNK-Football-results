package Repository;

import Models.LeagueMatches;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LeagueMatchesRepository {
    public static void insert(LeagueMatches leagueMatches) throws SQLException {
        String sql = "INSERT INTO league_matches (league_id,match_id) " +
                "Values (?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,leagueMatches.getMatch_id().getId());
        statement.setInt(2,leagueMatches.getLeague_id().getId());
        statement.executeUpdate();
    }
}