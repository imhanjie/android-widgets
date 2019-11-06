package com.imhanjie.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.imhanjie.widget.R;
import com.imhanjie.widget.R2;
import com.imhanjie.widget.dialog.base.BaseCustomDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-08-13
 */

public class PureAlertDialog extends BaseCustomDialog {

    private OnClickListener mPositiveClickListener;
    private OnClickListener mNegativeClickListener;
    private OnClickListener mNeutralClickListener;

    @BindView(R2.id.tv_title)
    TextView mTitleTv;
    @BindView(R2.id.tv_content)
    TextView mContentTv;
    @BindView(R2.id.btn_positive)
    TextView mPositiveBtn;
    @BindView(R2.id.btn_negative)
    TextView mNegativeBtn;
    @BindView(R2.id.btn_neutral)
    TextView mNeutralBtn;

    @Override
    public int getWindowGravity() {
        return Gravity.CENTER;
    }

    @Override
    public int getWindowAnimation() {
        return R.style.WidgetCenterDialogAnimation;
    }

    public PureAlertDialog(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.widget_dialog_alert;
    }

    @Override
    public void initView(View view) {

    }

    @OnClick(R2.id.btn_positive)
    void onPositiveClick() {
        if (mPositiveClickListener != null) {
            mPositiveClickListener.onClick(this);
        }
    }

    @OnClick(R2.id.btn_negative)
    void onNegativeClick() {
        if (mNegativeClickListener != null) {
            mNegativeClickListener.onClick(this);
        }
        dismiss();
    }

    @OnClick(R2.id.btn_neutral)
    void onNeutralClick() {
        if (mNeutralClickListener != null) {
            mNeutralClickListener.onClick(this);
        }
        dismiss();
    }

    public PureAlertDialog withTitle(String title) {
        if (title == null) {
            mTitleTv.setVisibility(View.GONE);
        } else {
            mTitleTv.setVisibility(View.VISIBLE);
            mTitleTv.setText(title);
        }
        return this;
    }

    public PureAlertDialog withContent(String content) {
        mContentTv.setText(content);
        return this;
    }

    public PureAlertDialog withCancelable(boolean cancelable) {
        setCancelable(cancelable);
        return this;
    }

    public PureAlertDialog withPositiveText(String text) {
        if (text == null) {
            mPositiveBtn.setVisibility(View.GONE);
        } else {
            mPositiveBtn.setVisibility(View.VISIBLE);
            mPositiveBtn.setText(text);
        }
        return this;
    }

    public PureAlertDialog withPositiveClick(OnClickListener listener) {
        mPositiveClickListener = listener;
        return this;
    }

    public PureAlertDialog withNegativeText(String text) {
        if (text == null) {
            mNegativeBtn.setVisibility(View.GONE);
        } else {
            mNegativeBtn.setVisibility(View.VISIBLE);
            mNegativeBtn.setText(text);
        }
        return this;
    }

    public PureAlertDialog withNegativeClick(OnClickListener listener) {
        mNegativeClickListener = listener;
        return this;
    }

    public PureAlertDialog withNeutralText(String text) {
        if (text == null) {
            mNeutralBtn.setVisibility(View.GONE);
        } else {
            mNeutralBtn.setVisibility(View.VISIBLE);
            mNeutralBtn.setText(text);
        }
        return this;
    }

    public PureAlertDialog withNeutralClick(OnClickListener listener) {
        mNeutralClickListener = listener;
        return this;
    }

    public interface OnClickListener {
        void onClick(Dialog dialog);
    }

}
