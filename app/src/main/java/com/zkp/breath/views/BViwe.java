package com.zkp.breath.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created b Zwp on 2019/8/13.
 */
public class BViwe extends View {

    public BViwe(Context context) {
        this(context, null);
    }

    public BViwe(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("BViwe", "dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("BViwe", "onTouchEvent: ");
        return super.onTouchEvent(event);
    }
}
