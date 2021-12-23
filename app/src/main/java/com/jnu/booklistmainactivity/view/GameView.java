package com.jnu.booklistmainactivity.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import com.jnu.booklistmainactivity.MoleSpriter;
import com.jnu.booklistmainactivity.R;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;

    public GameView(Context context) {
        super(context);
        initView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initView();
    }

    private void initView() {
        surfaceHolder=getHolder();
        surfaceHolder.addCallback(this);
    }

    private DrawThread drawThread;
    private boolean isTouched=false;
    private float touchedX=-1;
    private float touchedY=-1;
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

        drawThread = new DrawThread();
        drawThread.start();
        GameView.this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if( MotionEvent.ACTION_DOWN==motionEvent.getAction())
                {
                    touchedX=motionEvent.getX();
                    touchedY=motionEvent.getY();
                    isTouched=true;
                }
                return false;
            }
        });
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        drawThread.myStop();
        try {
            drawThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class DrawThread extends Thread
    {
        private final ArrayList<MoleSpriter> spriters=new ArrayList<>();
        private boolean isStopped=false;
        public DrawThread()
        {
            Bitmap mouseBitmap=BitmapFactory.decodeResource(getResources(),R.drawable.mouse);
            spriters.add(new MoleSpriter(100,100,mouseBitmap));
            spriters.add(new MoleSpriter(300,300,mouseBitmap));
        }

        public void myStop()
        {
            isStopped=true;
        }
        @Override
        public void run() {
            super.run();
            Canvas canvas = null;
            int hitCount=0;

            while(!isStopped) {
                try {
                    Paint paint1=new Paint();
                    canvas = surfaceHolder.lockCanvas();
                    Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.background);
                    Matrix matrix=new Matrix();
                    matrix.postScale(0.280f,0.8f);
                    canvas.drawBitmap(bitmap,matrix,paint1);
                    if(GameView.this.isTouched)
                    {
                        for(int index=0;index<spriters.size();index++)
                        {
                            if(spriters.get(index).isShot(GameView.this.touchedX,GameView.this.touchedY)){
                                hitCount++;
                            }
                        }

                    }
                    Random random=new Random();
                    //让地鼠数量从1-3随机
                    int i = random.nextInt(3)+1;
                    spriters.get(i).draw(canvas);

                    for(int index=0;index<spriters.size();index++)
                    {
                        spriters.get(index).draw(canvas);
                    }
                    GameView.this.isTouched=false;
                    Paint paint=new Paint();
                    paint.setTextSize(100);
                    paint.setColor(Color.RED);
                    canvas.drawText("hit "+hitCount,100,100,paint);

                } catch (Exception e) {

                } finally {
                    if (null != canvas) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
