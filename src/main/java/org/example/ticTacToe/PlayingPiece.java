package org.example.ticTacToe;

public class PlayingPiece {
    private final PieceType pieceType;

    protected PlayingPiece(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}
