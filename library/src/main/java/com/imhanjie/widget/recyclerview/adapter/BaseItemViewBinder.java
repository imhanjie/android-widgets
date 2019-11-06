package com.imhanjie.widget.recyclerview.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Author:      hanjie
 * Created at:  2017/4/19.
 * Description:
 */

public abstract class BaseItemViewBinder<T, VH extends RecyclerView.ViewHolder> extends ItemViewBinder<T, VH> {

    public Context mContext;

    public BaseItemViewBinder(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    private OnItemClickListener<T, VH> mOnItemClickListener;
    private OnItemLongClickListener<T, VH> mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener<T, VH> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T, VH> itemLongClickListener) {
        mOnItemLongClickListener = itemLongClickListener;
    }

    protected String getResString(int resId) {
        return getContext().getString(resId);
    }

    protected String getResString(int resId, Object... formatArgs) {
        return getContext().getString(resId, formatArgs);
    }

    protected int getResColor(int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }

    @Override
    protected void onBindViewHolder(@NonNull final VH holder, @NonNull final T item) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> mOnItemClickListener.onItemClick(holder, item, holder.getAdapterPosition()));
        }
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(v -> mOnItemLongClickListener.onItemLongClick(holder, item, holder.getAdapterPosition()));
        }
    }

}
