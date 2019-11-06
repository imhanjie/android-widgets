package com.imhanjie.widget.dialog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.imhanjie.support.DimensionUtil;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-08-30
 */

public class DialogRecyclerView extends RecyclerView {

    public DialogRecyclerView(@NonNull Context context) {
        super(context);
    }

    public DialogRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DialogRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) DimensionUtil.dp(350), View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
