package sk.tuke.gamestudio.game.core;
public class Player extends Token {
    private final String name;
    private String color;


    public Player(TokenState tokenState, String name) {
        super(tokenState);
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}
