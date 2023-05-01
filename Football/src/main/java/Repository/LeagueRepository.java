package Repository;

import Models.League;
import Services.ConnectionUtil;
import Services.CostumedAlerts;
import Services.ImagesToResources;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public  class LeagueRepository {

    public static void insert(League league) throws SQLException{
        String sql = "INSERT INTO league (name,leaguelogo) " +
                "Values (?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,league.getName());
        statement.setString(2,league.getLeague_logo());
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
            String path = ImagesToResources.getImagePath()+"\\"+name +"\\"+image;
            File file = new File(path);
            if(file.delete()){
                System.out.println("File deleted successfuly");
            }
            CostumedAlerts.costumeAlert(Alert.AlertType.INFORMATION,"Manage Leagues","Manage Leagues","The league has been deleted!");
        } catch (SQLException e) {
            CostumedAlerts.costumeAlert(Alert.AlertType.ERROR,"Manage Leagues","Manage Leagues","The league failed to be deleted!");

            throw new RuntimeException(e);
        }

    }

    public static void Update(TableView<League> table, League league, Path fileSource){
        int index = table.getSelectionModel().getSelectedIndex();
        int id = table.getItems().get(index).getId();
        String name = table.getItems().get(index).getName();
        String image = table.getItems().get(index).getLeague_logo();

        try
        {
            String sql = "Update league set name = ?, leaguelogo = ? where id = ?";
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,league.getName());
            statement.setString(2, league.getLeague_logo());
            statement.setInt(3,id);

            if(league.getName() != name || league.getLeague_logo() != image) {
                statement.executeUpdate();
                CostumedAlerts.costumeAlert(Alert.AlertType.INFORMATION,"Manage League","Manage League","The League Updated Successfully");
                    try {
                        ImagesToResources.imageToResources(league.getName(),league.getLeague_logo(),fileSource);
                        File oldImageFile = new File(ImagesToResources.getImagePath()+"\\"+name+"\\"+image);
                        oldImageFile.delete();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                }
            }
        }catch (SQLException e){
            CostumedAlerts.costumeAlert(Alert.AlertType.ERROR,"Manage League","Manage League","The League Failed To Be Updated");

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
    public static ObservableList<League> getAllLeagues() throws SQLException {
        ObservableList leagues = FXCollections.observableArrayList();
        String sql = "Select * from league";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();

        while (result.next()){
            League league = new League(result.getInt("id"),result.getString("name"),result.getString("leaguelogo"));
            leagues.add(league);
        }
        return leagues;
    }

}
