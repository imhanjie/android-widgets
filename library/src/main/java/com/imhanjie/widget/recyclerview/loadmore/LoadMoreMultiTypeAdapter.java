package com.imhanjie.widget.recyclerview.loadmore;

import android.content.Context;

import androidx.annotation.Nullable;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by hanjie on 2017/6/28.
 * <p>
 * 加载更多MultiTypeAdapter，配合{@link LoadMoreItems)}实现
 */

public class LoadMoreMultiTypeAdapter extends MultiTypeAdapter {

    private LoadMoreItemViewBinder mLoadMoreItemViewBinder;

    public LoadMoreMultiTypeAdapter(Context context, @Nullable List<?> items) {
        super(items);
        mLoadMoreItemViewBinder = new LoadMoreItemViewBinder(context);
        register(ListLoadingFooter.class, mLoadMoreItemViewBinder);
    }

    public LoadMoreItemViewBinder getLoadMoreItemViewBinder() {
        return mLoadMoreItemViewBinder;
    }

}
