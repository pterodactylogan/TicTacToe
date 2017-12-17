package com.pterodactylogan.tictactoe;

/**
 * Created by Logan on 12/17/17.
 */

public class BoardStructure {
    public int BoardSize=3;

    public enum value {X, O, EMPTY}

    value[][] board = new value[BoardSize][BoardSize];

    //initialize board to be empty
    public BoardStructure(int size){
        for (int r = 0 ; r < size ; r++){
            for (int c = 0; c < size ; c++){
                board[r][c] = value.EMPTY;
            }
        }
        board[1][1]=value.X;
        board[0][0]=value.O;
    }

    /**
     * Evaluates the value of a cell.
     * @param r
     * @param c
     * @return
     */
    public value eval(int r, int c){
        return board[r][c];
    }
}
