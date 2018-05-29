package com.example.gaope.oneviewpager;

import android.os.Message;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * 事件分发机制
 * 多线程
 * 都不懂
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ViewPager viewpager;
    private LinearLayout point;

    /**
     * 图片的集合
     */
    private int[] images;

    /**
     * ImageView的集合
     */
    private List<ImageView> imageLists;

    private boolean isPlay = true;

    private Thread mThread;
    private MyHandler mHandler;
    private int currentViewPagerItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"111");
        mHandler = new MyHandler(this);
        imageLists = new ArrayList<>();
        viewpager = (ViewPager) findViewById(R.id.view_pager);
        point = (LinearLayout) findViewById(R.id.point);
        images = new int[]{R.drawable.a1,R.drawable.a2,R.drawable.a3};
        init();
        viewpager.setAdapter(new ViewPagerAdapter(imageLists));

        beginPlay();

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //当页面在滑动时至滑动被停止之前，此方法会一直调用
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            //页面跳转完后调用
            @Override
            public void onPageSelected(int position) {
                Log.d(TAG,"bbb:"+position);
                currentViewPagerItem = position;
                Log.d(TAG,"curr");
                position %= imageLists.size();
//                Log.d(TAG,"bbbPosition:"+position);
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
            }
        });

        viewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        isPlay = false;
                        Log.d(TAG,TAG+"onTouch");
                        break;
                    case MotionEvent.ACTION_UP:
                        isPlay = true;
                        break;

                }
                return false;
            }
        });


        //从中间位置开始
        int mid = 10000 / 2;
        viewpager.setCurrentItem(mid);
        currentViewPagerItem = mid;
        Log.d(TAG,"333");
        mThread = new Thread(){
            @Override
            public void run() {
                super.run();
                while (true) {
                    mHandler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        mThread.start();

    }


    /**
     * budong
     */
    private void beginPlay() {

        Log.d(TAG,"444");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isPlay){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG,"run");
                            viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                        }
                    });
                    SystemClock.sleep(3000);
                }
            }
        }).start();
    }

    private void init() {

        Log.d(TAG,"222");

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

    private static class MyHandler extends android.os.Handler {
        private WeakReference<MainActivity> mWeakReference;

        public MyHandler(MainActivity activity) {
            mWeakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    MainActivity activity = mWeakReference.get();
                    if (activity.isPlay) {
                        Log.d(TAG,"isPlay"+activity.isPlay);

                        Log.d(TAG,"handle");

                        activity.viewpager.setCurrentItem(++activity.currentViewPagerItem);
                    }

                    break;
            }

        }
    }
}
