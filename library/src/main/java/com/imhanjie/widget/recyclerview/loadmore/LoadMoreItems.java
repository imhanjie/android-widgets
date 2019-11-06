package com.imhanjie.widget.recyclerview.loadmore;

import java.util.Collection;

import me.drakeet.multitype.Items;

/**
 * Created by hanjie on 2017/6/28.
 * <p>
 * 加载更多，始终维持一个LoadingItem在最底部，配合{@link LoadMoreMultiTypeAdapter)}实现
 */

public class LoadMoreItems extends Items {

    private ListLoadingFooter mLoadingItem;

    public LoadMoreItems() {
        mLoadingItem = new ListLoadingFooter();
        add(0, mLoadingItem);
    }

    @Override
    public boolean add(Object o) {
        add(size() - 1, o);
        return true;
    }

    @Override
    public boolean addAll(Collection<?> c) {
        return super.addAll(size() - 1, c);
    }

    @Override
    public void clear() {
        super.clear();
        add(0, mLoadingItem);
    }

    public void setFooterType(FooterType type) {
        if (get(size() - 1) instanceof ListLoadingFooter) {
            ListLoadingFooter footer = (ListLoadingFooter) get(size() - 1);
            footer.type = type;
        }
    }

    public FooterType getFooterType() {
        Object o = get(size() - 1);
        if (o instanceof ListLoadingFooter) {
            return ((ListLoadingFooter) o).type;
        } else {
            return FooterType.NO_MORE;
        }
    }

    @Override
    public final int size() {
        return super.size();
    }

    /**
     * 外部不要直接调用 size() 方法，使用 getItemSize()
     */
    public int getItemSize() {
        return size() - 1;
    }

    public int getFooterPosition() {
        return size() - 1;
    }

}
