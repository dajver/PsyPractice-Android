package com.project.dajver.psypractice.ui.details.task.parser;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by gleb on 11/7/17.
 */

public class URLDrawable extends BitmapDrawable {

    protected Drawable drawable;

    @Override
    public void draw(Canvas canvas) {
        if(drawable != null) {
            drawable.draw(canvas);
        }
    }
}