package com.example.kk.arttraining.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kk.arttraining.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/10/25 15:23
 * 说明:
 */
public class PopWindowDialogUtil extends Dialog implements View.OnClickListener {

    private ChosePicDialogListener listener;
    private Context context;
    private int theme;
    private int layout;
    private String type;
    Button btn_takePic;
    Button btn_chosePic;
    Button btn_header_cancel;

    Button btn_clean;
    Button btn_clean_cancel;

    Button btn_video;
    Button btn_music;
    Button btn_image;
    Button btn_cancel;

    Button bt_share_collect;
    Button bt_share_report;
    Button bt_share_cancel;

    Button bt_dynamic_image_save;
    Button bt_dynamic_image_cancel;

    TextView tv_word;
    String word;


    public PopWindowDialogUtil(Context context, ChosePicDialogListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    public PopWindowDialogUtil(Context context, int theme) {
        super(context);
        this.context = context;
        this.theme = theme;

    }

    public PopWindowDialogUtil(Context context, int theme, int layout, String type, ChosePicDialogListener listener) {
        super(context, theme);
        this.context = context;
        this.theme = theme;
        this.listener = listener;
        this.layout = layout;
        this.type = type;
    }

    public PopWindowDialogUtil(Context context, int theme,int layout, String type,String word) {
        super(context,theme);
        this.context = context;
        this.theme = theme;
        this.layout = layout;
        this.type = type;
        this.word = word;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout);
        ButterKnife.inject(this);
        findView();

    }

    private void findView() {
        switch (type) {
            //选择图片dialog
            case "chosePic":
                btn_takePic = (Button) findViewById(R.id.btn_takePic);
                btn_chosePic = (Button) findViewById(R.id.btn_chosePic);
                btn_header_cancel = (Button) findViewById(R.id.btn_header_cancel);


                btn_takePic.setOnClickListener(this);
                btn_chosePic.setOnClickListener(this);
                btn_header_cancel.setOnClickListener(this);
                break;
            //清除数据dialog
            case "clean":
                btn_clean = (Button) findViewById(R.id.btn_me_dialog_clean);
                btn_clean_cancel = (Button) findViewById(R.id.btn_me_dialog_cancel);
                btn_clean.setOnClickListener(this);
                btn_clean_cancel.setOnClickListener(this);
                break;

            case "chose_production":
                btn_cancel = (Button) findViewById(R.id.btn_valutaion_dialog_cancel);
                btn_video = (Button) findViewById(R.id.btn_valutaion_dialog_video);
                btn_music = (Button) findViewById(R.id.btn_valutaion_dialog_music);
                btn_image = (Button)findViewById(R.id.btn_valutaion_dialog_image);
                btn_cancel.setOnClickListener(this);
                btn_video.setOnClickListener(this);
                btn_music.setOnClickListener(this);
                btn_image.setOnClickListener(this);
                break;
            case "share":
                bt_share_collect = (Button) findViewById(R.id.bt_homepage_share_collect);
                bt_share_report = (Button) findViewById(R.id.bt_homepage_share_report);
                bt_share_cancel = (Button) findViewById(R.id.bt_homepage_share_cancel);
                bt_share_collect.setOnClickListener(this);
                bt_share_report.setOnClickListener(this);
                bt_share_cancel.setOnClickListener(this);
                break;
            case "dynamicImage":
                bt_dynamic_image_save = (Button) findViewById(R.id.bt_dynamic_image_save);
                bt_dynamic_image_cancel = (Button) findViewById(R.id.bt_dynamic_image_cancel);
                bt_dynamic_image_save.setOnClickListener(this);
                bt_dynamic_image_cancel.setOnClickListener(this);
                break;
            case "word":
                tv_word = (TextView) findViewById(R.id.tv_word);
                tv_word.setText(word);
                break;
        }


    }

    public interface ChosePicDialogListener {
        public void onClick(View view);
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v);

    }
}
