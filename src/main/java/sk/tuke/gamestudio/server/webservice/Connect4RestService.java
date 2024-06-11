package sk.tuke.gamestudio.server.webservice;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.game.core.Board;
import sk.tuke.gamestudio.game.core.TokenState;


import java.util.Map;


@RestController
@RequestMapping("/api/connect4/board")
public class Connect4RestService {
//    private Board board = new Board(7, 8, 2);
    private Board board;

    @GetMapping()
    public Map<String, Object> board() {
        return board.getBoardState();
    }

    @GetMapping("/newgame")
    public Map<String, Object> newGame() {
//        this.board = new Board(row, column, mine);
        return board.getNewGameInfo();
    }

    @GetMapping("/drop")
    public Map<String, Object> getDropDiskInfo(@RequestParam int column) {
//        this.board.dropDisk(column);
        return board.getDropDiskInfo(column);
    }

    @GetMapping("/explode")
    public Map<String, Object> explodeMine(@RequestParam int row, @RequestParam int column) {
//        this.board.explodeMine(row, column);
        return board.getExplodeMineInfo(row,column);
    }

    @GetMapping("/minedetect")
    public Boolean mineDetect(@RequestParam int column) {
        return this.board.mineDetect(column);
    }

    @GetMapping("/randommove")
    public Map<String, Object> randomMove() {
        board.randomMove();
        return board.getBoardState();
    }

    @GetMapping("/setwinner")
    public Map<String, Object> setWinner(@RequestParam TokenState tokenState) {
        this.board.setWinner(tokenState);
        return board.getBoardState();
    }
    @GetMapping("/undoMove")
    public Map<String, Object> undoMove() {
        this.board.undoMve();
        return board.getBoardState();
    }

    @GetMapping("/init")
    public Map<String, Object> init(@RequestParam int row, @RequestParam int column, @RequestParam int mines) {
        this.board = new Board(row, column, mines);
        return board.getBoardState();
    }
}
