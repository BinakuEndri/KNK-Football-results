package Repository;

import Models.*;
import Services.ConnectionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class MatchRepository {
    public static void insert(Match match, League league, MatchStatistics matchStatistics) throws SQLException {
        String sql = "INSERT INTO matches (home_team_id,away_team_id,match_date) " +
                "Values (?,?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,match.getHome_team_id().getId());
        statement.setInt(2,match.getAway_team_id().getId());
        statement.setDate(3,match.getMatch_date());
        statement.executeUpdate();

        match.setId(findIdByData(match));
        matchStatistics.setMatch_id(match);
        MatchStatisticsRepository.insert(matchStatistics);
        LeagueMatches leagueMatches = new LeagueMatches(league,match);
        LeagueMatchesRepository.insert(leagueMatches);
    }

    public static int findIdByData(Match match) throws SQLException {
        String sql = "Select id from matches where home_team_id = ? and away_team_id = ?  and match_date = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,match.getHome_team_id().getId());
        statement.setInt(2,match.getAway_team_id().getId());
        statement.setDate(3,match.getMatch_date());
        ResultSet result = statement.executeQuery();
        if(result.next()){
            int id  = result.getInt("id");
            return id;
        }
        else {
            return -1;
        }
    }

    public static ObservableList<Match> getAllMatchesByDate(Date date) throws SQLException {
        ObservableList matches = FXCollections.observableArrayList();
        String sql = "Select * from matches where match_date = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1,date);
        ResultSet result = statement.executeQuery();

        while (result.next()){
            Match match = new Match(
                    result.getInt("id"),
                    TeamRepository.findById(result.getInt("home_team_id")),
                    TeamRepository.findById(result.getInt("away_team_id")),
                    result.getDate("match_date")
            );
            matches.add(match);
        }
        return matches;
    }
    public static ObservableList<Match> getAllMatchesToday() throws SQLException {
        ObservableList matches = FXCollections.observableArrayList();
        String sql = "Select * from matches where match_date = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        LocalDate now = LocalDate.now();
        Date date = Date.valueOf(now);
        statement.setDate(1,date);
        ResultSet result = statement.executeQuery();

        while (result.next()){
            Match match = new Match(
                    result.getInt("id"),
                    TeamRepository.findById(result.getInt("home_team_id")),
                    TeamRepository.findById(result.getInt("away_team_id")),
                    result.getDate("match_date")
            );
            matches.add(match);
        }
        return matches;
    }
}
