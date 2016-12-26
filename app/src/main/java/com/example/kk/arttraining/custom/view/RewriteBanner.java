package com.example.kk.arttraining.custom.view;

/**
 * 作者：wschenyongyin on 2016/12/20 11:39
 * 说明:
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.BannerScroller;
import com.youth.banner.WeakHandler;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoaderInterface;
import com.youth.banner.view.BannerViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * 作者：wschenyongyin on 2016/12/20 11:14
 * 说明:轮播
 */
public class RewriteBanner extends Banner{

    public String tag = "banner";
    private int mIndicatorMargin = BannerConfig.PADDING_SIZE;
    private int mIndicatorWidth = BannerConfig.INDICATOR_SIZE;
    private int mIndicatorHeight = BannerConfig.INDICATOR_SIZE;
    private int bannerStyle = BannerConfig.CIRCLE_INDICATOR;
    private int delayTime = BannerConfig.TIME;
    private boolean isAutoPlay = BannerConfig.IS_AUTO_PLAY;
    private int mIndicatorSelectedResId = com.youth.banner.R.drawable.gray_radius;
    private int mIndicatorUnselectedResId = com.youth.banner.R.drawable.white_radius;
    private int titleHeight;
    private int titleBackground;
    private int titleTextColor;
    private int titleTextSize;
    private int count = 0;
    private int currentItem;
    private int gravity = -1;
    private int lastPosition = 1;
    private int scaleType=0;
    private List<String> titles;
    private List<?> imageUrls;
    private List<View> imageViews;
    private List<ImageView> indicatorImages;
    private Context context;
    private BannerViewPager viewPager;
    private TextView bannerTitle, numIndicatorInside, numIndicator;
    private LinearLayout indicator, indicatorInside, titleView;
    private ImageLoaderInterface imageLoader;
    private BannerPagerAdapter adapter;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private BannerScroller mScroller;
    private OnBannerClickListener listener;
    private WeakHandler handler = new WeakHandler();

    public RewriteBanner(Context context) {
        this(context, null);
    }

    public RewriteBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RewriteBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        titles = new ArrayList<>();
        imageUrls = new ArrayList<>();
        imageViews = new ArrayList<>();
        indicatorImages = new ArrayList<>();
        initView(context, attrs);
    }
//    mIndicatorSelectedResId = typedArray.getResourceId(com.youth.banner.R.styleable.Banner_indicator_drawable_selected, R.mipmap.ic_dot_selected);
//    mIndicatorUnselectedResId = typedArray.getResourceId(com.youth.banner.R.styleable.Banner_indicator_drawable_unselected, R.mipmap.ic_dot_default);

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, com.youth.banner.R.styleable.Banner);
        mIndicatorWidth = typedArray.getDimensionPixelSize(com.youth.banner.R.styleable.Banner_indicator_width, BannerConfig.INDICATOR_SIZE);
        mIndicatorHeight = typedArray.getDimensionPixelSize(com.youth.banner.R.styleable.Banner_indicator_height, BannerConfig.INDICATOR_SIZE);
        mIndicatorMargin = typedArray.getDimensionPixelSize(com.youth.banner.R.styleable.Banner_indicator_margin, BannerConfig.PADDING_SIZE);
        mIndicatorSelectedResId = typedArray.getResourceId(com.youth.banner.R.styleable.Banner_indicator_drawable_selected, com.youth.banner.R.drawable.gray_radius);
        mIndicatorUnselectedResId = typedArray.getResourceId(com.youth.banner.R.styleable.Banner_indicator_drawable_unselected, com.youth.banner.R.drawable.white_radius);
        scaleType=typedArray.getInt(com.youth.banner.R.styleable.Banner_image_scale_type,0);
        delayTime = typedArray.getInt(com.youth.banner.R.styleable.Banner_delay_time, BannerConfig.TIME);
        isAutoPlay = typedArray.getBoolean(com.youth.banner.R.styleable.Banner_is_auto_play, BannerConfig.IS_AUTO_PLAY);
        titleBackground = typedArray.getColor(com.youth.banner.R.styleable.Banner_title_background, BannerConfig.TITLE_BACKGROUND);
        titleHeight = typedArray.getDimensionPixelSize(com.youth.banner.R.styleable.Banner_title_height, BannerConfig.TITLE_HEIGHT);
        titleTextColor = typedArray.getColor(com.youth.banner.R.styleable.Banner_title_textcolor, BannerConfig.TITLE_TEXT_COLOR);
        titleTextSize = typedArray.getDimensionPixelSize(com.youth.banner.R.styleable.Banner_title_textsize, BannerConfig.TITLE_TEXT_SIZE);
        typedArray.recycle();
    }

    private void initView(Context context, AttributeSet attrs) {
        imageViews.clear();
        View view = LayoutInflater.from(context).inflate(com.youth.banner.R.layout.banner, this, true);
        viewPager = (BannerViewPager) view.findViewById(com.youth.banner.R.id.viewpager);
        titleView = (LinearLayout) view.findViewById(com.youth.banner.R.id.titleView);
        indicator = (LinearLayout) view.findViewById(com.youth.banner.R.id.indicator);
        indicatorInside = (LinearLayout) view.findViewById(com.youth.banner.R.id.indicatorInside);
        bannerTitle = (TextView) view.findViewById(com.youth.banner.R.id.bannerTitle);
        numIndicator = (TextView) view.findViewById(com.youth.banner.R.id.numIndicator);
        numIndicatorInside = (TextView) view.findViewById(com.youth.banner.R.id.numIndicatorInside);
        handleTypedArray(context, attrs);
        initViewPagerScroll();
    }
    private void initViewPagerScroll() {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mScroller = new BannerScroller(viewPager.getContext());
            mField.set(viewPager, mScroller);
        } catch (Exception e) {
            Log.e(tag,e.getMessage());
        }
    }

    public Banner isAutoPlay(boolean isAutoPlay) {
        this.isAutoPlay = isAutoPlay;
        return this;
    }

    public Banner setImageLoader(ImageLoaderInterface imageLoader) {
        this.imageLoader = imageLoader;
        return this;
    }

    public Banner setDelayTime(int delayTime) {
        this.delayTime = delayTime;
        return this;
    }

    public Banner setIndicatorGravity(int type) {
        switch (type) {
            case BannerConfig.LEFT:
                this.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                break;
            case BannerConfig.CENTER:
                this.gravity = Gravity.CENTER;
                break;
            case BannerConfig.RIGHT:
                this.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
        }
        return this;
    }
    public Banner setBannerAnimation(Class<? extends ViewPager.PageTransformer> transformer) {
        try {
            setPageTransformer(true, transformer.newInstance());
        } catch (Exception e) {
            Log.e(tag,"Please set the PageTransformer class");
        }
        return this;
    }
    /**
     * Set the number of pages that should be retained to either side of the
     * current page in the view hierarchy in an idle state. Pages beyond this
     * limit will be recreated from the adapter when needed.
     *
     * @param limit How many pages will be kept offscreen in an idle state.
     * @return Banner
     */
    public Banner setOffscreenPageLimit(int limit){
        if (viewPager!=null){
            viewPager.setOffscreenPageLimit(limit);
        }
        return this;
    }
    /**
     * Set a {@link ViewPager.PageTransformer} that will be called for each attached page whenever
     * the scroll position is changed. This allows the application to apply custom property
     * transformations to each page, overriding the default sliding look and feel.
     *
     * @param reverseDrawingOrder true if the supplied PageTransformer requires page views
     *                            to be drawn from last to first instead of first to last.
     * @param transformer PageTransformer that will modify each page's animation properties
     * @return Banner
     */
    public Banner setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        viewPager.setPageTransformer(reverseDrawingOrder, transformer);
        return this;
    }

    public Banner setBannerTitles(List<String> titles) {
        this.titles = titles;
        return this;
    }

    public Banner setBannerStyle(int bannerStyle) {
        this.bannerStyle = bannerStyle;
        return this;
    }


    public Banner setImages(List<?> imagesUrl) {
        this.imageUrls=imagesUrl;
        return this;
    }

    public Banner start(){
        setBannerStyleUI();
        setImageList(imageUrls);
        if (isAutoPlay)
            startAutoPlay();
        return this;
    }

    private void setTitleStyleUI() {
        if (titleBackground != -1) {
            titleView.setBackgroundColor(titleBackground);
        }
        if (titleHeight != -1) {
            titleView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, titleHeight));
        }
        if (titleTextColor != -1) {
            bannerTitle.setTextColor(titleTextColor);
        }
        if (titleTextSize != -1) {
            bannerTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,titleTextSize);
        }
        if (titles != null && titles.size() > 0) {
            bannerTitle.setText(titles.get(0));
            bannerTitle.setVisibility(View.VISIBLE);
            titleView.setVisibility(View.VISIBLE);
        }
    }

    private void setBannerStyleUI() {
        switch (bannerStyle) {
            case BannerConfig.CIRCLE_INDICATOR:
                indicator.setVisibility(View.VISIBLE);
                break;
            case BannerConfig.NUM_INDICATOR:
                numIndicator.setVisibility(View.VISIBLE);
                break;
            case BannerConfig.NUM_INDICATOR_TITLE:
                numIndicatorInside.setVisibility(View.VISIBLE);
                setTitleStyleUI();
                break;
            case BannerConfig.CIRCLE_INDICATOR_TITLE:
                indicator.setVisibility(View.VISIBLE);
                setTitleStyleUI();
                break;
            case BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE:
                indicatorInside.setVisibility(VISIBLE);
                setTitleStyleUI();
                break;
        }
    }

    private void initImages() {
        imageViews.clear();
        if (bannerStyle == BannerConfig.CIRCLE_INDICATOR ||
                bannerStyle == BannerConfig.CIRCLE_INDICATOR_TITLE ||
                bannerStyle == BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE) {
            createIndicator();
        } else if (bannerStyle == BannerConfig.NUM_INDICATOR_TITLE) {
            numIndicatorInside.setText("1/" + count);
        } else if (bannerStyle == BannerConfig.NUM_INDICATOR) {
            numIndicator.setText("1/" + count);
        }
    }

    private void setImageList(List<?> imagesUrl) {
        if (imagesUrl == null || imagesUrl.size() <= 0) {
            Log.e(tag, "Please set the images data.");
            return;
        }
        count = imagesUrl.size();
        initImages();
        for (int i = 0; i <= count + 1; i++) {
            View imageView = null;
            if (imageLoader != null) {
                imageView = imageLoader.createImageView(context);
            }
            if (imageView == null) {
                imageView = new ImageView(context);
            }
            if (imageView instanceof ImageView) {
                if (scaleType==0) {
                    ((ImageView) imageView).setScaleType(ImageView.ScaleType.FIT_XY);
                } else {
                    ((ImageView) imageView).setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
            }
            Object url = null;
            if (i == 0) {
                url = imagesUrl.get(count - 1);
            } else if (i == count + 1) {
                url = imagesUrl.get(0);
            } else {
                url = imagesUrl.get(i - 1);
            }
            imageViews.add(imageView);
            if (imageLoader!=null)
                imageLoader.displayImage(context, url, imageView);
            else
                Log.e(tag, "Please set images loader.");
        }
        setData();
    }

    private void createIndicator() {
        indicatorImages.clear();
        indicator.removeAllViews();
        indicatorInside.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //设置轮播点的大小和间距
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.leftMargin = 10;
            params.rightMargin = 10;
            if (i == 0) {
                imageView.setImageResource(mIndicatorSelectedResId);
            } else {
                imageView.setImageResource(mIndicatorUnselectedResId);
            }
            indicatorImages.add(imageView);
            if (bannerStyle == BannerConfig.CIRCLE_INDICATOR ||
                    bannerStyle == BannerConfig.CIRCLE_INDICATOR_TITLE)
                indicator.addView(imageView, params);
            else if (bannerStyle == BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                indicatorInside.addView(imageView, params);
        }
    }


    private void setData() {
        currentItem = 1;
        if (adapter == null) {
            adapter = new BannerPagerAdapter();
        }
        viewPager.setAdapter(adapter);
        viewPager.setFocusable(true);
        viewPager.setCurrentItem(1);
        viewPager.addOnPageChangeListener(this);
        if (gravity != -1)
            indicator.setGravity(gravity);
        if(count<=1)
            viewPager.setScrollable(false);
        else
            viewPager.setScrollable(true);
    }


    public void startAutoPlay() {
        handler.removeCallbacks(task);
        handler.postDelayed(task, delayTime);
    }
    public void stopAutoPlay() {
        handler.removeCallbacks(task);
    }
    private final Runnable task = new Runnable() {

        @Override
        public void run() {
            if (count > 1&&isAutoPlay) {
                currentItem = currentItem % (count + 1) + 1;
                if (currentItem == 1) {
                    viewPager.setCurrentItem(currentItem, false);
                    handler.postDelayed(task, delayTime);
                }else if(currentItem==count+1){
                    viewPager.setCurrentItem(currentItem);
                    handler.postDelayed(task, 500);
                }else {
                    viewPager.setCurrentItem(currentItem);
                    handler.postDelayed(task, delayTime);
                }
            }
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.i(tag, ev.getAction() + "--" + isAutoPlay);
        if (isAutoPlay) {
            int action = ev.getAction();
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
                    || action == MotionEvent.ACTION_OUTSIDE) {
                startAutoPlay();
            } else if (action == MotionEvent.ACTION_DOWN) {
                stopAutoPlay();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(imageViews.get(position));
            View view = imageViews.get(position);
            if (listener!=null) {
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.OnBannerClick(position);
                    }
                });
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews.get(position));
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
        currentItem = viewPager.getCurrentItem();
        switch (state) {
            case 0://No operation
                if (currentItem == 0) {
                    viewPager.setCurrentItem(count, false);
                } else if (currentItem == count + 1) {
                    viewPager.setCurrentItem(1, false);
                }
                break;
            case 1://start Sliding
                if (currentItem == count + 1) {
                    viewPager.setCurrentItem(1, false);
                }else if(currentItem == 0){
                    viewPager.setCurrentItem(count, false);
                }
                break;
            case 2://end Sliding
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
        if (bannerStyle == BannerConfig.CIRCLE_INDICATOR ||
                bannerStyle == BannerConfig.CIRCLE_INDICATOR_TITLE ||
                bannerStyle == BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE) {
            indicatorImages.get((lastPosition - 1 + count) % count).setImageResource(mIndicatorUnselectedResId);
            indicatorImages.get((position - 1 + count) % count).setImageResource(mIndicatorSelectedResId);
            lastPosition = position;
        }
        if (position == 0) position = 1;
        int titleSize=titles.size();
        switch (bannerStyle) {
            case BannerConfig.CIRCLE_INDICATOR:
                break;
            case BannerConfig.NUM_INDICATOR:
                if (position > count) position = count;
                numIndicator.setText(position + "/" + count);
                break;
            case BannerConfig.NUM_INDICATOR_TITLE:
                if (position > count) position = count;
                numIndicatorInside.setText(position + "/" + count);
                if (titles != null && titleSize > 0) {
                    if (position > titleSize) position = titleSize;
                    bannerTitle.setText(titles.get(position - 1));
                }
                break;
            case BannerConfig.CIRCLE_INDICATOR_TITLE:
                if (titles != null && titleSize> 0) {
                    if (position > titleSize) position = titleSize;
                    bannerTitle.setText(titles.get(position - 1));
                }
                break;
            case BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE:
                if (titles != null && titleSize > 0) {
                    if (position > titleSize) position = titleSize;
                    bannerTitle.setText(titles.get(position - 1));
                }
                break;
        }

    }

    public void setOnBannerClickListener(OnBannerClickListener listener) {
        this.listener=listener;
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }
}
