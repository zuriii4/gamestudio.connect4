package sk.tuke.gamestudio.game.userinterface;

import java.util.Scanner;
import java.util.regex.Pattern;

import sk.tuke.gamestudio.game.core.Board;

public class BoardUI {

    private static final Pattern BOARD_INPUT = Pattern.compile("([4-9])([6-9])([0-6])") ;
    private Scanner scanner = new Scanner(System.in);

    public BoardUI() {
        printConnect4();
    }
   
    // Make board -------------------------------------
    public Board makeBoard() {
        int row;
        int column;
        int maxMines;
        System.out.println("Welcome to Connect4 Game");
        System.out.println("Please enter the size of the game board (rows)(columns)(max number of mines) = (4-9)(6-9)(0-6)");
        var line = scanner.nextLine();
        var matcher = BOARD_INPUT.matcher(line);

        while (!matcher.matches()) {
            System.out.println("Invalid input. Please try again.");
            line = scanner.nextLine();
            matcher = BOARD_INPUT.matcher(line);
        }

        row = Integer.parseInt(matcher.group(1));
        column = Integer.parseInt(matcher.group(2));
        maxMines = Integer.parseInt(matcher.group(3));

        return new Board(row,column,maxMines);

    }

    private void printConnect4() {
        System.out.println("  ____                            _     _  _   ");
        System.out.println(" / ___|___  _ __  _ __   ___  ___| |_  | || |  ");
        System.out.println("| |   / _ \\| '_ \\| '_ \\ / _ \\/ __| __| | || |_ ");
        System.out.println("| |__| (_) | | | | | | |  __/ (__| |_  |__   _|");
        System.out.println(" \\____\\___/|_| |_|_| |_|\\___|\\___|\\__|    |_|  ");

        System.out.println();
    }
}
