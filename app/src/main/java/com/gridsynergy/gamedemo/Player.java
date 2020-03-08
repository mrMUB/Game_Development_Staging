package com.gridsynergy.gamedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Player extends GameObject{
    private Bitmap spritesheet;
    private int score;
    private int playerSpeed = 6;
    private double dya;
    private double dxa;
    private boolean up;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;

    private boolean collide;

    private boolean ctl_UP,ctl_DOWN,ctl_RIGHT,ctl_LEFT;

    String Logs = "Player Logs";

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


        initPlayerSprite();
        animation.setFrames(walkRightSprite);
        animation.setDelay(100);
        startTime = System.nanoTime();

    }

    Bitmap[] walkLeftSprite;
    Bitmap[] walkRightSprite;
    Bitmap[] walkUpSprite;
    Bitmap[] walkDownSprite;
    public void initPlayerSprite(){

        int numFrames = 4;
        walkRightSprite = new Bitmap[numFrames];
        Bitmap walkRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_p1_right_dir);
        walkRight = Bitmap.createScaledBitmap(walkRight, 200, 50, false);
        for (int i = 0; i < walkRightSprite.length; i++)
        {
            walkRightSprite[i] = Bitmap.createBitmap(walkRight, i*width, 0, width, height);
        }

        walkLeftSprite = new Bitmap[numFrames];
        Bitmap walkLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_p1_left_dir_100px);
        walkLeft = Bitmap.createScaledBitmap(walkLeft, 200, 50, false);
        for (int i = 0; i < walkLeftSprite.length; i++)
        {
            walkLeftSprite[i] = Bitmap.createBitmap(walkLeft, i*width, 0, width, height);
        }

        walkUpSprite = new Bitmap[numFrames];
        Bitmap walkUp = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_p1_up_dir_100px);
        walkUp = Bitmap.createScaledBitmap(walkUp, 200, 50, false);
        for (int i = 0; i < walkUpSprite.length; i++)
        {
            walkUpSprite[i] = Bitmap.createBitmap(walkUp, i*width, 0, width, height);
        }

        walkDownSprite = new Bitmap[numFrames];
        Bitmap walkDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_p1_down_dir_100px);
        walkDown = Bitmap.createScaledBitmap(walkDown, 200, 50, false);
        for (int i = 0; i < walkDownSprite.length; i++)
        {
            walkDownSprite[i] = Bitmap.createBitmap(walkDown, i*width, 0, width, height);
        }

    }


    public void setCollide(boolean b){collide = b;}

    public void setUp(boolean b){up = b;}

    private int playerAction = 0;
    private int collideDirection = 0;
    public void setPlayerAction(int action){


        // change player sprite
        if(playerAction != action){

            Log.i(Logs, "Player action changed to: "+ action);
            switch (action){
                case 1:
                    animation.setFrames(walkUpSprite);
                    break;
                case 2:
                    animation.setFrames(walkDownSprite);
                    break;
                case 3:
                    animation.setFrames(walkLeftSprite);
                    break;
                case 4:
                    animation.setFrames(walkRightSprite);
                    break;
            }
        }


        playerAction = action;
    }

    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>100)
        {
            score++;
            startTime = System.nanoTime();
        }
        animation.update();



        switch (playerAction){
            case 1:
                dy = -playerSpeed;
                break;
            case 2:
                dy = playerSpeed;
                break;
            case 3:
                dx = -playerSpeed;
                break;
            case 4:
                dx = playerSpeed;
                break;
        }


        if(collide){
            if(collideDirection == 0) collideDirection = playerAction;
            switch (collideDirection){
                case 1:
                    y += 1;
                    break;
                case 2:
                    y -= 1;
                    break;
                case 3:
                    x += 1;
                    break;
                case 4:
                    x -= 1;
                    break;
            }
            playerAction = 0;
        }else{
            collideDirection = 0;

            y += dy;
            x += dx;

            if(y < 24){
                y = 24;
            }

            if(y > MainActivity.HEIGHT-100){
                y = MainActivity.HEIGHT-100;
            }
        }


        dy = 0;
        dx = 0;

        Log.i("PlayerPosition","Y:"+y+" X:"+x);

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
