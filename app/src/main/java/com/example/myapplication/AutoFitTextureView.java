package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;


public class AutoFitTextureView extends TextureView{

    int mRatioWidth ;
    int mRatioHeight ;

    public void setAspectRatio( int mRatioWidth, int mRatioHeight ){
        this.mRatioHeight = mRatioHeight ;
        this.mRatioWidth = mRatioWidth ;
        requestLayout();
    }

    public AutoFitTextureView(Context context) {
        super(context);
    }

    public AutoFitTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoFitTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec) ;

        if ( 0 == mRatioWidth || 0==mRatioHeight){
            setMeasuredDimension(w,h);
        }else {
            if (w < h * mRatioWidth / mRatioHeight){
                setMeasuredDimension(w , mRatioHeight * w  /mRatioWidth );
            }else {
                setMeasuredDimension(h*mRatioWidth / mRatioHeight , h);
            }
        }
    }
}
