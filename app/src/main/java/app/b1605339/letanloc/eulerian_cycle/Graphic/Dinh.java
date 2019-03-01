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
import android.view.View;

public class Dinh extends View {
    private Paint paint = new Paint();
    private int positionX = 0;
    private int positionY = 0;

    public Dinh(Context context) {
        super(context);
    }

    public Dinh(Context context, int positionX, int positionY){
        super(context);
        this.positionX = positionX;
        this.positionY = positionY;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int widthCanvas = canvas.getWidth();
        int heightCanvas = canvas.getHeight();

        paint.setColor(Color.RED);
        canvas.drawCircle(100, 100, 100, paint);

    }
}
