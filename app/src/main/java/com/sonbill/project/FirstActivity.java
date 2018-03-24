package com.sonbill.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class FirstActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

    }
    public void gameStart(View view)
    {
        Intent it = new Intent(this,InGameActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(it);
    }
}
