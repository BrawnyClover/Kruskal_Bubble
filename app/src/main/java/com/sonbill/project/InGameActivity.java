package com.sonbill.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class InGameActivity extends AppCompatActivity {


    TextView scoreView;
    TextView timeView;
    LinearLayout outerLinear;
    TimerThread timer;
    int score = 0;
    CircleView cv;
    int questionNumber= 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        CircleView.InitCircleViewContext(this);

        outerLinear = (LinearLayout)findViewById(R.id.outerLinear);
        scoreView = (TextView)findViewById(R.id.scoreView);
        timeView = (TextView)findViewById(R.id.timeView);

        cv = new CircleView(this,score,questionNumber++);
        outerLinear.addView(cv);
        System.out.println("wow");

        timer = new TimerThread();
        timer.setTv(timeView);

        timer.flag = true;
        timer.start();
    }
    public void reLoad(View view){
        score = cv.score;
        if(cv.isSolved == false){
            Toast.makeText(this, "Please Solve this Question", Toast.LENGTH_SHORT).show();
        }
        else {
            if (questionNumber == 6) {
                timer.flag = false;
                Intent it = new Intent(this, ResultActivity.class);
                it.putExtra("score", score);
                it.putExtra("time", timer.time);
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(it);
            } else {
                outerLinear.removeView(cv);
                scoreView.setText(String.valueOf(score));
                cv = new CircleView(this, score, questionNumber++);
                outerLinear.addView(cv);
            }
        }
    }
}
