package Repository;

import Models.Coach;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoachRepository {
    public static Coach findById(int squadId) throws SQLException {
        String sql = "Select * from coach where id = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,squadId);
        ResultSet result = statement.executeQuery();
        while (result.next()){
            Coach coach = new Coach(result.getInt("id"),
                    NationRepository.findById(result.getInt("nationality")),
                    result.getString("name"),
                    result.getDate("birthday")
                    );
            return coach;
        }
        return null;
    }
}
