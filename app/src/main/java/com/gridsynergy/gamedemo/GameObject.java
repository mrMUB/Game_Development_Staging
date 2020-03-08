package com.gridsynergy.gamedemo;

import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;

public class GameObject {
    protected int x;
    protected int y;
    protected int dy;
    protected int dx;
    protected int width;
    protected int height;

    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public int getHeight()
    {
        return height;
    }
    public int getWidth()
    {
        return width;
    }
    public Rect getRectangle()
    {
        return new Rect(x, y, (x+width), (y+height));
    }
    public Region getRegion()
    {
        Path path1 = new Path();
        path1.addRect(new RectF(x, y, (x+width), (y+height)),Path.Direction.CW);

        Region region = new Region();

        region.setPath(path1, new Region(0, 0, 1920, 1080));
        return region;
    }

}
