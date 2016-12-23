package com.example.kk.arttraining.ui.discover.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.discover.adapter.ImageViewPagerAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/12/22 17:19
 * 说明:用于图片浏览的类
 */
public class ImageViewPagerActivity extends Activity implements ViewPager.OnPageChangeListener {
    @InjectView(R.id.pager)
    ViewPager pager;
    @InjectView(R.id.pager_num)
    LinearLayout mNumLayout;
    private ImageViewPagerAdapter viewPagerAdapter;
    private List<String> imageList;
    private int postion;


    LayoutInflater mLayoutInflater;

    Button mPreSelectedBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewpager);
        ButterKnife.inject(this);
        initView();
    }

    void initView() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        imageList = bundle.getStringArrayList("imageList");
        postion = bundle.getInt("position");
        viewPagerAdapter = new ImageViewPagerAdapter(this, imageList);
        pager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        pager.setAdapter(viewPagerAdapter);
        pager.setCurrentItem(postion);

        //动态加小圆点
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_dot_default);
        for (int i = 0; i < imageList.size(); i++) {
            Button bt = new Button(this);
            bt.setLayoutParams(new ViewGroup.LayoutParams(bitmap.getWidth(), bitmap.getHeight()));
            if(i==postion){
                bt.setBackgroundResource(R.mipmap.ic_dot_selected);
            }else {
                bt.setBackgroundResource(R.mipmap.ic_dot_default);
            }
            mNumLayout.addView(bt);
        }
        pager.addOnPageChangeListener(this);
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        if (mPreSelectedBt != null) {
//            mPreSelectedBt.setBackgroundResource(R.mipmap.ic_dot_default);
//        }
        for(int i=0;i<imageList.size();i++){
            if(i==position){
                Button currentBt = (Button) mNumLayout.getChildAt(position);
                currentBt.setBackgroundResource(R.mipmap.ic_dot_selected);
            }else {
                Button currentBt = (Button) mNumLayout.getChildAt(i);
                currentBt.setBackgroundResource(R.mipmap.ic_dot_default);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(R.anim.activity_enter_anim, R.anim.activity_exit_anim);
        }
    }
}
