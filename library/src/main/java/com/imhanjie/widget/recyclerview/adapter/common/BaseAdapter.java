package com.imhanjie.widget.recyclerview.adapter.common;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Author:      hanjie
 * Email:
 * Created at:  2016/5/31.
 * Description:
 */
public abstract class BaseAdapter<VH extends ViewHolder> extends RecyclerView.Adapter<VH> {

    protected BaseAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(ViewHolder viewHolder, int position);
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder, holder.getLayoutPosition());
                }
            });
        }
    }

}
