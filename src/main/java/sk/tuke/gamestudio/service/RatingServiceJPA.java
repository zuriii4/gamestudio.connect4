package sk.tuke.gamestudio.service;

import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addRating(Rating rating) {
        if (rating == null) {
            throw new IllegalArgumentException("Rating cannot be null");
        }

        Rating existingRating = entityManager.createQuery("SELECT r FROM Rating r WHERE r.game = :game AND r.playerName = :playerName", Rating.class)
                .setParameter("game", rating.getGame())
                .setParameter("playerName", rating.getPlayerName())
                .getResultList()
                .stream().findFirst().orElse(null);

        if (existingRating != null) {
            existingRating.setRating(rating.getRating());
            existingRating.setRated(new Date());
            entityManager.merge(existingRating);
        } else {
            entityManager.persist(rating);
        }
    }


    @Override
    public List<Rating> getTopRatings(String game) {
        if (game == null || game.isEmpty()) {
            throw new IllegalArgumentException("Game cannot be null or empty");
        }
        List<Rating> topRatings = entityManager.createQuery("select s from Rating s where s.game = :game", Rating.class)
                .setParameter("game", game)
                .setMaxResults(10)
                .getResultList();
        return topRatings != null ? topRatings : Collections.emptyList();
    }


    @Override
    public double getAverageRating(String game) {
        if (game == null || game.isEmpty()) {
            throw new IllegalArgumentException("Game cannot be null or empty");
        }
        Double averageRating = entityManager.createQuery("select avg(r.rating) from Rating r where r.game = :game", Double.class)
                .setParameter("game", game)
                .getSingleResult();
        return averageRating != null ? averageRating : 0;
    }
    @Override
    public void reset() {
        entityManager.createQuery("DELETE FROM Rating").executeUpdate();
    }
}
