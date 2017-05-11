package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.modelbean.TecInfoBean;
import com.example.kk.arttraining.custom.view.FilletImageView;
import com.example.kk.arttraining.ui.homePage.activity.ThemeTeacherContent;
import com.example.kk.arttraining.utils.ScreenUtils;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

/**
 * Created by kanghuicong on 2016/10/19.
 * QQ邮箱:515849594@qq.com
 * 权威测评adapter
 */
public class AuthorityAdapter extends BaseAdapter {
    private Context context;
    List<TecInfoBean> tecInfoBeanList;
    TecInfoBean tecInfoBean;
    int width;
    ViewHolder viewHolder;

    public AuthorityAdapter(Context context, List<TecInfoBean> tecInfoBeanList) {
        this.context = context;
        this.tecInfoBeanList = tecInfoBeanList;
        width = ScreenUtils.getScreenWidth(context);
    }

    @Override
    public int getCount() {
        return tecInfoBeanList.size();
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
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.homepage_authority_item, null);
            viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.ll_homepage_authority);
            viewHolder.iv_hear = (FilletImageView) convertView.findViewById(R.id.iv_homepage_authority_header);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_authority_teacher_name);
            viewHolder.tv_professor = (TextView) convertView.findViewById(R.id.tv_authority_professor);
            viewHolder.tv_introduction = (TextView) convertView.findViewById(R.id.tv_authority_introduction);
            viewHolder.tv_popularity = (TextView) convertView.findViewById(R.id.tv_authority_teacher_popularity);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        String headerPath = tecInfoBean.getBg_pic();
        if (headerPath == null || headerPath.equals("")) {
            viewHolder.iv_hear.setImageResource(R.mipmap.posting_reslut_music);
        } else {
            Glide.with(context).load(headerPath).error(R.mipmap.posting_reslut_music).into(viewHolder.iv_hear);
        }

        viewHolder.tv_name.setText(tecInfoBean.getName());
        viewHolder.tv_professor.setText(tecInfoBean.getTitle());
        viewHolder.tv_popularity.setText(tecInfoBean.getComment()+"");

        if (tecInfoBean.getIntroduction() != null && !tecInfoBean.getIntroduction().equals("")) {
            String tv1 = tecInfoBean.getIntroduction().replace("\\n", "\n\n");
            String tv2 = tv1.replace("\\u3000", "");
            tecInfoBean.setIntroduction(tv2);
            viewHolder.tv_introduction.setText(tecInfoBean.getIntroduction());
        }


        viewHolder.layout.setOnClickListener(new LayoutAuthority(position));
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

    class ViewHolder {
        LinearLayout layout;
        FilletImageView iv_hear;
        TextView tv_name;
        TextView tv_professor;
        TextView tv_introduction;
        TextView tv_popularity;
    }
}