package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.bean.LiveChapterBean;
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

    public LiveChapterAdapter(Context context, List<LiveChapterBean> liveChapterBeanList, String curr_time) {
        this.context = context;
        this.liveChapterBeanList = liveChapterBeanList;
        this.curr_time = curr_time;
    }

    @Override
    public int getCount() {
        return liveChapterBeanList.size() - 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //购买状态
    public int getOrderStatus(int position) {
        return liveChapterBeanList.get(position).getOrder_status();
    }

    //章节id
    public int getChapterId(int position) {
        return liveChapterBeanList.get(position).getChapter_id();
    }

    //直播价钱
    public double getChapterLivePrice(int position) {
        return liveChapterBeanList.get(position).getLive_price();
    }

    //重播价格
    public double getChapterRecordPrice(int position) {
        return liveChapterBeanList.get(position).getRecord_price();
    }

    //章节名字
    public String getChapterName(int position) {
        return liveChapterBeanList.get(position).getChapter_name();
    }

    //直播状态
    public int getChapterType(int position) {
        return liveChapterBeanList.get(position).getLive_status();
    }

    //是否免费
    public int getChapterFree(int position) {
        return liveChapterBeanList.get(position).getIs_free();
    }

    //重播地址
    public String getChapterRecord(int position) {
        return liveChapterBeanList.get(position).getRecord_url();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UIUtil.showLog("Chapter_list",liveChapterBeanList.size()+"-----4");
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
                holder.ivReplay.setVisibility(View.GONE);
                break;
            case 1:
                holder.tvChapterItemType.setText("直播中");
                holder.tvChapterItemType.setTextColor(context.getResources().getColor(R.color.green));
                holder.ivReplay.setVisibility(View.GONE);
                break;
            case 2:
                holder.tvChapterItemType.setText("重播");
                holder.tvChapterItemType.setTextColor(context.getResources().getColor(R.color.grey));
                holder.ivReplay.setVisibility(View.VISIBLE);
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
        if (subCurr.equals(subStart)) {
            holder.tvChapterItemTime.setText("今天" + liveChapterBean.getStart_time().substring(10, 16));
        } else {
            holder.tvChapterItemTime.setText(liveChapterBean.getStart_time());
        }

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_chapter_item_name)
        TextView tvChapterItemName;
        @InjectView(R.id.tv_chapter_item_type)
        TextView tvChapterItemType;
        @InjectView(R.id.tv_chapter_item_free)
        TextView tvChapterItemFree;
        @InjectView(R.id.tv_chapter_item_time)
        TextView tvChapterItemTime;
        @InjectView(R.id.ll_chapter_item)
        LinearLayout llChapterItem;
        @InjectView(R.id.iv_replay)
        ImageView ivReplay;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
