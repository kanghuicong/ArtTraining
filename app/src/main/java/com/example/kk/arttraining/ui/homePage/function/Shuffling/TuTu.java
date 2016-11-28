package com.example.kk.arttraining.ui.homePage.function.shuffling;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.utils.UIUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TuTu {
    public static final String TAG = "TuTu";
    private ViewPager mViewPager;
    private TextView mTextView;
    private LinearLayout mLinearLayout;
    private ShufflingAdapter myPagerAdapter;
    private int lastPoint = 0;
    private boolean isRunning = false;
    boolean stat = false;
    private Timer timer;
    private TimerTask timerTask;
    private List<ADBean> listADbeans;
    private Context mContext;
    private ImageUtil imageUtil;
    private long delay = 4000;
    private OnLunBoClickListener onLunBoClickListener;;
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            if (what == 0) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
            }
            if (what == 1) {
                myPagerAdapter.notifyImages(listADbeans);
            }
        }

        ;
    };

    public TuTu(ViewPager mViewPager, TextView mTextView, LinearLayout mLinearLayout, Context mContext, List<ADBean> listADbeans) {
        this.mContext = mContext;
        this.listADbeans = listADbeans;
        this.mViewPager = mViewPager;
        this.mTextView = mTextView;
        this.mLinearLayout = mLinearLayout;
        imageUtil = new ImageUtil(mContext);
        //changeViewpagerSpace();
        initADViewpager();


    }

    /**
     * 改变viewpager切换速度
     */
    private void changeViewpagerSpace() {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(mViewPager.getContext(),
                    new AccelerateInterpolator());
            field.set(mViewPager, scroller);
            //scroller.setmDuration(200);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initADViewpager() {
        for (int i = 0; i < listADbeans.size(); i++) {

            ImageView view = new ImageView(mContext);
            //view.setBackgroundResource(listADbeans.get(i).getImgPath());
            // 把图片添加到列表
            listADbeans.get(i).setmImageView(view);
            // 实例化指示点
            ImageView point = new ImageView(mContext);
            point.setImageResource(R.drawable.point_seletor);
            LayoutParams params = new LayoutParams(dip2px(mContext, 6), dip2px(mContext, 6));
            if (i == 0) {
                params.leftMargin = dip2px(mContext, 0);
            } else {
                params.leftMargin = dip2px(mContext, 10);
            }

            point.setLayoutParams(params);


            // 将指示点添加到线性布局里
            mLinearLayout.addView(point);

            // 设置第一个高亮显示
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }


        }
        // 设置适配器
        myPagerAdapter = new ShufflingAdapter(listADbeans);

        mViewPager.setAdapter(myPagerAdapter);

        // 设置默认文字信息
        if (listADbeans != null && listADbeans.size() > 0 && mTextView != null) {
//            mTextView.setText(listADbeans.get(0).getAdName());
            mTextView.setText("");
        }
        final int midPosition = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2
                % listADbeans.size();
        mViewPager.setCurrentItem(midPosition);
        // 设置页面改变监听
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            private float mPreviousOffset = -1;
            private float mPreviousPosition = -1;

            /**
             * 当某个页面被选中的时候回调
             */
            @Override
            public void onPageSelected(int position) {


                int newPosition = position % listADbeans.size();
                // 取出广告文字
                String msg = listADbeans.get(position % listADbeans.size()).getAdName();
                if (mTextView != null) {
                    mTextView.setText(msg);
                }
                // 设置对应的页面高亮
                mLinearLayout.getChildAt(newPosition).setEnabled(true);
                // 是上次的点不显示高亮
                mLinearLayout.getChildAt(lastPoint).setEnabled(false);

                lastPoint = newPosition;
            }

            /**
             * 当某个页面被滚动的时候回调
             */
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            /**
             * 当状态发生改变的时候回调, 静止-滚动-静止
             */
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 1) {
                    timer.cancel();
                    //timerTask.cancel();
                    stat = true;
                }
                if (state == 0) {
                    if (stat) {
                        startViewPager(delay);
                    }
                    stat = false;
                }
            }
        });

        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            private float mDownX;
            private float mDownY;
            private long mDownTime;
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mDownX = event.getX();
                        mDownY = event.getY();
                        mDownTime = System.currentTimeMillis();
                        UIUtil.showLog("mDownX",mDownX+"---1");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        float upX = event.getX();
                        float upY = event.getY();
                        UIUtil.showLog("mDownX",upX+"---3");
                        long upTime = System.currentTimeMillis();
                        // 设置点击事件
                        if (mDownX == upX && mDownY == upY) {
                            if (upTime - mDownTime < 500) {
                                // 点击
                                onLunBoClickListener.clickLunbo(mViewPager.getCurrentItem() % listADbeans.size());
                            }
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        isRunning = true;
        getNetImages();
        handler.sendEmptyMessageDelayed(0, 4000);

    }

    /**
     * 加载网络图片
     */
    private void getNetImages() {
        for (ADBean ad : listADbeans) {

            String url = ad.getImgUrl();
            if (url == null || TextUtils.isEmpty(url)) {
                return;
            }
            String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
            if (!imageName.endsWith(".jpg") && !imageName.endsWith(".png")) {
                imageName += ".png";
            }
            String imagePath = ImageUtil.IMAGE_PATH + imageName;
            if (url != null && !TextUtils.isEmpty(url)) {
                imageUtil.loadImage(imageName, url, true, new ImageUtil.ImageCallback() {

                    @Override
                    public void onStart(String imageUrl) {
                        Log.i(TAG, "开始==" + imageUrl);
                    }

                    @Override
                    public void onFailed(String imageUrl) {
                        Log.i(TAG, "失败==" + imageUrl);
                        for (ADBean bean : listADbeans) {
                            bean.getmImageView().setBackgroundResource(R.mipmap.default_shuffling);
                        }
                    }

                    @Override
                    public void loadImage(Bitmap bitmap, String imageUrl) {

                        Log.i(TAG, imageUrl);
                        for (ADBean bean : listADbeans) {
                            if (imageUrl.equals(bean.getImgUrl())) {
                                bean.getmImageView().setImageBitmap(bitmap);
                            }
                        }
                        handler.sendEmptyMessage(1);
                    }
                });
            }
        }
    }

    /**
     * 开启轮播图
     *
     * @param delay
     */
    public void startViewPager(long delay) {
        this.delay = delay;
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        timer.schedule(timerTask, delay, delay);
    }


    public void destroyView() {
        if (timer != null) {
            timer.cancel();
        }
        if (timerTask != null) {
            timer.cancel();
        }
    }

    public class FixedSpeedScroller extends Scroller {
        private int mDuration = 300;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setmDuration(int time) {
            mDuration = time;
        }

        public int getmDuration() {
            return mDuration;
        }
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /** 设置监听 */
    public void setOnLunBoClickListener(OnLunBoClickListener onLunBoClickListener) {
        this.onLunBoClickListener = onLunBoClickListener;
    }

    public interface OnLunBoClickListener
    {
        void clickLunbo(int position);
    }
}
