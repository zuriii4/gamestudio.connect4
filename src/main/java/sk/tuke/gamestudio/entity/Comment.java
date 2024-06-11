package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"game", "playerName"}))
public class Comment {
    @Id
    @GeneratedValue
    private int ident; //identifikator primary key
    private String playerName;
    private String game;
    private String commentText;
    private Date commented;

    public Comment(String playerName, String game, String commentText, Date commented) {
        this.playerName = playerName;
        this.game = game;
        this.commentText = commentText;
        this.commented = commented;
    }

    public Comment() {

    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getCommented() {
        return commented;
    }

    public void setCommented(Date commented) {
        this.commented = commented;
    }
    public int getIdent() { return ident; }
    public void setIdent(int ident) { this.ident = ident; }
}
