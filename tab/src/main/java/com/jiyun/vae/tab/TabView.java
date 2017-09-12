package com.jiyun.vae.tab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 冯玉苗 on 2017/9/11.
 */

public class TabView extends View {
    public TabView(Context context) {
        super(context);

    }

    public TabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Paint paint=new Paint();
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(4);
            paint.setTextSize(25);
            Paint paint1=new Paint();
            paint1.setColor(Color.BLUE);
            paint1.setStrokeWidth(4);
            paint1.setTextSize(40);

            Paint paint2=new Paint();
            paint2.setColor(Color.RED);
            paint2.setStrokeWidth(4);
            paint2.setTextSize(30);
            canvas.drawLine(40,160,canvas.getWidth()-40,160,paint);
            canvas.drawText("课程表",400,120,paint1);

            canvas.drawLine(50,250,canvas.getWidth()-40,250,paint);
            canvas.drawLine(50,350,canvas.getWidth()-40,350,paint);
            canvas.drawLine(50,450,canvas.getWidth()-40,450,paint);
            canvas.drawLine(50,550,canvas.getWidth()-40,550,paint);
            canvas.drawLine(50,650,canvas.getWidth()-40,650,paint);
            canvas.drawLine(50,750,canvas.getWidth()-40,750,paint);
            canvas.drawLine(50,850,canvas.getWidth()-40,850,paint);
            canvas.drawLine(50,950,canvas.getWidth()-40,950,paint);

            canvas.drawLine(50,250,150,350,paint);

            canvas.drawLine(50,950,50,250,paint);
            canvas.drawLine(150,950,150,250,paint);
            canvas.drawLine(250,950,250,250,paint);
            canvas.drawLine(350,950,350,250,paint);
            canvas.drawLine(450,950,450,250,paint);
            canvas.drawLine(550,950,550,250,paint);
            canvas.drawLine(650,950,650,250,paint);
            canvas.drawLine(750,950,750,250,paint);
            canvas.drawLine(850,950,850,250,paint);
            canvas.drawLine(950,950,950,250,paint);
            canvas.drawLine(1040,950,1040,250,paint);

            canvas.drawText("课程",85,280,paint);
            canvas.drawText("日期",55,330,paint);
            canvas.drawText("一",80,420,paint1);
            canvas.drawText("二",80,520,paint1);
            canvas.drawText("三",80,620,paint1);
            canvas.drawText("四",80,720,paint1);
            canvas.drawText("五",80,820,paint1);
            canvas.drawText("六",80,920,paint1);



            canvas.drawText("语文",160,310,paint1);
            canvas.drawText("数学",260,310,paint1);
            canvas.drawText("化学",360,310,paint1);
            canvas.drawText("音乐",460,310,paint1);
            canvas.drawText("物理",560,310,paint1);
            canvas.drawText("生物",660,310,paint1);
            canvas.drawText("体育",760,310,paint1);
            canvas.drawText("活动",860,310,paint1);
            canvas.drawText("历史",960,310,paint1);
            for (int i = 1; i <11 ; i++) {
                canvas.drawText("第"+i+"节",(i*100)+60,410,paint2);
            }
            for (int i = 1; i <11 ; i++) {
                canvas.drawText("第"+i+"节",(i*100)+60,510,paint2);
            }
            for (int i = 1; i <11 ; i++) {
                canvas.drawText("第"+i+"节",(i*100)+60,610,paint2);
            }
            for (int i = 1; i <11 ; i++) {
                canvas.drawText("第"+i+"节",(i*100)+60,710,paint2);
            }
            for (int i = 1; i <11 ; i++) {
                canvas.drawText("第"+i+"节",(i*100)+60,810,paint2);
            }
            for (int i = 1; i <11 ; i++) {
                canvas.drawText("自习",(i*100)+60,910,paint2);
            }
        }
    }
