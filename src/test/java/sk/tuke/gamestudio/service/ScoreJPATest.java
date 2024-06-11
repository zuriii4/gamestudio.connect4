package sk.tuke.gamestudio.service;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.GameStudioException;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.service.ScoreServiceJPA;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScoreJPATest {

    @PersistenceContext
//    private EntityManager entityManager;

    private ScoreService scoreServiceJPA = new ScoreServiceJPA();

    @Test
    public void testAddScore() throws GameStudioException {
        Score score = new Score("player1", "connect4", 100, new Date());
        scoreServiceJPA.addScore(score);
        List<Score> scores = scoreServiceJPA.getTopScores("connect4");

        assertEquals(1, scores.size());
        assertEquals(score.getPlayerName(), scores.get(0).getScore());
        assertEquals(score.getGame(), scores.get(0).getGame());
        assertEquals(score.getPlayerName(), scores.get(0).getScore());
    }

    @Test
    public void testGetTopScores() throws GameStudioException {
        Score score1 = new Score("player1", "connect4", 100, new Date());
        Score score2 = new Score("player2", "connect4", 90, new Date());
        scoreServiceJPA.addScore(score1);
        scoreServiceJPA.addScore(score2);
        List<Score> scores = scoreServiceJPA.getTopScores("connect4");

        assertEquals(2, scores.size());
        assertEquals(score1.getPlayerName(), scores.get(0).getPlayerName());
        assertEquals(score1.getScore(), scores.get(0).getScore());
    }

    @Test
    public void testReset() throws GameStudioException {
        Score score = new Score("player1", "connect4", 100, new Date());
        scoreServiceJPA.addScore(score);
        scoreServiceJPA.reset();
        List<Score> scores = scoreServiceJPA.getTopScores("connect4");

        assertTrue(scores.isEmpty());
    }
}
