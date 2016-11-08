package com.example.kk.arttraining.ui.me.view;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.sqlite.bean.UploadBean;
import com.example.kk.arttraining.sqlite.dao.UploadDao;
import com.example.kk.arttraining.ui.me.adapter.UploadingAdapter;
import com.example.kk.arttraining.ui.me.presenter.UploadPresenter;
import com.example.kk.arttraining.utils.UIUtil;
import com.example.kk.arttraining.utils.upload.service.UploadQiNiuService;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/9/23 15:32
 * 说明:正在上传列表
 */
public class UploadingFragment extends Fragment implements IUploadFragment {
    @InjectView(R.id.lv_download)
    ListView lvDownload;
    private Fragment fragment;
    private View download_view;
    private Context context;
    private UploadPresenter presenter;
    private List<UploadBean> uploadBeanList;
    private UploadingAdapter uploadingAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        download_view = inflater.inflate(R.layout.me_download_fragment, null);
        presenter = new UploadPresenter(this);
        ButterKnife.inject(this, download_view);
        getLocalUploadData();

        return download_view;
    }

    @Override
    public void getLocalUploadData() {
        presenter.getLocalUploadData(context, "0");

    }

    @Override
    public void updateProgress() {

    }

    @Override
    public void onSuccess(List<UploadBean> uploadBeanList) {
        uploadingAdapter = new UploadingAdapter(context, uploadBeanList);
        lvDownload.setAdapter(uploadingAdapter);


    }

    //
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(UploadQiNiuService.ACTION_UPDATE);
        context.registerReceiver(myReceiver, filter);
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {


            int progress = intent.getIntExtra("progress", 0);
            String order_id = intent.getStringExtra("order_id");
            //如果下载完成，更改本地数据库状态
            if(progress==100){
                UploadDao uploadDao = new UploadDao(context);
                uploadDao.update("type","1",order_id);
            }
            //更新adapter进度条
            uploadingAdapter.updateProgress(order_id, progress);
            UIUtil.showLog("UploadingFragment->myReceiver","true");
        }

    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if(myReceiver!=null) unregisterReceiver(myReceiver);
//
//    }
}
