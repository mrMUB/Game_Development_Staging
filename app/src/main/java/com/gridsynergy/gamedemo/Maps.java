package com.gridsynergy.gamedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.Map;

public class Maps {

    private int gameLevel = 1;

    private ArrayList<Wall> MapObjects;
    private int objectWidth = 100;
    private int objectHeight = 100;
    Context context;
    public Maps(Context context){
        this.context = context;
    }

    public ArrayList<Wall> getMap(int gameLevel){

        Log.i("Wall","log from wall");
        this.gameLevel = gameLevel;
        MapObjects = new ArrayList<>();

        borders();

        // Render wall objects
        int[][] WallsObjects = walls();
        Bitmap WallBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.wall);
        for (int i = 0; i < WallsObjects.length; ++i) {
            MapObjects.add(new Wall(WallBitmap,WallsObjects[i][0],+WallsObjects[i][1],objectWidth,objectHeight));
        }

        return MapObjects;
    }

    public void borders(){
        // Render border objects
//        Bitmap BorderBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.wall);
//        MapObjects.add(new Wall(BorderBitmap,BorderObjects[i][0],+BorderObjects[i][1],objectWidth,objectHeight));
    }

    public int[][] walls(){
        int[][] walls = null;
        if(gameLevel == 1){
            walls = new int[][] {
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
        }

        return walls;

    }

}
