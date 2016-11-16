package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.kk.arttraining.ui.homePage.activity.ThemeTeacherContent;
import com.example.kk.arttraining.ui.valuation.view.ValuationMain;
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
        ImageView iv_valuation = (ImageView) convertView.findViewById(R.id.iv_homepage_authority_valuation);
        ImageView iv_hear = (ImageView) convertView.findViewById(R.id.iv_homepage_authority_header);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_authority_teacher_name);
        TextView tv_professor = (TextView) convertView.findViewById(R.id.tv_authority_professor);
        TextView tv_like = (TextView) convertView.findViewById(R.id.tv_authority_like);
        TextView tv_eyes = (TextView) convertView.findViewById(R.id.tv_authority_eyes);

        //设置Item宽度
        ScreenUtils.accordWidth(layout, width, 1, 2);

        String headerPath = tecInfoBean.getPic();
        if (headerPath.equals("") || headerPath == null) {
            iv_hear.setBackgroundResource(R.mipmap.default_user_header);
        } else {
            Glide.with(context).load(headerPath).transform(new GlideCircleTransform(context)).error(R.mipmap.default_user_header).into(iv_hear);
        }
        tv_name.setText(tecInfoBean.getName());
        tv_professor.setText(tecInfoBean.getCollege());
        tv_like.setText(String.valueOf(tecInfoBean.getLike_num()));
        tv_eyes.setText(String.valueOf(tecInfoBean.getFans_num()));
        layout.setOnClickListener(new LayoutAuthority(position));
        iv_valuation.setOnClickListener(new ValuationClick(tecInfoBean.getSpecialty(),tecInfoBean));
        return convertView;
    }

    private class ValuationClick implements View.OnClickListener {
        String type;
        TecInfoBean tecInfoBean;
        public ValuationClick(String type, TecInfoBean tecInfoBean) {
            this.type = type;
            this.tecInfoBean = tecInfoBean;
        }

        @Override
        public void onClick(View v) {
            List<TecInfoBean> list = new ArrayList<TecInfoBean>();
            list.add(tecInfoBean);
            Intent intent = new Intent(context, ValuationMain.class);
            intent.putExtra("type", type);
            intent.putStringArrayListExtra("tec", (ArrayList)list);
            context.startActivity(intent);
        }
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