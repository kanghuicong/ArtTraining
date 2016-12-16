package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.custom.view.XCRoundRectImageView;
import com.example.kk.arttraining.ui.homePage.activity.ThemeTeacherContent;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.ui.valuation.view.ValuationMain;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.GlideCircleTransform;
import com.example.kk.arttraining.utils.ScreenUtils;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanghuicong on 2016/10/19.
 * QQ邮箱:515849594@qq.com
 */
public class AuthorityAdapter extends BaseAdapter {
    private Context context;
    List<TecInfoBean> tecInfoBeanList;
    TecInfoBean tecInfoBean;
    int width;

    public AuthorityAdapter(Context context, List<TecInfoBean> tecInfoBeanList) {
        this.context = context;
        this.tecInfoBeanList = tecInfoBeanList;
        width = ScreenUtils.getScreenWidth(context);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        tecInfoBean = tecInfoBeanList.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.homepage_authority_item, null);
        LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.ll_homepage_authority);
        XCRoundRectImageView iv_hear = (XCRoundRectImageView) convertView.findViewById(R.id.iv_homepage_authority_header);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_authority_teacher_name);
        TextView tv_professor = (TextView) convertView.findViewById(R.id.tv_authority_professor);
        TextView tv_introduction = (TextView) convertView.findViewById(R.id.tv_authority_introduction);

        String headerPath = tecInfoBean.getBg_pic();
        if (headerPath == null ||headerPath.equals("") ) {
            iv_hear.setImageResource(R.mipmap.posting_reslut_music);
        } else {
            Glide.with(context).load(headerPath).error(R.mipmap.posting_reslut_music).into(iv_hear);
        }

        tv_name.setText(tecInfoBean.getName());
        tv_professor.setText(tecInfoBean.getTitle());

        if(tecInfoBean.getIntroduction()!=null&&!tecInfoBean.getIntroduction().equals("")){
            String tv1 = tecInfoBean.getIntroduction().replace("\\n", "\n\n");
            String tv2 = tv1.replace("\\u3000", "");
            tecInfoBean.setIntroduction(tv2);
            tv_introduction.setText(tecInfoBean.getIntroduction());
        }


        layout.setOnClickListener(new LayoutAuthority(position));
        return convertView;
    }

    private class LayoutAuthority implements View.OnClickListener {
        int position;

        public LayoutAuthority(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            TecInfoBean tecInfoBean = tecInfoBeanList.get(position);
            Intent intent = new Intent(context, ThemeTeacherContent.class);
            intent.putExtra("tec_id", tecInfoBean.getTec_id() + "");
            UIUtil.showLog("tec_id", tecInfoBean.getTec_id() + "");
            context.startActivity(intent);
        }
    }
}