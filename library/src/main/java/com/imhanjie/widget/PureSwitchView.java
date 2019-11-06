package com.imhanjie.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatCheckBox;

/**
 * Author:      Melodyxxx
 * Email:       95hanjie@gmail.com
 * Created at:  16/10/28.
 * Description:
 */

public class PureSwitchView extends AppCompatCheckBox {

    private Paint mPaint;

    // 圆角矩形高度
    private int mRoundRectHeight;
    // 圆角矩形四周半径
    private float mCornerRadius;

    // 圆角矩形
    private RectF mRectF;

    // 圆角矩形距View四周的padding
    private int mRoundRectPadding;
    // 圆距圆角矩形的内边距
    private int mCirclePadding;
    // 圆半径
    private float mRadius;

    // 两种状态圆的颜色
    private int mOnColor;
    private int mOffColor = 0x66888888;

    private int mBorderColor = mOffColor;

    private float mCircleMinX;
    private float mCircleMaxX;
    private float mCircleX;
    private float mCircleY;

    private int mProgress;

    public PureSwitchView(Context context) {
        this(context, null);
    }

    public PureSwitchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PureSwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mOnColor = Utils.getPrimaryColor(getContext());
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(dp2px(52), dp2px(27));
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mRoundRectPadding = dp2px(3); // 5dp
            mRoundRectHeight = getMeasuredHeight() - mRoundRectPadding * 2;
            mCirclePadding = dp2px(4);
            mCornerRadius = mRoundRectHeight / 2;
            mRadius = mCornerRadius - mCirclePadding;

            mRectF = new RectF(mRoundRectPadding, mRoundRectPadding, getMeasuredWidth() - mRoundRectPadding, mRoundRectPadding + mRoundRectHeight);
            mCircleMaxX = mRectF.right - mCornerRadius;
            mCircleX = mCircleMinX = mRectF.left + mCornerRadius;
            mCircleY = mRectF.centerY();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 绘制圆角矩形(边框)
        mPaint.setColor(mBorderColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dp2px(1));
        canvas.drawRoundRect(mRectF, mCornerRadius, mCornerRadius, mPaint);

        if (isChecked() && !animating) {
            mCircleX = mCircleMaxX;
        }
        // 计算当前进度
        float percent = (mCircleX - mCircleMinX) / (mCircleMaxX - mCircleMinX);
        mProgress = (int) (percent * 100);

        // 绘制圆
        mPaint.setColor(mProgress > 50 ? mOnColor : mOffColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mCircleX, mCircleY, Math.abs(0.5f - percent) * 2 * mRadius, mPaint);
    }

    private boolean animating = false;

    /**
     * 动画至指定位置
     */
    private void animateToPosition(float dstPosition) {
        if (dstPosition == 0) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(mCircleX, dstPosition);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCircleX = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                animating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animating = false;
            }
        });
        animator.start();
    }

    protected int dp2px(int pxValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pxValue, getResources().getDisplayMetrics());
    }

    @Override
    public void setChecked(final boolean checked) {
        super.setChecked(checked);
        invalidate();
        animateToPosition(checked ? mCircleMaxX : mCircleMinX);
    }
}
