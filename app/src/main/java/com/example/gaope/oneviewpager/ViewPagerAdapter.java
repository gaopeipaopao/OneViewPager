package com.example.gaope.oneviewpager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;
import java.util.Objects;

/**
 * Created by gaope on 2018/5/28.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private List<ImageView> imageViewList;

    public ViewPagerAdapter(List<ImageView> imageViewList){
        this.imageViewList = imageViewList;
    }

    @Override
    public int getCount() {
        return imageViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //是创建指定位置的页面视图。适配器有责任增加即将创建的View视图到这里给定的container中
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageViewList.get(position),0);
        return imageViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageViewList.get(position));
    }
}
