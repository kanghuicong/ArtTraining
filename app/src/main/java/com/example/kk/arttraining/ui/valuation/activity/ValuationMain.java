package com.example.kk.arttraining.ui.valuation.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.UploadUtils;
import com.yixia.camera.demo.ui.record.MediaRecorderActivity;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/9/19.
 * QQ邮箱:515849594@qq.com
 */

public class ValuationMain extends Activity {
    Button btn;
    TextView tc;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuation_main);
        btn= (Button) findViewById(R.id.test_btn);
        tc= (TextView) findViewById(R.id.tv);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ValuationMain.this, MediaRecorderActivity.class);
                startActivityForResult(intent, 7001);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 7001:
                    String path = data.getStringExtra("mPath");
                    tc.setText("视频路径： " + path);
                    break;
            }
        }
    }

}
