package com.jnu.booklistmainactivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.util.Random;

public class MoleSpriter {
    float x,y;
    Bitmap bitmap;
    int X[]={150,450,750};
    int Y[]={450,850,1350};

    public MoleSpriter(float x, float y,Bitmap bitmap){
        this.x=x;
        this.y=y;
        this.bitmap=bitmap;
    }
    public void draw(Canvas canvas)
    {
        Paint paint=new Paint();
        Random random=new Random();
        int i,j;
        //让地鼠在九个格子中随机出现
        i=random.nextInt(3);
        j=random.nextInt(3);
        x=X[i];
        y=Y[j];
        canvas.drawBitmap(bitmap,x,y,paint);
    }

    public boolean isShot(float touchedX, float touchedY) {
        //点击到长和宽为200的正方形即算击中地鼠
        if ((touchedX > x && touchedX < x + 200) && (touchedY > y && touchedY < y + 200))
            return true;
        else
            return false;
    }
}
