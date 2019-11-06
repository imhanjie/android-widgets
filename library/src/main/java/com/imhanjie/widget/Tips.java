package com.imhanjie.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.view.ViewCompat;

import com.imhanjie.support.DimensionUtil;

/**
 * Author:      hanjie
 * Created at:  2017/4/14.
 * Description: 防重复自定义 Toast
 */

public class Tips {

    public enum Type {
        /**
         * 通知
         */
        NOTICE,
        /**
         * 成功
         */
        SUCCESS
    }

    private static Tips sTips;
    private Context mContext;
    private TextView mTextView;
    private Toast mToast;

    private Tips(Context context) {
        this.mContext = context;
        mTextView = new TextView(mContext);
        mTextView.setCompoundDrawablePadding((int) DimensionUtil.dp(9));
        mTextView.setTextColor(Color.WHITE);
        mTextView.setTextSize(13);
        mTextView.setGravity(Gravity.CENTER);
        int paddingLeftAndRight = (int) DimensionUtil.dp(25);
        int paddingTopAndBottom = (int) DimensionUtil.dp(9);
        mTextView.setPadding(paddingLeftAndRight, paddingTopAndBottom, paddingLeftAndRight, paddingTopAndBottom);
        ViewCompat.setBackground(mTextView, mContext.getResources().getDrawable(R.drawable.widget_bg_tip_toast_success));
        mToast = new Toast(mContext);
        mToast.setView(mTextView);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.BOTTOM, 0, (int) DimensionUtil.dp(70));
    }

    /**
     * 实例化入口
     */
    private static Tips with(Context context) {
        if (sTips == null) {
            synchronized (Tips.class) {
                if (sTips == null) {
                    sTips = new Tips(context.getApplicationContext());
                }
            }
        }
        return sTips;
    }

    public static void onNotice(@NonNull Context context, @StringRes int resId) {
        with(context.getApplicationContext()).notice(resId, Toast.LENGTH_SHORT);
    }

    public static void onNotice(@NonNull Context context, String tip) {
        with(context.getApplicationContext()).notice(tip, Toast.LENGTH_SHORT);
    }

    public static void onNoticeLong(@NonNull Context context, @StringRes int resId) {
        with(context.getApplicationContext()).notice(resId, Toast.LENGTH_LONG);
    }

    public static void onNoticeLong(@NonNull Context context, String tip) {
        with(context.getApplicationContext()).notice(tip, Toast.LENGTH_LONG);
    }

    public static void onSuccess(@NonNull Context context, @StringRes int tipResId) {
        with(context.getApplicationContext()).success(tipResId, Toast.LENGTH_SHORT);
    }

    public static void onSuccess(@NonNull Context context, String tip) {
        with(context.getApplicationContext()).success(tip, Toast.LENGTH_SHORT);
    }

    public static void onSuccessLong(@NonNull Context context, @StringRes int tipResId) {
        with(context.getApplicationContext()).success(tipResId, Toast.LENGTH_LONG);
    }

    public static void onSuccessLong(@NonNull Context context, String tip) {
        with(context.getApplicationContext()).success(tip, Toast.LENGTH_LONG);
    }

    // ----------------------------------------------------------------------

    private void notice(@StringRes int resId, int duration) {
        notice(mContext.getString(resId), duration);
    }

    private void notice(String s, int duration) {
        show(s, Type.NOTICE, duration);
    }

    private void success(@StringRes int resId, int duration) {
        success(mContext.getString(resId), duration);
    }

    private void success(String s, int duration) {
        show(s, Type.SUCCESS, duration);
    }

    private void show(String s, Type type, int duration) {
        mTextView.setText(s);
        switch (type) {
            case NOTICE:
                ViewCompat.setBackground(mTextView, mContext.getResources().getDrawable(R.drawable.widget_bg_tip_toast_error));
                break;
            case SUCCESS:
                ViewCompat.setBackground(mTextView, mContext.getResources().getDrawable(R.drawable.widget_bg_tip_toast_success));
                break;
        }
        mToast.setDuration(duration);
        mToast.show();
    }

}
