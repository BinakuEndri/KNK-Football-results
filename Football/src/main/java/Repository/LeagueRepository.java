package Repository;

import Models.League;
import Services.ConnectionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public  class LeagueRepository {

    public static void insert(League league) throws SQLException{
        String sql = "INSERT INTO league (id,name,leaguelogo) " +
                "Values (?,?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,league.getId());
        statement.setString(2,league.getName());
        statement.setString(3,league.getLeague_logo());
        statement.executeUpdate();
    }

    public static void Delete(TableView<League> table) {
        int index = table.getSelectionModel().getSelectedIndex();
        int id = table.getItems().get(index).getId();
        String name = table.getItems().get(index).getName();
        String image = table.getItems().get(index).getLeague_logo();

        try {
            String sql = "Delete From league where id = ?";
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            statement.executeUpdate();
            String path = "C:\\Users\\PC-SYSTEMS\\IdeaProjects\\KNK-Football-results\\Football\\src\\main\\resources\\images"+name +"\\"+image;
            File file = new File(path);
            if(file.delete()){
                System.out.println("File deleted successfuly");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Manage Leagues");
            alert.setHeaderText("Manage Leagues");
            alert.setContentText("The league has been deleted!");

            alert.showAndWait();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Manage Leagues");
            alert.setHeaderText("Manage Leagues");
            alert.setContentText("The league failed to be deleted!");
            alert.showAndWait();

            throw new RuntimeException(e);
        }

    }

    public static void fetchToTable(TableView<League> table,
                                    TableColumn<League, Integer> colid, TableColumn<League,String > colname,
                                    TableColumn<League, String> colimage) throws SQLException {
        ObservableList<League> leagues = FXCollections.observableArrayList();
        String sql = "Select id, name, leaguelogo From league";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result  = statement.executeQuery();

        while (result.next()){
            League league = new League(1," "," ");
            league.setId(result.getInt("id"));
            league.setName(result.getString("name"));
            league.setLeague_logo(result.getString("leaguelogo"));
            leagues.add(league);
        }
        table.setItems(leagues);
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colimage.setCellValueFactory(new PropertyValueFactory<>("league_logo"));

    }


}
