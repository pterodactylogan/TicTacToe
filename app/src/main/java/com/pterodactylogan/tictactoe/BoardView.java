package com.pterodactylogan.tictactoe;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
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
        setMeasuredDimension(width, width);
    }

    private Paint mLinePaint;

    //custom constructor helper
    private void init(){
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.parseColor("#161616"));
        mLinePaint.setStrokeWidth(4);
    }

    public void onDraw(Canvas canvas){
        int cellSize = getWidth() / 3;
        mLinePaint.setStrokeWidth(cellSize/15.0f);
        for (int i = 1; i < 3; i++) {
            canvas.drawLine(cellSize * i, 0, cellSize * i, getHeight(), mLinePaint);
            canvas.drawLine(0, cellSize * i, getWidth(), cellSize * i, mLinePaint);
        }
    }


}
