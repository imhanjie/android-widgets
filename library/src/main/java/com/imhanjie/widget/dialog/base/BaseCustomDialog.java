package com.imhanjie.widget.dialog.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.imhanjie.widget.R;

import butterknife.ButterKnife;


public abstract class BaseCustomDialog extends Dialog {

    /**
     * 获取布局
     */
    public abstract int getLayoutId();

    /**
     * 获取动画
     */
    public int getWindowAnimation() {
        return R.style.WidgetBottomDialogAnimation;
    }

    private Context mContext;
    private View mRootView;

    public BaseCustomDialog(Context context) {
        this(context, R.style.Widget_Pure_Custom_Dialog);
    }

    public BaseCustomDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        if (getLayoutId() == 0) {
            throw new Resources.NotFoundException();
        }
        mRootView = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
        setContentView(mRootView);

        // ButterKnife 绑定
        ButterKnife.bind(this);

        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = getWindowGravity();
        window.setAttributes(lp);
        window.setWindowAnimations(getWindowAnimation());
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView(mRootView);
    }

    public View getRootView() {
        return mRootView;
    }

    public int getWindowGravity() {
        return Gravity.BOTTOM;
    }

    public abstract void initView(View view);

    protected int getResColor(int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }

    protected Drawable getResDrawable(int resId) {
        return ContextCompat.getDrawable(getContext(), resId);
    }

    protected String getResString(int resId, Object... formatArgs) {
        return getContext().getString(resId, formatArgs);
    }

    public Activity getActivity(@Nullable Context context) {
        if (context == null) {
            return null;
        } else if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return getActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

}
