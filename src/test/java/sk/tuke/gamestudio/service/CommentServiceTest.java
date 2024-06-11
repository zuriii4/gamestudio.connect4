package sk.tuke.gamestudio.service;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Comment;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommentServiceTest {
    private CommentService commentService = new CommentServiceJDBC();

    @Test
    public void testAddComment() {
        commentService.reset();
        Comment comment = new Comment("player1", "connect4", "Great game!", new Date());
        commentService.addComment(comment);
        List<Comment> comments = commentService.getComments("connect4");

        assertEquals(1, comments.size());
        assertEquals(comment.getPlayerName(), comments.get(0).getPlayerName());
        assertEquals(comment.getGame(), comments.get(0).getGame());
        assertEquals(comment.getCommentText(), comments.get(0).getCommentText());
    }

    @Test
    public void testGetComments() {
        commentService.reset();
        Comment comment1 = new Comment("player1", "connect4", "Great game!", new Date());
        Comment comment2 = new Comment("player2", "connect4", "Nice!", new Date());
        commentService.addComment(comment1);
        commentService.addComment(comment2);
        List<Comment> comments = commentService.getComments("connect4");

        assertEquals(2, comments.size());
        assertEquals(comment1.getPlayerName(), comments.get(0).getPlayerName());
        assertEquals(comment1.getCommentText(), comments.get(0).getCommentText());
    }

    @Test
    public void testReset() {
        Comment comment = new Comment("player1", "connect4", "Great game!", new Date());
        commentService.addComment(comment);
        commentService.reset();
        List<Comment> comments = commentService.getComments("connect4");

        assertTrue(comments.isEmpty());
    }
}
