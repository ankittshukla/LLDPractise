package org.example.snakesAndLaddersGame;

public class Board {
    Cell[][] playingBoard;
    public Board(){
        playingBoard = new Cell[10][10];
    }
    public void initializeBoard(){
        boolean leftToRight = true;
        int cellNumber = 1;
        for(int i = 9; i >= 0; i--){
            for(int j = 0; j < 10; j++){
                int col = leftToRight ? j : 9 - j;
                Cell cell = new Cell(cellNumber++, null);
                cell.row = i;
                cell.col = col;
                playingBoard[i][col] = cell;
            }
            leftToRight = !leftToRight;
        }
    }
    public void addSnake(int rowSt, int colSt, int rowEn, int colEn){
        playingBoard[rowSt][colSt].jump = playingBoard[rowEn][colEn];
    }
    public void addLadder(int rowSt, int colSt, int rowEn, int colEn){
        playingBoard[rowSt][colSt].jump = playingBoard[rowEn][colEn];
    }
    public Cell traverseBoard(Cell cell, int steps){
        int targetCellNumber = cell.getNumId() + steps;
        if(targetCellNumber > 100)
            return cell;

        Cell targetCell = getCellByNumber(targetCellNumber);
        if(targetCell.jump != null) {
            if(targetCell.jump.numId > targetCell.numId)
                System.out.println("*** Found a ladder ***");
            else
                System.out.println("??? bitten by a snake ???");
            return targetCell.jump;
        }
        return targetCell;
    }

    public Cell getCellByNumber(int cellNumber){
        int index = cellNumber - 1;
        int rowFromBottom = index / 10;
        int row = 9 - rowFromBottom;
        int colFromLeft = index % 10;
        int col = rowFromBottom % 2 == 0 ? colFromLeft : 9 - colFromLeft;
        return playingBoard[row][col];
    }
    public boolean isWinnerCell(Cell cell){
        if(cell.numId == 100)
            return true;
        return false;
    }
}
