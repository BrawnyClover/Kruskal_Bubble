package com.sonbill.project;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by sonbill on 2017-06-22.
 */

public class Circle{
    Paint pen = new Paint();
    Paint penTouchCiricle = new Paint();
    float cx;
    float cy;
    float radius;
    String name;
    int touchCircleId;
    boolean isTouched = false;

    public void setPen(){
        pen.setColor(Color.RED);
        pen.setStyle(Paint.Style.FILL);
        pen.setStrokeWidth(10);
        pen.setStyle(Paint.Style.FILL_AND_STROKE);
        penTouchCiricle.setColor(Color.WHITE);
    }
    public Circle(float cx, float cy, float radius, String name){
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
        this.name = name;
        setPen();
    }
    public Circle(float cx, float cy, float radius) {
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
        setPen();
    }
}
