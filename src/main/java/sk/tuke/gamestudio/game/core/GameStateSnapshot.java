package sk.tuke.gamestudio.game.core;

public class GameStateSnapshot {
    private Token[][] boardState;
    private GameState gameState;
    private TokenState currentPlayer;
    private int score;
    private int mine;

    public GameStateSnapshot(Token[][] boardState, GameState gameState, TokenState currentPlayer, int score, int mine) {
        this.boardState = copyBoard(boardState);
        this.gameState = gameState;
        this.currentPlayer = currentPlayer;
        this.score = score;
        this.mine = mine;
    }

    private Token[][] copyBoard(Token[][] original) {
        Token[][] copy = new Token[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                copy[i][j] = new Token(original[i][j].getTokenState());
            }
        }
        return copy;
    }

    public Token[][] getBoardState() {
        return boardState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public TokenState getCurrentPlayer() {
        return currentPlayer;
    }

    public int getScore() {
        return score;
    }

    public int getMine() {
        return mine;
    }
}
