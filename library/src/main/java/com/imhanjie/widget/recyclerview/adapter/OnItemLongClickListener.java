package com.imhanjie.widget.recyclerview.adapter;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Author:      hanjie
 * Created at:  2017/4/19.
 * Description: 适用MultiType的长按监听(单类型继续使用CommonAdapter中的点击监听)
 */
public interface OnItemLongClickListener<T, VH extends RecyclerView.ViewHolder> {

    boolean onItemLongClick(VH holder, T item, int position);

}
