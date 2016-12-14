package com.example.kk.arttraining.ui.homePage.function.shuffling;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;

import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

public class ShufflingAdapter extends PagerAdapter {
    List<ADBean> listADbeans;

    public ShufflingAdapter(List<ADBean> listADbeans) {
        this.listADbeans = listADbeans;
    }

    public int getCount() {
        //把这个条数数值很大很大
        return Integer.MAX_VALUE;
    }

    /**
     * 相当于getView:实例化每个页面的View和添加View
     * container:ViewPage 容器
     * position 位置
     */
    public Object instantiateItem(ViewGroup container, int position) {

        position %= listADbeans.size();
        if (position<0){
            position = listADbeans.size()+position;
        }
        ImageView view = listADbeans.get(position).getmImageView();
        view.setBackgroundResource(listADbeans.get(position).getImgPath());

        ViewParent vp =view.getParent();
        if (vp!=null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(view);
        }
        container.addView(view);

        return view;//返回实例化的View


    }

    /**
     * 判断view和instantiateItem返回的对象是否一样
     * Object : 时instantiateItem返回的对象
     */
    public boolean isViewFromObject(View view, Object object) {
        if (view == object) {
            UIUtil.showLog("container","true");
            return true;
        } else {
            UIUtil.showLog("container","false");
            return false;
        }
    }


    /*
     * 实例化两张图片,最多只能装三张图片
     */
    public void destroyItem(ViewGroup container, int position, Object object) {

        //释放资源
//        container.removeView((View) object);

    }

    public synchronized void notifyImages(List<ADBean> listADbeans) {
        this.listADbeans = listADbeans;
        notifyDataSetChanged();
    }


}
