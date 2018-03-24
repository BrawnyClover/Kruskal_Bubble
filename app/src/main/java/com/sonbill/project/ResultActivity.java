package com.sonbill.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent it = getIntent();
        int score = it.getIntExtra("score",0);
        TextView scoreResult = (TextView)findViewById(R.id.scoreResult);
        scoreResult.setText(String.valueOf(score));

        int time = it.getIntExtra("time",0);
        TextView timePassedText = (TextView)findViewById(R.id.timePassedText);
        timePassedText.setText(String.valueOf(time));
    }
    public void replayGame(View view){
        Intent it = new Intent(this,InGameActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
    }
    public void exitGame(View view){
        moveTaskToBack(true);
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
