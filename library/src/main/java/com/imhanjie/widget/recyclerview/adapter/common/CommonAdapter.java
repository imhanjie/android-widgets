package com.imhanjie.widget.recyclerview.adapter.common;
/**
 * Created by hanjie on 2017/4/13.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:      zhy
 * Created at:  16/4/9.
 * Description:
 */
public abstract class CommonAdapter<T> extends BaseAdapter<ViewHolder> {

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public abstract void convert(ViewHolder holder, T t);

    public CommonAdapter(Context context, int layoutId) {
        this(context, layoutId, null);
    }

    public CommonAdapter(Context context, int layoutId, List<T> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        this.mDatas = datas;
        if (datas == null) {
            this.mDatas = new ArrayList<>();
        }
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        convert(holder, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        if (mDatas == null) {
            return 0;
        } else {
            return mDatas.size();
        }
    }

    public void addItem(T t) {
        if (t != null) {
            addItem(mDatas.size(), t);
        }
    }

    public void addItem(int position, T t) {
        if (t != null) {
            mDatas.add(position, t);
            notifyItemInserted(position);
        }
    }

    public void addAll(List<T> list) {
        if (list != null && !list.isEmpty()) {
            int startPos = mDatas.size();
            mDatas.addAll(list);
            notifyItemRangeInserted(startPos, list.size());
        }
    }

    public void replaceAll(List<T> list) {
        if (list != null) {
            mDatas.clear();
            addAll(list);
            notifyDataSetChanged();
        }
    }

    public void replaceAllExcludeHeader(List<T> list) {
        if (list != null) {
            for (int i = 1, length = mDatas.size(); i < length; i++) {
                mDatas.remove(1);
            }
            addAll(list);
            notifyDataSetChanged();
        }
    }
}