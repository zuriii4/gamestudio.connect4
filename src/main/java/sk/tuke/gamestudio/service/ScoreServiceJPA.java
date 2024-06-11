package sk.tuke.gamestudio.service;

import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class ScoreServiceJPA implements ScoreService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addScore(Score score) {
        if (score == null) {
            throw new IllegalArgumentException("Score cannot be null");
        }

        Score existingScore = entityManager.createQuery("SELECT s FROM Score s WHERE s.game = :game AND s.playerName = :playerName", Score.class)
                .setParameter("game", score.getGame())
                .setParameter("playerName", score.getPlayerName())
                .getResultList()
                .stream().findFirst().orElse(null);

        if (existingScore != null) {
            existingScore.setScore(score.getScore());
            existingScore.setPlayed(new Date());
            entityManager.merge(existingScore);
        } else {
            entityManager.persist(score);
        }
    }


    @Override
    public List<Score> getTopScores() {
        List<Score> topScores = entityManager.createQuery("select s from Score s order by s.score desc", Score.class)
                .setMaxResults(10)
                .getResultList();
        return topScores != null ? topScores : Collections.emptyList();
    }

    @Override
    public List<Score> getTopScores(String game) {
        if(game == null || game.isEmpty()) {
            throw new IllegalArgumentException("Game cannot be null or empty");
        }
        List<Score> topScores = entityManager.createQuery("select s from Score s where s.game = :game order by s.score desc", Score.class)
                .setParameter("game", game)
                .setMaxResults(10)
                .getResultList();
        return topScores != null ? topScores : Collections.emptyList();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("DELETE FROM score").executeUpdate();
    }
}
