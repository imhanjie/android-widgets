package com.imhanjie.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imhanjie.widget.R;
import com.imhanjie.widget.R2;
import com.imhanjie.widget.Utils;
import com.imhanjie.widget.dialog.base.BaseCustomDialog;
import com.imhanjie.widget.model.ChoiceMenuItem;
import com.imhanjie.widget.recyclerview.adapter.common.CommonAdapter;
import com.imhanjie.widget.recyclerview.adapter.common.ViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-08-14
 */

public class PureSingleChoiceDialog<T> extends BaseCustomDialog {

    public interface OnClickListener {
        void onClick(Dialog dialog);
    }

    public interface OnChooseListener<T> {
        void onChoose(Dialog dialog, ChoiceMenuItem<T> menu);
    }

    @BindView(R2.id.tv_title)
    TextView mTitleTv;
    @BindView(R2.id.rv_menu)
    RecyclerView mMenuRv;
    @BindView(R2.id.btn_positive)
    TextView mPositiveBtn;
    @BindView(R2.id.btn_negative)
    TextView mNegativeBtn;

    private OnChooseListener<T> mPositiveClickListener;
    private OnClickListener mNegativeClickListener;

    private CommonAdapter<ChoiceMenuItem<T>> mAdapter;
    private List<ChoiceMenuItem<T>> mMenus = new ArrayList<>();
    private int mCurrentSelectedIndex = 0;
    private ChoiceMenuItem<T> mDefault;
    private boolean mQuickMode = false;

    public PureSingleChoiceDialog(Context context) {
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
        return R.layout.widget_dialog_single_choice;
    }

    @Override
    public void initView(View view) {
        mCurrentSelectedIndex = (mDefault != null ? mMenus.indexOf(mDefault) : -1);
        mMenuRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CommonAdapter<ChoiceMenuItem<T>>(getContext(), R.layout.widget_item_single_choice, mMenus) {
            @Override
            public void convert(ViewHolder holder, ChoiceMenuItem<T> menu) {
                holder.setText(R.id.tv_menu, menu.getName());
                boolean selected = holder.getAdapterPosition() == mCurrentSelectedIndex;
                holder.setImage(R.id.iv_status, selected ? R.drawable.widget_ic_selected : R.drawable.widget_ic_un_selected);
                if (selected) {
                    holder.setImageTint(R.id.iv_status, Utils.getPrimaryColor(getContext()));
                } else {
                    holder.setImageTint(R.id.iv_status, 0);
                }
            }
        };
        mAdapter.setOnItemClickListener((holder, position) -> {
            mCurrentSelectedIndex = holder.getAdapterPosition();
            mAdapter.notifyDataSetChanged();
            if (mQuickMode) {
                if (mPositiveClickListener != null) {
                    mPositiveClickListener.onChoose(PureSingleChoiceDialog.this, mMenus.get(mCurrentSelectedIndex));
                }
            }
        });
        mMenuRv.setAdapter(mAdapter);
        if (mQuickMode) {
            mPositiveBtn.setVisibility(View.GONE);
            mNegativeBtn.setVisibility(View.GONE);
        }
    }

    @OnClick(R2.id.btn_positive)
    void onPositiveClick() {
        if (mPositiveClickListener != null) {
            if (mCurrentSelectedIndex >= 0) {
                mPositiveClickListener.onChoose(this, mMenus.get(mCurrentSelectedIndex));
            } else {
                dismiss();
            }
        }
    }

    @OnClick(R2.id.btn_negative)
    void onNegativeClick() {
        if (mNegativeClickListener != null) {
            mNegativeClickListener.onClick(this);
        }
        dismiss();
    }

    public PureSingleChoiceDialog<T> withCancelable(boolean cancelable) {
        setCancelable(cancelable);
        return this;
    }

    public PureSingleChoiceDialog<T> withTitle(String title) {
        mTitleTv.setText(title);
        return this;
    }

    public PureSingleChoiceDialog<T> withMenuItem(ChoiceMenuItem<T> menu) {
        mMenus.add(menu);
        return this;
    }

    public PureSingleChoiceDialog<T> withMenuItems(Collection<ChoiceMenuItem<T>> menus) {
        mMenus.addAll(menus);
        return this;
    }

    public PureSingleChoiceDialog<T> withDefault(ChoiceMenuItem<T> defaultM) {
        this.mDefault = defaultM;
        return this;
    }

    public PureSingleChoiceDialog<T> withDefaultValue(T defaultValue) {
        this.mDefault = new ChoiceMenuItem<>(defaultValue);
        return this;
    }

    public PureSingleChoiceDialog<T> withPositiveText(String text) {
        if (text == null) {
            mPositiveBtn.setVisibility(View.GONE);
        } else {
            mPositiveBtn.setVisibility(View.VISIBLE);
            mPositiveBtn.setText(text);
        }
        return this;
    }

    public PureSingleChoiceDialog<T> withPositiveClick(OnChooseListener<T> listener) {
        mPositiveClickListener = listener;
        return this;
    }

    public PureSingleChoiceDialog<T> withNegativeText(String text) {
        if (text == null) {
            mNegativeBtn.setVisibility(View.GONE);
        } else {
            mNegativeBtn.setVisibility(View.VISIBLE);
            mNegativeBtn.setText(text);
        }
        return this;
    }

    public PureSingleChoiceDialog<T> withNegativeClick(OnClickListener listener) {
        mNegativeClickListener = listener;
        return this;
    }

    public PureSingleChoiceDialog<T> withQuickMode(boolean quickMode) {
        mQuickMode = quickMode;
        return this;
    }

    @Override
    public void show() {
        if (mMenus.isEmpty()) {
            return;
        }
        super.show();
    }
}
