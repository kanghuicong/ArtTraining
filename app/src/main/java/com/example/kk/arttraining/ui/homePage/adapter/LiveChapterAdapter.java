package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.bean.LiveChapterBean;
import com.example.kk.arttraining.utils.ScreenUtils;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2017/1/19.
 * QQ邮箱:515849594@qq.com
 */
public class LiveChapterAdapter extends BaseAdapter {
    Context context;
    List<LiveChapterBean> liveChapterBeanList;
    LiveChapterBean liveChapterBean;
    ViewHolder holder = null;
    String curr_time;

    public LiveChapterAdapter(Context context, List<LiveChapterBean> liveChapterBeanList,String curr_time) {
        this.context = context;
        this.liveChapterBeanList = liveChapterBeanList;
        this.curr_time = curr_time;
    }

    @Override
    public int getCount() {
        return liveChapterBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public int getOrderStatus(int position) {
        return liveChapterBeanList.get(position).getOrder_status();
    }

    public int getChapterId(int position) {
        return liveChapterBeanList.get(position).getChapter_id();
    }

    public int getChapterLivePrice(int position) {
        return liveChapterBeanList.get(position).getLive_price();
    }

    public int getChapterRecordPrice(int position) {
        return liveChapterBeanList.get(position).getRecord_price();
    }

    public String getChapterName(int position) {
        return liveChapterBeanList.get(position).getChapter_name();
    }

    public int getChapterType(int position) {
        return liveChapterBeanList.get(position).getLive_status();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        liveChapterBean = liveChapterBeanList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.live_wait_chapter_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //状态
        switch (liveChapterBean.getLive_status()) {
            case 0:
                holder.tvChapterItemType.setText("未开播");
                holder.tvChapterItemType.setTextColor(context.getResources().getColor(R.color.color_bule2));
                break;
            case 1:
                holder.tvChapterItemType.setText("直播中");
                holder.tvChapterItemType.setTextColor(context.getResources().getColor(R.color.green));
                break;
            case 2:
                holder.tvChapterItemType.setText("直播已结束");
                holder.tvChapterItemType.setTextColor(context.getResources().getColor(R.color.grey));
                break;
        }

        //费用
        holder.tvChapterItemName.setText(liveChapterBean.getChapter_name());
        switch (liveChapterBean.getIs_free()) {
            case 0:
                holder.tvChapterItemFree.setText("¥ " + liveChapterBean.getLive_price());
                holder.tvChapterItemFree.setTextColor(context.getResources().getColor(R.color.color_bule));
                break;
            case 1:
                holder.tvChapterItemFree.setText("免费");
                holder.tvChapterItemFree.setTextColor(context.getResources().getColor(R.color.green));
                break;
        }

        //时间
        String subCurr = curr_time.substring(0, 10);
        String subStart = liveChapterBean.getStart_time().substring(0, 10);
        if (subCurr.equals(subStart)){
            holder.tvChapterItemTime.setText("今天" + liveChapterBean.getStart_time().substring(10, 16));
        }else {
            holder.tvChapterItemTime.setText(liveChapterBean.getStart_time());
        }

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.ll_chapter_item)
        LinearLayout llChapterItem;
        @InjectView(R.id.tv_chapter_item_name)
        TextView tvChapterItemName;
        @InjectView(R.id.tv_chapter_item_free)
        TextView tvChapterItemFree;
        @InjectView(R.id.tv_chapter_item_time)
        TextView tvChapterItemTime;
        @InjectView(R.id.tv_chapter_item_type)
        TextView tvChapterItemType;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
