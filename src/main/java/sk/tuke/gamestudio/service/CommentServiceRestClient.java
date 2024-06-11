package sk.tuke.gamestudio.service;

import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;

import java.util.Arrays;
import java.util.List;

public class CommentServiceRestClient implements CommentService{

    private final String url = "http://localhost:8080/api/comment";


    private RestTemplate restTemplate;

    public CommentServiceRestClient(RestTemplate restTemplate) {
        if(restTemplate == null) {
            throw new IllegalArgumentException("RestTemplate cannot be null");
        }
        this.restTemplate = restTemplate;
    }

    @Override
    public void addComment(Comment comment) {
//        if(comment == null) {
//            throw new IllegalArgumentException("Comment cannot be null");
//        }
//        restTemplate.postForEntity(url, comment, Comment.class);

        if(comment == null) {
            throw new IllegalArgumentException("Comment cannot be null");
        }
        boolean updated = false;
        List<Comment> existingRatings = getComments(comment.getGame());

        for(Comment newComment : existingRatings) {
            if (newComment.getPlayerName().equals(comment.getPlayerName()) && newComment.getGame().equals(comment.getGame())) {
                restTemplate.put(url + "/" + newComment.getIdent(), comment, Rating.class);
                updated = true;
            }
        }

        if(updated == false) {
            restTemplate.postForEntity(url, comment, Rating.class);
        }
    }

    @Override
    public List<Comment> getComments(String game) {
        if(game == null || game.isEmpty()) {
            throw new IllegalArgumentException("Game cannot be null or empty");
        }
        return Arrays.asList(restTemplate.getForEntity(url + "/" + game, Comment[].class).getBody());
    }


    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
