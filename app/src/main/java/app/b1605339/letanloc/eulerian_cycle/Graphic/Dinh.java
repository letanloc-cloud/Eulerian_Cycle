/*
 * Created by Lê Tấn Lộc on 13:05 01/03/2019
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 13:05 01/03/2019
 */

package app.b1605339.letanloc.eulerian_cycle.Graphic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class Dinh extends View {
    private Paint paint = new Paint();


    private int ve = 0; //0: không làm gì, 1: vẽ đỉnh, 2: vẽ cung, 3: kéo thả đỉnh
    private float x;
    private float y;

    ArrayList<Float> listX = new ArrayList<>();
    ArrayList<Float> listY = new ArrayList<>();

    ArrayList<Float> listStartX = new ArrayList<>();
    ArrayList<Float> listStartY = new ArrayList<>();
    ArrayList<Float> listStopX = new ArrayList<>();
    ArrayList<Float> listStopY = new ArrayList<>();


    public Dinh(Context context) {
        super(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int widthCanvas = canvas.getWidth();
        int heightCanvas = canvas.getHeight();

        paint.setColor(Color.YELLOW);
        canvas.drawCircle(100, 100, 10, paint);

        paint.setColor(Color.BLUE);
        canvas.drawLine(200, 200, 400, 400, paint);

        canvas.drawCircle(x, y, 20, paint);


        paint.setColor(Color.BLUE);
        for (int i = 0; i < listX.size(); i++) {
            canvas.drawCircle((float)listX.get(i), listY.get(i), 20, paint);
        }

        for (int i = 0; i < listStartX.size(); i++) {
            canvas.drawLine(listStartX.get(i), listStartY.get(i), listStopX.get(i), listStopY.get(i), paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                listX.add(x);
                listY.add(y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                x = event.getX();
                y = event.getY();
                break;
        }
        return true;
    }
}
