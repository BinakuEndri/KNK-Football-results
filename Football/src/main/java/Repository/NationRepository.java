package Repository;

import Models.Coach;
import Models.League;
import Models.Nation;
import Models.Team;
import Services.ConnectionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

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
    public static ObservableList<Nation> getAllNations() throws SQLException {
        ObservableList nations = FXCollections.observableArrayList();
        String sql = "Select * from nation";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();

        while (result.next()){
            Nation nation = new Nation(result.getInt("id"),result.getString("name"),result.getString("flag"));
            nations.add(nation);
        }
        return nations;
    }

    public static ComboBox<Nation> setValues(ComboBox<Nation> nations) {
        try {
            for (Nation nation : getAllNations()) {
                nations.getItems().add(nation);
            }
            return nations;
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
