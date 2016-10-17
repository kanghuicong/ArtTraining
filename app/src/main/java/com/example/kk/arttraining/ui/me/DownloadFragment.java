package com.example.kk.arttraining.ui.me;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kk.arttraining.R;

/**
 * 作者：wschenyongyin on 2016/9/23 15:32
 * 说明:
 */
public class DownloadFragment extends Fragment {
    private Fragment fragment;
    private View download_view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        download_view = inflater.inflate(R.layout.me_download_fragment, null);

        return download_view;
    }
}
