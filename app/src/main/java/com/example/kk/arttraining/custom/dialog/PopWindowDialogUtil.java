package com.example.kk.arttraining.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
