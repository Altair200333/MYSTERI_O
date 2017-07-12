package com.altaindustr.mysterio.graphicPassDrawer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by КАРАТ on 24.02.2017.
 */

public class Draw2D extends View
{
    private Paint mPaint = new Paint();

    public Draw2D(Context context)
    {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        // стиль Заливка
        mPaint.setStyle(Paint.Style.FILL);

        // закрашиваем холст белым цветом
        mPaint.setColor(Color.BLACK);
        canvas.drawPaint(mPaint);
    }
}
