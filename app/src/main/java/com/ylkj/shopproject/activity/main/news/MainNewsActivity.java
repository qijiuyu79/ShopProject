package com.ylkj.shopproject.activity.main.news;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.ylkj.shopproject.R;
import com.ylkj.shopproject.activity.main.fragment.AppNewsFragment;
import com.ylkj.shopproject.activity.main.fragment.OrderNewsFragment;
import com.ylkj.shopproject.view.PagerSlidingTabStrip;
import com.zxdc.utils.library.base.BaseActivity;
/**
 * 首页消息
 */
public class MainNewsActivity extends BaseActivity{
    private PagerSlidingTabStrip tabs;
    private DisplayMetrics dm;
    private ViewPager pager;
    //订单消息
    private OrderNewsFragment orderNewsFragment=new OrderNewsFragment();
    //平台消息
    private AppNewsFragment appNewsFragment=new AppNewsFragment();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_news);
        initView();
    }


    /**
     * 初始化
     */
    private void initView(){
        dm = getResources().getDisplayMetrics();
        pager =  findViewById(R.id.pager);
        tabs =  findViewById(R.id.tabs);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(2);
        tabs.setViewPager(pager);
        setTabsValue();

        //返回
        findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainNewsActivity.this.finish();
            }
        });
    }


    /**
     * 对PagerSlidingTabStrip的各项属性进行赋值。
     */
    private void setTabsValue() {
        // 设置Tab是自动填充满屏幕的
        tabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        tabs.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab Indicator的高度
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2, dm));
        // 设置Tab标题文字的大小
        tabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 14, dm));
        // 设置Tab Indicator的颜色
        tabs.setIndicatorColorResource(R.color.color_37C7B5);
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        tabs.setTextColorResource(R.color.color_33333);
        tabs.setSelectedTextColorResource(R.color.color_37C7B5);
        // 取消点击Tab时的背景色
        tabs.setTabBackground(0);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = { "订单消息","平台消息"};

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return orderNewsFragment;
            }else{
                return appNewsFragment;
            }
        }
    }
}
