package sk.tuke.gamestudio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.core.Board;
import sk.tuke.gamestudio.game.userinterface.BoardUI;
import sk.tuke.gamestudio.game.userinterface.ConsoleUI;
import sk.tuke.gamestudio.service.*;

import javax.persistence.EntityManager;
import java.util.Date;

@SpringBootApplication
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tuke.gamestudio.server.*"))
public class SpringClient {

    public static void main(String[] args) {
//        SpringApplication.run(SpringClient.class, args);
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
    }
    @Bean
    public CommandLineRunner runner(ConsoleUI ui) {
        return args -> ui.gameLoop();
    }

//    @Bean
//    public CommandLineRunner runner(ScoreService scoreService,CommentService commentService,RatingService ratingService) {
//        return s -> {
//            var rating = new Rating("Niekto", "connect4",7,new Date());
//            var comment = new Comment("Niekto","connect4","nieco2",new Date());
//            var score = new Score("Niekto","connect4",1000,new Date());
//
//            scoreService.addScore(score);
//            commentService.addComment(comment);
//            ratingService.addRating(rating);
//        };
//    }

    @Bean
    public ConsoleUI consoleUI(Board board, ScoreService scoreService,CommentService commentService,RatingService ratingService) {
        return new ConsoleUI(board, scoreService, commentService, ratingService);
    }

    @Bean
    public Board board(BoardUI boardUI) {
        return boardUI.makeBoard();
    }

    @Bean
    public BoardUI boardUI() {
        return new BoardUI();
    }

    @Bean
    public ScoreService scoreService(RestTemplate restTemplate) {
//        return new ScoreServiceJPA();
        return new ScoreServiceRestClient(restTemplate);
    }
    @Bean
    public CommentService commentService(RestTemplate restTemplate) {
//        return new CommentServiceJPA();
        return new CommentServiceRestClient(restTemplate);
    }

    @Bean
    public RatingService ratingService(RestTemplate restTemplate) {
//        return new RatingServiceJPA();
        return new RatingServiceRestClient(restTemplate);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
