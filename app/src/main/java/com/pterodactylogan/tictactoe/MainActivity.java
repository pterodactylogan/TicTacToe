package com.pterodactylogan.tictactoe;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BoardView game = (BoardView) findViewById(R.id.board);
        Button resetButton = (Button) findViewById(R.id.NewGame);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset(game);
            }
        });

        final Button front = (Button) findViewById(R.id.front);
        Button side = (Button) findViewById(R.id.side);
        Button top = (Button) findViewById(R.id.top);
        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orient(game, BoardView.ot.FRONT);
            }
        });
        side.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orient(game, BoardView.ot.SIDE);
            }
        });
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orient(game, BoardView.ot.TOP);
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
