package com.imhanjie.widget.recyclerview.loadmore;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.imhanjie.widget.R;
import com.imhanjie.widget.R2;
import com.imhanjie.widget.recyclerview.adapter.BaseItemViewBinder;
import com.imhanjie.widget.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * 加载更多 item
 *
 * @author hanjie
 * @date 2019/4/17
 */
public class LoadMoreItemViewBinder extends BaseItemViewBinder<ListLoadingFooter, LoadMoreItemViewBinder.ViewHolder> {

    public interface ActionListener {
        void onBadNetworkRetry();
    }

    private ActionListener mActionListener;

    public void setActionListener(ActionListener actionListener) {
        mActionListener = actionListener;
    }

    public LoadMoreItemViewBinder(Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.widget_item_load_more, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final ListLoadingFooter item) {
        super.onBindViewHolder(holder, item);
        holder.loadingPb.getIndeterminateDrawable().setColorFilter(Color.parseColor("#d7d9da"), PorterDuff.Mode.SRC_IN);
        switch (item.type) {
            case HAS_MORE:
                showView(holder.loadingView, ((ViewGroup) holder.itemView));
                break;
            case NO_MORE:
                showView(holder.noMoreView, ((ViewGroup) holder.itemView));
                break;
            case BAD_NETWORK:
                showView(holder.badNetworkRetryView, ((ViewGroup) holder.itemView));
                holder.badNetworkRetryView.setOnClickListener(v -> {
                    if (mActionListener != null) {
                        mActionListener.onBadNetworkRetry();
                    }
                });
                break;
            default:
                break;
        }
    }

    private void showView(View view, ViewGroup parent) {
        View v;
        for (int i = 0; i < parent.getChildCount(); i++) {
            v = parent.getChildAt(i);
            if (view == v) {
                v.setVisibility(View.VISIBLE);
            } else {
                v.setVisibility(View.GONE);
            }
        }
    }

    public static class ViewHolder extends BaseViewHolder {

        @BindView(R2.id.view_no_more)
        View noMoreView;

        @BindView(R2.id.loading_view)
        View loadingView;

        @BindView(R2.id.view_bad_network_retry)
        View badNetworkRetryView;

        @BindView(R2.id.pb_loading)
        ProgressBar loadingPb;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
