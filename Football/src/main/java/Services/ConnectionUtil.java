package Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static Connection connection;

    public static  Connection getConnection() throws SQLException{
        if (connection == null || connection.isClosed()){
            String url = "jdbc:mysql://127.0.0.1:3306/knk_football";
            String user = "root";
            String password ="root";
            connection = DriverManager.getConnection(url,user,password);
        }
        return connection;
    }
}
