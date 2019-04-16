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

import app.b1605339.letanloc.eulerian_cycle.Graph;

public class DrawGraph extends View {
    private Paint paint = new Paint();

    //Position touch
    private float x;
    private float y;
    //Vertex
    private ArrayList<Float> listX = new ArrayList<>();
    private ArrayList<Float> listY = new ArrayList<>();

    //-1: no choice
    private int chooseVertex = -1;
    //Edge
    private ArrayList<Integer> edgeStart = new ArrayList<>();
    private ArrayList<Integer> edgeEnd = new ArrayList<>();

    //Touch area around vertex
    private int areaVertex = -1;

    //-1: no action
    //0: action click
    //1: action move
    //2: action up
    private static int actionTouch = -1;
    private static int timeTouch = 0;

    //Draw result
    private static ArrayList<Integer> listVertex = new ArrayList<>();


    public DrawGraph(Context context) {
        super(context);
    }

    public Boolean saveFile(String fileName){
        Graph graph = new Graph(listX.size());
        for (int i = 0; i < this.edgeStart.size(); i++) {
            graph.addEdge(this.edgeStart.get(i), this.edgeEnd.get(i));
        }
        return graph.saveFile(fileName);
    }

    //Multithread need synchronized
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int widthCanvas = canvas.getWidth();
        int heightCanvas = canvas.getHeight();

        //Draw background
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);

        if ((x < (widthCanvas - 20)) && (y < heightCanvas - 20) && (x > 20) && (y > 20)) {
            //two vertices are near => don't add
            if ((actionTouch > -1) || (actionTouch == 1)) {
                for (int touchArea = 0; touchArea <= listX.size(); touchArea++) {
                    if (touchArea == listX.size()) {
                        //remove touch around area
                        areaVertex = -1;
                        break;
                    } else if (Math.sqrt(Math.pow((listX.get(touchArea) - x), 2) + Math.pow((listY.get(touchArea) - y), 2)) <= 80) {
                        if ((actionTouch == 1) && (chooseVertex == touchArea)) {
                            continue;
                        } else {
                            areaVertex = touchArea;
                            break;
                        }
                    }
                }
            }

            //Need fix when vertex out canvas
            //Need fix: remove vertex
            //Need fix: save file
            //Action click
            if (actionTouch == 0) {
                //Click vertex
                int touchVertex; //Declare here to detect if no click vertex
                for (touchVertex = 0; touchVertex < listX.size(); touchVertex++) {
                    if (Math.sqrt(Math.pow((listX.get(touchVertex) - x), 2) + Math.pow((listY.get(touchVertex) - y), 2)) <= 20) {
                        //Need fix => delete
                        paint = new Paint();
                        paint.setAntiAlias(true);
                        paint.setColor(Color.BLUE);
                        //Code ((paint.descent() + paint.ascent()) / 2) is the distance from the baseline to the center.
                        canvas.drawText(touchVertex + "", x + 100, y, paint);
                        //Delete


                        if (chooseVertex == -1) {
                            //Nếu chưa chọn đỉnh nào
                            //Đỉnh chọn là đỉnh chạm vào
                            chooseVertex = touchVertex;
                            //Nếu chọn đỉnh => đổi màu
                        } else {
                            //Nếu chọn đỉnh khác đỉnh đã chọn => thêm cung
                            if (chooseVertex != touchVertex) {
                                int minVertex = chooseVertex < touchVertex ? chooseVertex : touchVertex;
                                int maxVertex = chooseVertex < touchVertex ? touchVertex : chooseVertex;
                                int edgeStarVertex;
                                for (edgeStarVertex = 0; edgeStarVertex <= edgeStart.size(); edgeStarVertex++) {
                                    //If no same edge => add edge
                                    if (edgeStarVertex == edgeStart.size()) {
                                        edgeStart.add(minVertex); //Thêm vị trí bắt đầu cung
                                        edgeEnd.add(maxVertex); //Kết thúc bắt đầu cung
                                        chooseVertex = -1; //after add edge, remove choose vertex
                                        break;
                                    } else if ((edgeStart.get(edgeStarVertex) == minVertex) && (edgeEnd.get(edgeStarVertex) == maxVertex)) {
                                        //If edge exist => remove this edge
                                        edgeStart.remove(edgeStarVertex);
                                        edgeEnd.remove(edgeStarVertex);
                                        chooseVertex = -1; //after remove edge, remove choose vertex
                                        break;
                                    }
                                }

                            } else {
                                //Đỉnh mới là đỉnh cũ => hủy trạng thái chọn
                                chooseVertex = -1;
                            }
                        }
                        break;
                    }
                }

                //click space => add vertex
                if (touchVertex == listX.size()) {
                    //remove choose vertex
                    chooseVertex = -1;

                    //two vertices are far => add vertex
                    if (areaVertex == -1) {
                        //add vertex
                        //need fix => use graph
                        //need fix => remove if it is edge
                        listX.add(x);
                        listY.add(y);
                    }
                }
            }

            //Action move
            if (actionTouch == 1) {
                //Choose vertex and move (drag and drop) => move this vertex
                if (chooseVertex > -1) {
                    if ((areaVertex == chooseVertex) || (areaVertex == -1)) {
                        //Set new position if vertex isn't touch other
                        listX.set(chooseVertex, x);
                        listY.set(chooseVertex, y);
                        paint = new Paint();
                        paint.setAntiAlias(true);
                        paint.setColor(Color.RED);
                        paint.setTextAlign(Paint.Align.CENTER);
                        paint.setTextSize(20f);
                        if((x > (widthCanvas - 60)) && (y > (heightCanvas - 60))){
//                            canvas.drawText("xóa", widthCanvas - 100, heightCanvas - 100, paint);

                            int edgeStarVertex;
                            for (edgeStarVertex = 0; edgeStarVertex < edgeStart.size(); edgeStarVertex++) {
                                //If this edge has chooseVertex => remove
                               if ((edgeStart.get(edgeStarVertex) == chooseVertex) || (edgeEnd.get(edgeStarVertex) == chooseVertex)) {
                                    edgeStart.remove(edgeStarVertex);
                                    edgeEnd.remove(edgeStarVertex);
                                    edgeStarVertex--;
                               }
                            }
                            for (edgeStarVertex = 0; edgeStarVertex < edgeStart.size(); edgeStarVertex++) {
                                //Change vertex of all edge
                                //If edge has (Vertex > chooseVertex) (will delete) => increment
                                if (edgeStart.get(edgeStarVertex) > chooseVertex) {
                                    edgeStart.set(edgeStarVertex, edgeStart.get(edgeStarVertex) - 1);
                                }
                                if (edgeEnd.get(edgeStarVertex) > chooseVertex) {
                                    edgeEnd.set(edgeStarVertex, edgeEnd.get(edgeStarVertex) - 1);
                                }
                            }
                            listX.remove(chooseVertex);
                            listY.remove(chooseVertex);
                            chooseVertex = -1; //after remove edge, remove choose vertex
                        }
                        canvas.drawLine(widthCanvas - 75, heightCanvas - 75, widthCanvas, heightCanvas - 75, paint);
                        canvas.drawLine(widthCanvas - 75, heightCanvas - 75, widthCanvas - 75, heightCanvas, paint);
                        canvas.drawText("Xóa", widthCanvas - 37, heightCanvas - 37, paint);
                    }
                    if (areaVertex > -1) {
                        //Draw area vertex around choose vertex
                        areaVertex = chooseVertex;
                    }
                    //Need fix: move into area vertex
                    //Action move: remove choose vertex
                }
                //Need fix: no choice vertex => add vertex and move it

            }
        }

        //The way to use this app
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(18f);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.BLACK);
        canvas.drawText("Click vào khoảng trống để vẽ đỉnh", 20, heightCanvas - 120, paint);
        canvas.drawText("Click vào đỉnh để chọn đỉnh", 20, heightCanvas - 100, paint);
        canvas.drawText("Chọn lần lượt 2 đỉnh chưa có cung để vẽ cung 2 đỉnh đó", 20, heightCanvas - 80, paint);
        canvas.drawText("Chọn lần lượt 2 đỉnh đã có cung để xóa cung 2 đỉnh đó", 20, heightCanvas - 60, paint);
        canvas.drawText("Nắm giữ 1 đỉnh và kéo thả để di chuyển đỉnh đó", 20, heightCanvas - 40, paint);
        canvas.drawText("Di chuyển 1 đỉnh vào khung đỏ ở góc phải phía dưới để xóa đỉnh đó", 20, heightCanvas - 20, paint);

        /*paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(20f);
        canvas.drawText(edgeStart.size() + " " + edgeEnd.size(), 100, 100, paint);*/

        //Draw areaVertex
        if (areaVertex > -1) {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(listX.get(areaVertex), listY.get(areaVertex), 80, paint);
        }
        //Draw graph
        //Draw edge (edge must be draw before vertex)
        for (int i = 0; i < edgeStart.size(); i++) {
            paint = new Paint();
            paint.setAntiAlias(true);
            //paint.setColor(Color.RED);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(20f);
            //canvas.drawText(edgeStart.get(i) + " - " + edgeEnd.get(i), (listX.get(edgeStart.get(i)) + listX.get(edgeEnd.get(i))) / 2, (listY.get(edgeStart.get(i)) + listY.get(edgeEnd.get(i))) / 2 - ((paint.descent() + paint.ascent()) / 2), paint);
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
            if (chooseVertex == i) {
                paint.setColor(Color.RED);
            }
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(listX.get(i), listY.get(i), 20, paint);
            paint.setColor(Color.BLUE);
            if (chooseVertex == i) {
                paint.setColor(Color.RED);
            }
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(20f);
            canvas.drawText(i + "", listX.get(i), listY.get(i) - ((paint.descent() + paint.ascent()) / 2), paint);
        }

        //After draw graph => find eulerian cycle
        Graph graph = new Graph(listX.size());
        for (int i = 0; i < edgeStart.size(); i++) {
            graph.addEdge(edgeStart.get(i), edgeEnd.get(i));
        }

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(20f);
        //canvas.drawText(graph.getEulerianCycle(), widthCanvas / 2, 100, paint);
        canvas.drawText("Số thành phần liên thông: " + graph.countConnectedComponents(), widthCanvas / 2, 100, paint);

        listVertex = new ArrayList<>(graph.getListVertexEulerianCycle());

        if (!listVertex.isEmpty()) {
            for (int i = 0; i < listVertex.size(); i++) {
                int edge2 = i + 1;
                if (i + 1 == listVertex.size()) {
                    edge2 = 0;
                }
                paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColor(Color.RED);
                canvas.drawCircle((listX.get(listVertex.get(i)) + listX.get(listVertex.get(edge2))) / 2, (listY.get(listVertex.get(i)) + listY.get(listVertex.get(edge2))) / 2, 10, paint);
                paint.setColor(Color.BLUE);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(20f);

                canvas.drawText((i + 1) + "", (listX.get(listVertex.get(i)) + listX.get(listVertex.get(edge2))) / 2, (listY.get(listVertex.get(i)) + listY.get(listVertex.get(edge2))) / 2 - ((paint.descent() + paint.ascent()) / 2), paint);

            }

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
                if (timeTouch < 1) {
                    x = event.getX();
                    y = event.getY();
                    actionTouch = 0;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                timeTouch++;
                if (timeTouch > 1) {
                    x = event.getX();
                    y = event.getY();
                    actionTouch = 1;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (actionTouch == 1) {
                    chooseVertex = -1;
                }
                areaVertex = -1;
                actionTouch = -1;
                timeTouch = 0;
                invalidate();
                break;
        }
        return true;
    }
}