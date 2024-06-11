package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService {

    private final String INSERT_STATEMENT = "INSERT INTO comment(player, game, comment_text, commented_at) VALUES (?, ?, ?, ?)";
    private final String DELETE_STATEMENT = "DELETE FROM comment";
    private final String SELECT_STATEMENT = "SELECT * FROM comment WHERE game = ?" + "ON CONFLICT (player, game) DO UPDATE SET comment_text = ?, commented_at - ?";

    private final String url = "jdbc:postgresql://localhost:5433/postgres";
    private final String user = "postgres";
    private final String password = "Hlavicka123";

    @Override
    public void addComment(Comment comment) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             var statement = conn.prepareStatement(INSERT_STATEMENT)) {
            statement.setString(1, comment.getPlayerName());
            statement.setString(2, comment.getGame());
            statement.setString(3, comment.getCommentText());
            statement.setTimestamp(4, new java.sql.Timestamp(comment.getCommented().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public List<Comment> getComments(String game) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             var statement = conn.prepareStatement(SELECT_STATEMENT)) {
            statement.setString(1, game);
            try (var rs = statement.executeQuery()) {
                var comments = new ArrayList<Comment>();
                while (rs.next())
                    comments.add(new Comment(rs.getString(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4)));
                return comments;
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
}
