package com.example.kk.arttraining.ui.live.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.live.bean.GiftBean;
import com.example.kk.arttraining.ui.live.view.IPLVideoView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class GiftGridViewAdpter extends BaseAdapter{
	
	private Context context;
	private List<GiftBean> lists;//数据源
	private int mIndex; // 页数下标，标示第几页，从0开始
	private int mPargerSize;// 每页显示的最大的数量

	private Map<Integer,Boolean> mapSelected;

	IPLVideoView iplVideoView;
	
	public GiftGridViewAdpter(Context context, IPLVideoView iplVideoView , List<GiftBean> lists,
							  int mIndex, int mPargerSize) {
		this.context = context;
		this.lists = lists;
		this.mIndex = mIndex;
		this.mPargerSize = mPargerSize;
		this.iplVideoView=iplVideoView;
		mapSelected=new HashMap<Integer, Boolean>();
		cleanSelected();
	}

	/**
	 * 先判断数据及的大小是否显示满本页lists.size() > (mIndex + 1)*mPagerSize
	 * 如果满足，则此页就显示最大数量lists的个数
	 * 如果不够显示每页的最大数量，那么剩下几个就显示几个
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size() > (mIndex + 1) * mPargerSize ? 
				mPargerSize : (lists.size() - mIndex*mPargerSize);
	}

	@Override
	public GiftBean getItem(int arg0) {
		// TODO Auto-generated method stub
		return lists.get(arg0 + mIndex * mPargerSize);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0 + mIndex * mPargerSize;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_gridview_gift, null);
			holder.tv_name = (TextView)convertView.findViewById(R.id.item_name);
			holder.iv_nul = (ImageView)convertView.findViewById(R.id.item_image);
			holder.gift_selected = (ImageView)convertView.findViewById(R.id.gift_selected);

			holder.ll_gift_layout = (LinearLayout) convertView.findViewById(R.id.ll_gift_layout);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder)convertView.getTag();
		}
		//重新确定position因为拿到的总是数据源，数据源是分页加载到每页的GridView上的
		final int pos = position + mIndex * mPargerSize;//假设mPageSiez
		//假设mPagerSize=8，假如点击的是第二页（即mIndex=1）上的第二个位置item(position=1),那么这个item的实际位置就是pos=9
		Glide.with(context).load(lists.get(pos).getPic()).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.iv_nul);
//		holder.iv_nul.setImageResource(R.mipmap.default_user_header);
		if (lists.get(pos).getPrice()!=0){
			holder.tv_name.setText(lists.get(pos).getPrice() + "云币");
		}else {
			holder.tv_name.setText(lists.get(pos).getScore() + "金币");
		}
		holder.ll_gift_layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (mapSelected.get(pos)){
					cleanSelected();
				}else {
					cleanSelected();
					mapSelected.put(pos,true);
					iplVideoView.setGiftBean(lists.get(position + mIndex * mPargerSize));
				}
				notifyDataSetChanged();
			}
		});
		if (mapSelected.get(pos)){
			holder.gift_selected.setVisibility(View.VISIBLE);
		}else {
			holder.gift_selected.setVisibility(View.GONE);
		}
		return convertView;
	}


	public void cleanSelected(){
		for (int i=0;i<lists.size();i++){
			mapSelected.put(i,false);
		}
	}
	static class ViewHolder{
		private TextView tv_name;
		private ImageView iv_nul;
		private ImageView gift_selected;
		private LinearLayout ll_gift_layout;
	}
}
