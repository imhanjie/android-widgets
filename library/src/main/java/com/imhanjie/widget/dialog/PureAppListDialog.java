package com.imhanjie.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.imhanjie.widget.LoadingWrapLayout;
import com.imhanjie.widget.R;
import com.imhanjie.widget.R2;
import com.imhanjie.widget.dialog.base.BaseCustomDialog;
import com.imhanjie.widget.model.AppInfo;
import com.imhanjie.widget.recyclerview.adapter.common.CommonAdapter;
import com.imhanjie.widget.recyclerview.adapter.common.ViewHolder;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-09-05
 */

public class PureAppListDialog extends BaseCustomDialog {

    public interface OnSelectedListener {
        void onSelected(Dialog dialog, AppInfo appInfo);
    }

    @BindView(R2.id.rv_apps)
    DialogRecyclerView mAppsRv;
    @BindView(R2.id.btn_negative)
    TextView mNegativeBtn;
    @BindView(R2.id.btn_neutral)
    TextView mNeutralBtn;
    @BindView(R2.id.view_content)
    LoadingWrapLayout mLoadingWrapLayout;

    private OnClickListener mNegativeClickListener;
    private OnClickListener mNeutralClickListener;
    private OnSelectedListener mOnSelectedListener;

    private CommonAdapter<AppInfo> mAdapter;
    private List<AppInfo> mAppInfos = new ArrayList<>();
    private Disposable mDisposable;

    public PureAppListDialog(Context context) {
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
        return R.layout.widget_dialog_app_list;
    }

    @Override
    public void initView(View view) {
        mAppsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CommonAdapter<AppInfo>(getContext(), R.layout.widget_item_app_list, mAppInfos) {
            @Override
            public void convert(ViewHolder holder, AppInfo appInfo) {
                holder.setImageDrawable(R.id.iv_icon, appInfo.icon);
                holder.setText(R.id.tv_name, appInfo.name);
            }
        };
        mAdapter.setOnItemClickListener((viewHolder, position) -> {
            if (mOnSelectedListener != null) {
                mOnSelectedListener.onSelected(PureAppListDialog.this, mAppInfos.get(position));
            }
            dismiss();
        });
        mAppsRv.setAdapter(mAdapter);
        loadAppInfos();
    }

    /**
     * load apps with Rx
     */
    private void loadAppInfos() {
        Observable
                .create((ObservableOnSubscribe<List<AppInfo>>) emitter -> {
                    emitter.onNext(getAppInfos());
                    emitter.onComplete();
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AppInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                        mLoadingWrapLayout.show();
                    }

                    @Override
                    public void onNext(List<AppInfo> appInfos) {
                        mAppInfos.clear();
                        mAppInfos.addAll(appInfos);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mLoadingWrapLayout.hide();
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    private List<AppInfo> getAppInfos() {
        PackageManager pm = getContext().getPackageManager();
        ArrayList<AppInfo> appInfos = new ArrayList<>();
        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfos) {
            String appPackageName = packageInfo.applicationInfo.packageName;
            if (pm.getLaunchIntentForPackage(appPackageName) == null) {
                continue;
            }
            String appLabel = pm.getApplicationLabel(packageInfo.applicationInfo).toString();
            Drawable appIcon = pm.getApplicationIcon(packageInfo.applicationInfo);
            appInfos.add(new AppInfo(appLabel, appIcon, appPackageName));
        }
        final Collator cmp = Collator.getInstance(java.util.Locale.CHINA);
        Collections.sort(appInfos, (a1, a2) -> {
            if (cmp.compare(a1.name, a2.name) > 0) {
                return 1;
            } else if (cmp.compare(a1.name, a2.name) < 0) {
                return -1;
            }
            return 0;
        });
        return appInfos;
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

    public PureAppListDialog withNegativeText(String text) {
        if (text == null) {
            mNegativeBtn.setVisibility(View.GONE);
        } else {
            mNegativeBtn.setVisibility(View.VISIBLE);
            mNegativeBtn.setText(text);
        }
        return this;
    }

    public PureAppListDialog withNegativeClick(OnClickListener listener) {
        mNegativeClickListener = listener;
        return this;
    }

    public PureAppListDialog withNeutralText(String text) {
        if (text == null) {
            mNeutralBtn.setVisibility(View.GONE);
        } else {
            mNeutralBtn.setVisibility(View.VISIBLE);
            mNeutralBtn.setText(text);
        }
        return this;
    }

    public PureAppListDialog withNeutralClick(OnClickListener listener) {
        mNeutralClickListener = listener;
        return this;
    }

    public PureAppListDialog withOnSelectedListener(OnSelectedListener listener) {
        this.mOnSelectedListener = listener;
        return this;
    }

    public interface OnClickListener {
        void onClick(Dialog dialog);
    }

}
