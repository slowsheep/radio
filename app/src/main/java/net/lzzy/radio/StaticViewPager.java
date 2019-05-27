package net.lzzy.radio;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author lzzy_gxy on 2019/5/27.
 * Description:
 */
public class StaticViewPager extends ViewPager {
    private boolean slide = false;

    public StaticViewPager(@NonNull Context context) {
        super(context);
    }

    public StaticViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void enableSlide(boolean enabled){
        slide = enabled;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (slide){
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (slide){
            return super.onTouchEvent(ev);
        }
        return true;
    }
}
