package sk.tuke.gamestudio.game.userinterface;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.game.core.Board;
import sk.tuke.gamestudio.game.core.GameState;
import sk.tuke.gamestudio.game.core.Player;
import sk.tuke.gamestudio.game.core.TokenState;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.*;

import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;



@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class ConsoleUI {
    @Autowired
    private ScoreService scoreService; //= new ScoreServiceJDBC();
    @Autowired
    private CommentService commentService; // = new CommentServiceJDBC();
    @Autowired
    private RatingService ratingService; // = new RatingServiceJDBC();

    private static final Pattern MINE_INPUT = Pattern.compile("([A-G])([1-6])") ;
    private static final String RESET = "\u001B[0m";


    private Board board;

    private Player player1;
    private Player player2;
    private Player winner;

    private Scanner scanner = new Scanner(System.in);

    private String previousColor;


    public ConsoleUI(Board board, ScoreService scoreService, CommentService commentService, RatingService ratingService) {
        this.board = board;
        this.scoreService = scoreService;
        this.commentService = commentService;
        this.ratingService = ratingService;
        intro();
    }
    public ConsoleUI(Board board) {
        this.board = board;
        this.scoreService = new ScoreServiceJDBC();
        this.commentService = new CommentServiceJDBC();
        this.ratingService = new RatingServiceJDBC();
//        System.out.println("dacooo");

        intro();
    }


    // gameloop -------------------------------------
    public void gameLoop() {
        while(board.getGameState().equals(GameState.PLAYING)) {
            update();
            processInput();
            System.out.println();
        }
        update();
        winner = board.getWinner().equals(TokenState.PLAYER1) ? player1 : player2;
        System.out.println("Congratulation " + (winner.getName()) + " you won");
        var score = new Score(winner,board);
        scoreService.addScore(score);
        System.out.println("You earned " + (score.getScore()) + " points in the game. Keep up the good work!");


        String input;
        do {
            System.out.println("Want to replay the game?(Y/N)");
            input = scanner.next().toUpperCase();
        }while(!input.equals("Y") && !input.equals("N"));
        if (input.equals("Y")) {
            board.reset();
            gameLoop();
        }

        addComment();

    }

    // welcome -------------------------------------
    private void intro() {
        printTopScores();
        printComments();
        printRatings();
        System.out.println("Priemerny rating: " + ratingService.getAverageRating("connect4"));


        String name;
        do {
            System.out.println("What is the name of the first player?");
            name = scanner.nextLine();
        }while(name.isEmpty());
        player1 = new Player(TokenState.PLAYER1,name);
        setPlayerColor(player1);

        System.out.println();
        do {
            System.out.println("What is the name of the second player?");
            name = scanner.nextLine();
        }while(name.isEmpty());
        player2 = new Player(TokenState.PLAYER2,name);
        setPlayerColor(player2);

    }

    // upadte board console -------------------------------------
    private void update() {

        for (int row = 0; row < board.getRowCount(); row++) {
                System.out.print((char) ('A' + row) + " |");
            for (int column = 0; column < board.getColumnCount(); column++) {
                switch (board.getTokens(row,column).getTokenState()) {
                    case EMPTY:
                        System.out.print(" 0");
                        break;
                    case PLAYER1:
                        System.out.print(player1.getColor() + " 1" + RESET);
                        break;
                    case PLAYER2:
                        System.out.print(player2.getColor() + " 2" + RESET);
                        break;
                    case MINE:
                        System.out.print("\u001B[31m" + " M" + RESET);
                        break;
                    default:
                        break;
                }
            }
            System.out.println();
        }
        System.out.print("   ");
        for (int index = 0; index < board.getColumnCount(); index++) {
            System.out.print(" ˉ");
        }
        System.out.println(" ");
        System.out.print("   ");
        for (int index = 0; index < board.getColumnCount(); index++) {
            System.out.print(" " + (index + 1));
        }
        System.out.println(" ");
    }

    // input column -------------------------------------
    private void processInput() {
        int column;
        String input;
        do {
            System.out.print(board.getCurrentPlayer().equals(TokenState.PLAYER1)? player1.getName(): player2.getName());
            System.out.println(" : Enter number of column:");
            input = scanner.nextLine();
            try {
                column = Integer.parseInt(input);
            } catch (NumberFormatException e){
                column = -1;
            }
        } while(column >= board.getColumnCount() + 1 || column < 0);
        if (board.mineDetect(column - 1)){
            mineUI();
        }else {
            if (!board.dropDisk(column - 1)) {
                System.out.println("Column is full, select another column");
                processInput();
            }
        }
    }

    // write mine -------------------------------------
    private void mineUI() {
        int row;
        int column;
        System.out.println("Enter command (A-G)(1-6)");
        var line = scanner.nextLine().toUpperCase();
        var matcher = MINE_INPUT.matcher(line);

        if (matcher.matches()) {
            row = matcher.group(1).charAt(0) - 'A';
            column = Integer.parseInt(matcher.group(2)) - 1;
            if (board.getTokens(row,column).getTokenState().equals(TokenState.EMPTY)) {
                System.out.println("Wrong input");
                mineUI();
            }else {
                board.explodeMine(row ,column);
                board.checkWin();
            }
        }else {
            System.out.println("Wrong input");
            mineUI();
        }

    }

    // color -------------------------------------

    private void setPlayerColor(Player player) {

        String color;
        do {
            System.out.println("Select the colour of the player(GREEN, YELLOW, BLUE,PURPLE, CYAN)");
            color = scanner.nextLine().toUpperCase();
            switch (color) {
                case "GREEN":
                    player.setColor("\u001B[32m");
                    break;
                case "YELLOW":
                    player.setColor("\u001B[33m");
                    break;
                case "BLUE":
                    player.setColor("\u001B[34m");
                    break;
                case "PURPLE":
                    player.setColor("\u001B[35m");
                    break;
                case "CYAN":
                    player.setColor("\u001B[36m");
                    break;
                default:
                    System.out.println("Invalid input!");
                    break;
            }
        }while(!isValidColor(color) || color.isEmpty());
        previousColor = color;
    }
    private boolean isValidColor(String color) {
        switch (color) {
            case "GREEN":
            case "YELLOW":
            case "BLUE":
            case "PURPLE":
            case "CYAN":
                if (color.equals(previousColor)) {
                    System.out.println("Player already has this color. Choose a different color.");
                    return false;
                }
                return true;
            default:
                return false;
        }
    }

    // database -------------------------------------

    private void printTopScores() {
        var scores = scoreService.getTopScores("connect4");
        System.out.printf("This is TOP 10 players:\n");
        System.out.println("---------------------------------------------------------------");
        for (int index = 0; index < scores.size(); index++) {
            var score = scores.get(index);
            System.out.printf("%d. %s %d\n", index + 1, score.getPlayerName(), score.getScore());
        }
        System.out.println("---------------------------------------------------------------");

    }

    private void printComments() {
        var comments = commentService.getComments("connect4");
        System.out.printf("Comments:\n");
        System.out.println("---------------------------------------------------------------");
        for (int index = 0; index < comments.size(); index++) {
            var comment = comments.get(index);
            System.out.printf("%d. %s: %s\n", index + 1, comment.getPlayerName(), comment.getCommentText());
        }
        System.out.println("---------------------------------------------------------------");
    }
    private void printRatings() {
        var ratings = ratingService.getTopRatings("connect4");
        System.out.printf("Ratings:\n");
        System.out.println("---------------------------------------------------------------");
        for (int index = 0; index < ratings.size(); index++) {
            var rating = ratings.get(index);
            System.out.printf("%d. %s: %d\n", index + 1, rating.getPlayerName(), rating.getRating());
        }
        System.out.println("---------------------------------------------------------------");
    }
    public void addComment() {

        String line;
            System.out.println("Do you want to leave a rating?(Y/N)");
        do{
            line = scanner.nextLine().toUpperCase();
        }while(!line.equals("Y") && !line.equals("N") || line.isEmpty());
        if (line.equals("Y")) {
            System.out.println("Write rating:");
            line = scanner.nextLine();
            var comment = new Comment(winner.getName(),"connect4",line,new Date());
            commentService.addComment(comment);
            System.out.println("Write rating(1-10):");
            int rate = 0;
            do {
                line = scanner.nextLine();
                try {
                    rate = Integer.parseInt((line));
                }catch (NumberFormatException e){
                    System.out.println("Invalid input. Please enter a number between 1 and 10.");
                    continue;
                }

            }while(rate > 10 || rate < 1);
            var rating = new Rating(winner.getName(), "connect4",rate,new Date());
            ratingService.addRating(rating);
        }
    }
}
