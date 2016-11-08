package com.example.kk.arttraining.ui.me.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.Media.playvideo.media.VideoAdapter;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.sqlite.bean.UploadBean;
import com.example.kk.arttraining.sqlite.dao.UploadDao;
import com.example.kk.arttraining.ui.me.view.IUploadManager;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.upload.service.UploadQiNiuService;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 作者：wschenyongyin on 2016/11/7 09:11
 * 说明:正在上传的适配器
 */
public class UploadingAdapter extends BaseAdapter implements View.OnClickListener, IUploadManager {
    List<UploadBean> uploadBeanList;
    Context context;
    ViewHolder viewHolder;
    Map<String, Integer> mapProgress;
    Map<String, Boolean> mapBtn;
    Intent intent;
    private int progress_num;

    public UploadingAdapter(Context context, List<UploadBean> uploadBeanList) {
        this.context = context;
        this.uploadBeanList = uploadBeanList;
        intent= new Intent(context, UploadQiNiuService.class);
        mapProgress = new HashMap<String, Integer>();
        mapBtn = new HashMap<String, Boolean>();
        for (int i = 0; i < uploadBeanList.size(); i++) {
            UploadBean uploadBean = uploadBeanList.get(i);
            mapProgress.put(uploadBean.getOrder_id(), uploadBean.getProgress());
            mapBtn.put(uploadBean.getOrder_id(), false);
        }

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
            convertView = View.inflate(context, R.layout.item_me_uploading, null);
            viewHolder.order_pic = (ImageView) convertView.findViewById(R.id.order_pic);
            viewHolder.btn_upload = (Button) convertView.findViewById(R.id.btn_upload);
            viewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.dialog_progress);
            viewHolder.tv_progress = (TextView) convertView.findViewById(R.id.tv_progress_num);
            viewHolder.order_title = (TextView) convertView.findViewById(R.id.order_title);
            viewHolder.tv_create_time= (TextView) convertView.findViewById(R.id.create_time);
        }

        int progress_num = mapProgress.get(uploadBean.getOrder_id());
        final Boolean btn_state = mapBtn.get(uploadBean.getOrder_id());
        if (btn_state) {
            viewHolder.btn_upload.setSelected(false);
        } else {
            viewHolder.btn_upload.setSelected(true);
        }
        viewHolder.progressBar.setProgress(progress_num);
        viewHolder.tv_progress.setText(progress_num + "%");
        viewHolder.order_title.setText(uploadBean.getOrder_title());
        viewHolder.tv_create_time.setText(uploadBean.getCreate_time()+"");
//        Glide.with(context).load(uploadBean.getOrder_pic()).into(viewHolder.order_pic);
        Glide.with(context).load(Config.USER_HEADER_Url).into(viewHolder.order_pic);
        viewHolder.btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btn_state) {
                    startUpload(uploadBean.getFile_path(), uploadBean.getOrder_id());
                } else {
                    stopUpload(uploadBean.getFile_path(), uploadBean.getOrder_id());
                }
            }
        });
        return convertView;
    }


    //更新进度条
    public void updateProgress(String order_id, int progress_num) {
        this.progress_num=progress_num;
        mapProgress.put(order_id, progress_num);
        mapBtn.put(order_id, true);
        notifyDataSetChanged();
    }

    //开始下载
    @Override
    public void startUpload(String file_path, String order_id) {

        intent.setAction(UploadQiNiuService.ACTION_START);
        intent.putExtra("file_path", file_path);
        intent.putExtra("order_id", order_id);
        intent.putExtra("token", Config.QINIUYUN_TOKEN);
        mapBtn.put(order_id, true);
        context.startService(intent);

    }

    //停止下载
    @Override
    public void stopUpload(String file_path, String order_id) {
        intent.setAction(UploadQiNiuService.ACTION_PAUSE);
        intent.putExtra("file_path", file_path);
        intent.putExtra("order_id", order_id);
        intent.putExtra("token", Config.QINIUYUN_TOKEN);
        mapBtn.put(order_id, false);
        UploadDao uploadDao=new UploadDao(context);
        uploadDao.update("progress",progress_num+"",order_id);
        notifyDataSetChanged();
        context.startService(intent);
    }


    @Override
    public void onClick(View v) {

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
