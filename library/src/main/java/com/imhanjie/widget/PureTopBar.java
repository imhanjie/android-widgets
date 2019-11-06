package com.imhanjie.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-06-24
 */

public class PureTopBar extends RelativeLayout {

    private TextView mTitleTv;
    private ImageView mTitleStartIv;
    private TextView mRightTv;
    private TextView mRightSecondaryTv;
    private ImageView mLeftIv;

    public PureTopBar(Context context) {
        this(context, null);
    }

    public PureTopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PureTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.widget_top_bar, this, true);
        findViews();
        readAttrs(attrs);
        setDefaultEvents();
    }

    private void findViews() {
        mTitleTv = findViewById(R.id.tv_title);
        mTitleStartIv = findViewById(R.id.iv_title_start);
        mRightTv = findViewById(R.id.tv_right);
        mRightSecondaryTv = findViewById(R.id.tv_right_secondary);
        mLeftIv = findViewById(R.id.iv_left);
    }

    /**
     * read attrs from xml
     */
    @SuppressLint("CustomViewStyleable")
    private void readAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.WidgetPureTopBar);

        // title
        mTitleTv.setText(a.getString(R.styleable.WidgetPureTopBar_widget_bar_titleText));

        // right
        mRightTv.setText(a.getString(R.styleable.WidgetPureTopBar_widget_bar_rightText));
        Drawable rightDrawable = a.getDrawable(R.styleable.WidgetPureTopBar_widget_bar_rightIcon);
        mRightTv.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, rightDrawable, null);
        mRightTv.setTextColor(a.getColor(R.styleable.WidgetPureTopBar_widget_bar_rightTextColor, Color.BLACK));
        mRightTv.setVisibility(a.getInt(R.styleable.WidgetPureTopBar_widget_bar_rightVisibility, VISIBLE));

        // right secondary
        mRightSecondaryTv.setText(a.getString(R.styleable.WidgetPureTopBar_widget_bar_rightSecondaryText));
        Drawable rightSecondaryDrawable = a.getDrawable(R.styleable.WidgetPureTopBar_widget_bar_rightSecondaryIcon);
        mRightSecondaryTv.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, rightSecondaryDrawable, null);
        mRightSecondaryTv.setTextColor(a.getColor(R.styleable.WidgetPureTopBar_widget_bar_rightSecondaryTextColor, Color.BLACK));
        mRightSecondaryTv.setVisibility(a.getInt(R.styleable.WidgetPureTopBar_widget_bar_rightSecondaryVisibility, VISIBLE));

        // left
        mLeftIv.setVisibility(a.getInt(R.styleable.WidgetPureTopBar_widget_bar_leftVisibility, VISIBLE));

        // 处理 light mode
        boolean isLightMode = a.getBoolean(R.styleable.WidgetPureTopBar_widget_bar_light_mode, false);
        if (isLightMode) {
            mTitleTv.setTextColor(ContextCompat.getColor(getContext(), R.color.widget_color_text_black_1));
            mLeftIv.setColorFilter(ContextCompat.getColor(getContext(), R.color.widget_color_text_black_1));
        }

        a.recycle();
    }

    /**
     * 设置默认的点击事件
     */
    private void setDefaultEvents() {
        setOnLeftClickListener(v -> {
            if (getContext() instanceof Activity) {
                ((Activity) getContext()).onBackPressed();
            }
        });
    }

    public void setOnRightClickListener(OnClickListener listener) {
        mRightTv.setOnClickListener(listener);
    }

    public void setOnRightSecondaryClickListener(OnClickListener listener) {
        mRightSecondaryTv.setOnClickListener(listener);
    }

    public void setOnLeftClickListener(OnClickListener listener) {
        mLeftIv.setOnClickListener(listener);
    }

    public void setTitleText(String s) {
        mTitleTv.setText(s);
    }

    public void setTitleText(@StringRes int resId) {
        mTitleTv.setText(resId);
    }

    public void setRightVisibility(int visibility) {
        mRightTv.setVisibility(visibility);
    }

    public void setTitleStartIcon(@DrawableRes int iconRes) {
        if (iconRes != 0) {
            mTitleStartIv.setVisibility(VISIBLE);
            mTitleStartIv.setImageResource(iconRes);
        } else {
            mTitleStartIv.setVisibility(GONE);
        }
    }

}
