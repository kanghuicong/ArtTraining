package com.example.kk.arttraining.ui.discover.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.kk.arttraining.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/9/19.
 * QQ邮箱:515849594@qq.com
 */
public class DiscoverMain extends Fragment {
    Activity activity;
    View view_discover;
    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        activity = getActivity();
        if (view_discover == null) {
            view_discover = View.inflate(activity, R.layout.discover_main, null);
            ButterKnife.inject(this, view_discover);
            initView();
        }
        ViewGroup parent = (ViewGroup) view_discover.getParent();
        if (parent != null) {
            parent.removeView(view_discover);
        }
        return view_discover;
    }

    void initView() {
        ivTitleBack.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
