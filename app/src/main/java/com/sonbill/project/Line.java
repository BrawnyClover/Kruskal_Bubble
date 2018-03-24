package com.sonbill.project;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by sonbill on 2017-06-22.
 */

public class Line {
    Paint pen = new Paint();
    float x1, y1;
    float x2, y2;
    int value;
    int startCircleIndex, endCircleIndex;
    int lineId;
    public void setPen()
    {
        pen.setColor(Color.YELLOW);
        pen.setStyle(Paint.Style.STROKE);
        pen.setStrokeWidth(10);
    }
    public Line(float x1, float y1, float x2, float y2, int value){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.value = value;
        setPen();
    }
}
