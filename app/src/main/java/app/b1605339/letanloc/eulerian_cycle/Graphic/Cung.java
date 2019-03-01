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

public class Cung extends View {
    private Paint paint = new Paint();
    private int positionBeginX = 0;
    private int positionBeginY = 0;
    private int positionEndX = 0;
    private int positionEndY = 0;

    public Cung(Context context) {
        super(context);
    }

    public Cung(Context context, int positionBeginX, int positionBeginY, int positionEndX, int positionEndY){
        super(context);
        this.positionBeginX = positionBeginX;
        this.positionBeginY = positionBeginY;
        this.positionEndX = positionEndX;
        this.positionEndY = positionEndY;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int widthCanvas = canvas.getWidth();
        int heightCanvas = canvas.getHeight();

        paint.setColor(Color.WHITE);

    }
}
