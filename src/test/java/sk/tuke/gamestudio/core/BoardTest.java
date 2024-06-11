package sk.tuke.gamestudio.core;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import sk.tuke.gamestudio.game.core.Board;
import sk.tuke.gamestudio.game.core.TokenState;

class BoardTest {

    @Test
    void testDropDisk() {
        Board board = new Board(5, 5, 0);
        assertTrue(board.dropDisk(0));
        assertEquals(TokenState.PLAYER1, board.getTokens(4, 0).getTokenState());
    }

    @Test
    void testExplodeMine() {
        Board board = new Board(5, 5, 0);
        board.dropDisk(0);
        board.explodeMine(4, 0);
        assertEquals(TokenState.EMPTY, board.getTokens(4, 0).getTokenState());
    }

    @Test
    void testIsFull() {
        Board board = new Board(1, 1, 0);
        assertFalse(board.isFull());
        board.dropDisk(0);
        assertTrue(board.isFull());
    }

    @Test
    void testMineDetect() {
        Board board = new Board(2, 2, 0);
        board.getTokens(0,0).setTokenState(TokenState.MINE);
        board.dropDisk(0);
//        assertFalse(board.mineDetect(0));
        assertTrue(board.mineDetect(0));
    }
}