package Repository;

import Models.SquadPlayers;
import Models.Standings;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SquadPlayerRepository {
    public static void insert(SquadPlayers squadPlayer) throws SQLException {
        String sql = "INSERT INTO squad_player (squad_id,player_id) " +
                "Values (?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, squadPlayer.getSquad_id().getId());
        statement.setInt(1, squadPlayer.getPlayer_id().getId());

        statement.executeUpdate();

    }
}
