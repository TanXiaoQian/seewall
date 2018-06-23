package com.seesong.seewall.view.activity.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seesong.seewall.R;
import com.seesong.seewall.model.Wallpaper;

import java.util.List;

/**
 * Created by tanxiaoqian on 2018/6/21.
 */

public class WallPagerAdapter extends BaseQuickAdapter<Wallpaper, BaseViewHolder> {

    public WallPagerAdapter(@Nullable List<Wallpaper> data) {
        super(R.layout.item_wallpager, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Wallpaper item) {
        Glide.with(mContext).
                load(item.getSrc())
                .placeholder(R.drawable.pic_place_holder)
                .error(R.drawable.ic_launcher_background)
                .into((ImageView) helper.getView(R.id.image_wall));
        helper.setTag(R.id.image_wall, R.id.item_data, item);
        helper.addOnClickListener(R.id.image_wall);
    }
}
