package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.custom.view.FilletImageView;
import com.example.kk.arttraining.ui.course.bean.ArtTeacherBean;
import com.example.kk.arttraining.utils.ScreenUtils;

import java.util.List;

/**
 * Created by kanghuicong on 2016/12/23.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeArtTeacherAdapter extends BaseAdapter {
    Context context;
    List<ArtTeacherBean> artTeacherBeanList;
    ArtTeacherBean artTeacherBean;
    int count;
    int width;

    public ThemeArtTeacherAdapter(Context context, List<ArtTeacherBean> artTeacherBeanList) {
        this.context = context;
        this.artTeacherBeanList = artTeacherBeanList;
        count = artTeacherBeanList.size();
        width = ScreenUtils.getScreenWidth(context);
    }

    @Override
    public int getCount() {
        return count;
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
        final ViewHolder holder;
        artTeacherBean = artTeacherBeanList.get(position);

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.homepage_art_teacher_list_item, null);
            holder = new ViewHolder();
            holder.iv_header = (FilletImageView) convertView.findViewById(R.id.iv_teacher_list_header);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_teacher_item_name);
            holder.tv_specialty = (TextView) convertView.findViewById(R.id.tv_teacher_item_specialty);
            holder.view_splitter = (View) convertView.findViewById(R.id.view_splitter);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (artTeacherBeanList.size() % 2 != 0 && position == artTeacherBeanList.size() - 1) {
            holder.view_splitter.setVisibility(View.GONE);
        } else {
            holder.view_splitter.setVisibility(View.VISIBLE);
        }

        Glide.with(context).load(artTeacherBean.getIcon_url()).error(R.mipmap.posting_reslut_music).into(holder.iv_header);
        holder.tv_name.setText(artTeacherBean.getName());
        holder.tv_specialty.setText(artTeacherBean.getArt_type());

        return convertView;
    }

    class ViewHolder {
        FilletImageView iv_header;
        TextView tv_name;
        TextView tv_specialty;
        View view_splitter;
    }


    public void ChangeCount(int changeCount) {
        count = changeCount;
    }
}