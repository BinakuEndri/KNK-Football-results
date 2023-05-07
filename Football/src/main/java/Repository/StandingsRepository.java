package Repository;

import Models.Standings;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StandingsRepository {
    public static void insertByTeam(Standings standings) throws SQLException {
        String sql = "INSERT INTO standings (team_id,league_id,matches_played,wins,draws,losses,goals_for,goals_against,points) " +
                "Values (?,?,?,?,?,?,?,?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(2, standings.getLeague_id().getId());
        statement.setInt(1, standings.getTeam_id().getId());
        statement.setInt(3,0);
        statement.setInt(4,0);
        statement.setInt(5,0);
        statement.setInt(6,0);
        statement.setInt(7,0);
        statement.setInt(8,0);
        statement.setInt(9,0);

        statement.executeUpdate();

    }
}
