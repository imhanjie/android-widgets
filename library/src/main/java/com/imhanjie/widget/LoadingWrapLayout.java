package com.imhanjie.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;

import com.imhanjie.support.DimensionUtil;

/**
 * 加载包装 View
 *
 * @author hanjie
 * @date 2019-05-23
 */

public class LoadingWrapLayout extends FrameLayout {

    private View contentView;

    public LoadingWrapLayout(Context context) {
        this(context, null);
    }

    public LoadingWrapLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingWrapLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        ProgressBar pb = new ProgressBar(getContext());
        LayoutParams params = new LayoutParams(((int) DimensionUtil.dp(38)), ((int) DimensionUtil.dp(38)));
        params.gravity = Gravity.CENTER;
        pb.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.widget_color_d7d9da), PorterDuff.Mode.SRC_IN);
        addView(pb, params);
    }

    public void show() {
        for (int i = 0; i < getChildCount(); i++) {
            if (i == 0) {
                getChildAt(i).setVisibility(VISIBLE);
            } else {
                getChildAt(i).setVisibility(GONE);
            }
        }
    }

    public void hide() {
        for (int i = 0; i < getChildCount(); i++) {
            if (i == 0) {
                getChildAt(i).setVisibility(GONE);
            } else {
                getChildAt(i).setVisibility(VISIBLE);
            }
        }
    }

}

