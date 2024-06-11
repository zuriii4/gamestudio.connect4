package sk.tuke.gamestudio.service;

import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;

import java.util.Arrays;
import java.util.List;

public class RatingServiceRestClient implements RatingService {

    private final String url = "http://localhost:8080/api/rating";


    private RestTemplate restTemplate;

    public RatingServiceRestClient(RestTemplate restTemplate) {
        if(restTemplate == null) {
            throw new IllegalArgumentException("RestTemplate cannot be null");
        }
        this.restTemplate = restTemplate;
    }

    @Override
    public void addRating(Rating rating) {
//        if(rating == null) {
//            throw new IllegalArgumentException("Comment cannot be null");
//        }
//        restTemplate.postForEntity(url, rating, Rating.class);
        if(rating == null) {
            throw new IllegalArgumentException("Rating cannot be null");
        }
        boolean updated = false;
        List<Rating> existingRatings = getTopRatings(rating.getGame());

        for(Rating newRating : existingRatings) {
            if (newRating.getPlayerName().equals(rating.getPlayerName()) && newRating.getGame().equals(rating.getGame())) {
                restTemplate.put(url + "/" + newRating.getIdent(), rating, Rating.class);
                updated = true;
            }
        }

        if(updated == false) {
            restTemplate.postForEntity(url, rating, Rating.class);
        }
    }

    @Override
    public List<Rating> getTopRatings(String game) {
        if(game == null || game.isEmpty()) {
            throw new IllegalArgumentException("Game cannot be null or empty");
        }
        return Arrays.asList(restTemplate.getForEntity(url + "/" + game, Rating[].class).getBody());
    }

    @Override
    public double getAverageRating(String game) {
        if(game == null || game.isEmpty()) {
            throw new IllegalArgumentException("Game cannot be null or empty");
        }
        return restTemplate.getForObject(url + "/average/" + game, Double.class);
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
