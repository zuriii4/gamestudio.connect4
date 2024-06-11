package sk.tuke.gamestudio.game.core;
public class Token {
    private TokenState tokenState;

    public Token(TokenState tokenState) {
        this.tokenState = tokenState;
    }

    public TokenState getTokenState() {
        return tokenState;
    }

    public void setTokenState(TokenState tokenState) {
        this.tokenState = tokenState;
    }
}
