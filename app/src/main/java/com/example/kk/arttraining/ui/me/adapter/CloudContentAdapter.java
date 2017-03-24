package com.example.kk.arttraining.ui.me.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.me.bean.CloudContentBean;
import com.example.kk.arttraining.utils.StringUtils;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2017/3/15.
 * QQ邮箱:515849594@qq.com
 */
public class CloudContentAdapter extends BaseAdapter {
    Context context;
    ViewHolder holder = null;
    int count;
    List<CloudContentBean> cloudBeanList;
    CloudContentBean cloudContentBean;

    public CloudContentAdapter(Context context,List<CloudContentBean> cloudBeanList) {
        this.context = context;
        this.cloudBeanList = cloudBeanList;
        count = cloudBeanList.size();
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
        cloudContentBean = cloudBeanList.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.me_cloud_content_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        switch (cloudContentBean.getConsume_type()) {
            case "consume":
                holder.tvCloudItemPay.setText("-" + StringUtils.getDouble(cloudContentBean.getCloud_money()));
                break;
            case "add":
                holder.tvCloudItemPay.setText("+" + StringUtils.getDouble(cloudContentBean.getCloud_money()));
                break;
        }

        holder.tvCloudItemNum.setText("余额："+StringUtils.getDouble(cloudContentBean.getCurr_cloud_money()));
        holder.tvCloudItemRemark.setText(cloudContentBean.getRemark());
        holder.tvCloudItemTime.setText(cloudContentBean.getConsume_time());

        return convertView;
    }

    public void  changeCount(int count) {
        this.count = count;
    }

    public int getSelf() {
        return cloudBeanList.get(count - 1).getCloud_id();
    }

    static class ViewHolder {
        @InjectView(R.id.tv_cloud_item_pay)
        TextView tvCloudItemPay;
        @InjectView(R.id.tv_cloud_item_num)
        TextView tvCloudItemNum;
        @InjectView(R.id.tv_cloud_item_time)
        TextView tvCloudItemTime;
        @InjectView(R.id.tv_cloud_item_remark)
        TextView tvCloudItemRemark;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
