package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.modelbean.TecInfoBean;
import com.example.kk.arttraining.custom.view.FilletImageView;
import com.example.kk.arttraining.utils.LruCacheUtils;
import com.example.kk.arttraining.utils.PhotoLoader;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/15.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeTeacherAdapter extends BaseAdapter {
    Context context;
    List<TecInfoBean> tecInfoBeanList;
    TecInfoBean tecInfoBean;
    int count;

    public ThemeTeacherAdapter(Context context, List<TecInfoBean> tecInfoBeanList) {
        this.context = context;
        this.tecInfoBeanList = tecInfoBeanList;
        count = tecInfoBeanList.size();
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
        tecInfoBean = tecInfoBeanList.get(position);

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.homepage_teacher_list_item, null);
            holder = new ViewHolder();
            holder.iv_header = (FilletImageView) convertView.findViewById(R.id.iv_teacher_list_header);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_teacher_item_name);
            holder.tv_professor = (TextView) convertView.findViewById(R.id.tv_teacher_item_professor);
            holder.tv_specialty = (TextView) convertView.findViewById(R.id.tv_teacher_item_specialty);
            holder.tv_introduction = (TextView) convertView.findViewById(R.id.tv_teacher_item_introduction);
            holder.view_splitter = (View) convertView.findViewById(R.id.view_splitter);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (tecInfoBeanList.size() % 2 != 0 && position == tecInfoBeanList.size() - 1) {
            holder.view_splitter.setVisibility(View.GONE);
        } else {
            holder.view_splitter.setVisibility(View.VISIBLE);
        }

        //优先加载缩略图，只缓存最高解析图的image
        Glide.with(context).load(tecInfoBean.getBg_pic()).diskCacheStrategy(DiskCacheStrategy.SOURCE).thumbnail(0.5f).error(R.mipmap.posting_reslut_music).into(holder.iv_header);

        //缓存可能引起数据错乱

//        Bitmap bitmap = LruCacheUtils.getInstance().getBitmapFromMemCache(tecInfoBean.getBg_pic());
//        if (bitmap != null) {
//            holder.iv_header.setImageBitmap(bitmap);
//        } else {
//            PhotoLoader.displayImageTarget(holder.iv_header, tecInfoBean.getBg_pic(), PhotoLoader.getTarget(holder.iv_header,
//                    tecInfoBean.getBg_pic(), position),R.mipmap.default_video_icon);
//        }

        holder.tv_name.setText(tecInfoBean.getName());
        holder.tv_professor.setText(tecInfoBean.getTitle());
        holder.tv_specialty.setText("擅长:" + tecInfoBean.getSpecialty());

        if (tecInfoBean.getIntroduction() != null && !tecInfoBean.getIntroduction().equals("")) {
            String tv1 = tecInfoBean.getIntroduction().replace("\\n", "\n\n");
            String tv2 = tv1.replace("\\u3000", "");
            tecInfoBean.setIntroduction(tv2);
            holder.tv_introduction.setText(tecInfoBean.getIntroduction());
        }
        return convertView;
    }

    class ViewHolder {
        FilletImageView iv_header;
        TextView tv_name;
        TextView tv_professor;
        TextView tv_specialty;
        TextView tv_introduction;
        View view_splitter;
    }

    public int getSelfId() {
        return tecInfoBeanList.get(tecInfoBeanList.size() - 1).getTec_id();
    }

    public void ChangeCount(int changeCount) {
        count = changeCount;
    }
}