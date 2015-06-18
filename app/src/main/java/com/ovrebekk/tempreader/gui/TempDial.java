package com.ovrebekk.tempreader.gui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.ovrebekk.tempreader.R;

/**
 * Created by Admin on 6/16/15.
 */
public class TempDial  extends View {
    float mTemperature;
    final String mTemperaturePostfix = " C";

    boolean mShowText;
    String  mText, mTemperatureString;

    // TEMP stuff
    Paint mTextPaint, mTextPaintTemp, mTempPaint, mShadowPaint;

    // Graphics
    Drawable mBackground;
    float mTextX, mTextY, mTextTempX, mTextTempY;

    public TempDial(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TempDial, 0, 0);
        try {
            mTemperature = a.getFloat(R.styleable.TempDial_temperature, 20.0f);
            mShowText = a.getBoolean(R.styleable.TempDial_showText, true);
            mText = a.getString(R.styleable.TempDial_text);
        }finally {
            a.recycle();
        }
        if(mText == null) mText = "";
        mTemperatureString = String.format("%.1f", mTemperature) + mTemperaturePostfix;
        Resources res = context.getResources();
        mBackground = res.getDrawable(R.drawable.tempdial01);
        init();
    }

    public void init(){
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(60.0f);
        mTextPaintTemp = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaintTemp.setTextSize(65.0f);
        mTempPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTempPaint.setStyle(Paint.Style.FILL);
        mShadowPaint = new Paint(0);
        mShadowPaint.setColor(0xff101010);
        mShadowPaint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBackground.draw(canvas);
        canvas.drawText(mText, mTextX, mTextY, mTextPaint);
        canvas.drawText(mTemperatureString, mTextTempX, mTextTempY, mTextPaintTemp);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Set bounds
        int left = getLeft();
        int top = getTop();
        int right = getRight();
        int bottom = getBottom();
        int height = bottom - top;
        int width = right - left;
        mBackground.setBounds(left, top+10, right, bottom);

        float textHeight = mTextPaint.getTextSize();
        float textWidth = mTextPaint.measureText(mText);
        mTextX = (float)width * 0.5f + (float)left - textWidth / 2.0f;
        mTextY = (float)height * 0.5f + (float)top + (textHeight / 2.0f);
        textHeight = mTextPaintTemp.getTextSize();
        textWidth = mTextPaintTemp.measureText(mTemperatureString);
        mTextTempX = (float)width * 0.5f + (float)left - textWidth / 2.0f;
        mTextTempY = (float)height * 0.34f + (float)top + (textHeight / 2.0f);
    }

    public float isTemperature(){
        return mTemperature;
    }

    public void setTemperature(float temperature){
        mTemperature = temperature;
        mTemperatureString = String.format("%.1f", mTemperature) + mTemperaturePostfix;
        invalidate();
        requestLayout();
    }

    public boolean isShowText(){
        return mShowText;
    }

    public void setShowText(boolean showText){
        mShowText = showText;
        invalidate();
        requestLayout();
    }

    public String isText(){
        return mText;
    }

    public void setText(String text){
        mText = text;
        invalidate();
        requestLayout();
    }

}
