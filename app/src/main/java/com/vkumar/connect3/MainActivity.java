package com.vkumar.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Active Player - Yellow(0) Red (1) Blank = 2
    int activePlayer = 0;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    public void dropIn(View view){
        ImageView counter = (ImageView) view;
        int tagged = Integer.parseInt(counter.getTag().toString()) - 1;

        if (gameState[tagged] == 2){
            gameState[tagged] = activePlayer;
            counter.setTranslationY(-1000f);
            if (activePlayer == 0){
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).setDuration(300);

            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        (gameState[winningPosition[1]] == gameState[winningPosition[2]]) &&
                        gameState[winningPosition[0]] != 2) {

                    TextView winnerMessage = (TextView)findViewById(R.id.textView);
                    String winner = "Red";

                    if (gameState[winningPosition[0]] == 0){
                        winner = "Yellow";
                    }
                    winnerMessage.setText("Player " + winner + " has WON!!!");

                    LinearLayout layout = (LinearLayout)findViewById(R.id.Layout);
                    layout.setVisibility(View.VISIBLE);
                }
            }
        } else {
            Toast.makeText(MainActivity.this,"Block is Already Taken!!!",Toast.LENGTH_LONG).show();
        }
    }
    public void playAgain(View view){
        LinearLayout layout = (LinearLayout)findViewById(R.id.Layout);
        layout.setVisibility(View.INVISIBLE);
        activePlayer = 0;
        for (int i=0;i < gameState.length;i++){
            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);

        for (int i=0; i< gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}