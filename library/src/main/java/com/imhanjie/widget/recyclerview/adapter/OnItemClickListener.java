package com.imhanjie.widget.recyclerview.adapter;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Author:      hanjie
 * Created at:  2017/4/19.
 * Description: 适用MultiType的点击监听(单类型继续使用CommonAdapter中的点击监听)
 */
public interface OnItemClickListener<T, VH extends RecyclerView.ViewHolder> {

    void onItemClick(VH holder, T item, int position);

}
