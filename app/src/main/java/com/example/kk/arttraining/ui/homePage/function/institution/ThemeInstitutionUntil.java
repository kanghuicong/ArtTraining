package com.example.kk.arttraining.ui.homePage.function.institution;

import android.content.Context;
import android.widget.ListView;

import com.example.kk.arttraining.ui.homePage.adapter.InstitutionFragmentAdapter;

/**
 * Created by kanghuicong on 2016/11/2.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeInstitutionUntil {

    public static void themeInstitutionUntil(Context context, ListView lvInstitution){
        InstitutionFragmentAdapter adapter = new InstitutionFragmentAdapter(context);
        lvInstitution.setAdapter(adapter);
    }

}
