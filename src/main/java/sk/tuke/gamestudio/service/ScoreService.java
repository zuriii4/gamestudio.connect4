package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;

import java.util.List;

public interface ScoreService {
    void addScore(Score score);

    List<Score> getTopScores();

    List<Score> getTopScores(String game) throws GameStudioException;

    void reset();
}
