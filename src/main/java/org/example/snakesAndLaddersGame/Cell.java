package org.example.snakesAndLaddersGame;

import lombok.Getter;

@Getter
public class Cell {
    int numId;
    int row;
    int col;
    Cell jump;
    public Cell(int numId, Cell jump){
        this.numId = numId;
        this.jump = jump;
    }
}
