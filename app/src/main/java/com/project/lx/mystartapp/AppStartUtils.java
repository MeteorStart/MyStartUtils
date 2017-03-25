package com.project.lx.mystartapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: X_Meteor
 * @description: App首次启动的引导页
 * @version: V_1.0.0
 * @date: 2017/3/25 0025 10:41
 * @company: 洛阳魅力文化传媒   http://www.fenglin.tv/
 * @email: lx802315@163.com
 */
public class AppStartUtils {

    //上下文对象
    private Context context;
    //导航动画ViewPager（通过构造方法传过来）
    private ViewPager viewPager;
    //页面指示器
    private ViewGroup viewGroup;
    //ViewPager的视图数组
    private List<View> list;
    //每页展示的ImageView
    private ImageView imageView;
    //用于存放导航时的图片
    private ImageView[] imageViews;
    //计算当前页码
    private int currentPage;
    //图片资源路径
    private int[] imageResources;
    //网络图片资源url
    private String[] imageUrls;
    //指示器资源路径
    private int[] iconResources;
    //手势操作封装类
    private GestureDetector gestureDetector;
    //传入意图
    private Intent intent;
    //跳转按钮
    private Button button;
    //引导页页数
    private int len;
    //设置指示器大小（默认为60）
    private int iconPx = 60;
    //设置左右间距（默认为80）
    private int padding = 80;

    /**
     * 设置指示器大小
     *
     * @param iconPx
     */
    public void setIconPx(int iconPx) {
        this.iconPx = iconPx;
    }

    /**
     * 设置指示器间距
     *
     * @param padding
     */
    public void setPadding(int padding) {
        this.padding = padding;
    }

    /**
     * 设置跳转按钮
     *
     * @param button
     */
    public void setButton(Button button) {
        this.button = button;
    }

    /**
     * 设置指示器图标资源
     *
     * @param iconResources
     */
    public void setIconResources(int[] iconResources) {
        this.iconResources = iconResources;
    }

    /**
     * 设置导航页面图片资源
     *
     * @param imageResources
     */
    public void setImageResources(int[] imageResources) {
        this.imageResources = imageResources;
    }

    /**
     * 设置导航页面图片资源
     *
     * @param imageUrls
     */
    public void setImageResources(String[] imageUrls) {
        this.imageUrls = imageUrls;
    }

    /**
     * @param context
     * @param viewPager
     * @param indicator 指示器容器
     */
    public AppStartUtils(Context context, ViewPager viewPager, ViewGroup indicator) {
        this.context = context;
        this.viewPager = viewPager;
        this.viewGroup = indicator;
    }

    /**
     * @param context
     * @param viewPager
     * @param indicator
     * @param intent
     */
    public AppStartUtils(Context context, ViewPager viewPager, ViewGroup indicator, Intent intent) {
        this.context = context;
        this.viewPager = viewPager;
        this.viewGroup = indicator;
        this.intent = intent;
    }

    /**
     * 开始
     */
    public void start() {
        initData();
        initListener();
//        SharedPreferencesUtils.putBoolean(context, "isFirst", false);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        list = new ArrayList<>();

        len = imageResources == null ? imageUrls == null ? 0 : imageUrls.length : imageResources.length;

        for (int i = 0; i < len; i++) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            if (imageResources != null){
                imageView.setImageResource(imageResources[i]);
            }
            if (imageUrls != null){
//                Glide.with(context).load(imageUrls[i]).into(imageView);
            }
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            list.add(imageView);
        }

        imageViews = new ImageView[list.size()];
        for (int i = 0; i < imageViews.length; i++) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(iconPx, iconPx));
            imageView.setPadding(padding, 0, padding, 0);
            imageViews[i] = imageView;
            if (i == 0) {
                imageViews[i].setBackgroundResource(iconResources[0]);
            } else {
                imageViews[i].setBackgroundResource(iconResources[1]);
            }
            viewGroup.addView(imageViews[i]);
        }

        GuidePageAdapter guidePageAdapter = new GuidePageAdapter();
        viewPager.setAdapter(guidePageAdapter);
    }

    /**
     * 初始化接口
     */
    public void initListener() {

        GuidPageChangeListener guidPageChangeListener = new GuidPageChangeListener();
        viewPager.addOnPageChangeListener(guidPageChangeListener);

        gestureDetector = new GestureDetector(context, new IGestureDetectorListener());
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

    }

    /**
     * PagerAdapter类
     */
    class GuidePageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = list.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }
    }

    /**
     * 对ViewPager的页面改变进行监听
     */
    class GuidPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentPage = position;
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[position].setBackgroundResource(iconResources[0]);
                if (position != i) {
                    imageViews[i].setBackgroundResource(iconResources[1]);
                }
            }

            if (button != null){
                //判断是否是最后一页，若是则显示按钮

                if (position == len - 1){
                    button.setVisibility(View.VISIBLE);
                }else {
                    button.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * 对手势操作监听
     */
    class IGestureDetectorListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (button != null){
                return false;
            }

            if (currentPage == list.size() - 1) {
                if ((e1.getX() - e2.getX()) > 0) {
                    if (intent != null) {
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
                }
            }
            return false;
        }
    }
}
