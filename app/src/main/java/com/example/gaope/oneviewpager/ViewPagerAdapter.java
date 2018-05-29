package com.example.gaope.oneviewpager;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;
import java.util.Objects;

/**
 * Created by gaope on 2018/5/28.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private static final String TAG = "ViewPagerAdapter";

    private List<ImageView> imageViewList;

    public ViewPagerAdapter(List<ImageView> imageViewList){
        this.imageViewList = imageViewList;
    }

    @Override
    public int getCount() {
//        return imageViewList.size();
        return 10000;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //是创建指定位置的页面视图。适配器有责任增加即将创建的View视图到这里给定的container中
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

//        Log.d(TAG,"instantiate:");
//        Log.d(TAG,"insOption:"+position);

//        container.addView(imageViewList.get(position),0);
//        return imageViewList.get(position);
        View view = null;
        position %= imageViewList.size();
        view = imageViewList.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        //getParent()到的肯定是容器视图或者null
        ViewParent viewParent = view.getParent();
        if (viewParent != null){
            ViewGroup viewGroup = (ViewGroup) viewParent;
            viewGroup.removeView(view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //container.removeView(imageViewList.get(position));
    }
}
