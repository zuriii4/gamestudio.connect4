package sk.tuke.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.game.core.Board;
import sk.tuke.gamestudio.game.core.GameState;
import sk.tuke.gamestudio.game.core.TokenState;

@Controller
@RequestMapping("/connect4")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class Connect4Controller {
    private Board board = new Board(6, 7, 0);
    private int currentPlayer = 1;  // Assuming 1 for player 1 and 2 for player 2

    @GetMapping
    public String play(@RequestParam(required = false) Integer column) {
        if (column != null && column >= 0 && column < board.getColumnCount()) {
            board.dropDisk(column);
            this.currentPlayer = this.currentPlayer == 1 ? 2 : 1; // Toggle player
        }
        return "connect4";
    }

    @GetMapping("/new")
    public String newGame() {
        board = new Board(6, 7, 3);
        currentPlayer = 1;
        return "connect4";
    }

    public String getHtmlBoard() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table class='connect4'>\n");
        for (int row = 0; row < board.getRowCount(); row++) {
            sb.append("<tr>\n");
            for (int column = 0; column < board.getColumnCount(); column++) {
                sb.append("<td class='" + getTileClass(board.getTokens(row, column).getTokenState()) + "'>\n");
                sb.append("<a href='/connect4?column=" + column + "'>\n");
                sb.append("<span>" + getTileText(board.getTokens(row, column).getTokenState()) + "</span>\n");
                sb.append("</a>\n");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");
        return sb.toString();
    }

    private String getTileClass(TokenState state) {
        switch (state) {
            case EMPTY:
                return "empty";
            case PLAYER1:
                return "player1";
            case PLAYER2:
                return "player2";
            case MINE:
                return "mine";
            default:
                throw new IllegalArgumentException("Invalid token state: " + state);
        }
    }

    private String getTileText(TokenState state) {
        switch (state) {
            case EMPTY:
                return "-";
            case PLAYER1:
                return "1";
            case PLAYER2:
                return "2";
            default:
                throw new IllegalArgumentException("Invalid token state: " + state);
        }
    }

    public GameState getState() {
        return board.getGameState();
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }
}
