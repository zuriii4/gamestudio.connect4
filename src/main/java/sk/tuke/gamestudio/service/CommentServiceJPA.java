package sk.tuke.gamestudio.service;

import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class CommentServiceJPA implements CommentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("Comment cannot") ;
        }
        Comment existingComment = entityManager.createQuery("select c from Comment c where c.game = :game and c.playerName = :playerName", Comment.class)
        .setParameter("game", comment.getGame())
        .setParameter("playerName", comment.getPlayerName())
        .getResultList()
        .stream().findFirst().orElse(null);

        if(existingComment != null) {
            existingComment.setCommentText(comment.getCommentText());
            existingComment.setCommented(new Date());
            entityManager.merge(existingComment);
        } else {
            comment.setPlayerName(comment.getPlayerName());
            entityManager.persist(comment);
        }
    }

    @Override
    public List<Comment> getComments(String game) {
        if (game == null || game.isEmpty()) {
            throw new IllegalArgumentException("Game cannot be null or empty");
        }
        List<Comment> comments = entityManager.createQuery("select s from Comment s where s.game = :game", Comment.class)
                .setParameter("game", game)
                .setMaxResults(10)
                .getResultList();
        return comments != null ? comments : Collections.emptyList();
    }

    @Override
    public void reset() {
        entityManager.createQuery("DELETE FROM Comment").executeUpdate();
    }
}
