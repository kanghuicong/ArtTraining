package com.example.kk.arttraining.ui.me.view;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.sqlite.bean.UploadBean;
import com.example.kk.arttraining.ui.me.adapter.UploadOkAdapter;
import com.example.kk.arttraining.ui.me.presenter.UploadPresenter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/9/23 15:32
 * 说明:上传完成
 */
public class UploadOkFragment extends Fragment implements IUploadFragment {
    @InjectView(R.id.lv_download)
    ListView lvDownload;
    private Fragment fragment;
    private View download_view;
    private UploadOkAdapter uploadOkAdapter;
    private UploadPresenter presenter;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        download_view = inflater.inflate(R.layout.me_download_fragment, null);
        context = getActivity().getApplicationContext();
        presenter = new UploadPresenter(this);
        ButterKnife.inject(this, download_view);
        getLocalUploadData();
        return download_view;
    }


    @Override
    public void getLocalUploadData() {
        presenter.getLocalUploadData(context, "1");
    }

    @Override
    public void updateProgress() {

    }

    @Override
    public void onSuccess(List<UploadBean> uploadBeanList) {
        uploadOkAdapter = new UploadOkAdapter(context, uploadBeanList);
        lvDownload.setAdapter(uploadOkAdapter);
    }
}
