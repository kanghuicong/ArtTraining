package com.example.kk.arttraining.ui.me.view;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicAdapter;
import com.example.kk.arttraining.ui.homePage.function.homepage.Headlines;
import com.example.kk.arttraining.ui.homePage.function.homepage.MusicTouch;
import com.example.kk.arttraining.utils.PlayAudioUtil;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;
import java.util.Map;

/**
 * 作者：wschenyongyin on 2016/11/10 11:31
 * 说明:个人主页fragment
 */
public class PersonalPageFragment extends Fragment implements DynamicAdapter.MusicCallBack{
    private View view;
    Activity context;
    List<Map<String, Object>> mapList;
    private ListView listView;
    PlayAudioUtil playAudioUtil = null;
    int MusicPosition = -5;
    AnimatorSet MusicArtSet = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        view = View.inflate(context, R.layout.me_personal_page_fragment, null);


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void getUId(List<Map<String, Object>> mapList, Activity activity) {
        DynamicAdapter dynamicadapter = new DynamicAdapter(activity, mapList,this);
        view.findViewById(R.id.lv_personal_page);
        listView.setAdapter(dynamicadapter);

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        // 触摸移动时的操作
                        if (MusicPosition!=-5) {
                            if (listView.getFirstVisiblePosition() - 2 >= MusicPosition || listView.getLastVisiblePosition() <= MusicPosition) {
                                UIUtil.showLog("MusicStart", "onScroll");
                                playAudioUtil.stop();
                                MusicArtSet.end();
                            }
                        }
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public void backPlayAudio(PlayAudioUtil playAudioUtil, AnimatorSet MusicArtSet, int position) {
        this.playAudioUtil = playAudioUtil;
        this.MusicPosition = position;
        this.MusicArtSet = MusicArtSet;
    }

    @Override
    public void onPause() {
        super.onPause();
        MusicTouch.stopMusicAnimator(playAudioUtil, MusicArtSet);
    }

//上拉下拉也要
//    MusicTouch.stopMusicAnimator(playAudioUtil, MusicArtSet);
}
