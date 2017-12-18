package com.pterodactylogan.tictactoe;

/**
 * Created by Logan on 12/17/17.
 */

public class BoardStructure {
    public int BoardSize=3;

    public enum value {X, O, EMPTY}

    value [][][] board = new value[3][BoardSize][BoardSize];

    //initialize board to be empty
    public BoardStructure(int size){
        for(int z=0; z<3; z++){
            for (int x = 0 ; x < size ; x++){
                for (int y = 0; y < size ; y++){
                    board[z][x][y]=value.EMPTY;
                }
            }
        }
        board[0][0][0]=value.O;
        board[1][1][0]=value.X;
        board[2][0][0]=value.O;
        board[2][1][0]=value.X;
    }

    /**
     * Evaluates the value of a cell.
     * @param z
     * @param x
     * @param y
     * @return
     */
    public value eval(int z, int x, int y){
        return board[z][x][y];
    }
}
