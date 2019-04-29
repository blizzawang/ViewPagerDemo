package com.itcast.test0429;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnPageChange;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_point_group)
    LinearLayout llPointGroup;

    private ArrayList<ImageView> imageViews;

    //图片资源ID
    private final int[] imageIds = {
            R.drawable.b1,
            R.drawable.b2,
            R.drawable.b3,
            R.drawable.b1,
            R.drawable.b2,
            R.drawable.b3
    };

    /**
     * 上一次高亮显示的位置
     */
    private int prePosition = 0;

    //图片标题集合
    private final String[] imageDescriptions = {
            "标题一",
            "标题二",
            "标题三",
            "标题四",
            "标题五",
            "标题六"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        imageViews = new ArrayList<>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);

            //添加到集合中
            imageViews.add(imageView);

            //添加点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8, 8);

            if (i == 0) {
                point.setEnabled(true);//显示红色
            } else {
                point.setEnabled(false);//显示灰色
                params.leftMargin = 8;
            }

            point.setLayoutParams(params);

            llPointGroup.addView(point);
        }

        //设置适配器
        viewpager.setAdapter(new MyAdapter());

        //设置中间位置
        int item = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % imageViews.size();//要保证imageViews的整数倍
        viewpager.setCurrentItem(item);

        tvTitle.setText(imageDescriptions[prePosition]);

        //设置监听ViewPager页面的改变
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * 当页面滚动的时候回调此方法
             * @param position  当前页面的位置
             * @param positionOffset    滑动页面的百分比
             * @param positionOffsetPixels  在屏幕上滑动的像素
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * 当某个页面被选中的时候回调此方法
             * @param position  被选中页面的位置
             */
            @Override
            public void onPageSelected(int position) {

                int realPosition = position % imageViews.size();

                //设置对应页面的文本信息
                tvTitle.setText(imageDescriptions[realPosition]);
                //把上一个高亮的设置为默认-灰色
                llPointGroup.getChildAt(prePosition).setEnabled(false);
                //当前设置为高亮-红色
                llPointGroup.getChildAt(realPosition).setEnabled(true);

                prePosition = realPosition;
            }

            /**
             * 当页面滚动状态变化的时候回调此方法
             * 静止-》滑动
             * 滑动--》静止
             * 静止---》拖拽
             * @param state
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class MyAdapter extends PagerAdapter {

        /**
         * 得到图片的总数
         *
         * @return
         */
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        /**
         * 相当于getView方法
         *
         * @param container ViewPager自身
         * @param position  当前实例化页面的位置
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            int realPosition = position % imageViews.size();

            ImageView imageView = imageViews.get(realPosition);
            container.addView(imageView);//添加到ViewPager中
            Log.e(TAG, "instantiateItem==" + position + ",---imageView==" + imageView);
            return imageView;
        }

        /**
         * 比较view和object是否同一个实例
         *
         * @param view   页面
         * @param object instantiateItem方法返回的结果
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 释放资源
         *
         * @param container ViewPager
         * @param position  要释放的位置
         * @param object    要释放的页面
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.e(TAG, "destroyItem==" + position + ",---object==" + object);
            container.removeView((View) object);
        }
    }
}
