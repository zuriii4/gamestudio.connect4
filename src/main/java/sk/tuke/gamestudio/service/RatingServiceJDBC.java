package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RatingServiceJDBC implements RatingService {

    private final String INSERT_STATEMENT = "INSERT INTO rating(player, game, rating, rated_at) VALUES (?, ?, ?, ?)";
    private final String DELETE_STATEMENT = "DELETE FROM rating";
    private final String SELECT_STATEMENT = "SELECT * FROM rating WHERE game = ?" + "ON CONFLICT (player, game) DO UPDATE SET rating = ?, rated_at - ?";

    private final String url = "jdbc:postgresql://localhost:5433/postgres";
    private final String user = "postgres";
    private final String password = "Hlavicka123";

    @Override
    public void addRating(Rating rating) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             var statement = conn.prepareStatement(INSERT_STATEMENT)) {
            statement.setString(1, rating.getPlayerName());
            statement.setString(2, rating.getGame());
            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new java.sql.Timestamp(rating.getRated().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public List<Rating> getTopRatings(String game) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             var statement = conn.prepareStatement(SELECT_STATEMENT)) {
            statement.setString(1, game);
            try (var rs = statement.executeQuery()) {
                var ratings = new ArrayList<Rating>();
                while (rs.next())
                    ratings.add(new Rating(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4)));
                return ratings;
            }
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public void reset() {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             var statement = conn.createStatement()) {
            statement.executeUpdate(DELETE_STATEMENT);
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }


    @Override
    public double getAverageRating(String game) {
        return 0;
    }
}
