package Repository;

import Models.PlayerStatistics;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerStatisticsRepository {
    public static void insertByPlayer(PlayerStatistics playerStatistics) throws SQLException {
        String sql = "INSERT INTO player_statistics (player_id) " +
                "Values (?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, playerStatistics.getPlayer_id().getId());
        statement.executeUpdate();

    }
}
