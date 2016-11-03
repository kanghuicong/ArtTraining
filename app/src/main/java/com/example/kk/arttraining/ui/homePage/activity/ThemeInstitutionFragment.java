package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.adapter.InstitutionFragmentAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/11/1.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeInstitutionFragment extends Fragment {
    Activity activity;
    View view_institution;
    @InjectView(R.id.lv_institution)
    MyListView lvInstitution;
    InstitutionFragmentAdapter adapter;
    public ThemeInstitutionFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        activity = getActivity();
        if (view_institution == null) {
            view_institution = View.inflate(activity, R.layout.homepage_institution_fragment, null);
            ButterKnife.inject(this, view_institution);
            initAdapter();
        }
        ViewGroup parent = (ViewGroup) view_institution.getParent();
        if (parent != null) {
            parent.removeView(view_institution);
        }
        return view_institution;
    }

    public void initAdapter(){
        adapter = new InstitutionFragmentAdapter(activity);
        lvInstitution.setAdapter(adapter);
        lvInstitution.setOnItemClickListener(new InstitutionItemClick());

    }

    private class InstitutionItemClick implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(activity, ThemeInstitutionContent.class);
            activity.startActivity(intent);

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
