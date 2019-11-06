package com.imhanjie.widget.recyclerview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.imhanjie.widget.R;
import com.imhanjie.widget.R2;

import butterknife.BindView;

/**
 * 通用的间距 item
 *
 * @author hanjie
 * @date 2019/4/17
 */
public class CommonSpaceItemViewBinder extends BaseItemViewBinder<Float, CommonSpaceItemViewBinder.ViewHolder> {

    private int color = Color.TRANSPARENT;

    public CommonSpaceItemViewBinder(Context context) {
        super(context);
    }

    public CommonSpaceItemViewBinder(Context context, int color) {
        super(context);
        this.color = color;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.widget_item_common_space, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Float height) {
        super.onBindViewHolder(holder, height);
        ViewGroup.LayoutParams params = holder.spaceView.getLayoutParams();
        params.height = height.intValue();
        holder.spaceView.setBackgroundColor(color);
    }

    public static class ViewHolder extends BaseViewHolder {

        @BindView(R2.id.view_space)
        View spaceView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
