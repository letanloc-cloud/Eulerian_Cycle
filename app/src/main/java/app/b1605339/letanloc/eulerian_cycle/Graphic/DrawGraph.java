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

public class DrawGraph extends View {
    private Paint paint = new Paint();

    private float x;
    private float y;
    private ArrayList<Float> listX = new ArrayList<>();
    private ArrayList<Float> listY = new ArrayList<>();

    //-1: no choice
    private int chooseVertex = -1;
    private ArrayList<Integer> edgeStart = new ArrayList<>();
    private ArrayList<Integer> edgeEnd = new ArrayList<>();
    private static boolean isChooseVertex = false; //delete

    //-1: no action
    //0: action click
    //1: action move
    //2: action up
    private static int actionTouch = -1;
    private static int timeTouch = 0;


    public DrawGraph(Context context) {
        super(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int widthCanvas = canvas.getWidth();
        int heightCanvas = canvas.getHeight();

        //Draw background
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);

        if (actionTouch == 0) {
            int touchVertex;
            for (touchVertex = 0; touchVertex < listX.size(); touchVertex++) {
                if (Math.sqrt(Math.pow((listX.get(touchVertex) - x), 2) + Math.pow((listY.get(touchVertex) - y), 2)) <= 20) {
                    //delete
                    paint = new Paint();
                    paint.setAntiAlias(true);
                    paint.setColor(Color.BLUE);
                    //((paint.descent() + paint.ascent()) / 2) is the distance from the baseline to the center.
                    canvas.drawText(touchVertex + "", x + 100, y, paint);
                    //delete


                    if (chooseVertex == -1) {
                        //Nếu chưa chọn đỉnh nào
                        chooseVertex = touchVertex; //Đỉnh chọn là đỉnh chạm vào
                        //Nếu chọn đỉnh => đổi màu
                        //Vẽ các cung
                        //Dùng graph fix lại
                        //Đổi màu tại đỉnh click vào
                    } else {
                        //Nếu đã chọn 1 đỉnh
                        //Nên dùng graph để fix lại
                        //Nếu chọn đỉnh khác đỉnh đã chọn => thêm cung
                        if (chooseVertex != touchVertex) {
                            edgeStart.add(edgeStart.size(), chooseVertex); //Thêm vị trí bắt đầu cung
                            edgeEnd.add(edgeEnd.size(), touchVertex); //Kết thúc bắt đầu cung
                            isChooseVertex = false; //Chưa chọn đỉnh nào
                        } else {
                            //Đỉnh mới là đỉnh cũ => hủy trạng thái chọn
                            chooseVertex = -1;
                        }
                        //Vẽ các cung
                        //Dùng graph fix lại
                        //Vẽ các đỉnh

                    }
                    break;
                }
            }


        }

        //Draw graph
        //Draw edge (edge must be draw before vertex)
        for (int i = 0; i < edgeStart.size(); i++) {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.BLUE);
            canvas.drawLine(listX.get(edgeStart.get(i)), listY.get(edgeStart.get(i)), listX.get(edgeEnd.get(i)), listY.get(edgeEnd.get(i)), paint);
        }
        //Draw vertex
        for (int i = 0; i < listX.size(); i++) {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.WHITE);
            canvas.drawCircle(listX.get(i), listY.get(i), 20, paint);
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(listX.get(i), listY.get(i), 20, paint);
            paint.setColor(Color.BLUE);
            if (chooseVertex == i) {
                paint.setColor(Color.GREEN);
            }
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(20f);
            canvas.drawText(i + "", listX.get(i), listY.get(i) - ((paint.descent() + paint.ascent()) / 2), paint);
        }

        //Old code
        /*int touchVertex = -1;
        if (actionTouch == 1) {
            for (int i = 0; i < listX.size(); i++) {
                if (Math.sqrt(Math.pow((listX.get(i) - x), 2) + Math.pow((listY.get(i) - y), 2)) <= 20) {
                    paint = new Paint();
                    paint.setAntiAlias(true);
                    paint.setColor(Color.BLUE);
                    //((paint.descent() + paint.ascent()) / 2) is the distance from the baseline to the center.
                    canvas.drawText(i + "", x + 100, y, paint);
                    touchVertex = i;
                    break;
                }
            }
        }

        //Khi người dùng click
        if (actionTouch == 1) {
            //Vẽ lại đồ thị
            //Vẽ các cung
            //Dùng graph fix lại
            for (int i = 0; i < edgeStart.size(); i++) {
                paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColor(Color.BLUE);
                canvas.drawLine(listX.get(edgeStart.get(i)), listY.get(edgeStart.get(i)), listX.get(edgeEnd.get(i)), listY.get(edgeEnd.get(i)), paint);
            }
            //Vẽ các đỉnh
            for (int i = 0; i < listX.size(); i++) {
                paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColor(Color.BLUE);
                canvas.drawCircle(listX.get(i), listY.get(i), 20, paint);
                paint.setColor(Color.WHITE);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(20f);
                canvas.drawText(i + "", listX.get(i), listY.get(i) - ((paint.descent() + paint.ascent()) / 2), paint);
            }

            if (touchVertex != -1) {

                if (!isChooseVertex) {
                    //Nếu chưa chọn đỉnh nào
                    chooseVertex = touchVertex; //Đỉnh chọn là đỉnh chạm vào
                    isChooseVertex = true; //Đã chọn đỉnh
                    //Nếu chọn đỉnh => đổi màu
                    //Vẽ các cung
                    //Dùng graph fix lại
                    for (int i = 0; i < edgeStart.size(); i++) {
                        paint = new Paint();
                        paint.setAntiAlias(true);
                        paint.setColor(Color.BLUE);
                        canvas.drawLine(listX.get(edgeStart.get(i)), listY.get(edgeStart.get(i)), listX.get(edgeEnd.get(i)), listY.get(edgeEnd.get(i)), paint);
                    }
                    //Vẽ các đỉnh
                    for (int i = 0; i < listX.size(); i++) {
                        paint = new Paint();
                        paint.setAntiAlias(true);
                        paint.setColor(Color.BLUE);

                        //Đổi màu tại đỉnh click vào
                        if (touchVertex == i) {
                            paint.setColor(Color.RED);
                        }
                        canvas.drawCircle(listX.get(i), listY.get(i), 20, paint);
                        paint.setColor(Color.WHITE);
                        paint.setTextAlign(Paint.Align.CENTER);
                        paint.setTextSize(20f);
                        canvas.drawText(i + "", listX.get(i), listY.get(i) - ((paint.descent() + paint.ascent()) / 2), paint);
                    }
                } else {
                    //Nếu đã chọn 1 đỉnh => vẽ cung
                    //Nên dùng graph để fix lại
                    //Nếu chọn đỉnh khác đỉnh đã chọn
                    if (chooseVertex != touchVertex) {
                        edgeStart.add(edgeStart.size(), chooseVertex); //Thêm vị trí bắt đầu cung
                        edgeEnd.add(edgeEnd.size(), touchVertex); //Kết thúc bắt đầu cung
                        isChooseVertex = false; //Chưa chọn đỉnh nào
                    } else {
                        //Đỉnh mới là đỉnh cũ => hủy trạng thái chọn
                        isChooseVertex = false;
                    }
                    //Vẽ các cung
                    //Dùng graph fix lại
                    for (int i = 0; i < edgeStart.size(); i++) {
                        paint = new Paint();
                        paint.setAntiAlias(true);
                        paint.setColor(Color.BLUE);
                        canvas.drawLine(listX.get(edgeStart.get(i)), listY.get(edgeStart.get(i)), listX.get(edgeEnd.get(i)), listY.get(edgeEnd.get(i)), paint);
                    }
                    //Vẽ các đỉnh
                    for (int i = 0; i < listX.size(); i++) {

                        paint = new Paint();
                        paint.setAntiAlias(true);
                        paint.setColor(Color.BLUE);
                        canvas.drawCircle(listX.get(i), listY.get(i), 20, paint);
                        paint.setColor(Color.WHITE);
                        paint.setTextAlign(Paint.Align.CENTER);
                        paint.setTextSize(20f);
                        canvas.drawText(i + "", listX.get(i), listY.get(i) - ((paint.descent() + paint.ascent()) / 2), paint);
                    }

                }
            } else {
                //Nếu chưa có đỉnh => add vào

                //Nếu quá gần thì không add
                boolean touchArea = false;
                for (int i = 0; i < listX.size(); i++) {
                    if (Math.sqrt(Math.pow((listX.get(i) - x), 2) + Math.pow((listY.get(i) - y), 2)) <= 100) {
                        paint = new Paint();
                        paint.setAntiAlias(true);
                        paint.setColor(Color.YELLOW);
                        paint.setStyle(Paint.Style.STROKE);
                        canvas.drawCircle(listX.get(i), listY.get(i), 80, paint);

                        touchArea = true;
                        break;
                    }
                }

                if (!touchArea) {
                    listX.add(x);
                    listY.add(y);
                    //Vẽ lại đồ thị
                    //Vẽ các cung
                    //Dùng graph fix lại
                    for (int i = 0; i < edgeStart.size(); i++) {
                        paint = new Paint();
                        paint.setAntiAlias(true);
                        paint.setColor(Color.BLUE);
                        canvas.drawLine(listX.get(edgeStart.get(i)), listY.get(edgeStart.get(i)), listX.get(edgeEnd.get(i)), listY.get(edgeEnd.get(i)), paint);
                    }
                    //Vẽ các đỉnh
                    for (int i = 0; i < listX.size(); i++) {

                        paint = new Paint();
                        paint.setAntiAlias(true);
                        paint.setColor(Color.BLUE);
                        canvas.drawCircle(listX.get(i), listY.get(i), 20, paint);
                        paint.setColor(Color.WHITE);
                        paint.setTextAlign(Paint.Align.CENTER);
                        paint.setTextSize(20f);
                        canvas.drawText(i + "", listX.get(i), listY.get(i) - ((paint.descent() + paint.ascent()) / 2), paint);
                    }
                }
            }
            actionTouch = 0;
        }

        if (actionTouch == 2) {
            //Vẽ lại đồ thị
            //Vẽ các cung
            //Dùng graph fix lại
            for (int i = 0; i < edgeStart.size(); i++) {
                paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColor(Color.BLUE);
                canvas.drawLine(listX.get(edgeStart.get(i)), listY.get(edgeStart.get(i)), listX.get(edgeEnd.get(i)), listY.get(edgeEnd.get(i)), paint);
            }
            //Vẽ các đỉnh
            for (int i = 0; i < listX.size(); i++) {

                paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColor(Color.BLUE);
                canvas.drawCircle(listX.get(i), listY.get(i), 20, paint);
                paint.setColor(Color.WHITE);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(20f);
                canvas.drawText(i + "", listX.get(i), listY.get(i) - ((paint.descent() + paint.ascent()) / 2), paint);
            }

            boolean touchArea = false;
            for (int i = 0; i < listX.size(); i++) {
                if (Math.sqrt(Math.pow((listX.get(i) - x), 2) + Math.pow((listY.get(i) - y), 2)) <= 100) {
                    paint = new Paint();
                    paint.setAntiAlias(true);
                    paint.setColor(Color.YELLOW);
                    paint.setStyle(Paint.Style.STROKE);
                    canvas.drawCircle(listX.get(i), listY.get(i), 80, paint);

                    touchArea = true;
                    break;
                }
            }

            //Di chuyển
            if (!touchArea) {
                paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColor(Color.RED);
                canvas.drawCircle(x, y, 20, paint);
                paint.setColor(Color.WHITE);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(20f);
                canvas.drawText("", x, y - ((paint.descent() + paint.ascent()) / 2), paint);
                actionTouch = 0;
            }
        }

        if (actionTouch == 3) {
            //Vẽ lại đồ thị
            //Vẽ các cung
            //Dùng graph fix lại
            for (int i = 0; i < edgeStart.size(); i++) {
                paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColor(Color.BLUE);
                canvas.drawLine(listX.get(edgeStart.get(i)), listY.get(edgeStart.get(i)), listX.get(edgeEnd.get(i)), listY.get(edgeEnd.get(i)), paint);
            }
            //Vẽ các đỉnh
            for (int i = 0; i < listX.size(); i++) {

                paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColor(Color.BLUE);
                canvas.drawCircle(listX.get(i), listY.get(i), 20, paint);
                paint.setColor(Color.WHITE);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(20f);
                canvas.drawText(i + "", listX.get(i), listY.get(i) - ((paint.descent() + paint.ascent()) / 2), paint);
            }

            //Nếu quá gần thì không add
            boolean touchArea = false;
            for (int i = 0; i < listX.size(); i++) {
                if (Math.sqrt(Math.pow((listX.get(i) - x), 2) + Math.pow((listY.get(i) - y), 2)) <= 100) {
                    touchArea = true;
                    break;
                }
            }
            if (!touchArea) {
                listX.add(x);
                listY.add(y);
                for (int i = 0; i < listX.size(); i++) {
                    paint = new Paint();
                    paint.setAntiAlias(true);
                    paint.setColor(Color.BLUE);
                    canvas.drawCircle(listX.get(i), listY.get(i), 20, paint);
                    paint.setColor(Color.WHITE);
                    paint.setTextAlign(Paint.Align.CENTER);
                    paint.setTextSize(20f);
                    canvas.drawText(i + "", listX.get(i), listY.get(i) - ((paint.descent() + paint.ascent()) / 2), paint);
                }
            }
        }*/

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //x = event.getX();
                //y = event.getY();
                //actionTouch = 1;
                break;
            case MotionEvent.ACTION_MOVE:
                timeTouch++;
                if (timeTouch > 5) {
                    x = event.getX();
                    y = event.getY();
                    actionTouch = 2;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (timeTouch < 5) {
                    //Click
                    x = event.getX();
                    y = event.getY();
                    actionTouch = 1;
                    invalidate();
                } else {
                    //Move
                    x = event.getX();
                    y = event.getY();
                    actionTouch = 3;
                    invalidate();
                }
                timeTouch = 0;
                break;
        }
        return true;
    }
}