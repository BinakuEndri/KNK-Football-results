package Repository;

import Models.Squad;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SquadRepository {

    public static void insert(Squad squad) throws SQLException {
        String sql = "INSERT INTO squad (team_id) " +
                "Values (?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, squad.getTeam_id().getId());
        statement.executeUpdate();

    }
}
