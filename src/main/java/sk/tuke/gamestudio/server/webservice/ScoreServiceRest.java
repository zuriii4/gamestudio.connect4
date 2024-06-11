package sk.tuke.gamestudio.server.webservice;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.GameStudioException;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreServiceRest {

//    @GetMapping
//    public String smth() {
//        return "ahoj";
//    }

    @Autowired
    private ScoreService scoreService;

//    public ScoreServiceRest(ScoreService scoreService) {
//        this.scoreService = scoreService;
//    }

    @GetMapping("/{game}") //api/score/game
    public List<Score> getTopScores(@PathVariable String game) {
        return scoreService.getTopScores(game);
    }

    @GetMapping //api/score/
    public List<Score> getTopScoresDefault() {
        return scoreService.getTopScores();
    }

    @PostMapping
    public void addScore(@RequestBody Score score) {
        scoreService.addScore(score);
    }

    @PutMapping("/{id}")
    public void updateScore(@PathVariable int id, @RequestBody Score score) {
        List<Score> existingScores = scoreService.getTopScores();

        for(Score newScore : existingScores) {
            if (newScore.getIdent() == id) {
                newScore.setScore(score.getScore());
                newScore.setPlayed(new Date());
                try {
                    scoreService.addScore(newScore);
                }catch (Exception e) {
                    throw new GameStudioException("Can t upadte");
                }
            }
        }
    }

}