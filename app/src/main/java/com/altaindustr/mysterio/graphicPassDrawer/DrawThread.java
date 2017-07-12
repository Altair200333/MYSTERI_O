package com.altaindustr.mysterio.graphicPassDrawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by IT ШКОЛА SAMSUNG on 22.11.2015.
 */
public class DrawThread extends Thread {

    private SurfaceHolder surfaceHolder;


    private volatile boolean running = true;//флаг для остановки потока
    private Paint backgroundPaint = new Paint();
    public Paint paint = new Paint();

    int width, height, X,Y,startX,startY, r;

    String indexes = "";
    ArrayList<Point> points = new ArrayList<>(9);
    boolean is_moving;
    {
        backgroundPaint.setColor(Color.parseColor("#0b1218"));
        backgroundPaint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#fb135c"));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(20);
    }

    public DrawThread(Context context, SurfaceHolder surfaceHolder)
    {
        this.surfaceHolder = surfaceHolder;
    }

    public void requestStop() {
        running = false;
    }


    @Override
    public void run()
    {

        while (running)
        {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null)
            {
                try
                {
                    width = canvas.getWidth();
                    height = canvas.getHeight();

                    Point[] centers = {new Point(width/10,width/10), new Point(width/10*5,width/10), new Point(width/10*9,width/10),
                            new Point(width/10,width/10*5),new Point(width/10*5,width/10*5),new Point(width/10*9,width/10*5),
                            new Point(width/10,width/10*9),new Point(width/10*5,width/10*9),new Point(width/10*9,width/10*9)};

                    r = width/30;

                    canvas.drawRect(0, 0, width, height, backgroundPaint);
                    for (Point p:centers)
                    {
                        canvas.drawCircle(p.x, p.y, r, paint);
                    }
                    if (is_moving)
                    {
                        for (Point p:centers)
                        {
                            if (p.isInCircle(X,Y, p.x,p.y,(int)(r*4)))
                            {
                                if (!indexes.contains(Arrays.asList(centers).indexOf(p)+""))
                                {
                                    points.add(new Point(p.x, p.y));
                                    startX = p.x;
                                    startY = p.y;
                                    indexes+=Arrays.asList(centers).indexOf(p)+"";
                                }
                            }
                        }



                        if (points.size()==1)
                        {
                            canvas.drawLine(points.get(0).x, points.get(0).y, startX, startY
                                    , paint);
                        }
                        else if (points.size()>1)
                        {
                            for (int i = 0; i < points.size()-1; i++)
                            {
                                canvas.drawLine(points.get(i).x,points.get(i).y,
                                        points.get(i+1).x,points.get(i+1).y,paint);
                            }
                            try
                            {
                                canvas.drawLine(points.get(points.size() - 1).x, points.get(points.size() - 1).y,
                                        startX, startY, paint);
                            }catch (Exception e){}
                        }
                        if (points.size()>0)
                        {
                            canvas.drawLine(startX, startY, X, Y, paint);
                        }
                    }

                } finally
                {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }

            }
        }
    }
    class Point extends Object
    {
        int x,y;
        Point(int x1,int y1)
        {
            x=x1;
            y=y1;
        }
        public boolean isInCircle(int x, int y, int x1, int y1, int r)
        {
            if (((x-x1)*(x-x1)+(y-y1)*(y-y1))<r*r)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        public boolean isInCircle(int x1, int y1, int r)
        {
            if (((x-x1)*(x-x1)+(y-y1)*(y-y1))<r*r)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj instanceof Point)
            {
                if(((Point) obj).x == this.x && ((Point) obj).y == this.y)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            return false;
        }
    }
}
