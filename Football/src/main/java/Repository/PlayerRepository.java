package Repository;

import Models.*;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerRepository {
    public static void insert(Player player,Squad squad, Team team, League league) throws SQLException {
        String sql = "INSERT INTO player (name,position,birthday,nationality) " +
                "Values (?,?,?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,player.getName());
        statement.setString(2,player.getPosition());
        statement.setDate(3,player.getBirthday());
        statement.setInt(4,player.getNationality().getId());
        statement.executeUpdate();

        squad.setId(SquadRepository.findIdByTeam(team).getId());
        SquadPlayers squad_players = new SquadPlayers(squad,player);
        SquadPlayerRepository.insert(squad_players);
        PlayerStatistics playerStatistics = new PlayerStatistics(0,player,0,0,0,0,0);
        PlayerStatisticsRepository.insertByPlayer(playerStatistics);


    }
}
