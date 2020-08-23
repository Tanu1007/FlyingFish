package com.tanvi.flyingfish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

public class FlyingFishView extends View
{
    private Bitmap fish[] = new Bitmap[2];
    private int fishx = 10;
    private int fishy;
    private int fishspeed;

    private int yellowx,yellowy,yellowspeed;
    private Paint yellowPaint = new Paint();

    private int redx,redy,redspeed;
    private Paint redPaint = new Paint();

    private int greenx,greeny,greenspeed;
    private Paint greenPaint = new Paint();

    private int canvaswidth;
    private int canvasheight;
    private Bitmap backgroundImg;
    private Paint scorePaint = new Paint();
    private Bitmap life[] = new Bitmap[2];
    private boolean touch = false;

    private int score, lifecounterFish;

    public FlyingFishView(Context context){
        super(context);
        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);
        backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        fishy = 550;
        score = 0;
        lifecounterFish = 3;
        greenspeed=17;
        yellowspeed=15;
        redspeed=20;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvaswidth = canvas.getWidth();
        canvasheight = canvas.getHeight();
        canvas.drawBitmap(backgroundImg, 0,0, null);
        int minfishy = fish[0].getHeight();
        int maxfishy = canvasheight - fish[0].getHeight()*3;
        fishy = fishy +fishspeed;
        if (fishy < minfishy){
            fishy = minfishy;
        }
        if (fishy > maxfishy){
            fishy = maxfishy;
        }
        fishspeed = fishspeed + 2;
        if(touch){
            canvas.drawBitmap(fish[1],fishx,fishy,null);
            touch = false;
        }
        else{
            canvas.drawBitmap(fish[0],fishx,fishy,null);
        }
        //yellowspeed= (int) Math.floor(yellowspeed+0.3);
        yellowx = yellowx - yellowspeed;
        if(hitBallChecker(yellowx,yellowy)){
            score = score + 10;
            yellowx = - 100;
        }

        if(yellowx < 0){
            yellowx = canvaswidth + 21;
            yellowy = (int) Math.floor(Math.random()*(maxfishy - minfishy) + minfishy);
        }
        canvas.drawCircle(yellowx, yellowy, 20, yellowPaint);

        //greenspeed= (int) Math.floor(greenspeed+0.5);
        greenx = greenx - greenspeed;
        if(hitBallChecker(greenx,greeny)){
            score = score + 20;
            greenx = - 100;
        }

        if(greenx < 0){
            greenx = canvaswidth + 21;
            greeny = (int) Math.floor(Math.random()*(maxfishy - minfishy) + minfishy);
        }
        canvas.drawCircle(greenx, greeny, 25, greenPaint);

        //redspeed=(int) Math.floor(redspeed+0.3);
        redx = redx - redspeed;
        if(hitBallChecker(redx,redy)){
            lifecounterFish = lifecounterFish - 1;
            redx = - 100;

            if(lifecounterFish == 0){
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();

                Intent gameoverIntent = new Intent(getContext(), GameOverActivity.class);
                gameoverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameoverIntent.putExtra("score", score);
                getContext().startActivity(gameoverIntent);
            }
        }

        if(redx < 0){
            redx = canvaswidth + 21;
            redy = (int) Math.floor(Math.random()*(maxfishy - minfishy) + minfishy);
        }
        canvas.drawCircle(redx, redy, 30, redPaint);

        canvas.drawText("Score : " + score, 20,60,scorePaint);

        for (int i=0;i<3;i++){
            int x = (int) (500+ life[0].getWidth() * 1.5 *i);
            int y = 30;
            if (i<lifecounterFish){
                canvas.drawBitmap(life[0],x,y,null);
            }
            else{
                canvas.drawBitmap(life[1],x,y,null);
            }
        }
    }

    public boolean hitBallChecker(int x, int y){
        if(fishx < x && x < (fishx + fish[0].getWidth()) && fishy < y && y < (fishy + fish[0].getHeight()) )
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            touch = true;
            fishspeed = -22;
        }
        return true;
    }
}
