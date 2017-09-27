package com.hemantjoshi.scarnedice;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mComputerScore;
    private TextView mUserScore;
    private Button hold,reset;
    private ImageView mRollDice;
    private static int userScore = 0,
            userCurrentScore = 0,
            computerScore = 0,
            computerCurrentScore = 0;
    private Random random;
    private boolean isClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mComputerScore = (TextView) findViewById(R.id.computerScore);
        mUserScore = (TextView) findViewById(R.id.userScore);
        hold = (Button) findViewById(R.id.hold);
        reset = (Button) findViewById(R.id.reset);
        mRollDice = (ImageView) findViewById(R.id.diceFace);

        mRollDice.setOnClickListener(this);
        hold.setOnClickListener(this);
        reset.setOnClickListener(this);

        random = new Random();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.diceFace){
            userTurn(view);
        }else if(id == R.id.hold){
            computerTurn(view);
        }
    }

    private void userTurn(View view) {
        int id = view.getId();
        int score = random.nextInt(6) + 1;
        //Toast.makeText(this,"The score is " + String.valueOf(score),Toast.LENGTH_SHORT).show();
        if(id != R.id.hold && score != 1){
            isClicked = true;
            mRollDice.setClickable(true);
            hold.setClickable(true);
            userCurrentScore += score;
            mUserScore.setText("Your Score " + String.valueOf(userScore));
            switch (score){
                case 2:
                    mRollDice.setImageResource(R.drawable.dice2);
                    break;
                case 3:
                    mRollDice.setImageResource(R.drawable.dice3);
                    break;
                case 4:
                    mRollDice.setImageResource(R.drawable.dice4);
                    break;
                case 5:
                    mRollDice.setImageResource(R.drawable.dice5);
                    break;
                case 6:
                    mRollDice.setImageResource(R.drawable.dice6);
                    break;
            }
        }else{
            userScore += userCurrentScore;
            userCurrentScore = 0;
            mUserScore.setText("Your Score " + String.valueOf(userScore));
            mRollDice.setClickable(false);
            Toast.makeText(this,"Turn Changed to computer",Toast.LENGTH_SHORT).show();
            computerTurn(view);
        }
    }

    private void computerTurn(final View view) {
        int id = view.getId();
        hold.setClickable(false);
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int score = random.nextInt(6) + 1;
//                while(score != 1){
//                    computerCurrentScore += score;
//                    score = random.nextInt(6) + 1;
//                    try{
//                        Thread.sleep(1000);
//                    }catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
//                }
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        computerScore = computerCurrentScore;
//                        mComputerScore.setText("Computer Score "+ String.valueOf(computerScore));
//                        Toast.makeText(MainActivity.this,"User's Turn",Toast.LENGTH_SHORT).show();
//                        userTurn(view);
//                    }
//                });
//            }
//        });
//        t.start();
        final Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int score = random.nextInt(6) + 1;
                if(score != 1){
                    computerCurrentScore += score;
                }else{
                    computerScore = computerCurrentScore;
                    mComputerScore.setText("Computer Score "+ String.valueOf(computerScore));
                    Toast.makeText(MainActivity.this,"User's Turn",Toast.LENGTH_SHORT).show();
                    View view1 = findViewById(R.id.diceFace);
                    view1.performClick();
                }
                handler.postDelayed(this,500);
            }
        };
        handler.postDelayed(runnable,0);
    }
}
