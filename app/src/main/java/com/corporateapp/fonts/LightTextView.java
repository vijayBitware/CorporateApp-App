package com.corporateapp.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by bitware on 3/6/17.
 */

public class LightTextView extends TextView {
    public LightTextView(Context context) {
        super(context);
        setFont();
    }

    public LightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public LightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LightTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setFont();
    }

    public void setFont() {

        Typeface typedValue = Typeface.createFromAsset(getContext().getAssets(), "roboto_light.ttf");
        setTypeface(typedValue);
    }
}
