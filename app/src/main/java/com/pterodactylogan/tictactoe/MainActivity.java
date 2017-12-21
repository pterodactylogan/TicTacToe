package com.pterodactylogan.tictactoe;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BoardView game = (BoardView) findViewById(R.id.board);

        //button click listeners
        Button resetButton = (Button) findViewById(R.id.NewGame);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset(game);
            }
        });

        final Button front = (Button) findViewById(R.id.front);
        final Button side = (Button) findViewById(R.id.side);
        final Button top = (Button) findViewById(R.id.top);
        top.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orient(game, BoardView.ot.FRONT);
                front.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                top.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                side.setBackgroundColor(getResources().getColor(R.color.buttonColor));
            }
        });
        side.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orient(game, BoardView.ot.SIDE);
                front.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                top.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                side.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orient(game, BoardView.ot.TOP);
                front.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                top.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                side.setBackgroundColor(getResources().getColor(R.color.buttonColor));
            }
        });

        //status listener
        game.setStatusListener(new BoardView.GameStatusListener() {
            @Override
            public void updateText(String text) {
                TextView message = (TextView) findViewById(R.id.message);
                message.setText(text);
            }
        });
    }

    public void reset(BoardView v){
        v.reset();
    }

    public void orient(BoardView v, BoardView.ot orientation){
        v.orient(orientation);
    }
}
