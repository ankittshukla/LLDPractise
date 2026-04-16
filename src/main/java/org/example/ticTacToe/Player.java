package org.example.ticTacToe;

public class Player {
    String name;
    boolean currTurn;
    PlayingPiece playingPiece;
    Player(String name, PlayingPiece playingPiece){
        this.name = name;
        this.playingPiece = playingPiece;
    }
}
