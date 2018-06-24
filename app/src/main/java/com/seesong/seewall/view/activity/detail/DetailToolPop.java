package com.seesong.seewall.view.activity.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;

import com.seesong.seewall.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by tanxiaoqian on 2018/6/24.
 */

public class DetailToolPop extends BasePopupWindow {


    private View popupView;
    private View.OnClickListener mOnClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public DetailToolPop(Context context) {
        super(context);
        bindEvent();
    }

    @Override
    protected Animation initShowAnimation() {
        return getDefaultScaleAnimation();
    }

    @Override
    protected Animation initExitAnimation() {
        return getDefaultScaleAnimation(false);
    }

    @Override
    public View getClickToDismissView() {
        return popupView.findViewById(R.id.click_to_dismiss);
    }

    @Override
    public View onCreatePopupView() {
        popupView = LayoutInflater.from(getContext()).inflate(R.layout.layout_detail_pop, null);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return popupView.findViewById(R.id.tool_containers);
    }

    private void bindEvent() {
        if (popupView != null) {
            popupView.findViewById(R.id.image_share).setOnClickListener(mOnClickListener);
            popupView.findViewById(R.id.image_photo).setOnClickListener(mOnClickListener);
            popupView.findViewById(R.id.image_download).setOnClickListener(mOnClickListener);
        }

    }


}
