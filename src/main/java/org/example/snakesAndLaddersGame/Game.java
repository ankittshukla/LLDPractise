package org.example.snakesAndLaddersGame;

import org.example.ticTacToe.GameStatus;

import java.util.ArrayList;
import java.util.List;

public class Game {
    Board board;
    List<Player> playerList;
    SLGameStatus gameStatus;
    DiceRolling diceRolling;
    public Game(){
        board = new Board();
        playerList = new ArrayList<>();
    }
    public void initializeGame(){
        board.initializeBoard();
        diceRolling = new DiceRolling(2);
        Player p1 = new Player("Ank", 1);
        Player p2 = new Player("Shu", 2);
        p1.setCurrPos(board.getCellByNumber(1));
        p2.setCurrPos(board.getCellByNumber(1));
        gameStatus = SLGameStatus.NOT_STARTED;
        playerList.add(p1);
        playerList.add(p2);
        board.addSnake(0,1,9,8);
        board.addSnake(4,6,2,2);
        board.addLadder(1,4,6,3);
        board.addLadder(4,9,8,3);
    }
    public void playGame(){
        int c = 0;
        Player winner = null;
        while(gameStatus != SLGameStatus.HAS_WINNER){
            Player currPlayer = playerList.get(c);
            Cell currPosCell = currPlayer.getCurrPos();
            System.out.print("current player " + currPlayer.name);
            System.out.println(" at position " + currPosCell.numId);
            int diceVal = diceRolling.rollDice();
            System.out.println("dice roll value " + diceVal);
            Cell newPosCell = board.traverseBoard(currPosCell, diceVal);
            if(board.isWinnerCell(newPosCell)){
                gameStatus = SLGameStatus.HAS_WINNER;
                winner = currPlayer;
            }
            currPlayer.setCurrPos(newPosCell);
            c = (c + 1) % playerList.size();
        }
        System.out.println("Winner is " + winner.name);
    }
}
