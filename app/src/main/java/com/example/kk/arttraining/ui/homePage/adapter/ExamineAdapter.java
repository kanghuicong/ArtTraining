package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.bean.ExamineCollegeBean;
import com.example.kk.arttraining.ui.webview.WebActivity;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2017/3/11.
 * QQ邮箱:515849594@qq.com
 */
public class ExamineAdapter extends BaseAdapter {
    Context context;
    ViewHolder holder;
    List<ExamineCollegeBean> examineList = new ArrayList<ExamineCollegeBean>();
    ExamineCollegeBean examineBean;
    Drawable drawable;

    public ExamineAdapter(Context context, List<ExamineCollegeBean> examineList) {
        this.context = context;
        this.examineList = examineList;
    }

    @Override
    public int getCount() {
        return examineList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        examineBean = examineList.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.home_examine_lv_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(examineBean.getCollege_logo()).error(R.mipmap.default_logo).into(holder.ivExamineLogo);
        holder.tvExamineSchool.setText(examineBean.getCollege_name());
        holder.tvExamineUrl.setText(examineBean.getWebsite());
        holder.tvExamineCode.setText("学校代码："+examineBean.getCollege_code());
        holder.tvExamineLevel.setText(examineBean.getLevel());
        holder.tvExamineProvince.setText(examineBean.getProvince());
        holder.tvExaminePMajor.setText("术科：" + examineBean.getP_major_score());
        holder.tvExaminePCulture.setText("文化：" + examineBean.getP_culture_score());
        holder.tvExamineCMajor.setText("术科：" + examineBean.getC_major_score());
        holder.tvExamineCCulture.setText("文化：" + examineBean.getC_culture_score());

        holder.tvExamineTrend.setText(examineBean.getScore()+"");

        if ("rise".equals(examineBean.getTrend())){
            drawable = context.getResources().getDrawable(R.mipmap.examine_rise);
            holder.tvExamineTrend.setTextColor(context.getResources().getColor(R.color.examine_green));
        }else if("decline".equals(examineBean.getTrend())){
            drawable = context.getResources().getDrawable(R.mipmap.examine_decline);
            holder.tvExamineTrend.setTextColor(context.getResources().getColor(R.color.examine_red));

        }

        holder.tvExamineTrend.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
        holder.tvExamineUrl.setOnClickListener(new clickExamineUrl(holder.tvExamineUrl,examineBean.getWebsite()) );

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_examine_school)
        TextView tvExamineSchool;
        @InjectView(R.id.tv_examine_url)
        TextView tvExamineUrl;
        @InjectView(R.id.tv_examine_trend)
        TextView tvExamineTrend;
        @InjectView(R.id.tv_examine_code)
        TextView tvExamineCode;
        @InjectView(R.id.tv_examine_level)
        TextView tvExamineLevel;
        @InjectView(R.id.tv_examine_province)
        TextView tvExamineProvince;
        @InjectView(R.id.tv_examine_pMajor)
        TextView tvExaminePMajor;
        @InjectView(R.id.tv_examine_pCulture)
        TextView tvExaminePCulture;
        @InjectView(R.id.tv_examine_cMajor)
        TextView tvExamineCMajor;
        @InjectView(R.id.tv_examine_cCulture)
        TextView tvExamineCCulture;
        @InjectView(R.id.iv_examine_logo)
        ImageView ivExamineLogo;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    private class clickExamineUrl implements View.OnClickListener {
        String website;
        TextView tvExamineUrl;
        public clickExamineUrl(TextView tvExamineUrl, String website) {
            this.tvExamineUrl = tvExamineUrl;
            this.website = website;
        }

        @Override
        public void onClick(View v) {
            tvExamineUrl.isEnabled();
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra("url",website);
            intent.putExtra("title", "官网");
            context.startActivity(intent);
            tvExamineUrl.isClickable();
        }
    }
}
