package com.altaindustr.mysterio.graphicPassDrawer;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.altaindustr.mysterio.GraphicPasswordHelper;

/**
 * Created by IT ШКОЛА SAMSUNG on 22.11.2015.
 */
public class DrawView extends SurfaceView implements SurfaceHolder.Callback
{


    public DrawThread drawThread;

    public static String index="";
    GraphicPasswordHelper gph;
    public DrawView(Context context, GraphicPasswordHelper gph) {
        super(context);
        getHolder().addCallback(this);
        this.gph = gph;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getContext(),getHolder());
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawThread.requestStop();
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                //
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN )
        {
            drawThread.is_moving = true;
            drawThread.startX = (int)event.getX(event.getActionIndex());
            drawThread.startY = (int)event.getY(event.getActionIndex());
            drawThread.X = (int)event.getX(event.getActionIndex());
            drawThread.Y = (int)event.getY(event.getActionIndex());
            index = drawThread.indexes;
        }
        if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL ||
                event.getAction() == MotionEvent.ACTION_OUTSIDE  )
        {
            gph.saveGraphicPassword(index);
            drawThread.is_moving = false;
            drawThread.points.clear();
            drawThread.indexes = "";
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE)
        {
            drawThread.X = (int)event.getX(event.getActionIndex());
            drawThread.Y = (int)event.getY(event.getActionIndex());
            index=drawThread.indexes;
            gph.updateGraphicPassword(index);
        }
        return true;
    }
}
