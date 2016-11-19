package com.example.kk.arttraining.ui.me.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.AttachmentBean;
import com.example.kk.arttraining.custom.view.EmptyGridView;
import com.example.kk.arttraining.custom.view.VipTextView;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicImageAdapter;
import com.example.kk.arttraining.ui.me.bean.CollectBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.GlideCircleTransform;
import com.example.kk.arttraining.utils.GlideRoundTransform;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/9 16:37
 * 说明:收藏adapter
 */
public class CollectAdapter extends BaseAdapter {
    Context context;
    List<CollectBean> collectBeanList;
    ViewHolder holder;
    CollectBean collectBean;
    AttachmentBean attachmentBean;
    String att_type;
    private int count;

    public CollectAdapter(Context context, List<CollectBean> collectBeanList) {
        this.collectBeanList = collectBeanList;
        count = collectBeanList.size();
        this.context = context;

    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {

        return collectBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        collectBean = collectBeanList.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.me_collect_item, null);
            holder = new ViewHolder();
            holder.ll_dynamic = (LinearLayout) convertView.findViewById(R.id.ll_homepage_dynamic_item);
            holder.iv_header = (ImageView) convertView.findViewById(R.id.iv_homepage_dynamic_header);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_time);
            holder.tv_ordinary = (TextView) convertView.findViewById(R.id.tv_homepage_ordinary_name);
            holder.tv_city = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_address);
            holder.tv_identity = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_identity);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_dynamic_content);
            holder.gv_image = (EmptyGridView) convertView.findViewById(R.id.gv_dynamic_content_image);
            holder.ll_music = (LinearLayout) convertView.findViewById(R.id.ll_dynamic_music);
            holder.iv_video = (ImageView) convertView.findViewById(R.id.iv_dynamic_video);
            holder.collect_time = (TextView) convertView.findViewById(R.id.collect_time);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(Config.USER_HEADER_Url).transform(new GlideCircleTransform(context)).error(R.mipmap.ic_launcher).into(holder.iv_header);
        String headerPath = collectBean.getOwner_head_pic();
        Glide.with(context).load(headerPath).transform(new GlideCircleTransform(context)).error(R.mipmap.ic_launcher).into(holder.iv_header);
        holder.tv_ordinary.setText(collectBean.getOwner_name());
        holder.tv_city.setText(collectBean.getCity());
        holder.tv_identity.setText(collectBean.getIdentity());
        holder.tv_content.setText(collectBean.getContent());
        //设置附件显示内容
        List<AttachmentBean> attachmentBeanList = collectBean.getAtt();
        if (attachmentBeanList.size() != 0) {
            attachmentBean = attachmentBeanList.get(0);
            att_type = attachmentBean.getAtt_type();
            //判断附件类型
            switch (att_type) {
                case "pic":
                    holder.gv_image.setVisibility(View.VISIBLE);
                    holder.ll_music.setVisibility(View.GONE);
                    holder.iv_video.setVisibility(View.GONE);
                    DynamicImageAdapter adapter = new DynamicImageAdapter(context, attachmentBeanList);
                    holder.gv_image.setAdapter(adapter);
                    //gridView空白部分点击事件
                    holder.gv_image.setOnTouchInvalidPositionListener(new EmptyGridView.OnTouchInvalidPositionListener() {
                        @Override
                        public boolean onTouchInvalidPosition(int motionEvent) {
                            return false;
                        }
                    });
                    break;
                case "music":
                    holder.gv_image.setVisibility(View.GONE);
                    holder.ll_music.setVisibility(View.VISIBLE);
                    holder.iv_video.setVisibility(View.GONE);
                    break;
                case "video":
                    holder.iv_video.setVisibility(View.VISIBLE);
                    holder.gv_image.setVisibility(View.GONE);
                    holder.ll_music.setVisibility(View.GONE);
                    String imagePath = attachmentBean.getThumbnail();
                    Glide.with(context).load(imagePath).transform(new GlideRoundTransform(context)).error(R.mipmap.ic_launcher).into(holder.iv_video);
                    break;
            }
        }
        return convertView;
    }


    public void RefreshCount(int count) {
        this.count = count;
    }

    public int getSelfId() {
        return collectBeanList.get(count - 1).getStus_id();
    }

    class ViewHolder {
        LinearLayout ll_dynamic;
        ImageView iv_header;
        VipTextView tv_vip;
        TextView tv_ordinary;
        TextView tv_time;
        TextView tv_city;
        TextView tv_identity;
        TextView tv_content;
        EmptyGridView gv_image;
        LinearLayout ll_music;
        ImageView iv_video;
        TextView collect_time;
    }
}
