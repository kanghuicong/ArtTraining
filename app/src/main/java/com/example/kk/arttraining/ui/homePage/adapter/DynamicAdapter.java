package com.example.kk.arttraining.ui.homePage.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.AdvertisBean;
import com.example.kk.arttraining.bean.AdvertisementEntity;
import com.example.kk.arttraining.bean.AttachmentBean;
import com.example.kk.arttraining.bean.DynamicContentEntity;
import com.example.kk.arttraining.bean.ThemesBean;
import com.example.kk.arttraining.bean.TopicEntity;
import com.example.kk.arttraining.bean.parsebean.ParseStatusesBean;
import com.example.kk.arttraining.custom.view.EmptyGridView;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.custom.view.VipTextView;
import com.example.kk.arttraining.ui.homePage.function.homepage.FindTitle;
import com.example.kk.arttraining.ui.homePage.function.homepage.LikeAnimatorSet;
import com.example.kk.arttraining.utils.GlideRoundTransform;
import com.nostra13.universalimageloader.utils.L;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kanghuicong on 2016/10/17.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicAdapter extends BaseAdapter {
    Context context;
    List<String> likeList = new ArrayList<String>();
    String att_type;
    int like_position;
    TextView tv_like;
    List<Map<String, Object>> mapList;
    ParseStatusesBean parseStatusesBean = new ParseStatusesBean();

    public DynamicAdapter(Context context, List<Map<String, Object>> mapList) {
        this.context = context;
        this.mapList = mapList;
        Log.i("mapList", mapList.size() + "----");

        for (int i = 0; i < mapList.size(); i++) {
            likeList.add("no");
        }
    }

    @Override
    public int getCount() {
        return mapList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getItemViewType(int position) {
        int ret;
        Map<String, Object> map = mapList.get(position);
        String type = map.get("type").toString();

        if (type.equals("ad")) {
            ret = 1;
        } else if (type.equals("theme")) {
            ret = 2;
        } else {
            ret = 3;
        }
        Log.i("position-ret", ret + "----" + position);
        return ret;
    }

    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case 1:
                Log.i("position1", "----" + position);
                Map<String, Object> adMap = mapList.get(position);
                convertView = View.inflate(context, R.layout.homepage_dynamic_advertisement_item, null);
                ImageView iv_advertisement = (ImageView) convertView.findViewById(R.id.iv_advertisement);

                AdvertisBean advertisBean = new AdvertisBean();
                advertisBean = (AdvertisBean) adMap.get("data");
                Glide.with(context).load(advertisBean.getAd_pic()).transform(new GlideRoundTransform(context)).into(iv_advertisement);
                break;
            case 2:
                Log.i("position2", "----" + position);
                convertView = View.inflate(context, R.layout.homepage_dynamic_topic_list, null);
                View view_title = (View) convertView.findViewById(R.id.layout_dynamic_topic_title);
                FindTitle.findTitle(view_title, context, "话题", R.mipmap.arrow_right_topic, "topic");
                MyListView lv_topic = (MyListView) convertView.findViewById(R.id.lv_dynamic_topic);

                Map<String, Object> themesMap = mapList.get(position);
                TopicAdapter topicAdapter = new TopicAdapter(context, themesMap);
                lv_topic.setAdapter(topicAdapter);
                break;
            case 3:
                Log.i("position3", "----" + position);
                final ViewHolder holder;
                if (convertView == null) {
                    convertView = View.inflate(context, R.layout.homepage_dynamic_content_item, null);
                    holder = new ViewHolder();

                    holder.tv_ordinary = (TextView) convertView.findViewById(R.id.tv_homepage_ordinary_name);
                    holder.tv_city = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_address);
                    holder.tv_identity = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_identity);
                    holder.tv_content = (TextView) convertView.findViewById(R.id.tv_dynamic_content);
                    holder.gv_image = (EmptyGridView) convertView.findViewById(R.id.gv_dynamic_content_image);
                    holder.ll_music = (LinearLayout) convertView.findViewById(R.id.ll_dynamic_music);
                    holder.tv_like = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_like);
                    holder.tv_comment = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_comment);
                    holder.tv_browse = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_browse);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                //获取精品动态数据
                Map<String, Object> statusMap = mapList.get(position);
                parseStatusesBean = (ParseStatusesBean) statusMap.get("data");//一条数据

                holder.tv_ordinary.setText(parseStatusesBean.getOwner_name());
                holder.tv_city.setText(parseStatusesBean.getCity());
                holder.tv_identity.setText(parseStatusesBean.getIdentity());
                holder.tv_content.setText(parseStatusesBean.getContent());
                holder.tv_like.setText(String.valueOf(parseStatusesBean.getLike_num()));
                holder.tv_comment.setText(String.valueOf(parseStatusesBean.getComment_num()));
                holder.tv_browse.setText(String.valueOf(parseStatusesBean.getBrowse_num()));

                List<AttachmentBean> attachmentBeanList = parseStatusesBean.getAtt();
                Log.i("attList", attachmentBeanList + "----");
                for (int i = 0; i < attachmentBeanList.size(); i++) {
                    AttachmentBean attachmentBean = attachmentBeanList.get(i);
                    att_type = attachmentBean.getAtt_type();
                    Log.i("attType1", att_type + "----");
                }
                Log.i("attType2", att_type + "----");
                switch (att_type) {
                    case "pic":
                        holder.gv_image.setVisibility(View.VISIBLE);
                        holder.ll_music.setVisibility(View.GONE);
                        Log.i("attpic", "123");
                        DynamicImageAdapter adapter = new DynamicImageAdapter(context, attachmentBeanList);
                        holder.gv_image.setAdapter(adapter);
                        //gridview空白部分点击事件
                        holder.gv_image.setOnTouchInvalidPositionListener(new EmptyGridView.OnTouchInvalidPositionListener() {
                            @Override
                            public boolean onTouchInvalidPosition(int motionEvent) {
                                return false;
                            }
                        });
                        break;
                    case "music":
                        break;
                    case "video":
                        break;
                }


                likeList.add(position, parseStatusesBean.getIs_like());
                if (likeList.get(position).equals("yes")) {
                    LikeAnimatorSet.setLikeImage(context, holder.tv_like, R.mipmap.like_yes);
                } else if (likeList.get(position).equals("no")) {
                    LikeAnimatorSet.setLikeImage(context, holder.tv_like, R.mipmap.like_no);
                }

                holder.tv_like.setOnClickListener(new LikeClick(position, holder.tv_like));

                break;
        }
        return convertView;
    }

    private class LikeClick implements View.OnClickListener {
        int position;
        TextView tv_like;

        public LikeClick(int position, TextView tv_like) {
            this.position = position;
            this.tv_like = tv_like;
        }

        @Override
        public void onClick(View v) {
            like_position = position;
            if (likeList.get(position).equals("no")) {
                LikeAnimatorSet.likeAnimatorSet(context, tv_like, R.mipmap.like_yes);
                likeList.add(position, "yes");
                parseStatusesBean = (ParseStatusesBean) mapList.get(position).get("data");
                parseStatusesBean.setIs_like("yes");
                parseStatusesBean.setLike_num(Integer.valueOf(tv_like.getText().toString()));
            } else if (likeList.get(position).equals("yes")) {
                tv_like.setClickable(false);
            }
        }
    }

    class ViewHolder {
        ImageView iv_header;
        VipTextView tv_vip;
        TextView tv_ordinary;
        TextView tv_time;
        TextView tv_city;
        TextView tv_identity;
        TextView tv_content;
        EmptyGridView gv_image;
        LinearLayout ll_music;
        TextView tv_like;
        TextView tv_comment;
        TextView tv_browse;
        TextView tv_share;
    }
}
