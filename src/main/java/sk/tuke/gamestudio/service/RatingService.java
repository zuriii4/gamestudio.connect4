package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import java.util.List;

public interface RatingService {
    void addRating(Rating rating);

    List<Rating> getTopRatings(String game);
    double getAverageRating(String game);

    void reset();
}
