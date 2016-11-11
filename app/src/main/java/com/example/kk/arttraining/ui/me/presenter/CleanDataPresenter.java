package com.example.kk.arttraining.ui.me.presenter;

import android.content.Context;

import com.example.kk.arttraining.sqlite.dao.UserDao;
import com.example.kk.arttraining.sqlite.dao.UserDaoImpl;
import com.example.kk.arttraining.ui.me.view.ICleanCacheActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DataCleanManager;
import com.example.kk.arttraining.utils.PreferencesUtils;

/**
 * 作者：wschenyongyin on 2016/10/27 10:06
 * 说明:
 */
public class CleanDataPresenter {

    private ICleanCacheActivity iCleanCacheActivity;
    private Context context;

    public CleanDataPresenter(ICleanCacheActivity iCleanCacheActivity, Context context) {
        this.iCleanCacheActivity = iCleanCacheActivity;
        this.context = context;

    }

    public int cleanData(Boolean cleanPwd, Boolean cleanUserData, Boolean cleanCache) {

        if (cleanPwd) cleanPwd();
        if (cleanUserData) cleanUserData();
        if (cleanCache) cleanCache();
        return 1;
    }

    public void cleanPwd() {
        UserDao userDao = new UserDaoImpl(context);
        userDao.Delete(Config.UID);
    }

    public void cleanUserData() {
        PreferencesUtils.remove(context, "access_token");
        PreferencesUtils.remove(context, "uid");
        PreferencesUtils.remove(context, "user_code");
    }

    public void cleanCache() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                DataCleanManager.cleanInternalCache(context);
            }
        }).start();

    }

}
