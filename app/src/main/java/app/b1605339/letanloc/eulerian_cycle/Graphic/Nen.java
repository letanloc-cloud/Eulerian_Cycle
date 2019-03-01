/*
 * Created by Lê Tấn Lộc on 13:15 01/03/2019
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 13:15 01/03/2019
 */

package app.b1605339.letanloc.eulerian_cycle.Graphic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Nen extends View {
    private Paint paint = new Paint();

    public Nen(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int widthCanvas = canvas.getWidth();
        int heightCanvas = canvas.getHeight();

        paint.setColor(Color.BLUE);
        canvas.drawPaint(paint);
        paint.setColor(Color.RED);
        canvas.drawCircle(100, 100, 100, paint); //tọa độ x, y, tâm, paint

    }



}
