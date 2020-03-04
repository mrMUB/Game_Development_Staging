package com.gridsynergy.gamedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

public class Player extends GameObject{
    private Bitmap spritesheet;
    private int score;
    private double dya;
    private double dxa;
    private boolean up;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;

    private boolean ctl_UP,ctl_DOWN,ctl_RIGHT,ctl_LEFT;

    Context context;


    public Player(Context context, int w, int h, int numFrames) {
        this.context = context;
        x = MainActivity.WIDTH / 2;
        y = MainActivity.HEIGHT / 2;
        dy = 0;
        dx = 0;
        score = 0;
        height = h;
        width = w;

//        Bitmap[] image = new Bitmap[numFrames];
//        spritesheet = res;
//
//        for (int i = 0; i < image.length; i++)
//        {
//            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
//        }

        initPlayerSprite();
        animation.setFrames(walkRightSprite);
        animation.setDelay(100);
        startTime = System.nanoTime();

    }

    Bitmap[] walkLeftSprite;
    Bitmap[] walkRightSprite;
    public void initPlayerSprite(){

        int numFrames = 4;
        walkRightSprite = new Bitmap[numFrames];
        Bitmap walkRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_001);
        for (int i = 0; i < walkRightSprite.length; i++)
        {
            walkRightSprite[i] = Bitmap.createBitmap(walkRight, i*width, 0, width, height);
        }

        walkLeftSprite = new Bitmap[numFrames];
        Bitmap walkLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_002);
        for (int i = 0; i < walkLeftSprite.length; i++)
        {
            walkLeftSprite[i] = Bitmap.createBitmap(walkLeft, i*width, 0, width, height);
        }

    }

    public void setCtl_UP(boolean b){ctl_UP = b; }
    public void setCtl_DOWN(boolean b){ctl_DOWN = b;}
    public void setCtl_RIGHT(boolean b){ctl_RIGHT = b;   animation.setFrames(walkLeftSprite);}
    public void setCtl_LEFT(boolean b){ctl_LEFT = b; animation.setFrames(walkRightSprite);}

    public void setUp(boolean b){up = b;}

    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>100)
        {
            score++;
            startTime = System.nanoTime();
        }
        animation.update();

        if(ctl_UP){
            dy = -5;
        }

        if(ctl_DOWN){
            dy = 5;
        }

        if(ctl_LEFT){
            dx = -5;
        }

        if(ctl_RIGHT){
            dx = 5;
        }

        y += dy*2;
        x += dx*2;

        if(y < 24){
            y = 24;
        }

        if(y > MainActivity.HEIGHT){
            y = MainActivity.HEIGHT;
        }

        dy = 0;

        Log.i("PlayerPosition","Y: "+y);

    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }
    public int getScore(){return score;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void resetDYA(){dya = 0;}
    public void resetScore(){score = 0;}
}
