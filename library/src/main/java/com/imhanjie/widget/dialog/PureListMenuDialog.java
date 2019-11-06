package com.imhanjie.widget.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imhanjie.support.DimensionUtil;
import com.imhanjie.widget.R;
import com.imhanjie.widget.R2;
import com.imhanjie.widget.dialog.base.BaseCustomDialog;
import com.imhanjie.widget.model.ListMenuItem;
import com.imhanjie.widget.recyclerview.adapter.common.CommonAdapter;
import com.imhanjie.widget.recyclerview.adapter.common.ViewHolder;
import com.imhanjie.widget.recyclerview.decoration.CommonItemDecoration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-08-19
 */

public class PureListMenuDialog extends BaseCustomDialog {

    @BindView(R2.id.rv_menu)
    RecyclerView mMenuRv;

    private CommonAdapter<ListMenuItem> mAdapter;
    private List<ListMenuItem> mMenus = new ArrayList<>();

    @Override
    public int getWindowGravity() {
        return Gravity.CENTER;
    }

    @Override
    public int getWindowAnimation() {
        return R.style.WidgetCenterDialogAnimation;
    }

    public PureListMenuDialog(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.widget_dialog_list_menu;
    }

    @Override
    public void initView(View view) {
        mMenuRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CommonAdapter<ListMenuItem>(getContext(), R.layout.widget_item_list_menu, mMenus) {
            @Override
            public void convert(ViewHolder holder, ListMenuItem menuItem) {
                holder.setText(R.id.tv_menu, menuItem.getName());
                holder.setDrawableStart(R.id.tv_menu, menuItem.getDrawableRes());
            }
        };
        mMenuRv.addItemDecoration(new CommonItemDecoration(getContext(), getResColor(R.color.widget_color_common_divider), 1, ((int) DimensionUtil.dp(53)), 0));
        mAdapter.setOnItemClickListener((viewHolder, position) -> {
            ListMenuItem item = mMenus.get(position);
            if (item.getOnClickListener() != null) {
                item.getOnClickListener().onClick(PureListMenuDialog.this);
            }
        });
        mMenuRv.setAdapter(mAdapter);
    }

    public PureListMenuDialog withCancelable(boolean cancelable) {
        setCancelable(cancelable);
        return this;
    }

    public PureListMenuDialog withMenuItem(ListMenuItem menu) {
        mMenus.add(menu);
        return this;
    }

    public PureListMenuDialog withMenuItems(Collection<ListMenuItem> menus) {
        mMenus.addAll(menus);
        return this;
    }

}
