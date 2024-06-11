package sk.tuke.gamestudio.service;


import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Score;

import java.util.Date;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScoreServiceTest {
//    @Autowired
    private ScoreService scoreService = new ScoreServiceJDBC();



    @Test
    public void testAddScore() {
        scoreService.reset();
        Score score = new Score("player1", "connect4", 100, new Date());
        scoreService.addScore(score);
        var scores = scoreService.getTopScores();

        assertEquals(1, scores.size());
        assertEquals(score.getPlayerName(), scores.get(0).getPlayerName());
        assertEquals(score.getGame(), scores.get(0).getGame());
        assertEquals(score.getScore(), scores.get(0).getScore());
    }

    @Test
    public void testGetTopScores() {
        scoreService.reset();
        Score score1 = new Score("player1", "connect4", 100, new Date());
        Score score2 = new Score("player2", "connect4", 200, new Date());
        scoreService.addScore(score1);
        scoreService.addScore(score2);
        var scores = scoreService.getTopScores();
//        System.out.println(scores.get(0).getPlayerName());

        assertEquals(2, scores.size());
        assertEquals(score2.getPlayerName(), scores.get(0).getPlayerName());
        assertEquals(score2.getScore(), scores.get(0).getScore());
    }

    @Test
    public void testReset() {
        Score score = new Score("player1", "game1", 100, new Date());
        scoreService.addScore(score);
        scoreService.reset();
        var scores = scoreService.getTopScores();
        assertTrue(scores.isEmpty());
    }

}