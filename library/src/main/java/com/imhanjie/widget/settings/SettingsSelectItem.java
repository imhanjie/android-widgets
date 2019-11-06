package com.imhanjie.widget.settings;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.imhanjie.widget.R;
import com.imhanjie.widget.dialog.PureAlertDialog;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-08-25
 */

public class SettingsSelectItem extends ConstraintLayout implements Changeable {

    private TextView mTitleTv;
    private ImageView mTipsIv;
    private TextView mDescTv;
    private View mDividerView;

    public SettingsSelectItem(Context context) {
        this(context, null);
    }

    public SettingsSelectItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingsSelectItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.widget_item_settings_select, this, true);
        findViews();
        readAttrs(attrs);
    }

    private void findViews() {
        mTitleTv = findViewById(R.id.tv_title);
        mTipsIv = findViewById(R.id.iv_tips);
        mDescTv = findViewById(R.id.tv_desc);
        mDividerView = findViewById(R.id.view_divider);
    }

    @SuppressLint("CustomViewStyleable")
    private void readAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.WidgetSettingsSelectItem);

        // title
        mTitleTv.setText(a.getString(R.styleable.WidgetSettingsSelectItem_widget_item_titleText));

        // tips
        String tips = a.getString(R.styleable.WidgetSettingsSelectItem_widget_settings_tips);
        if (!TextUtils.isEmpty(tips)) {
            mTipsIv.setVisibility(VISIBLE);
            mTipsIv.setOnClickListener(v -> new PureAlertDialog(getContext())
                    .withTitle("提示")
                    .withContent(tips)
                    .withPositiveText("我知道了")
                    .withPositiveClick(Dialog::dismiss)
                    .withNegativeText(null)
                    .show());
        } else {
            mTipsIv.setVisibility(INVISIBLE);
        }

        // desc
        mDescTv.setText(a.getString(R.styleable.WidgetSettingsSelectItem_widget_item_descText));

        // divider
        boolean showDivider = a.getBoolean(R.styleable.WidgetSettingsSelectItem_widget_divider, true);
        mDividerView.setVisibility(showDivider ? VISIBLE : INVISIBLE);

        a.recycle();
    }

    public void setTitleText(CharSequence titleText) {
        mTitleTv.setText(titleText);
    }

    public void setDescText(CharSequence descText) {
        mDescTv.setText(descText);
    }

    @Override
    public void onActivated() {
        setAlpha(1.0f);
        setClickable(true);
    }

    @Override
    public void onDisabled() {
        setAlpha(0.3f);
        setClickable(false);
    }

}
