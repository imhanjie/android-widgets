package com.imhanjie.widget.recyclerview.adapter.common;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author:      hanjie
 * Created at:  2017/4/13.
 * Description:
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    public ViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }

    public static ViewHolder get(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        ViewHolder holder = new ViewHolder(context, itemView, parent);
        return holder;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    // ---------------- 可自行根据需求扩展 ----------------
    public View getConvertView() {
        return mConvertView;
    }

    public void setText(int viewId, int resId) {
        ((TextView) getView(viewId)).setText(resId);
    }

    public void setText(int viewId, String s) {
        setText(viewId, s, "");
    }

    public void setText(int viewId, CharSequence s) {
        setText(viewId, s, "");
    }

    public void setText(int viewId, String s, @NonNull String def) {
        String showStr = s;
        if (TextUtils.isEmpty(s)) {
            showStr = def;
        }
        ((TextView) getView(viewId)).setText(showStr);
    }

    public void setText(int viewId, CharSequence s, @NonNull String def) {
        CharSequence showStr = s;
        if (TextUtils.isEmpty(s)) {
            showStr = def;
        }
        ((TextView) getView(viewId)).setText(showStr);
    }

    public void setTextColor(int viewId, int color) {
        ((TextView) getView(viewId)).setTextColor(color);
    }

    public void setImage(int viewId, int resId) {
        ((ImageView) getView(viewId)).setImageResource(resId);
    }

    public void setImageDrawable(int viewId, Drawable drawable) {
        ((ImageView) getView(viewId)).setImageDrawable(drawable);
    }

    public void setImageTint(int viewId, int color) {
        if (color != 0) {
            ((ImageView) getView(viewId)).setColorFilter(color);
        } else {
            ((ImageView) getView(viewId)).setColorFilter(null);
        }
    }

    public void setVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
    }

    public void setDrawableLeft(int viewId, @DrawableRes int resId) {
        ((TextView) getView(viewId)).setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
    }

    public void setDrawableLeft(int viewId, Drawable drawable) {
        ((TextView) getView(viewId)).setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    public void setDrawableStart(int viewId, @DrawableRes int resId) {
        ((TextView) getView(viewId)).setCompoundDrawablesRelativeWithIntrinsicBounds(resId, 0, 0, 0);
    }

    public void setDrawableStart(int viewId, Drawable drawable) {
        ((TextView) getView(viewId)).setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
    }

    public void setDrawableRight(int viewId, @DrawableRes int resId) {
        ((TextView) getView(viewId)).setCompoundDrawablesWithIntrinsicBounds(0, 0, resId, 0);
    }

    public void setDrawableRight(int viewId, Drawable drawable) {
        ((TextView) getView(viewId)).setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }

    public void setDrawableTop(int viewId, @DrawableRes int resId) {
        ((TextView) getView(viewId)).setCompoundDrawablesWithIntrinsicBounds(0, resId, 0, 0);
    }

    public void setDrawableTop(int viewId, Drawable drawable) {
        ((TextView) getView(viewId)).setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
    }

    public void setDrawableBottom(int viewId, @DrawableRes int resId) {
        ((TextView) getView(viewId)).setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, resId);
    }

    public void setDrawableBottom(int viewId, Drawable drawable) {
        ((TextView) getView(viewId)).setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable);
    }

    public void setBackgroundColor(int viewId, @ColorInt int color) {
        getView(viewId).setBackgroundColor(color);
    }

    public void setBackgroundResource(int viewId, @DrawableRes int resId) {
        getView(viewId).setBackgroundResource(resId);
    }

    public void setCompoundDrawables(int viewId, Drawable leftDrawable, Drawable topDrawable, Drawable rightDrawable, Drawable bottomDrawable) {
        TextView view = getView(viewId);
        if (leftDrawable != null) {
            leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        }
        if (topDrawable != null) {
            topDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        }
        if (rightDrawable != null) {
            rightDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        }
        if (bottomDrawable != null) {
            bottomDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        }
        view.setCompoundDrawables(leftDrawable, topDrawable, rightDrawable, bottomDrawable);
    }

}