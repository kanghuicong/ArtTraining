package com.example.kk.arttraining.ui.me;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.ResponseObject;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/9/22 10:09
 * 说明:反馈
 */
public class FeedbackActivity extends BaseActivity {
    @InjectView(R.id.et_aboutus_feedback_content)
    EditText et_content;
    @InjectView(R.id.et_aboutus_feedback_phone)
    EditText et_phone;
    @InjectView(R.id.bt_aboutus_feedback_commit)
    Button btn_commit;
    @InjectView(R.id.title_back)
    ImageView img_back;
    @InjectView(R.id.title_barr)
    TextView title_barr;

    private String content;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_aboutus_feedback);
        init();
    }

    @Override
    public void init() {
        ButterKnife.inject(this);
        img_back.setOnClickListener(this);
        btn_commit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.bt_aboutus_feedback_commit:
                content = et_content.getText().toString();
                phoneNumber = et_content.getText().toString();
                CommitFeedback();
                break;
        }
    }

    //提交反馈
    private void CommitFeedback() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("", "");

        Callback callback = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.body() != null) {

                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        };
        Call<ResponseObject> call = HttpRequest.getApiService().feedback(map);
    }
}
