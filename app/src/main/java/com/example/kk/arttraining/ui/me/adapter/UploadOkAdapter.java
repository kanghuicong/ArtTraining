package com.example.kk.arttraining.ui.me.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.sqlite.bean.UploadBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/7 13:44
 * 说明:
 */
public class UploadOkAdapter extends BaseAdapter {
    ViewHolder viewHolder;
    List<UploadBean> uploadBeanList;
    Context context;

    public UploadOkAdapter(Context context, List<UploadBean> uploadBeanList) {
        this.context = context;
        this.uploadBeanList = uploadBeanList;
    }

    @Override
    public int getCount() {
        return uploadBeanList.size();
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
        final UploadBean uploadBean = uploadBeanList.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_me_upload_ok, null);
            viewHolder.order_pic = (ImageView) convertView.findViewById(R.id.order_pic);
            viewHolder.order_title = (TextView) convertView.findViewById(R.id.order_title);
            viewHolder.tv_create_time = (TextView) convertView.findViewById(R.id.create_time);

        }
        UIUtil.showLog("查询本地上传完成列表：", uploadBean.toString());
        viewHolder.order_title.setText(uploadBean.getOrder_title());
//        viewHolder.tv_create_time.setText(uploadBean.getCreate_time());
//        Glide.with(context).load(uploadBean.getOrder_pic()).into(viewHolder.order_pic);
        Glide.with(context).load(Config.USER_HEADER_Url).into(viewHolder.order_pic);
        return convertView;
    }


    class ViewHolder {

        ImageView order_pic;
        ProgressBar progressBar;
        Button btn_upload;
        TextView order_title;
        TextView tv_progress;
        TextView tv_create_time;
    }
}
