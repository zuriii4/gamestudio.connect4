package sk.tuke.gamestudio.entity;

import sk.tuke.gamestudio.game.core.Board;
import sk.tuke.gamestudio.game.core.Player;
import sk.tuke.gamestudio.game.core.TokenState;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"game", "playerName"}))
public class Score {
    @Id
    @GeneratedValue
    private int ident; //identifikator primary key
    private String playerName;
    private String game;
    private int score;
    private Date played;



    public Score(Player player, Board board) {
        this.playerName = player.getName();
        game = "connect4";
        this.played = new Date();
        this.score = calculateScore(player,board);
    }

    public Score(String player, String game, int score, Date date) {
        this.playerName = player;
        this.game = game;
        this.score = score;
        this.played = date;
//        this.game = game != null ? game : "connect4";
//        System.out.println(game + " " + player + " " + score);
    }

    public Score() {}

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPlayed(Date played) {
        this.played = played;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getGame() {
        return game;
    }

    public int getScore() {
        return score;
    }

    public Date getPlayed() {
        return played;
    }

    private int calculateScore(Player player, Board board) {
        int points = 500;

        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                if (board.getTokens(i,j).getTokenState().equals(player.getTokenState())) {
//                    points += horizontal(i, j,player,board) + vertical(i, j,player,board) + diagonal(i, j,player,board);
//                    System.out.printf("row%d column%d\n", i, j);
                    points *= 5;
                }
            }
        }

        return points;
    }




    private int horizontal(int row, int column,Player player, Board board) {
        TokenState tokenState = player.getTokenState();
        int counter = 10;

        for (int i = 0; i < 3; i++) {
            if (isValidPosition(row, column + i, board) && board.getTokens(row,column + 1).getTokenState() == tokenState) {
                counter *= 5;
            }
            else {
                break;
            }
        }

        return counter;
    }

    private int vertical(int row, int column,Player player, Board board) {
        TokenState tokenState = player.getTokenState();
        int counter = 10;


        for (int i = 0; i < 3; i++) {
            if (isValidPosition(row + i, column, board)) {
//                if (board.getTokens(row + 1,column).getTokenState() == tokenState) {
//                    counter *= 5;
//                }
                System.out.printf("row%d column%d %d\n", row + i, column, isValidPosition(row + i, column,board));
            }
            else {
                break;
            }
        }

        return counter;
    }

    private int diagonal(int row, int column,Player player, Board board) {
        TokenState tokenState = player.getTokenState();
        int counter = 10;

        for (int i = 0; i < 3; i++) {
            if (isValidPosition(row + i, column + i, board) && board.getTokens(row + i, column + i).getTokenState() == tokenState) {
                counter *= 10;
            } else {
                break;
            }
        }

        int helpCounter = 10;

        for (int i = 0; i < 4; i++) {
            if (isValidPosition(row + i, column - i,board) && board.getTokens(row + i, column - i).getTokenState() == tokenState) {
                helpCounter *= 10;
            } else {
                break;
            }
        }

        return counter + helpCounter;
    }


    private boolean isValidPosition(int row, int column, Board board) {
        return row >= 0 && row < board.getRowCount() && column >= 0 && column < board.getColumnCount();
    }

    public int getIdent() { return ident; }
    public void setIdent(int ident) { this.ident = ident; }
}
