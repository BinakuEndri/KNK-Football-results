package Repository;

import Models.Coach;
import Models.Nation;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NationRepository {

    public static Nation findById(int nationalityId) throws SQLException {
        String sql = "Select * from nation where id = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,nationalityId);
        ResultSet result = statement.executeQuery();
        while (result.next()){
            Nation nation = new Nation(result.getInt("id"),
                                result.getString("name"),
                                result.getString("flag"));
            return nation;
        }
        return null;
    }
}
