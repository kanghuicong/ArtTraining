package com.example.kk.arttraining.ui.me.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.live.view.PLVideoViewActivity;
import com.example.kk.arttraining.ui.live.view.PlayCallBackVideo;
import com.example.kk.arttraining.ui.me.bean.MyCourseBean;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2017/3/22.
 * QQ邮箱:515849594@qq.com
 */
public class MyCourseListAdapter extends BaseAdapter {
    int count;
    List<MyCourseBean> cloudBeanList;
    MyCourseBean courseBean;
    Context context;
    ViewHolder holder;

    public MyCourseListAdapter(Context context, List<MyCourseBean> cloudBeanList) {
        this.context = context;
        this.cloudBeanList = cloudBeanList;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        courseBean = cloudBeanList.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.me_mycourse_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvCourseOrder.setText("订单编号："+courseBean.getOrder_number());
        holder.tvCourseBuyTime.setText("购买时间："+courseBean.getBuy_time());
        holder.tvCourseLiveTime.setText("直播名称："+courseBean.getStart_time());
        holder.tvCoursePrice.setText("¥" + courseBean.getChapter_price());
        holder.tvCourseName.setText("课程名称："+courseBean.getChapter_name());

        switch (courseBean.getLive_status()) {
            case 0:
                holder.btCourse.setText("未开播");
                holder.btCourse.setOnClickListener(new clickCourse0());
                break;
            case 1:
                holder.btCourse.setText("正在直播");
                holder.btCourse.setOnClickListener(new clickCourse1(courseBean.getRoom_id(),courseBean.getChapter_id()));
                break;
            case 2:
                holder.btCourse.setText("回放");
                holder.btCourse.setOnClickListener(new clickCourse2(courseBean.getRecord_url()));
                break;
        }

        return convertView;
    }


    public void changeCount(int count) {
        this.count = count;
    }

    public int getSelf() {
        return cloudBeanList.get(count - 1).getBuy_id();
    }

    static class ViewHolder {
        @InjectView(R.id.tv_course_order)
        TextView tvCourseOrder;
        @InjectView(R.id.tv_course_buy_time)
        TextView tvCourseBuyTime;
        @InjectView(R.id.tv_course_name)
        TextView tvCourseName;
        @InjectView(R.id.tv_course_price)
        TextView tvCoursePrice;
        @InjectView(R.id.tv_course_live_time)
        TextView tvCourseLiveTime;
        @InjectView(R.id.bt_course)
        TextView btCourse;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    private class clickCourse0 implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            UIUtil.ToastshowShort(context,"课程还未开播！");
        }
    }

    private class clickCourse1 implements View.OnClickListener {
        int chapter_id;
        int room_id;

        public clickCourse1(int room_id, int chapter_id) {
            this.chapter_id = chapter_id;
            this.room_id = room_id;
        }

        @Override
        public void onClick(View v) {
            Intent intentBeing = new Intent(context, PLVideoViewActivity.class);
            intentBeing.putExtra("room_id", room_id);
            intentBeing.putExtra("chapter_id", chapter_id);
            context.startActivity(intentBeing);
        }
    }


    private class clickCourse2 implements View.OnClickListener {
        String record_url;
        public clickCourse2(String record_url) {
            this.record_url = record_url;
        }

        @Override
        public void onClick(View v) {
            Intent intentRecord = new Intent(context, PlayCallBackVideo.class);
            intentRecord.putExtra("path",record_url);
            context.startActivity(intentRecord);
        }
    }
}
