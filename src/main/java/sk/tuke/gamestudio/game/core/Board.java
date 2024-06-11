package sk.tuke.gamestudio.game.core;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Board {
    private int rowCount;
    private int columnCount;
    private Token[][] tokens;
    private GameState gameState;
    private TokenState currentPlayer;
    private TokenState winner;
    private int mine;
    private int maxMine;
    private int score;
    private MoveHistory moveHistory;


    public Board(int rowCount, int columnCount, int maxMine) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.tokens = new Token[rowCount][columnCount];
        this.moveHistory = new MoveHistory();
        this.mine = 0;
        this.score = 0;
        this.maxMine = maxMine;
        generate();

        if (mine < maxMine) {
            mineGenerate();
            mine++;
        }
        winner = TokenState.EMPTY;
        gameState = GameState.PLAYING;
        currentPlayer = TokenState.PLAYER1;
        recordGameState();
    }


//    token interaction functions ------------------------------------------------------------

    public boolean dropDisk(int column) {
        if (!isFull(column)) {
            for (int row = rowCount - 1; row >= 0; row--) {
                if (tokens[row][column].getTokenState() == TokenState.EMPTY) {
                    tokens[row][column].setTokenState(currentPlayer);
                    break;
                }
            }
            currentPlayer = currentPlayer.equals(TokenState.PLAYER1) ? TokenState.PLAYER2 : TokenState.PLAYER1;
            recordGameState();
            score += 5;
            checkWin();
            return true;
        }
        return false;
    }

    public boolean explodeMine(int row, int column) {
        if (tokens[row][column].getTokenState().equals(TokenState.EMPTY)) {
            return false;
        }
        tokens[row][column].setTokenState(TokenState.EMPTY);
        update(column);
        checkWin();
        if (mine < maxMine) {
            mineGenerate();
            mine++;
//            System.out.println("Mine count incremented to: " + mine);
        }
        recordGameState();
        return true;
    }


//    board functions ------------------------------------------------------------

    public void generate() {
        for(int column = 0; column < columnCount; column++) {
            for(int row = 0; row < rowCount;row++) {
                tokens[row][column] = new Token(TokenState.EMPTY);
            }
        }
    }

    public void reset() {
        for(int column = 0; column < columnCount; column++) {
            for(int row = 0; row < rowCount;row++) {
                tokens[row][column].setTokenState(TokenState.EMPTY);
            }
        }
        this.gameState = GameState.PLAYING;
        this.mine = 0;
        this.score = 0;

        if (mine < maxMine) {
            mineGenerate();
            mine++;
        }
        currentPlayer = TokenState.PLAYER1;
        winner = TokenState.EMPTY;
        recordGameState();
    }

    private void mineGenerate() {
        Random rand = new Random();
        int randRow;
        int randColumn;

        if (!isFull()) {
            do {
                randRow = rand.nextInt(rowCount - 2);
                randColumn = rand.nextInt(columnCount);
            } while (!tokens[randRow][randColumn].getTokenState().equals(TokenState.EMPTY));
            tokens[randRow][randColumn].setTokenState(TokenState.MINE);
        }
    }

    private void update(int column) {
        for (int i = rowCount - 1; i >= 0; i--) {
            if (i > 0 && tokens[i][column].getTokenState().equals(TokenState.EMPTY) && !tokens[i - 1][column].getTokenState().equals(TokenState.EMPTY)) {
                tokens[i][column].setTokenState(tokens[i - 1][column].getTokenState());
                tokens[i - 1][column].setTokenState(TokenState.EMPTY);
            }
        }
    }

    //    check functions ------------------------------------------------------------
    public boolean isFull() {
        for(int i = 0; i < columnCount; i++) {
            if(!isFull(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean isFull(int column) {
        for(int i = 0; i < rowCount; i++) {
            if (tokens[i][column].getTokenState() == TokenState.EMPTY){
                return false;
            }
        }
        return true;
    }

    public boolean mineDetect(int column) {
        for (int i = rowCount - 1; i >= 0; i--) {
            if (tokens[i][column].getTokenState().equals(TokenState.MINE) && !tokens[i + 1][column].getTokenState().equals(TokenState.EMPTY)) {
                tokens[i][column].setTokenState(TokenState.EMPTY);
                return true;
            }
        }
        return false;
    }

    public void checkWin() {
        for(int i = 0; i < columnCount; i++){
            for (int j = 0; j < rowCount; j++){
                if (!tokens[j][i].getTokenState().equals(TokenState.EMPTY)) {
                    if (horizontal(j, i) || vertical(j, i) || diagonal(j, i)) {
                        gameState = GameState.SOLVED;
                        this.winner = tokens[j][i].getTokenState();

                    }
                }
            }
        }
        if(isFull()) {
            gameState = GameState.FAILED;
        }
//        return false;
    }

    private boolean horizontal(int row, int column) {
        TokenState tokenState = tokens[row][column].getTokenState();
        boolean isSame = true;

        for (int i = 0; i < 4; i++) {
            if (!isValidPosition(row, column + i) || tokens[row][column + i].getTokenState() != tokenState) {
                isSame = false;
                break;
            }
        }

        return isSame;
    }

    private boolean vertical(int row, int column) {
        TokenState tokenState = tokens[row][column].getTokenState();
        boolean isSame = true;

        for (int i = 0; i < 4; i++) {
            if (!isValidPosition(row + i, column) || tokens[row + i][column].getTokenState() != tokenState) {
                isSame = false;
                break;
            }
        }

        return isSame;
    }

    private boolean diagonal(int row, int column) {
        TokenState tokenState = tokens[row][column].getTokenState();
        boolean isSame = true;

        for (int i = 0; i < 4; i++) {
            if (!isValidPosition(row + i, column + i) || tokens[row + i][column + i].getTokenState() != tokenState) {
                isSame = false;
                break;
            }
        }
        if (isSame) {
            return isSame;
        }

        isSame = true;
        for (int i = 0; i < 4; i++) {
            if (!isValidPosition(row + i, column - i) || tokens[row + i][column - i].getTokenState() != tokenState) {
                isSame = false;
                break;
            }
        }
        return isSame;
    }

    private boolean isValidPosition(int row, int column) {
        return row >= 0 && row < rowCount && column >= 0 && column < columnCount;
    }

//    getters/setters ------------------------------------------------------------

    public TokenState getCurrentPlayer() {
        return currentPlayer;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    public TokenState getWinner() {
        return winner;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Token getTokens(int row, int column) {
        return tokens[row][column];
    }

    public void setWinner(TokenState winner) {
        this.winner = winner;
        gameState = GameState.SOLVED;
    }

    public void undoMve() {
        GameStateSnapshot move = moveHistory.undoMove();
        this.gameState = move.getGameState();
        this.currentPlayer = move.getCurrentPlayer();
        this.score = move.getScore();
        this.mine = move.getMine();
        this.tokens = move.getBoardState();
    }

    private void recordGameState() {
        GameStateSnapshot snapshot = new GameStateSnapshot(
                tokens,
                gameState,
                currentPlayer,
                score,
                mine
        );
        moveHistory.recordMove(snapshot);
    }
    public boolean randomMove() {
        if (!isFull()) {
            Random rand = new Random();
            int randInt;
            do{
                randInt = rand.nextInt(columnCount);
            }while(isFull(randInt));
            dropDisk(randInt);
            return true;
        }else {
            return false;
        }
    }

    public Map<String, Object> getBoardState() {
        Map<String, Object> boardState = new HashMap<>();
        boardState.put("rowCount", rowCount);
        boardState.put("columnCount", columnCount);
        boardState.put("tokens", tokens);
        boardState.put("gameState", gameState);
        boardState.put("currentPlayer", currentPlayer);
        boardState.put("winner", winner);
        boardState.put("mine", mine);
        boardState.put("maxMine", maxMine);
        boardState.put("score", score);
        return boardState;
    }

    public Map<String, Object> getDropDiskInfo(int column) {
        dropDisk(column);
        Map<String, Object> boardState = new HashMap<>();
        boardState.put("rowCount", rowCount);
        boardState.put("columnCount", columnCount);
        boardState.put("tokens", tokens);
        boardState.put("gameState", gameState);
        boardState.put("currentPlayer", currentPlayer);
        boardState.put("winner", winner);
        boardState.put("mine", mine);
        boardState.put("maxMine", maxMine);
        boardState.put("score", score);
        return boardState;
    }
    public Map<String, Object> getExplodeMineInfo(int row, int column) {
        explodeMine(row, column);
        Map<String, Object> boardState = new HashMap<>();
        boardState.put("rowCount", rowCount);
        boardState.put("columnCount", columnCount);
        boardState.put("tokens", tokens);
        boardState.put("gameState", gameState);
        boardState.put("currentPlayer", currentPlayer);
        boardState.put("winner", winner);
        boardState.put("mine", mine);
        boardState.put("maxMine", maxMine);
        boardState.put("score", score);
        return boardState;
    }

    public Map<String, Object> getNewGameInfo() {
        reset();
        Map<String, Object> boardState = new HashMap<>();
        boardState.put("rowCount", rowCount);
        boardState.put("columnCount", columnCount);
        boardState.put("tokens", tokens);
        boardState.put("gameState", gameState);
        boardState.put("currentPlayer", currentPlayer);
        boardState.put("winner", winner);
        boardState.put("mine", mine);
        boardState.put("maxMine", maxMine);
        boardState.put("score", score);
        return boardState;
    }
}
