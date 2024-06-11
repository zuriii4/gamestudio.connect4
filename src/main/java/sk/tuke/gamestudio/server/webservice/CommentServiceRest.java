package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.GameStudioException;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentServiceRest {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{game}") //api/score/game
    public List<Comment> getTopScores(@PathVariable String game) {
        return commentService.getComments(game);
    }

    @PostMapping
    public void addScore(@RequestBody Comment comment) {
        commentService.addComment(comment);
    }

    @PutMapping("/{id}")
    public void updateScore(@PathVariable int id, @RequestBody Comment comment) {
        List<Comment> existingScores = commentService.getComments(comment.getGame());

        for(Comment newComment : existingScores) {
            if (newComment.getIdent() == id) {
                newComment.setCommentText(comment.getCommentText());
                newComment.setCommented(new Date());
                try {
                    commentService.addComment(newComment);
                }catch (Exception e) {
                    throw new GameStudioException("Can t upadte");
                }
            }
        }
    }
}
