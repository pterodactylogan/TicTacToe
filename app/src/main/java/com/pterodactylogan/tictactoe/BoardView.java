package com.pterodactylogan.tictactoe;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Logan on 12/17/17.
 */

public class BoardView extends View {

    BoardStructure b = new BoardStructure(3);

    //constructors
    public BoardView(Context context) {
        super(context);
        init();
    }

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width/3);
    }

    private Paint mLinePaint;
    private Paint mXPaint;
    private Paint mOPaint;

    //custom constructor helper
    private void init(){
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.parseColor("#161616"));
        mLinePaint.setStrokeWidth(4);

        mXPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mXPaint.setColor(Color.parseColor("#ff0000"));
        mXPaint.setStrokeWidth(4);

        mOPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mOPaint.setColor(Color.parseColor("#0000ff"));
        mOPaint.setStrokeWidth(4);

        //b.setCell(0,0,0, BoardStructure.value.X);
        b.winner(0,0,0);
    }

    public void onDraw(Canvas canvas){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float ratio = metrics.densityDpi/160f;
        float pad = 2*ratio; //change distance between boards

        int boardSize = getWidth()/3;
        int cellSize = boardSize / 3;
        mLinePaint.setStrokeWidth(cellSize/15.0f);
        for(int layer=0; layer<3; layer++){
            int inc=boardSize*layer;
            for (int i = 1; i < 3; i++) {
                canvas.drawLine(cellSize * i+inc, 0, cellSize * i+inc, getHeight(), mLinePaint);
                canvas.drawLine(0+inc+pad, cellSize * i, boardSize+inc-pad, cellSize * i, mLinePaint);
            }
        }

        BoardStructure.value cell;

        for(int l=0; l<b.BoardSize; l++){
            int inc=boardSize*l;
            for (int r = 0; r < b.BoardSize; r++) {
                for (int c = 0; c < b.BoardSize; c++) {
                    cell = b.eval(l, r, c);

                    if (cell == BoardStructure.value.X) {
                        mXPaint.setStrokeWidth(cellSize/8.0f);
                        canvas.drawLine(c*cellSize+cellSize/8+inc, r*cellSize+cellSize/8, c*cellSize+7*cellSize/8+inc, r*cellSize+7*cellSize/8, mXPaint);
                        canvas.drawLine(c*cellSize+7*cellSize/8+inc, r*cellSize+cellSize/8, c*cellSize+cellSize/8+inc, r*cellSize+7*cellSize/8, mXPaint);
                    } else if (cell == BoardStructure.value.O) {
                        mOPaint.setStyle(Paint.Style.STROKE);
                        mOPaint.setStrokeWidth(cellSize/8.0f);
                        canvas.drawCircle(c*cellSize + cellSize/2+inc, r*cellSize+cellSize/2, 0.30f*cellSize, mOPaint);
                    }
                }
            }
        }
    }


}
