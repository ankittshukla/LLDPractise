package org.example.ticTacToe;

public class Board {
    private final PlayingPiece[][] playingBoard;
    private final int capacity;
    private int size;

    public Board(int row, int col) {
        if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException("Board dimensions must be positive");
        }
        if (row != col) {
            throw new IllegalArgumentException("TicTacToe requires a square board");
        }
        playingBoard = new PlayingPiece[row][col];
        capacity = row * col;
    }

    public boolean placePiece(int r, int c, PlayingPiece piece) {
        if (!isValidMove(r, c) || isBoardFull()) {
            return false;
        }
        playingBoard[r][c] = piece;
        size++;
        return true;
    }

    public boolean isBoardFull() {
        return size == capacity;
    }

    public boolean isValidMove(int row, int col) {
        if (row < 0 || row >= playingBoard.length || col < 0 || col >= playingBoard[0].length) {
            return false;
        }
        if (playingBoard[row][col] != null) {
            return false;
        }
        return true;
    }

    public boolean hasWinner(int r, int c, PlayingPiece piece) {
        int dimensions = playingBoard.length;
        boolean toReturn = true;
        for (int i = 0; i < dimensions; i++) {
            if (playingBoard[r][i] != piece) {
                toReturn = false;
            }
        }
        if (toReturn) {
            return true;
        }

        toReturn = true;
        for (int i = 0; i < dimensions; i++) {
            if (playingBoard[i][c] != piece) {
                toReturn = false;
            }
        }
        if (toReturn) {
            return true;
        }

        if (r == c) {
            toReturn = true;
            for (int i = 0; i < dimensions; i++) {
                if (playingBoard[i][i] != piece) {
                    toReturn = false;
                }
            }
            if (toReturn) {
                return true;
            }
        }

        if (r + c == dimensions - 1) {
            toReturn = true;
            for (int i = 0; i < dimensions; i++) {
                if (playingBoard[i][dimensions - i - 1] != piece) {
                    toReturn = false;
                }
            }
            if (toReturn) {
                return true;
            }
        }
        return false;
    }

    public String render() {
        StringBuilder boardView = new StringBuilder();
        for (int row = 0; row < playingBoard.length; row++) {
            for (int col = 0; col < playingBoard[row].length; col++) {
                PlayingPiece piece = playingBoard[row][col];
                String symbol = piece == null ? " " : piece.getPieceType().name();
                boardView.append(symbol.charAt(0));
                if (col < playingBoard[row].length - 1) {
                    boardView.append(" | ");
                }
            }
            if (row < playingBoard.length - 1) {
                boardView.append(System.lineSeparator());
                boardView.append("-".repeat(Math.max(0, playingBoard[row].length * 4 - 1)));
                boardView.append(System.lineSeparator());
            }
        }
        return boardView.toString();
    }
}
