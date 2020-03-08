package com.gridsynergy.gamedemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Background {

    private Bitmap image;
    private int x, y, dx;

    public Background(Bitmap res)
    {
        image = res;
    }
    public void update()
    {
    }
    public void draw(Canvas canvas)
    {
        image = Bitmap.createScaledBitmap(image, 1920, 1080, false);
        canvas.drawBitmap(image, x, y,null);
    }
    public void setVector(int dx)
    {
        this.dx = dx;
    }
}
