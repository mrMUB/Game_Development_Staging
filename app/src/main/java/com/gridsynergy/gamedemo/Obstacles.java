package com.gridsynergy.gamedemo;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.Log;

import java.util.ArrayList;

public class Obstacles {

    ArrayList<ArrayList> levelObstaclesRegion = new ArrayList<>();
    ArrayList<ArrayList> levelObstaclesPath = new ArrayList<>();
    ArrayList<Region> currentObstaclesRegion = new ArrayList<>();
    ArrayList<Path> currentObstaclesPath = new ArrayList<>();

    ArrayList<ArrayList> levelObstacles = new ArrayList<>();
    ArrayList<Rect> currentObstacles = new ArrayList<>();

    public Obstacles(){
        createObstacles();
    }

    public void createObstacles(){
//        ArrayList<Rect> lvl_1 = new ArrayList<>();
//        lvl_1.add(toRect(128,128,128,64));
//        lvl_1.add(toRect(128,256,192,64));
//        lvl_1.add(toRect(256,320,64,256));
//
//        levelObstacles.add(lvl_1);

        ArrayList<Region> lvl_1 = new ArrayList<>();
        ArrayList<Path> lvl_1_path = new ArrayList<>();



        Path vectorPath = new Path();
        vectorPath.moveTo(6.5f, 79.99f);
        vectorPath.lineTo(37.21f, 50.5f);
        vectorPath.lineTo(6.5f, 19.79f);
        vectorPath.lineTo(18.79f, 7.5f);
        vectorPath.lineTo(49.5f, 38.21f);
        vectorPath.lineTo(80.21f, 7.5f);
        vectorPath.lineTo(92.5f, 19.79f);
        vectorPath.lineTo(61.79f, 50.5f);
        vectorPath.lineTo(92.5f, 79.99f);
        vectorPath.lineTo(80.21f, 93.5f);
        vectorPath.lineTo(49.5f, 62.79f);
        vectorPath.lineTo(18.79f, 93.5f);
        vectorPath.close();
        lvl_1.add(pathToRegion(vectorPath));
        lvl_1_path.add(vectorPath);

        Path vector_2 = new Path();
        vector_2.addRect(new RectF(toRect(128,128,128,64)),Path.Direction.CW);
        vector_2.close();
        lvl_1.add(pathToRegion(vector_2));
        lvl_1_path.add(vector_2);



        levelObstaclesRegion.add(lvl_1);
        levelObstaclesPath.add(lvl_1_path);


    }

    public Region pathToRegion(Path vectorPath){
//        Matrix drawMatrix = new Matrix();
//        drawMatrix.setScale(100 / 100.0f, 100 / 100.0f);
//        vectorPath.transform(drawMatrix);

        Region clip = new Region(0, 0, 1920, 1080);
        Region region = new Region();
        region.setPath(vectorPath, clip);
        return region;
    }

    public Path rectToVectorPath(RectF rect){
        Path vector = new Path();
        vector.addRect(rect,Path.Direction.CW);
        vector.close();
        return vector;
    }

    public ArrayList<Path> createGameBorderPath(){
        ArrayList<Path> gameBorder = new ArrayList<>();
        gameBorder.add(rectToVectorPath(toRect(0,0,1920,64)));
        gameBorder.add(rectToVectorPath(toRect(0,64,64,952)));
        gameBorder.add(rectToVectorPath(toRect(0,1016,1920,64)));
        gameBorder.add(rectToVectorPath(toRect(1856,64,64,952)));
        return gameBorder;
    }

    public ArrayList<Region> createGameBorderRegion(){
        ArrayList<Region> gameBorder = new ArrayList<>();
        gameBorder.add(pathToRegion(rectToVectorPath(toRect(0,0,1920,64))));
        gameBorder.add(pathToRegion(rectToVectorPath(toRect(0,64,64,952))));
        gameBorder.add(pathToRegion(rectToVectorPath(toRect(0,1016,1920,64))));
        gameBorder.add(pathToRegion(rectToVectorPath(toRect(1856,64,64,952))));

        return gameBorder;
    }

    public RectF toRect(int x,int y,int width,int height){
        return new RectF(x,y,x+width,y+height);
    }

    public ArrayList<Region> getObstacle(int gameLevel){
        currentObstaclesRegion = levelObstaclesRegion.get(gameLevel-1);
        currentObstaclesPath = levelObstaclesPath.get(gameLevel-1);
        currentObstaclesPath.addAll(createGameBorderPath());
        currentObstaclesRegion.addAll(createGameBorderRegion());
        return currentObstaclesRegion;
    }

    public boolean collidedObstacle(GameObject gameObject){
        for(Region ObstaclRegion : currentObstaclesRegion){
            if (!gameObject.getRegion().quickReject(ObstaclRegion) && gameObject.getRegion().op(ObstaclRegion, Region.Op.INTERSECT)) {
                // Collision!
                Log.i("Collision","true");
                return true;
            }
        }
        return false;
    }

    public void draw(Canvas canvas, Paint paint){
        for(Path ObstaclRect : currentObstaclesPath){
            canvas.drawPath(ObstaclRect,paint);
        }

    }

}
