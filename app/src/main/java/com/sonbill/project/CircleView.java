package com.sonbill.project;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by sonbill on 2017-06-22.
 */

public class CircleView extends View {

    static Context mMain;

    public static void InitCircleViewContext(Context main) {
        mMain = main;
    }

    int nMode = 0;
    float x1, y1, x2, y2;
    Path path = null;
    float radius = 350;
    int amount;
    boolean isFirst = true;
    int id = 0;
    int result = 0;
    int answer;
    int score;
    int qNum;
    boolean checkIsTouched[] = {false, false, false, false, false, false, false, false, false, false};
    TextView textResultView = (TextView) ((Activity) mMain).findViewById(R.id.textResultView);
    ScrollView scrollView = (ScrollView) ((Activity) mMain).findViewById(R.id.scrollView);
    boolean isSolved = false;
    ArrayList<Circle> arCircle = new ArrayList<>(

    );
    ArrayList<Line> arLine = new ArrayList<>(

    );
    ArrayList<Circle> arTouchCircle = new ArrayList<>(

    );

    public CircleView(Context context, int score, int questionNumber) {
        super(context);
        this.score = score;
        this.qNum = questionNumber;
    }


    public static double randomRange(int n1, int n2) {
        return (Math.random() * (n2 - n1 + 1)) + n1;
    }

    int xar[][] = {{180, 700, 700, 920, 150, 430}, {670, 350, 160, 940, 800, 430}};
    int yar[][] = {{250, 1050, 150, 550, 880, 450}, {140, 1010, 350, 510, 1060, 450}};

    public void setShape(int amount) {
        int piv = (int) randomRange(0, 1);
        for (int i = 0; i < amount; ++i) {
//            double xd = randomRange(3,5);
//            double yd = randomRange(3,5);
//            int x = (int)Math.pow(xd,4);
//            int y = (int)Math.pow(yd,4);
            if (amount == 6) piv = 1;
            int x = xar[piv][i];
            int y = yar[piv][i];
            char ch = (char) ('A' + i);
            Circle circle = new Circle(x, y, radius / amount, String.valueOf(ch));
            arCircle.add(circle);
        }
        if (amount == 6) {
            createEdge(0, 3);
            createEdge(0, 2);
            createEdge(3, 5);
            createEdge(2, 1);
            createEdge(1, 4);
            createEdge(4, 5);
            createEdge(1, 5);
        } else {
            for (int i = 0; i < amount; ++i) {
                for (int j = i + 1; j < amount; ++j) {
                    createEdge(i, j);
                }
            }
        }
    }

    public void createEdge(int st, int ed) {
        Circle stc = arCircle.get(st);
        Circle edc = arCircle.get(ed);
        Line line = new Line(stc.cx, stc.cy, edc.cx, edc.cy, (int) randomRange(1, 20));
        Circle touchCircle = new Circle((stc.cx + edc.cx) / 2, (stc.cy + edc.cy) / 2, 300 / amount);

        line.startCircleIndex = st;
        line.endCircleIndex = ed;
        line.lineId = id;

        touchCircle.touchCircleId = id++;

        arLine.add(line);
        arTouchCircle.add(touchCircle);
    }

    public void resetView() {
        arCircle.clear();
        arLine.clear();
        arTouchCircle.clear();
        amount = (int) randomRange(3, 6);
        try {
            setShape(amount);
        } catch (Exception e) {
            setShape(4);
        }
        MST mst = new MST(arLine, amount);
        answer = mst.getMst();
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    protected void onDraw(Canvas canvas) {
        if (isFirst) {
            resetView();
            isFirst = false;
        }
        Paint penText = new Paint();
        penText.setColor(Color.BLACK);
        penText.setTextSize(350 / amount);

        int addValue = 0;

        for (int i = 0; i < arLine.size(); ++i) {
            Line temp = arLine.get(i);
            canvas.drawLine(temp.x1, temp.y1, temp.x2, temp.y2, temp.pen);
            System.out.println(temp.x1 + " " + temp.y1 + " " + temp.x2 + " " + temp.y2);

            Circle tc = arTouchCircle.get(i);
            canvas.drawCircle(tc.cx, tc.cy, tc.radius, tc.penTouchCiricle);

            if (temp.value >= 10) addValue = -48;
            else addValue = -35;
            canvas.drawText(String.valueOf(temp.value), ((temp.x1 + temp.x2) / 2) + addValue, ((temp.y1 + temp.y2) / 2) + 30, penText);
        }
        for (int i = 0; i < amount; ++i) {
            Circle temp = arCircle.get(i);
            canvas.drawCircle(temp.cx, temp.cy, radius / amount, temp.pen);
            canvas.drawText(temp.name, temp.cx, temp.cy, penText);
            System.out.println(temp.name + " " + temp.cx + " " + temp.cy);
        }

    }

    public boolean checkPoint(float x, float y, Circle c) {
        if (Math.sqrt(Math.pow((x - c.cx), 2) + Math.pow((y - c.cy), 2)) < c.radius) {
            return true;
        }
        return false;
    }

    public void touchedChangeColor() {
        for (int i = 0; i < arTouchCircle.size(); ++i) {
            if (checkIsTouched[i] == false) {
                for (int j = 0; j < arLine.size(); ++j) {
                    if (arLine.get(j).lineId == arTouchCircle.get(i).touchCircleId) {
                        arTouchCircle.get(i).penTouchCiricle.setColor(Color.WHITE);
                        arCircle.get(arLine.get(j).startCircleIndex).pen.setColor(Color.RED);
                        arCircle.get(arLine.get(j).endCircleIndex).pen.setColor(Color.RED);
                        arLine.get(j).pen.setColor(Color.YELLOW);
                        break;
                    }
                }
            }
        }
        int lineCnt = 0;
        for (int i = 0; i < arTouchCircle.size(); ++i) {
            if (checkIsTouched[i] == true) {
                lineCnt++;
                for (int j = 0; j < arLine.size(); ++j) {
                    if (arLine.get(j).lineId == arTouchCircle.get(i).touchCircleId) {
                        arTouchCircle.get(i).penTouchCiricle.setColor(Color.GREEN);
                        arCircle.get(arLine.get(j).startCircleIndex).pen.setColor(Color.GREEN);
                        arCircle.get(arLine.get(j).endCircleIndex).pen.setColor(Color.GREEN);
                        arLine.get(j).pen.setColor(Color.GREEN);
                        break;
                    }
                }
            }
        }
        invalidate();
        if (lineCnt == amount - 1) {
            scoreing();
            isSolved = true;
        }
    }

    public void scoreing() {
        for (int i = 0; i < arTouchCircle.size(); ++i) {
            if (checkIsTouched[i] == true) {
                result += arLine.get(i).value;
            }
        }
        if (result == answer) {
            textResultView.setText(textResultView.getText() + "\n" + "Question Number " + " " + String.valueOf(qNum) + " Correct");
//            textResultView.setText(textResultView.getText()+"\n"+"Correct");
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.fullScroll(View.FOCUS_DOWN);
                }
            });
            score += 10;
        } else {
            textResultView.setText(textResultView.getText() + "\n" + "Question Number " + " " + String.valueOf(qNum) + " Wrong!");
//            textResultView.setText(textResultView.getText()+"\n"+"Wrong!");
            textResultView.setText(textResultView.getText() + "\n" + "The answer is " + String.valueOf(answer));
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.fullScroll(View.FOCUS_DOWN);
                }
            });
        }
    }

    public boolean onTouchEvent(MotionEvent event) {


        System.out.println("Wow");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                reLaodCanvas();
//                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (isSolved == true) {
                    Toast.makeText(mMain, "Please Go Next", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < arTouchCircle.size(); ++i) {
                        if (checkPoint(event.getX(), event.getY(), arTouchCircle.get(i))) {
                            checkIsTouched[i] = !checkIsTouched[i];
                            touchedChangeColor();
                        }
                    }
                    break;
                }
        }
        return true;
    }
}

