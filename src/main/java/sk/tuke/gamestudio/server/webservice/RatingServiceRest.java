package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.GameStudioException;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {

    @Autowired
    private RatingService ratingService;

    @GetMapping("/{game}") //api/score/game
    public List<Rating> getTopScores(@PathVariable String game) {
        return ratingService.getTopRatings(game);
    }

    @PostMapping
    public void addScore(@RequestBody Rating rating) {
        ratingService.addRating(rating);
    }

    @GetMapping("/average/{game}") //api/rating/average/game
    public double getAverageRating(@PathVariable String game) {
        return ratingService.getAverageRating(game);
    }

    @PutMapping("/{id}")
    public void updateRating(@PathVariable int id, @RequestBody Rating rating) {
        List<Rating> existingRatings = ratingService.getTopRatings(rating.getGame());

        for(Rating newRating : existingRatings) {
            if (newRating.getIdent() == id) {
                newRating.setRating(rating.getRating());
                newRating.setRated(new Date());
                try {
                    ratingService.addRating(newRating);
                }catch (Exception e) {
                    throw new GameStudioException("Can t upadte rating");
                }
            }
        }
    }
}
