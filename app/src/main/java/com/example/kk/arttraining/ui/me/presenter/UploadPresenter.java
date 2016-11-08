package com.example.kk.arttraining.ui.me.presenter;

import android.content.Context;

import com.example.kk.arttraining.sqlite.bean.UploadBean;
import com.example.kk.arttraining.sqlite.dao.UploadDao;
import com.example.kk.arttraining.ui.me.view.IUploadFragment;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/7 08:36
 * 说明:上传列表处理类
 */
public class UploadPresenter {
    public IUploadFragment iUploadFragment;

    public UploadPresenter(IUploadFragment iUploadFragment) {
        this.iUploadFragment = iUploadFragment;
    }

    //从本地数据库查询上传列表信息
    public void getLocalUploadData(Context context, String type) {
        UploadDao uploadDao = new UploadDao(context);
        List<UploadBean> uploadBeanList = uploadDao.query(type);
        UIUtil.showLog("UploadPresenter-->","true");
        iUploadFragment.onSuccess(uploadBeanList);
    }
}
