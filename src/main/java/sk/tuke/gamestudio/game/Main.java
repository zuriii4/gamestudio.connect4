package sk.tuke.gamestudio.game;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import sk.tuke.gamestudio.game.core.Board;
import sk.tuke.gamestudio.game.userinterface.BoardUI;
import sk.tuke.gamestudio.game.userinterface.ConsoleUI;

class Main {
    public static void main(String[] args) {
//        Board board = new Board(7,6, 3);
//        ConsoleUI consoleUI = new ConsoleUI(board);

//        var player = new Player(TokenState.PLAYER1,"Erik");
//        var service = new ScoreServiceJDBC();

//        board.getTokens(5,4).setTokenState(TokenState.PLAYER1);
//        var score = new Score("player","gam", 30,new Date());
//        service.addScore(score);

        var boardUI = new BoardUI();
        var board = boardUI.makeBoard();
        var consoleUI = new ConsoleUI(board);
        consoleUI.gameLoop();


//        board.mineGenerate();


    }
}