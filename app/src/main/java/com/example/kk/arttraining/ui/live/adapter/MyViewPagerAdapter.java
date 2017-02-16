package com.example.kk.arttraining.ui.live.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * ViewPage的适配器
 * @author Administrator
 *
 */
public class MyViewPagerAdapter extends PagerAdapter{
	
	private List<View> viewList;//View就二十GridView
	
	
	public MyViewPagerAdapter(List<View> viewList) {
		this.viewList = viewList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return viewList!=null ? viewList.size() : 0;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}
	/**
	 * 将当前的View添加到ViewGroup容器中
	 * 这个方法，return一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPage上
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		container.addView(viewList.get(position));
		return viewList.get(position);
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		 container.removeView((View) object);
	}
}
