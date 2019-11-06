package com.imhanjie.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.imhanjie.widget.R;
import com.imhanjie.widget.dialog.base.BaseCustomDialog;

/**
 * Loading dialog
 *
 * @author hanjie
 * @date 2019-08-13
 */

public class PureLoadingDialog extends BaseCustomDialog {

    public PureLoadingDialog(Context context) {
        super(context);
    }

    @Override
    public int getWindowGravity() {
        return Gravity.CENTER;
    }

    @Override
    public int getWindowAnimation() {
        return R.style.WidgetCenterDialogAnimation;
    }

    @Override
    public int getLayoutId() {
        return R.layout.widget_dialog_loading;
    }

    @Override
    public void initView(View view) {
    }

    @Override
    public void show() {
        try {
            Activity activity = getActivity(getContext());
            if (activity == null || activity.isFinishing()) {
                return;
            }
            if (isShowing()) {
                return;
            }
            super.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        try {
            Activity activity = getActivity(getContext());
            if (activity == null || activity.isFinishing()) {
                return;
            }
            if (!isShowing()) {
                return;
            }
            super.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
