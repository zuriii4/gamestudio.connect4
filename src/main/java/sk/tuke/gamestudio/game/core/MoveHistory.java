package sk.tuke.gamestudio.game.core;

import java.util.ArrayList;



public class MoveHistory {
    private ArrayList<GameStateSnapshot> moveHistory;

    public MoveHistory() {
        this.moveHistory = new ArrayList<>();
    }

    public void recordMove(GameStateSnapshot snapshot) {
        moveHistory.add(snapshot);
    }


    public GameStateSnapshot undoMove() {
        if (!moveHistory.isEmpty()) {
            return moveHistory.remove((moveHistory.size() - 1) - 1);
        }
        return null;
    }
}
