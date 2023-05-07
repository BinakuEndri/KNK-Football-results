package Repository;

import Models.Goal;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GoalRepository {
    public static void insert(Goal goal) throws SQLException {
        String sql = "INSERT INTO goal (game,team,scored,assisted,minute,owngoal,penalty) " +
                "Values (?,?,?,?,?,?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,goal.getGame().getId());
        statement.setInt(2,goal.getTeam().getId());
        statement.setInt(3,goal.getScored().getId());
        statement.setInt(4,goal.getAssisted().getId());
        statement.setString(5,goal.getMinute());
        statement.setBoolean(6,goal.getOwngoal());
        statement.setBoolean(7,goal.getPenalty());
        statement.executeUpdate();
    }
}
