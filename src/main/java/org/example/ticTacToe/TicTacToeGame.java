package org.example.ticTacToe;

import java.util.List;
import java.util.Scanner;

public class TicTacToeGame {
    private final Scanner scanner;
    private List<Player> players;
    private Board board;
    private int currentPlayerIndex;
    private GameStatus gameStatus;
    private Player winner;

    public TicTacToeGame() {
        this.scanner = new Scanner(System.in);
    }

    public void start(int row, int col) {
        board = new Board(row, col);
        players = List.of(
                new Player("Ank", new CrossPiece()),
                new Player("Shu", new KnotPiece())
        );
        currentPlayerIndex = 0;
        gameStatus = GameStatus.IN_PROGRESS;
        winner = null;
    }

    public void playGame() {
        while (gameStatus == GameStatus.IN_PROGRESS) {
            Player currentPlayer = players.get(currentPlayerIndex);
            printBoard();
            System.out.println(currentPlayer.getName() + "'s turn");
            int row = readCoordinate("Enter row: ");
            int col = readCoordinate("Enter col: ");
            if (!makeMove(currentPlayer, row, col)) {
                System.out.println("Invalid move");
                continue;
            }
        }
        printBoard();
        announceResult();
    }

    private int readCoordinate(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            }
            System.out.println("Please enter a number.");
            scanner.next();
        }
    }

    private boolean makeMove(Player currentPlayer, int row, int col) {
        if (!board.placePiece(row, col, currentPlayer.getPlayingPiece())) {
            return false;
        }

        if (board.hasWinner(row, col, currentPlayer.getPlayingPiece())) {
            winner = currentPlayer;
            gameStatus = GameStatus.WINNER;
            return true;
        }

        if (board.isBoardFull()) {
            gameStatus = GameStatus.DRAW;
            return true;
        }

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return true;
    }

    private void printBoard() {
        System.out.println(board.render());
    }

    private void announceResult() {
        if (gameStatus == GameStatus.WINNER) {
            System.out.println("Winner is " + winner.getName());
        } else if (gameStatus == GameStatus.DRAW) {
            System.out.println("Game ended in a draw");
        }
    }
}
