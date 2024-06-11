package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ScoreServiceJDBC implements ScoreService{

    private final String INSERT_STATEMENT = "INSERT INTO score(player, game, score, played_at) VALUES (?, ?, ?, ?)";
    private final String DELETE_STATEMENT = "DELETE  FROM score";
    private final String SELECT_STATEMENT = "SELECT * FROM score WHERE game = 'connect4' ORDER BY score DESC LIMIT 10";
    private final String SELECT_STATEMENT_GAME = "SELECT * FROM score WHERE game = ? ORDER BY score DESC LIMIT 10";

    private final String url = "jdbc:postgresql://localhost:5433/postgres";
    private final String user = "postgres";
    private final String password = "Hlavicka123";



    @Override
    public void addScore(Score score) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             var statement = conn.prepareStatement(INSERT_STATEMENT);) {
            statement.setString(1,score.getPlayerName());
            statement.setString(2,score.getGame());
            statement.setInt(3,score.getScore());
            statement.setTimestamp(4, new Timestamp(score.getPlayed().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public List<Score> getTopScores() {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             var statement = conn.prepareStatement(SELECT_STATEMENT);) {

            try(var st = statement.executeQuery();) {
                ArrayList<Score> scores = new ArrayList<Score>();
                while (st.next()) {
//                System.out.printf("%s %s %d %s \n", st.getString(1), st.getString(2), st.getInt(3), st.getTimestamp(4));
                    scores.add(new Score(st.getString(1),st.getString(2),st.getInt(3),st.getTimestamp(4)));
                }
                return scores;
            }

        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public List<Score> getTopScores(String game) throws GameStudioException {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             var statement = conn.prepareStatement(SELECT_STATEMENT_GAME);) {
            statement.setString(1,game);
            try(var st = statement.executeQuery();) {
                ArrayList<Score> scores = new ArrayList<Score>();
                while (st.next()) {
                    scores.add(new Score(st.getString(1),st.getString(2),st.getInt(3),st.getTimestamp(4)));
                }
                return scores;
            }

        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public void reset() {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             var statement = conn.createStatement();) {
            var st = statement.executeUpdate(DELETE_STATEMENT);
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
}
