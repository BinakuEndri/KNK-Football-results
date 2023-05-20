package Repository;

import Models.Users;
import Services.ConnectionUtil;
import Services.PasswordHasher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    public static void insert(){
        String fullname = "admin";
        String username = "admin";
        String password = "admin";
        String salt = PasswordHasher.generateSalt();
        String passwordHashed = PasswordHasher.generateSaltedHash(password,salt);

        String sql = "INSERT INTO users (fullname,username,password,salt) " +
                "Values (?,?,?,?)";
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,fullname);
            statement.setString(2,username);
            statement.setString(3,passwordHashed);
            statement.setString(4,salt);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Users getByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username=?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fullname = resultSet.getString("fullname");
                String password = resultSet.getString("password");
                String salt = resultSet.getString("salt");
                return new Users(id,fullname,username,password,null,null,null,salt);
            } else {
                return null;
            }
        }
    }


}
