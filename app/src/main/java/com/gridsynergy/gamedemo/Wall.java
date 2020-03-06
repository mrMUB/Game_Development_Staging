package com.gridsynergy.gamedemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

public class Wall extends GameObject{

    private Bitmap wallBitmap;

    public Wall(Bitmap wallBitmap, int x, int y, int w, int h)
    {
        super.x = x;
        super.y = y;
        width = w;
        height = h;
        this.wallBitmap = Bitmap.createScaledBitmap(wallBitmap, width, height, false);

    }
    public Wall(){

    }
    public void update()
    {

    }

    public void draw(Canvas canvas)
    {
        try{
            canvas.drawBitmap(wallBitmap,x,y,null);
        }catch(Exception e){}
    }

    @Override
    public int getWidth()
    {
        //offset slightly for more realistic collision detection
        return width;
    }


    public int[][] getLvlWalls(){

        int[][] boxWall = {
                // Left box wall
                {0,0},
                {0,100},
                {0,200},
                {0,300},
                {0,400},
                {0,500},
                {0,600},
                {0,700},
                {0,800},
                {0,900},
                {0,1000},
                {0,1100},
                {0,1200},
                {0,1300},
                {0,1400},

                // Left box wall
                {MainActivity.WIDTH-100,0},
                {0,100},
                {0,200},
                {0,300},
                {0,400},
                {0,500},
                {0,600},
                {0,700},
                {0,800},
                {0,900},
                {0,1000},
                {0,1100},
                {0,1200},
                {0,1300},
                {0,1400},
        };

        int[][] wallSet = {
                // set 1
                {100,200},
                {200,200},
                {300,200},
                {400,200},

                {400,300},
                {400,400},
                {400,500},
                {400,600},
                {400,700},
                {400,800},
                {400,900},

                {620,300},
                {720,300},
                {820,300},
                {920,300},
                {1020,300},
                {1120,300},
                {1220,300},
                {1320,300},

                {620,300},
                {620,400},
                {620,500},
                {620,600},
                {620,700},
                {620,800},
                {620,900},

        };

        int[][] result = new int[boxWall.length + wallSet.length][];

        System.arraycopy(boxWall, 0, result, 0, boxWall.length);
        System.arraycopy(wallSet, 0, result, boxWall.length, wallSet.length);

        return result;
    }



}
