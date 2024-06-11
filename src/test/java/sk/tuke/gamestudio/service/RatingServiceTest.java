package sk.tuke.gamestudio.service;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Rating;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RatingServiceTest {
    private RatingService ratingService = new RatingServiceJDBC();

    @Test
    public void testAddRating() {
        ratingService.reset();
        Rating rating = new Rating("player1", "connect4", 5, new Date());
        ratingService.addRating(rating);
        List<Rating> ratings = ratingService.getTopRatings("connect4");

        assertEquals(1, ratings.size());
        assertEquals(rating.getPlayerName(), ratings.get(0).getPlayerName());
        assertEquals(rating.getGame(), ratings.get(0).getGame());
        assertEquals(rating.getRating(), ratings.get(0).getRating());
    }

    @Test
    public void testGetTopRatings() {
        ratingService.reset();
        Rating rating1 = new Rating("player1", "connect4", 5, new Date());
        Rating rating2 = new Rating("player2", "connect4", 4, new Date());
        ratingService.addRating(rating1);
        ratingService.addRating(rating2);
        List<Rating> ratings = ratingService.getTopRatings("connect4");

        assertEquals(2, ratings.size());
        assertEquals(rating1.getPlayerName(), ratings.get(0).getPlayerName());
        assertEquals(rating1.getRating(), ratings.get(0).getRating());
    }

    @Test
    public void testReset() {
        Rating rating = new Rating("player1", "connect4", 5, new Date());
        ratingService.addRating(rating);
        ratingService.reset();
        List<Rating> ratings = ratingService.getTopRatings("connect4");

        assertTrue(ratings.isEmpty());
    }
}
