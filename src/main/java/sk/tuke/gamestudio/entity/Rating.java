package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"game", "playerName"}))
public class Rating {
    @Id
    @GeneratedValue
    private int ident; //identifikator primary key
    private String playerName;
    private String game;
    private int rating;
    private Date rated;

    public Rating(String playerName, String game, int rating, Date rated) {
        this.playerName = playerName;
        this.game = game;
        if (rating > 10) {
            this.rating = 10;
        } else if (rating < 10) {
            this.rating = 0;
        } else {
            this.rating = rating;
        }
        this.rated = rated;
    }

    public Rating() {

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getRated() {
        return rated;
    }

    public void setRated(Date rated) {
        this.rated = rated;
    }
    public int getIdent() { return ident; }
    public void setIdent(int ident) { this.ident = ident; }
}
