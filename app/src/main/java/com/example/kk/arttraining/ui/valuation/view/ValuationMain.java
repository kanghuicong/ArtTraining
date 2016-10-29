package com.example.kk.arttraining.ui.valuation.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kk.arttraining.R;

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
