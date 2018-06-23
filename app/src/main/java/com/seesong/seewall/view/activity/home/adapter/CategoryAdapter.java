package com.seesong.seewall.view.activity.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seesong.seewall.R;

import java.util.List;

/**
 * Created by tanxiaoqian on 2018/6/20.
 */

public class CategoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public CategoryAdapter(@Nullable List<String> data) {
        super(R.layout.item_category, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.category_name, item);
    }
}
