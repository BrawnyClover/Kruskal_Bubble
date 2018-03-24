package com.sonbill.project;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.widget.TextView;

/**
 * Created by IMAC_Android on 2017-06-20.
 */
public class TimerThread extends Thread
{
    int time = -1;
    boolean flag = false;
    TextView textView;

    public void setTv(TextView textView){
        this.textView = textView;
    }

    @Override
    public void run() {
        while(flag){
            handler.sendEmptyMessage(0);
            time++;
            SystemClock.sleep(1000);
        }
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0){
                textView.setText(String.valueOf(time));
            }
        }
    };
}
//public class TimerThread extends AsyncTask {
//    int time = -1;
//    boolean flag = false;
//    TextView textView;
//    public void setTv(TextView textView){
//        this.textView =  textView;
//    }
//    @Override
//    protected Object doInBackground(Object[] params) {
//        while(flag){
//            ++time;
//            publishProgress();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    @Override
//    protected void onProgressUpdate(Object[] values) {
//        textView.setText(String.valueOf(time));
//    }
//}
