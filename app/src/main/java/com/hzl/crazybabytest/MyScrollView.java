package com.hzl.crazybabytest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by hzl on 2016/12/22.
 */
public class MyScrollView extends ScrollView {

    private float oldY;
    private int newY;

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ScrollView parentScrollView;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (parentScrollView == null) {
            return super.onInterceptTouchEvent(ev);
        } else {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //记录位置
                    oldY = ev.getY();
                    //手指按下的时候，获得滑动事件，也就是让父scrollview失去滑动事件
                    setParentScrollAble(false);
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    setParentScrollAble(true);

                    break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //获取scrollview的子view
        View contentView = getChildAt(0);
        if (parentScrollView != null) {
            if (ev.getAction() == MotionEvent.ACTION_MOVE) {
                //子View的高度
                int height = contentView.getMeasuredHeight();
                //scrollview的高度
                int measuredHeight = getMeasuredHeight();
                //可滑动的距离
                int scrollH = height - measuredHeight;
                //得到y轴的滚动距离
                int scrollY = getScrollY();
                //记录最新的位置
                newY = (int) ev.getY();
                // Y轴向下 X轴向右 旧的Y轴位置小于新的Y轴位置  则手指向下滑动
                if (oldY < newY) {
                    //滚动距离小于等于0则说明滚动到最底部
                    if (scrollY <= 0) {
                        //就把滚动交给父Scrollview
                        setParentScrollAble(true);
                        return false;
                    } else {
                        //否则继续屏蔽父级的滚动事件
                        setParentScrollAble(false);
                    }
                } else if (oldY > newY) {
                    if (scrollY >= scrollH) {
                        //如果向上滑动到头，就把滚动交给父Scrollview
                        setParentScrollAble(true);
                        return false;
                    } else {
                        setParentScrollAble(false);
                    }
                }
                //重置Y轴位置
                oldY = newY;
            }
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 是否把滚动事件交给父scrollview
     *
     * @param flag
     */
    private void setParentScrollAble(boolean flag) {

        parentScrollView.requestDisallowInterceptTouchEvent(!flag);
    }
}
