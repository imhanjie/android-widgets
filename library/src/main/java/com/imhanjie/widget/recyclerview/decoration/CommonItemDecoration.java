package com.imhanjie.widget.recyclerview.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by hanjie on 2017/4/14.
 * <p>
 * 通用 RecyclerView 分割线
 * 适用情况不断完善中..目前仅支持横向，纵向日后再说
 * update: 2017/5/4.
 */

public class CommonItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private Drawable mBackground;
    private int mDividerHeight;
    private int mMarginStart;
    private int mMarginEnd;

    public CommonItemDecoration(Context context, int dividerColor, int dividerHeight, int marginStart, int marginEnd) {
        this(context, dividerColor, dividerHeight, marginStart, marginEnd, Color.TRANSPARENT);
    }

    public CommonItemDecoration(Context context, int dividerColor, int dividerHeight, int marginStart, int marginEnd, int backgroundColor) {
        mBackground = createDrawable(context, backgroundColor);
        mDivider = createDrawable(context, dividerColor);
        this.mDividerHeight = dividerHeight;
        this.mMarginStart = marginStart;
        this.mMarginEnd = marginEnd;
    }

    public GradientDrawable createDrawable(Context context, int dividerColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(dividerColor);
        return drawable;
    }

    /**
     * 是否跳过绘制，子类可以重写该方法
     *
     * @param position 位置
     */
    protected boolean isSkip(int position) {
        return false;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft() + mMarginStart;
        int right = parent.getWidth() - parent.getPaddingRight() - mMarginEnd;
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            int pos = parent.getChildAdapterPosition(child);
            if (pos == -1) {
                continue;
            }
            if (isSkip(pos)) {
                continue;
            }
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDividerHeight;
            mBackground.setBounds(0, top, parent.getWidth(), bottom);
            mBackground.draw(c);
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mDividerHeight);
    }


}