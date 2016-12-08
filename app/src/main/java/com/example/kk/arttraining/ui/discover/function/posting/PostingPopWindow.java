package com.example.kk.arttraining.ui.discover.function.posting;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.utils.UIUtil;

/**
 * Created by kanghuicong on 2016/11/6.
 * QQ邮箱:515849594@qq.com
 */
public class PostingPopWindow{
    public static PopupWindow window;
    public static ImageView ivChoosePostingType;
    public static GridView noScrollgridview;
    public static void showPopWindows(Activity activity, ImageView mivChoosePostingType, GridView mnoScrollgridview) {
        UIUtil.showLog("showPopWindows","showPopWindows");
        ivChoosePostingType = mivChoosePostingType;
        noScrollgridview = mnoScrollgridview;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = View.inflate(activity, R.layout.homepage_posting_popwindow, null);

        Button btPostingPopVideo = (Button) view.findViewById(R.id.bt_posting_pop_video);
        btPostingPopVideo.setOnClickListener(new PopVideo());
        Button btPostingPopAudio = (Button) view.findViewById(R.id.bt_posting_pop_audio);
        btPostingPopAudio.setOnClickListener(new PopAudio());
        Button btPostingPopImage = (Button) view.findViewById(R.id.bt_posting_pop_image);
        btPostingPopImage.setOnClickListener(new PopImage());
        Button btPostingPopCancel = (Button) view.findViewById(R.id.bt_posting_pop_cancel);
        btPostingPopCancel.setOnClickListener(new PopCancel());

        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        window.showAtLocation(activity.findViewById(R.id.ll_posting), Gravity.CENTER, 0, 0);
    }

    private static class PopVideo implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    private static class PopCancel implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            window.dismiss();
        }
    }

    private static class PopAudio implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    private static class PopImage implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ivChoosePostingType.setVisibility(View.GONE);
            noScrollgridview.setVisibility(View.VISIBLE);
            window.dismiss();
        }
    }
}
