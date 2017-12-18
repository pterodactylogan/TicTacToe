package com.pterodactylogan.tictactoe;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void checkWin_isCorrect() throws Exception{
        BoardStructure b1 = new BoardStructure(3);
        b1.setCell(0,0,0, BoardStructure.value.X);
        b1.setCell(1,1,1, BoardStructure.value.X);
        b1.setCell(2,2,2, BoardStructure.value.O);
        b1.setCell(0,1,1, BoardStructure.value.X);
        b1.setCell(0,2,2, BoardStructure.value.X);
        assertEquals(BoardStructure.value.X, b1.winner(0,2,2));

        BoardStructure b2 = new BoardStructure(3);
        b2.setCell(0,0,0, BoardStructure.value.X);
        b2.setCell(1,1,1, BoardStructure.value.X);
        b2.setCell(2,2,2, BoardStructure.value.O);
        b2.setCell(0,1,1, BoardStructure.value.X);
        b2.setCell(0,2,2, BoardStructure.value.O);
        b2.setCell(1,2,2, BoardStructure.value.O);
        assertEquals(BoardStructure.value.O, b2.winner(0,2,2));

        BoardStructure b3 = new BoardStructure(3);
        b3.setCell(0,0,0, BoardStructure.value.X);
        b3.setCell(1,1,1, BoardStructure.value.X);
        b3.setCell(2,2,2, BoardStructure.value.X);
        b3.setCell(0,1,1, BoardStructure.value.O);
        b3.setCell(0,2,2, BoardStructure.value.O);
        assertEquals(BoardStructure.value.X, b3.winner(0,2,2));
    }
}