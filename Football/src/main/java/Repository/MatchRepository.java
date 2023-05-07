package Repository;

import Models.*;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
