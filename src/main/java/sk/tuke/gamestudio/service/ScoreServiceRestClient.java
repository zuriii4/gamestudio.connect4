package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Score;

import java.util.Arrays;
import java.util.List;

public class ScoreServiceRestClient implements ScoreService {
    private final String url = "http://localhost:8080/api/score";


    private RestTemplate restTemplate;

    public ScoreServiceRestClient(RestTemplate restTemplate) {
        if(restTemplate == null) {
            throw new IllegalArgumentException("RestTemplate cannot be null");
        }
        this.restTemplate = restTemplate;
    }

    @Override
    public void addScore(Score score) {
//        if(score == null) {
//            throw new IllegalArgumentException("Score cannot be null");
//        }
//        restTemplate.postForEntity(url, score, Score.class);
        if(score == null) {
            throw new IllegalArgumentException("Score cannot be null");
        }
        boolean updated = false;
        List<Score> existingScore = getTopScores(score.getGame());

        for(Score newScore : existingScore) {
            if (newScore.getPlayerName().equals(newScore.getPlayerName()) && newScore.getGame().equals(score.getGame()) && newScore.getScore() < score.getScore()) {
                restTemplate.put(url + "/" + newScore.getIdent(), score, Score.class);
                updated = true;
            }
        }

        if(updated == false) {
            restTemplate.postForEntity(url, score, Score.class);
        }

    }

    @Override
    public List<Score> getTopScores() {
        return Arrays.asList(restTemplate.getForEntity(url, Score[].class).getBody());
    }

    @Override
    public List<Score> getTopScores(String gameName) {
        if(gameName == null || gameName.isEmpty()) {
            throw new IllegalArgumentException("Game cannot be null or empty");
        }
        return Arrays.asList(restTemplate.getForEntity(url + "/" + gameName, Score[].class).getBody());
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }

//    public Score getScore(int id) {
//        List<Score> existingScores = getTopScores();
//
//        for(Score score : existingScores) {
//            if (score.getIdent() == id) {
//                return score;
//            }
//        }
//
//        throw new GameStudioException("id not founded");
//    }
}
