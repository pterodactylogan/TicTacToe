package com.pterodactylogan.tictactoe;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Logan on 12/17/17.
 */

public class BoardView extends View {

    private boolean comp=false; //prevents player from doing anything when computer is going
    BoardStructure b = new BoardStructure(3); //the gameboard
    BoardStructure.value player = BoardStructure.value.X; //user's symbol
    BoardStructure.value computer = BoardStructure.value.O;

    //orientation of board
    public enum ot {TOP, FRONT, SIDE}
    public ot orientation=ot.TOP;
    public void orient(ot newOrientation){
        orientation=newOrientation;
        postInvalidate();
        System.out.println(orientation);
    }

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

    //set view dimensions
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
        orientation=ot.TOP;
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.parseColor("#161616"));
        mLinePaint.setStrokeWidth(4);

        mXPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mXPaint.setColor(Color.parseColor("#ff0000"));
        mXPaint.setStrokeWidth(4);

        mOPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mOPaint.setColor(Color.parseColor("#0000ff"));
        mOPaint.setStrokeWidth(4);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //Log.d("wow", "touch event!");
                if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    //Log.d("event", "down");
                    return true;
                } else if (motionEvent.getActionMasked() == MotionEvent.ACTION_UP) {
                    int boardSize = getWidth() / 3;
                    int cellSize = boardSize/3;
                    int l = (int) (motionEvent.getX()/boardSize); //layer
                    int c = (int)((motionEvent.getX()%boardSize)/cellSize); //row
                    int r = (int) (motionEvent.getY()/cellSize); //column
                    Log.d("coords", l +", "+c+", "+r);
                    if(!comp) playerTurn(l,c,r);
                    return true;
                }
                return false;
            }
        });
    }

    private void playerTurn(int l,int c,int r){
        b.setCell(l,c,r, player);
        BoardStructure.value win = b.winner(l,c,r);
        if(win!=BoardStructure.value.EMPTY){
            //game over
            postInvalidate();
            comp=true;
            return;
        }
        postInvalidate();
        comp=true;
        computerTurn();
        return;
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
                    if(orientation==ot.TOP) cell = b.eval(l, c, r);
                    else if(orientation==ot.SIDE) cell=b.eval(r,2-l,2-c);
                    else cell=b.eval(r,c,2-l);

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

    private final Handler handler = new Handler();

    final Runnable compTurn = new Runnable() {
        @Override
        public void run() {
            comp=true;
            //Log.d("comp turn","...");
            int[] move = b.getMove(computer);
            if(move==null){
                //game over
                return;
            }
            b.setCell(move[0],move[1],move[2],computer);
            BoardStructure.value win = b.winner(move[0],move[1],move[2]);
            if(win!= BoardStructure.value.EMPTY){
                //game over
                postInvalidate();
                return;
            }
            postInvalidate();
            comp=false;
        }
    };

    private void computerTurn(){
        handler.postDelayed(compTurn,1000);
    }

    public void reset(){
        b.reset();
        postInvalidate();
        comp=false;
    }
}
