package com.example.kk.arttraining.ui.me.view;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.PopDialogUtil;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.presenter.CleanDataPresenter;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/10/27 08:58
 * 说明:
 */
public class CleanCacheActivity extends BaseActivity implements ICleanCacheActivity, RadioGroup.OnCheckedChangeListener {
    @InjectView(R.id.rb_me_clean_pwd)
    CheckBox rb_clean_pwd;
    @InjectView(R.id.rb_me_clean_data)
    CheckBox rb_me_clean_data;
    @InjectView(R.id.rb_me_clean_cache)
    CheckBox rb_me_clean_cache;
    @InjectView(R.id.btn_me_clean)
    Button btn_me_clean;

    private PopDialogUtil cleanDialog;
    private CleanDataPresenter presenter;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_cleancache_activity);
        init();
    }

    @Override
    public void init() {
        ButterKnife.inject(this);
        loadingDialog = DialogUtils.createLoadingDialog(CleanCacheActivity.this, "正在清除...");
        presenter = new CleanDataPresenter(CleanCacheActivity.this, this);
        TitleBack.TitleBackActivity(CleanCacheActivity.this, "清除数据");
        btn_me_clean.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        ShowDialog();

    }

    private void ShowDialog() {
        cleanDialog = new PopDialogUtil(CleanCacheActivity.this, R.style.dialog, R.layout.dialog_clean_data, "clean", new PopDialogUtil.ChosePicDialogListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    case R.id.btn_me_dialog_clean:
                        cleanDialog.dismiss();
                        UIUtil.showLog("------>", "!!!!!!!!");
                        loadingDialog.show();
                        int flag = presenter.cleanData(rb_clean_pwd.isChecked(), rb_me_clean_data.isChecked(), rb_me_clean_cache.isChecked());
                        if (flag == 1) {
                            loadingDialog.dismiss();
                            UIUtil.ToastshowShort(CleanCacheActivity.this, "清除数据成功");
                        }
                        break;

                    case R.id.btn_me_dialog_cancel:
                        cleanDialog.dismiss();
                        break;
                }
            }
        });

        Window window = cleanDialog.getWindow();
        cleanDialog.show();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    @Override
    public void CleanPwd() {

    }

    @Override
    public void CleanLocalData() {

    }

    @Override
    public void CleanCache() {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }
}
