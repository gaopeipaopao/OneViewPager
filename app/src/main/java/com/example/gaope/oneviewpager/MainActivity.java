package com.example.gaope.oneviewpager;

import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ViewPager viewpager;
    private LinearLayout point;
    private FrameLayout frameLayout;

    /**
     * 图片的集合
     */
    private int[] images;

    /**
     * ImageView的集合
     */
    private List<ImageView> imageLists;


    /**
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout) findViewById(R.id.fragment);
        imageLists = new ArrayList<>();
        viewpager = (ViewPager) findViewById(R.id.view_pager);
        point = (LinearLayout) findViewById(R.id.point);
        images = new int[]{R.drawable.a1,R.drawable.a2,R.drawable.a3};
        init();
        viewpager.setAdapter(new ViewPagerAdapter(imageLists));
        Log.d(TAG,TAG);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //当页面在滑动时至滑动被停止之前，此方法会一直调用
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.d(TAG,"aaa");
//                Log.d(TAG,"aaa"+"    position:"+position+"   possOfff:"+positionOffset+"    pofffPix:"+positionOffsetPixels);
            }

            //页面跳转完后调用
            @Override
            public void onPageSelected(int position) {
//                Log.d(TAG,"bbb");
                Log.d(TAG,"bbb:"+position);
//                for (int i = 0;i < images.length;i++){
//                    View view = point.getChildAt(i);
//                    point.removeView(view);
//                    Log.d(TAG,"point:"+i);
//                }
                for (int i = 0;i < images.length;i++){
                    ImageView imageView = (ImageView) point.getChildAt(i);
                    if (i == position){
                        imageView.setImageResource(R.drawable.shape_point_res);
                    }else {
                        imageView.setImageResource(R.drawable.shape_point_white);
                    }

                }
           }

            //页面状态改变时
            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.d(TAG,"ccc");
//                Log.d(TAG,"ccc:"+state);
            }
        });
    }

    private void init() {

        for (int i = 0;i < images.length;i++){
            ImageView im = new ImageView(this);
            im.setBackgroundResource(images[i]);
            imageLists.add(im);
            ImageView iP = new ImageView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(20,20);
            lp.setMargins(5,0,5,0);
            iP.setLayoutParams(lp);
            if (i == 0){
                iP.setImageResource(R.drawable.shape_point_res);
            }else {
                iP.setImageResource(R.drawable.shape_point_white);
            }
            point.addView(iP);
        }

    }
}
