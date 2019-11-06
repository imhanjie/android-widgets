package com.imhanjie.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.imhanjie.widget.listener.BaseTextWatcher;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * 通用的顶部搜索 View
 *
 * @author hanjie
 * @date 2018/4/27
 */

public class PureSearchBar extends RelativeLayout {

    public interface OnSearchTextChangedListener {
        /**
         * 文字改变时
         *
         * @param searchText 搜索文字
         */
        void onSearchTextChanged(String searchText);
    }

    EditText mSearchEt;

    /**
     * 延迟搜索时间 ms
     */
    private static final long DELAY_TIMEOUT = 300;

    private OnSearchTextChangedListener mOnSearchTextChangedListener;

    // 用来防止编辑框频繁回调导致频繁操作数据库的问题
    private PublishSubject<String> mEditSubject = PublishSubject.create();

    public void setOnSearchTextChangedListener(OnSearchTextChangedListener onSearchTextChangedListener) {
        mOnSearchTextChangedListener = onSearchTextChangedListener;
    }

    /**
     * 是否开启延迟搜索，适用于根据搜索请求调用网络接口的场景，避免频繁请求
     */
    private boolean mEnableDelaySearch = false;

    public void setEnableDelaySearch(boolean enableDelaySearch) {
        mEnableDelaySearch = enableDelaySearch;
    }

    public EditText getSearchEt() {
        return mSearchEt;
    }

    public PureSearchBar(Context context) {
        this(context, null);
    }

    public PureSearchBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PureSearchBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.widget_search_bar, this, true);
        findViews();
        readAttrs(attrs);
        init();
    }

    private void findViews() {
        mSearchEt = findViewById(R.id.et_search);
    }

    /**
     * read attrs from xml
     */
    private void readAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.WidgetPureSearchBar);
        mSearchEt.setHint(a.getString(R.styleable.WidgetPureSearchBar_widget_search_hintText));
        mSearchEt.setBackground(a.getDrawable(R.styleable.WidgetPureSearchBar_widget_search_background));
        a.recycle();
    }

    private void init() {
        initEditSubject();
        mSearchEt.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (!mEnableDelaySearch) {
                    if (mOnSearchTextChangedListener != null) {
                        mOnSearchTextChangedListener.onSearchTextChanged(text);
                    }
                } else {
                    mEditSubject.onNext(s.toString());
                }
            }
        });
    }

    /**
     * 每隔400ms取出缓冲区搜索的关键字去搜索，防止频繁搜索
     */
    @SuppressLint("CheckResult")
    private void initEditSubject() {
        mEditSubject.buffer(mEditSubject.debounce(DELAY_TIMEOUT, TimeUnit.MILLISECONDS))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(strings -> {
                    // 缓冲区里取出最后一个关键字来搜索
                    String content = strings.get(strings.size() - 1);
                    if (mOnSearchTextChangedListener != null && mEnableDelaySearch) {
                        mOnSearchTextChangedListener.onSearchTextChanged(content);
                    }
                });
    }

    /**
     * 获取输入的搜索文字
     */
    public String getSearchText() {
        return mSearchEt.getText().toString().trim();
    }

}
