package org.example.ticTacToe;

public class Board {
    PlayingPiece[][] playingBoard;
    int capacity;
    int size;
    Board(int row, int col){
        playingBoard = new PlayingPiece[row][col];
        capacity = row * col;
    }
    public boolean addPiece(int r, int c, PlayingPiece piece){
        if(isBoardFull())
            return false;
        playingBoard[r][c] = piece;
        size++;
        return true;
    }
    public boolean isBoardFull(){
        if(size == capacity)
            return true;
        return false;
    }
}
