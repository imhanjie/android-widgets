package com.imhanjie.widget.recyclerview.loadmore;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 添加滑动监听，监听最后一个item的位置来load more
 */
public class LoadMoreDelegate {

    private static final int VISIBLE_THRESHOLD = 1;

    private final RecyclerView mRecyclerView;
    private final LoadMoreListener mLoadMoreListener;
    private final LoadMoreItems mItems;
    private final LoadMoreMultiTypeAdapter mAdapter;

    private boolean mLoading;
    private boolean mHasMore = true;

    public LoadMoreDelegate(@NonNull Context context, @NonNull RecyclerView recyclerView, @NonNull LoadMoreListener loadMoreListener) {
        // 初始化内部提供的组件 adapter、items，外部直接通过 get 方法获取即可
        this.mRecyclerView = recyclerView;
        mItems = new LoadMoreItems();
        mAdapter = new LoadMoreMultiTypeAdapter(context, mItems);
        mAdapter.getLoadMoreItemViewBinder().setActionListener(this::triggerLoadMore);
        this.mLoadMoreListener = loadMoreListener;

        // 依附 RecyclerView
        final LinearLayoutManager layoutManager
                = (LinearLayoutManager) recyclerView.getLayoutManager();
        final EndlessScrollListener scrollListener = new EndlessScrollListener(layoutManager);
        recyclerView.addOnScrollListener(scrollListener);
        recyclerView.setAdapter(mAdapter);
    }

    public LoadMoreItems getItems() {
        return mItems;
    }

    public LoadMoreMultiTypeAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * 通知加载成功
     *
     * @param hasMore 是否还有更多数据
     */
    public void setLoadSuccess(boolean hasMore) {
        mLoading = false;
        mHasMore = hasMore;
        if (!hasMore) {
            mItems.setFooterType(FooterType.NO_MORE);
        } else {
            mItems.setFooterType(FooterType.HAS_MORE);
        }
        mRecyclerView.post(() -> mAdapter.notifyItemChanged(mItems.getFooterPosition()));
        /*
        当调用方通知我们一次数据加载完成后，并且告诉我们还有更多数据，此时我们需要判断一下数据是否过少出现不足一屏幕的情况，
        如果数据不足一屏，则不会出现滑动的场景，则滑动加载更多永远不会被触发，但是实际情况是还有数据的，所以我们则再次
        继续自动触发 loadMore() 方法让调用方继续请求数据。
         */
        if (hasMore) {
            mRecyclerView.post(() -> {
                LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();   // 屏幕上可见的 item 数量
                int totalItemCount = layoutManager.getItemCount();  // 总 item 数量
                int lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();    // 最后一个完全显示出来的 item 的位置
                if (lastVisiblePosition >= totalItemCount - 1 && totalItemCount <= visibleItemCount) {
                    // 数据不足一屏
                    triggerLoadMore();
                }
            });
        }
    }

    /**
     * 通知加载失败
     */
    public void setLoadFailed() {
        mLoading = false;
        mItems.setFooterType(FooterType.BAD_NETWORK);
        mRecyclerView.post(() -> mAdapter.notifyItemChanged(mItems.getFooterPosition()));
    }

    /**
     * 触发加载更多
     */
    private void triggerLoadMore() {
        mLoading = true;
        if (mItems.getFooterType() != FooterType.HAS_MORE) {
            mItems.setFooterType(FooterType.HAS_MORE);
            mRecyclerView.post(() -> mAdapter.notifyItemChanged(mItems.getFooterPosition()));
        }
        mLoadMoreListener.onLoadMore();
    }

    /**
     * 当达到滑动的阈值时，需要判断是否允许进行加载更多操作
     */
    private boolean canLoadMoreWhenScrolling() {
        return mHasMore && !mLoading;
    }

    private class EndlessScrollListener extends RecyclerView.OnScrollListener {
        private final LinearLayoutManager layoutManager;

        private EndlessScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            if (dy <= 0) {
                return;
            }
            final int itemCount = layoutManager.getItemCount();
            final int lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();
            if (lastVisiblePosition >= itemCount - VISIBLE_THRESHOLD) {
                if (canLoadMoreWhenScrolling()) {
                    triggerLoadMore();
                }
            }
        }
    }


    public interface LoadMoreListener {

        void onLoadMore();

    }

}
