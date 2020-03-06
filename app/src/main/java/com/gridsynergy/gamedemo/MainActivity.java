package com.gridsynergy.gamedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends Activity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        setContentView(new GamePanel(this));
//    }


    private SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;

    public static int WIDTH;
    public static int HEIGHT;
    public static final int MOVESPEED = -5;
    private MainThread thread;
    private Background bg;
    private Player player;
    private Wall wall;

    private ArrayList<Wall> MapObjects;

    Paint gamePaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        WIDTH = dm.widthPixels;
        HEIGHT = dm.heightPixels;
        Log.i("GPanel Size: ",WIDTH +"x"+HEIGHT);
        initSurface();
        controller();

        gamePaint = new Paint();
        gamePaint.setColor(Color.rgb(255, 0, 0));
        gamePaint.setStrokeWidth(1);
        gamePaint.setStyle(Paint.Style.STROKE);
    }

    public void controller(){
        player.setPlaying(true);
        Button btn_UP = findViewById(R.id.btnUp);
        btn_UP.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction()==MotionEvent.ACTION_DOWN){
//                    player.setCtl_UP(false);
//                    player.setCtl_DOWN(false);
//                    player.setCtl_RIGHT(false);
//                    player.setCtl_LEFT(false);
//
//                    player.setCtl_UP(true);
                    player.setPlayerAction(1);
                }
                return true;
            }
        });

        Button btn_Down = findViewById(R.id.btnDown);
        btn_Down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction()==MotionEvent.ACTION_DOWN){

//                    player.setCtl_UP(false);
//                    player.setCtl_DOWN(false);
//                    player.setCtl_RIGHT(false);
//                    player.setCtl_LEFT(false);
//
//                    player.setCtl_DOWN(true);

                    player.setPlayerAction(2);
                }
                return true;
            }
        });

        Button btn_Left = findViewById(R.id.btnLeft);
        btn_Left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction()==MotionEvent.ACTION_DOWN){
//                    player.setCtl_UP(false);
//                    player.setCtl_DOWN(false);
//                    player.setCtl_RIGHT(false);
//                    player.setCtl_LEFT(false);
//
//                    player.setCtl_LEFT(true);
                    player.setPlayerAction(3);
                }
                return true;
            }
        });

        Button btn_Right = findViewById(R.id.btnRight);
        btn_Right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction()==MotionEvent.ACTION_DOWN){

//                    player.setCtl_UP(false);
//                    player.setCtl_DOWN(false);
//                    player.setCtl_RIGHT(false);
//                    player.setCtl_LEFT(false);
//
//                    player.setCtl_RIGHT(true);
                    player.setPlayerAction(4);
                }
                return true;
            }
        });


    }

    public void initSurface(){
        mSurfaceView = findViewById(R.id.GameSurfaceView);
        mSurfaceView.getHolder().setFormat(PixelFormat.TRANSPARENT);
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mHolder = holder;
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                while(retry)
                {
                    try{thread.setRunning(false);
                        thread.join();

                    }catch(InterruptedException e){e.printStackTrace();}
                    retry = false;
                }
            }
        });



        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.game_background_v1_4));

        player = new Player(this, 100, 100, 4);

//        walls = new ArrayList<Wall>();
//
//        int[][] wallSet = new Wall().getLvlWalls();
//
//        for (int i = 0; i < wallSet.length; ++i) {
//            walls.add(new Wall(BitmapFactory.decodeResource(getResources(), R.drawable.wall),wallSet[i][0],+wallSet[i][1],100,100));
//        }


        MapObjects = new Maps(this).getMap(1);

        //we can safely start the game loop
        thread = new MainThread(mSurfaceView.getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(!player.getPlaying())
            {
                player.setPlaying(true);
            }
            else
            {
                player.setUp(true);
            }
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            player.setUp(false);
            return true;
        }

        return super.onTouchEvent(event);
    }

    public void update()
    {
        if(player.getPlaying()) {
            bg.update();
            player.update();

            for(Wall wall: MapObjects)
            {
                if(isColliding(wall,player)){
                    player.setCollide(true);
                }
            }



        }
    }

    public void ShowCollisionBorder(Canvas canvas){
        canvas.drawRect(player.getRectangle(),gamePaint);
        canvas.drawRect(wall.getRectangle(),gamePaint);
    }


    public boolean isColliding(GameObject a,GameObject b){

        if(Rect.intersects(a.getRectangle(),b.getRectangle())) return true;
        else return false;

    }

    public void draw(Canvas canvas) {

//        final float scaleFactorX = WIDTH/(WIDTH*1f);
//        final float scaleFactorY = HEIGHT/(HEIGHT*1.1f);

        if (canvas != null) {
            final int savedState = canvas.save();
//            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            player.draw(canvas);

            for(Wall wall: MapObjects)
            {
                wall.draw(canvas);
            }

            ShowCollisionBorder(canvas);
            canvas.restoreToCount(savedState);
        }
    }
}
