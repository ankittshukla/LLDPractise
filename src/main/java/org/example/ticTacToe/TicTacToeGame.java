package org.example.ticTacToe;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class TicTacToeGame {
    Queue<Player> turnQ;
    Board board;

    public void initializeGame(){
        turnQ = new LinkedList<>();
        int row = 3;
        int col = 3;
        board = new Board(row, col);
        PlayingPiece crossPiece = new CrossPiece();
        Player p1 = new Player("Ank", crossPiece);
        PlayingPiece knotPiece = new KnotPiece();
        Player p2 = new Player("Shu", knotPiece);
        turnQ.offer(p1);
        turnQ.offer(p2);
    }
    public void playGame(){
        while(!turnQ.isEmpty() && !board.isBoardFull()){
            Player currP = turnQ.poll();
            System.out.println(currP.name + "'s turn");
            Scanner sc = new Scanner(System.in);
            int row = Integer.parseInt(sc.nextLine());
            int col = Integer.parseInt(sc.nextLine());
            board.addPiece(row, col, currP.playingPiece);
            if(board.isBoardFull())
                System.out.println("board is full");
            if(checkWinningCondition(row, col, currP.playingPiece)){
                System.out.println("Winner is " + currP.name);
                break;
            }
            else{
                turnQ.offer(currP);
            }
        }
    }
    public boolean checkWinningCondition(int r, int c, PlayingPiece piece){
        int rows = board.playingBoard.length;
        int cols = board.playingBoard[0].length;
        boolean toReturn = true;
        for(int i = 0; i < cols; i++){
            if(board.playingBoard[r][i] != piece)
                toReturn = false;
        }
        if(toReturn)
            return true;
        toReturn = true;
        for(int i = 0; i < rows; i++){
            if(board.playingBoard[i][c] != piece)
                toReturn = false;
        }
        if(toReturn)
            return true;
        if(r == c) {
            toReturn = true;
            for (int i = 0; i < rows; i++) {
                if (board.playingBoard[i][i] != piece)
                    toReturn = false;
            }
            if(toReturn)
                return true;
        }
        if(r + c == rows - 1){
            toReturn = true;
            for(int i = 0; i < cols; i++){
                if(board.playingBoard[i][cols - i - 1] != piece)
                    toReturn = false;
            }
            if(toReturn)
                return true;
        }
        return false;
    }
}
