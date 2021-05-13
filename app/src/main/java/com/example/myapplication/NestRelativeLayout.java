package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class NestRelativeLayout extends RelativeLayout {
    private int startX = 0;
    //private boolean beginScroll = false;
    private int startY = 0;
    public NestRelativeLayout(Context context) {
        super(context);
    }

    public NestRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = (int)ev.getX();
                startY = (int)ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int)ev.getX();
                int endY = (int)ev.getY();
                int disX = Math.abs(endX-startX);
                int disY = Math.abs(endY-startY);
                if(disX<disY){
                    //if(!beginScroll)
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
