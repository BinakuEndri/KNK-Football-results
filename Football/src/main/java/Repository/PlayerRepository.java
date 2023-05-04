package Repository;

import Models.*;
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
import java.sql.*;

public class PlayerRepository {
    public static void insert(Player player,Squad squad, Team team) throws SQLException {
        String sql = "INSERT INTO player (name,position,birthday,nationality,image) " +
                "Values (?,?,?,?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,player.getName());
        statement.setString(2,player.getPosition());
        statement.setDate(3,player.getBirthday());
        statement.setInt(4,player.getNationality().getId());
        statement.setString(5,player.getImage());
        statement.executeUpdate();

        player.setId(findIdByData(player));
        squad.setId(SquadRepository.findIdByTeam(team).getId());
        SquadPlayers squad_players = new SquadPlayers(squad,player);
        SquadPlayerRepository.insert(squad_players);
        PlayerStatistics playerStatistics = new PlayerStatistics(0,player,0,0,0,0,0);
        PlayerStatisticsRepository.insertByPlayer(playerStatistics);


    }
    public static void Delete(TableView<Player> tablePlayer) {
        int index = tablePlayer.getSelectionModel().getSelectedIndex();
        int id = tablePlayer.getItems().get(index).getId();
        League league = tablePlayer.getItems().get(index).getLeague();
        Team team = tablePlayer.getItems().get(index).getTeam();
        try {
            Player player = findById(id);
            String sql = "Delete From player where id = ?";
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            statement.executeUpdate();
            String path = ImagesToResources.getImagePath()+"\\"+league+"\\"+team +"\\"+player.getImage();
            File file = new File(path);
            if(file.delete()){
                System.out.println("File deleted successfully");
            }
            CostumedAlerts.costumeAlert(Alert.AlertType.INFORMATION,"Manage Players","Manage Players","The player has been deleted!");
        } catch (SQLException e) {
            CostumedAlerts.costumeAlert(Alert.AlertType.ERROR,"Manage Players","Manage Players","The player failed to be deleted!");

            throw new RuntimeException(e);
        }
    }
    public static int findIdByData(Player player) throws SQLException {
        String sql = "Select * from player where name=? and position =? and birthday= ? and nationality=? and image = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,player.getName());
        statement.setString(2,player.getPosition());
        statement.setDate(3,player.getBirthday());
        statement.setInt(4,player.getNationality().getId());
        statement.setString(5,player.getImage());
        ResultSet result = statement.executeQuery();
        if(result.next()){
            int id  = result.getInt("id");
            return id;
        }
        else {
            return -1;
        }

    }

    public static Player findById(int playerId) throws SQLException {
        String sql = "Select * from player where id = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,playerId);
        ResultSet result = statement.executeQuery();
        while (result.next()){
            Player player = new Player(result.getInt("id"),
                    result.getString("name"),
                    result.getString("position"),
                    result.getDate("birthday"),
                    NationRepository.findById(result.getInt("nationality")),
                    result.getString("image")

            );
            return player;
        }
        return null;
    }


    public static void fetchToTable(
            TableView<Player> tablePlayer,
            TableColumn<Player, Integer> colIdPlayer,
            TableColumn<Player, String> colNamePlayer,
            TableColumn<Player, Date> colPlayerBirthday,
            TableColumn<Player, League> colPlayerLeague,
            TableColumn<Player, Nation> colPlayerNation,
            TableColumn<Player, String> colPlayerPos,
            TableColumn<Player, Team> colPlayerTeam
    ) throws SQLException {
        ObservableList<Player> players = FXCollections.observableArrayList();
        String sql = "Select p.id as id, p.name as playerName, p.birthday as birthday, " +
                "l.id as leagueId, n.id as nationId, p.position , t.id as teamId From player p " +
                "Inner join squad_players sp on sp.player_id = p.id " +
                "Inner join squad s on sp.squad_id = s.id " +
                "Inner join team t on t.id = s.team_id " +
                "Inner join nation n on n.id = p.nationality " +
                "Inner join league_teams lt on lt.team_id = t.id " +
                "Inner join league l on l.id = lt.league_id;";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result  = statement.executeQuery();

        while (result.next()){
            dataToList(players,result);
        }
        objectToTable(tablePlayer,colIdPlayer,colNamePlayer,colPlayerBirthday,colPlayerNation,colPlayerPos,colPlayerTeam,colPlayerLeague,players);

    }

    public static void fetchToTableByLeague(TableView<Player> tablePlayer, TableColumn<Player, Integer> colIdPlayer,
                                            TableColumn<Player, String> colNamePlayer, TableColumn<Player, Date> colPlayerBirthday,
                                            TableColumn<Player, Nation> colPlayerNation, TableColumn<Player, String> colPlayerPos,
                                            TableColumn<Player, Team> colPlayerTeam,TableColumn<Player, League> colPlayerLeague ,League league) throws SQLException {

        ObservableList<Player> players = FXCollections.observableArrayList();
        String sql = "Select p.id as id, p.name as playerName, p.birthday as birthday, " +
                "l.id as leagueId, n.id as nationId, p.position , t.id as teamId From player p " +
                "Inner join squad_players sp on sp.player_id = p.id " +
                "Inner join squad s on sp.squad_id = s.id " +
                "Inner join team t on t.id = s.team_id " +
                "Inner join nation n on n.id = p.nationality " +
                "Inner join league_teams lt on lt.team_id = t.id " +
                "Inner join league l on l.id = lt.league_id " +
                "Where l.id = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,league.getId());
        ResultSet result  = statement.executeQuery();

        while (result.next()){
            dataToList(players,result);

        }
        objectToTable(tablePlayer,colIdPlayer,colNamePlayer,colPlayerBirthday,colPlayerNation,colPlayerPos,colPlayerTeam,colPlayerLeague,players);
    }

    public static void fetchToTableByTeam(TableView<Player> tablePlayer, TableColumn<Player, Integer> colIdPlayer,
                                          TableColumn<Player, String> colNamePlayer, TableColumn<Player, Date> colPlayerBirthday,
                                          TableColumn<Player, Nation> colPlayerNation, TableColumn<Player, String> colPlayerPos,
                                          TableColumn<Player, Team> colPlayerTeam,TableColumn<Player, League> colPlayerLeague ,Team team) throws SQLException{

        ObservableList<Player> players = FXCollections.observableArrayList();
        String sql = "Select p.id as id, p.name as playerName, p.birthday as birthday, " +
                "l.id as leagueId, n.id as nationId, p.position , t.id as teamId From player p " +
                "Inner join squad_players sp on sp.player_id = p.id " +
                "Inner join squad s on sp.squad_id = s.id " +
                "Inner join team t on t.id = s.team_id " +
                "Inner join nation n on n.id = p.nationality " +
                "Inner join league_teams lt on lt.team_id = t.id " +
                "Inner join league l on l.id = lt.league_id " +
                "Where t.id = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,team.getId());
        ResultSet result  = statement.executeQuery();

        while (result.next()){
            dataToList(players,result);
        }
        objectToTable(tablePlayer,colIdPlayer,colNamePlayer,colPlayerBirthday,colPlayerNation,colPlayerPos,colPlayerTeam,colPlayerLeague,players);

    }
    static void dataToList(ObservableList<Player> players,ResultSet result) throws SQLException {
        Player player = new Player(1,null,null, null,null,null);
        player.setId(result.getInt("id"));
        player.setName(result.getString("playerName"));
        player.setPosition(result.getString("position"));
        player.setNationality(NationRepository.findById(result.getInt("nationId")));
        player.setBirthday(result.getDate("birthday"));
        player.setTeam(TeamRepository.findById(result.getInt("teamId")));
        player.setLeague(LeagueRepository.findById(result.getInt("leagueId")));

        players.add(player);
    }
    static void objectToTable(TableView<Player> tablePlayer, TableColumn<Player, Integer> colIdPlayer,
                              TableColumn<Player, String> colNamePlayer, TableColumn<Player, Date> colPlayerBirthday,
                              TableColumn<Player, Nation> colPlayerNation, TableColumn<Player, String> colPlayerPos,
                              TableColumn<Player, Team> colPlayerTeam,TableColumn<Player, League> colPlayerLeague ,ObservableList<Player> players){
        tablePlayer.setItems(players);
        colIdPlayer.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNamePlayer.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPlayerBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        colPlayerNation.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        colPlayerPos.setCellValueFactory(new PropertyValueFactory<>("position"));
        colPlayerTeam.setCellValueFactory(new PropertyValueFactory<>("team"));
        colPlayerLeague.setCellValueFactory(new PropertyValueFactory<>("league"));
    }
}
