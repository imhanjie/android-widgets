package com.imhanjie.widget.model;

import android.app.Dialog;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-08-14
 */

public class ListMenuItem {

    public interface OnClickListener {
        void onClick(Dialog dialog);
    }

    private String name;

    private int drawableRes;

    private OnClickListener onClickListener;

    public ListMenuItem(String name, int drawableRes, OnClickListener listener) {
        this.name = name;
        this.drawableRes = drawableRes;
        this.onClickListener = listener;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawableRes() {
        return drawableRes;
    }

    public void setDrawableRes(int drawableRes) {
        this.drawableRes = drawableRes;
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
