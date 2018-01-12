package com.pterodactylogan.tictactoe;

import android.util.Log;
import android.webkit.ConsoleMessage;

import java.io.Console;
import java.util.Random;

/**
 * Created by Logan on 12/17/17.
 */

public class BoardStructure {

    public int BoardSize=3;
    public enum value {X, O, EMPTY}
    private value [][][] board = new value[3][BoardSize][BoardSize];
    private boolean[][][] winCombo = new boolean[3][BoardSize][BoardSize];
    Random rand = new Random();

    //initialize board to be empty, with no winning combo
    public BoardStructure(int size){
        for(int z=0; z<3; z++){
            for (int x = 0 ; x < size ; x++){
                for (int y = 0; y < size ; y++){
                    board[z][x][y]=value.EMPTY;
                    winCombo[z][x][y]=false;
                }
            }
        }
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

    public boolean partOfWin(int z, int x, int y){
        return winCombo[z][x][y];
    }

    //legal move?
    public boolean isLegal(int z, int x, int y){
        return board[z][x][y]==value.EMPTY;
    }

    public void setCell(int z, int x, int y, value val){
        if(isLegal(z,x,y)) board[z][x][y]=val;
    }

    //returns the winner, given the cell just played in
    // or EMPTY if no winner
    public value winner(int z, int x, int y){
        //non-diagonals
        if(board[z][x][0]==board[z][x][1] && board[z][x][0]==board[z][x][2]){
            winCombo[z][x][0]=true;
            winCombo[z][x][1]=true;
            winCombo[z][x][2]=true;
            return board[z][x][0];
        }
        if(board[0][x][y]==board[1][x][y] && board[0][x][y]==board[2][x][y]){
            winCombo[0][x][y]=true;
            winCombo[1][x][y]=true;
            winCombo[2][x][y]=true;
            return board[0][x][y];
        }
        if(board[z][0][y]==board[z][1][y] && board[z][0][y]==board[z][2][y]){
            winCombo[z][0][y]=true;
            winCombo[z][1][y]=true;
            winCombo[z][2][y]=true;
            return board[z][0][y];
        }
        //DIAGONALS: hold z constant
        if(x==y){
            if(board[z][0][0]==board[z][1][1] && board[z][0][0]==board[z][2][2]){
                if(board[z][0][0]!=value.EMPTY) {
                    winCombo[z][0][0] = true;
                    winCombo[z][1][1] = true;
                    winCombo[z][2][2] = true;
                    return board[z][0][0];
                }
            }
        }
        if((x+y)==2){
            if(board[z][0][2]==board[z][1][1]&&board[z][1][1]==board[z][2][0]){
                if(board[z][1][1]!=value.EMPTY) {
                    winCombo[z][0][2] = true;
                    winCombo[z][1][1] = true;
                    winCombo[z][2][0] = true;
                    return board[z][1][1];
                }
            }
        }
        //x constant
        if(z==y){
            if(board[0][x][0]==board[1][x][1] && board[0][x][0]==board[2][x][2]){
                if(board[0][x][0]!=value.EMPTY) {
                    winCombo[0][x][0] = true;
                    winCombo[1][x][1] = true;
                    winCombo[2][x][2] = true;
                    return board[0][x][0];
                }
            }
        }
        if((z+y)==2){
            if(board[0][x][2]==board[1][x][1]&&board[1][x][1]==board[2][x][0]){
                if(board[1][x][1]!=value.EMPTY) {
                    winCombo[0][x][2] = true;
                    winCombo[1][x][1] = true;
                    winCombo[2][x][0] = true;
                    return board[1][x][1];
                }
            }
        }
        //y
        if(x==z){
            if(board[0][0][y]==board[1][1][y] && board[0][0][y]==board[2][2][y]){
                if(board[0][0][y]!=value.EMPTY) {
                    winCombo[0][0][y] = true;
                    winCombo[1][1][y] = true;
                    winCombo[2][2][y] = true;
                    return board[0][0][y];
                }
            }
        }
        if((x+y)==2){
            if(board[2][0][y]==board[1][1][y]&&board[1][1][y]==board[0][2][y]){
                if(board[1][1][y]!=value.EMPTY) {
                    winCombo[2][0][y] = true;
                    winCombo[1][1][y] = true;
                    winCombo[0][2][y] = true;
                    return board[1][1][y];
                }
            }
        }
        //4 multi-layer diagonals
        value center=board[1][1][1];
        if(center==value.EMPTY) return value.EMPTY;
        if(center==board[0][0][0] && center==board[2][2][2]){
            winCombo[1][1][1]=true;
            winCombo[0][0][0]=true;
            winCombo[2][2][2]=true;
            return center;
        }
        if(center==board[0][2][0] && center==board[2][0][2]){
            winCombo[1][1][1]=true;
            winCombo[0][2][0]=true;
            winCombo[2][0][2]=true;
            return center;
        }
        if(center==board[0][0][2] && center==board[2][2][0]){
            winCombo[1][1][1]=true;
            winCombo[0][0][2]=true;
            winCombo[2][2][0]=true;
            return center;
        }
        if(center==board[0][2][2] && center==board[2][0][0]){
            winCombo[1][1][1]=true;
            winCombo[0][2][2]=true;
            winCombo[2][0][0]=true;
            return center;
        }
        return value.EMPTY;
    }

    //get a move for the specified player (ie Xs or Os)
    //finds first available move atm
    public int[] getMove(value player){
        int[] result= new int[3];
        int a = rand.nextInt(3);
        int b = rand.nextInt(3);
        int c = rand.nextInt(3);
        if(isLegal(a,b,c)){
            result[0]=a;
            result[1]=b;
            result[2]=c;
            return result;
        }
        for(int z=0; z<3;z++){
            for(int x=0; x<3; x++){
                for(int y=0; y<3; y++){
                    if(isLegal(z,x,y)){
                        result[0]=z;
                        result[1]=x;
                        result[2]=y;
                        return result;
                    }
                }
            }
        }
        return null;
    }

    public int[] getGoodMove(value player){
        int[] result = new int[3];
        int[] move = new int[3];
        int maxVal=-10;
        for(int z=0; z<3;z++){
            for(int x=0; x<3; x++){
                for(int y=0; y<3; y++){
                    if(isLegal(z,x,y)){
                        move[0]=z;
                        move[1]=x;
                        move[2]=y;
                        int val = boardValue(move, 3,-100,100, player);
                        if(player==value.O) val*=-1;
                        //save the max value and return that move;
                        if(val>maxVal){
                            result=move.clone();
                        }
                    }
                }
            }
        }
        return result;
    }

    //+5 for X (max) win, -5  for O (min) win, 0 for unsure
    private int boardValue(int[]move, int depth, int a, int b, value player){
        value[][][] copy = boardCopy();
        //if someone has won
        value winner = winner(move[0],move[1], move[2]);
        if(winner==value.O) return -5;
        if(winner==value.X) return 5;
        if(depth==0) return 0; //reached depth limit

        if(player==value.X){
            int v = -10;
            for(int z=0; z<3;z++){
                for(int x=0; x<3; x++){
                    for(int y=0; y<3; y++){
                        if(isLegal(z,x,y)){
                            move[0]=z;
                            move[1]=x;
                            move[2]=y;
                            setCell(z,x,y,player);
                            Log.d(z+", "+x+", "+y, ""+copy[z][x][y]);
                            v=Math.max(v, boardValue(move, depth-1, a,b, value.O));
                            a=Math.max(a,v);
                            if(b<=a) break;
                        }
                    }
                }
            }
            board=copy;
            if(v==-10) return 0;
            return v;
        }else{
            int v=10;
            for(int z=0; z<3;z++){
                for(int x=0; x<3; x++){
                    for(int y=0; y<3; y++){
                        if(isLegal(z,x,y)){
                            move[0]=z;
                            move[1]=x;
                            move[2]=y;
                            setCell(z,x,y,player);
                            Log.d(z+", "+x+", "+y, ""+copy[z][x][y]);
                            v=Math.min(v, boardValue(move, depth-1, a,b, value.X));
                            b=Math.min(b,v);
                            if(b<=a) break;
                        }
                    }
                }
            }
            board=copy;
            if(v==10) return 0;
            return v;
        }
    }

    public void reset(){
        for(int z=0; z<3; z++){
            for (int x = 0 ; x < BoardSize ; x++){
                for (int y = 0; y < BoardSize ; y++){
                    board[z][x][y]=value.EMPTY;
                    winCombo[z][x][y]=false;
                }
            }
        }
    }

    public value[][][] boardCopy(){
        value[][][] copy = new value[3][BoardSize][BoardSize];
        for(int i=0; i<3; i++){
            for(int j=0; j<BoardSize; j++){
                for(int k=0; k<BoardSize; k++){
                    copy[i][j][k] = board[i][j][k];
                }
            }
        }
        return copy;
    }
}
