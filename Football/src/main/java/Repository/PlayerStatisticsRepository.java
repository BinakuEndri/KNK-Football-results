package Repository;

import Models.PlayerStatistics;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerStatisticsRepository {
    public static void insertByPlayer(PlayerStatistics playerStatistics) throws SQLException {
        String sql = "INSERT INTO player_statistics (player_id,goals,assists,minutes_played,yellow_cards,red_cards) " +
                "Values (?,?,?,?,?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, playerStatistics.getPlayer_id().getId());
        statement.setInt(2, 0);
        statement.setInt(3, 0);
        statement.setInt(4, 0);
        statement.setInt(5, 0);
        statement.setInt(6, 0);

        statement.executeUpdate();
    }
}
