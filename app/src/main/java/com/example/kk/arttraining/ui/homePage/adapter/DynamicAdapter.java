package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.AdvertisBean;
import com.example.kk.arttraining.bean.AttachmentBean;
import com.example.kk.arttraining.bean.GeneralBean;
import com.example.kk.arttraining.bean.parsebean.ParseStatusesBean;
import com.example.kk.arttraining.bean.parsebean.StatusesBean;
import com.example.kk.arttraining.custom.view.EmptyGridView;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.custom.view.VipTextView;
import com.example.kk.arttraining.ui.homePage.activity.DynamicContent;
import com.example.kk.arttraining.ui.homePage.function.homepage.FindTitle;
import com.example.kk.arttraining.ui.homePage.function.homepage.LikeAnimatorSet;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DateUtils;
import com.example.kk.arttraining.utils.GlideCircleTransform;
import com.example.kk.arttraining.utils.GlideRoundTransform;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.JsonTools;
import com.example.kk.arttraining.utils.ScreenUtils;
import com.example.kk.arttraining.utils.TimeDelayClick;
import com.example.kk.arttraining.utils.UIUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/10/17.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicAdapter extends BaseAdapter {
    Context context;
    List<String> likeList = new ArrayList<String>();
    List<Integer> likeNum = new ArrayList<Integer>();
    String att_type;
    int like_position, width;
    List<Map<String, Object>> mapList;
    ParseStatusesBean parseStatusesBean = new ParseStatusesBean();
    AttachmentBean attachmentBean;
    int count;

    public DynamicAdapter(Context context, List<Map<String, Object>> mapList) {
        this.context = context;
        this.mapList = mapList;
        width = ScreenUtils.getScreenWidth(context);
        count = mapList.size();
        likeList.clear();
    }


    @Override
    public int getCount() {
        return mapList.size();
    }

    @Override
    public Object getItem(int position) {
        return mapList.get(position);
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
                Map<String, Object> adMap = mapList.get(position);
                convertView = View.inflate(context, R.layout.homepage_dynamic_advertisement_item, null);
                ImageView iv_advertisement = (ImageView) convertView.findViewById(R.id.iv_advertisement);
                likeList.add(position, "no");
                likeNum.add(position,0);
                AdvertisBean advertisBean = (AdvertisBean) adMap.get("data");
                Glide.with(context).load(advertisBean.getAd_pic()).error(R.mipmap.default_advertisement).into(iv_advertisement);
                break;

            case 2:
                convertView = View.inflate(context, R.layout.homepage_dynamic_topic_list, null);
                View view_title = (View) convertView.findViewById(R.id.layout_dynamic_topic_title);
                FindTitle.findTitle(view_title, context, "话题", R.mipmap.arrow_right_topic, "topic");
                MyListView lv_topic = (MyListView) convertView.findViewById(R.id.lv_dynamic_topic);
                likeList.add(position, "no");
                likeNum.add(position,0);
                Map<String, Object> themesMap = mapList.get(position);
                TopicAdapter topicAdapter = new TopicAdapter(context, themesMap);
                lv_topic.setAdapter(topicAdapter);
                break;

            case 3:
                final ViewHolder holder;
                if (convertView == null) {
                    convertView = View.inflate(context, R.layout.homepage_dynamic_item, null);
                    holder = new ViewHolder();
                    holder.ll_dynamic = (LinearLayout) convertView.findViewById(R.id.ll_homepage_dynamic);
                    holder.iv_header = (ImageView) convertView.findViewById(R.id.iv_homepage_dynamic_header);
                    holder.tv_time = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_time);
                    holder.tv_ordinary = (TextView) convertView.findViewById(R.id.tv_homepage_ordinary_name);
                    holder.tv_city = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_address);
                    holder.tv_review = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_teacher);
                    holder.tv_identity = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_identity);
                    holder.tv_content = (TextView) convertView.findViewById(R.id.tv_dynamic_content);
                    holder.gv_image = (EmptyGridView) convertView.findViewById(R.id.gv_dynamic_content_image);
                    holder.ll_music = (LinearLayout) convertView.findViewById(R.id.ll_dynamic_music);
                    holder.tv_like = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_like);
                    holder.tv_comment = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_comment);
                    holder.tv_browse = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_browse);
                    holder.tv_share = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_share);
                    holder.iv_video = (ImageView) convertView.findViewById(R.id.iv_dynamic_video);
                    holder.fl_video = (FrameLayout) convertView.findViewById(R.id.fl_dynamic_video);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                //获取精品动态数据
                Map<String, Object> statusMap = mapList.get(position);
                parseStatusesBean = (ParseStatusesBean) statusMap.get("data");//一条数据
                int like_id = parseStatusesBean.getStus_id();
                UIUtil.showLog("like_id", "" + like_id);
                String headerPath = parseStatusesBean.getOwner_head_pic();
                UIUtil.showLog("headerPath", headerPath);
                Glide.with(context).load(headerPath).transform(new GlideCircleTransform(context)).error(R.mipmap.default_user_header).into(holder.iv_header);
                if (parseStatusesBean.getIs_comment().equals("yes")) {
                    holder.tv_review.setText("已点评");
                    holder.tv_review.setVisibility(View.VISIBLE);
                } else {
                    holder.tv_review.setVisibility(View.GONE);
                }
                holder.tv_time.setText(DateUtils.getDate(parseStatusesBean.getCreate_time()));
                holder.tv_ordinary.setText(parseStatusesBean.getOwner_name());
                holder.tv_city.setText(parseStatusesBean.getCity());
                holder.tv_identity.setText(parseStatusesBean.getIdentity());
                if (parseStatusesBean.getContent() != null && !parseStatusesBean.getContent().equals("")) {
                    holder.tv_content.setVisibility(View.VISIBLE);
                    holder.tv_content.setText(parseStatusesBean.getContent());
                } else {
                    holder.tv_content.setVisibility(View.GONE);
                }
                likeNum.add(position, parseStatusesBean.getLike_num());
                holder.tv_like.setText(String.valueOf(likeNum.get(position)));
                holder.tv_comment.setText(String.valueOf(parseStatusesBean.getComment_num()));
                holder.tv_browse.setText(String.valueOf(parseStatusesBean.getBrowse_num()));


                //获取附件信息
                List<AttachmentBean> attachmentBeanList = parseStatusesBean.getAtt();
                UIUtil.showLog("AttachmentBean", attachmentBeanList + "-----" + position);
                if (attachmentBeanList != null && attachmentBeanList.size() != 0) {
                    attachmentBean = attachmentBeanList.get(0);
                    att_type = attachmentBean.getAtt_type();
                    //判断附件类型
                    switch (att_type) {
                        case "pic":
                            holder.gv_image.setVisibility(View.VISIBLE);
                            holder.ll_music.setVisibility(View.GONE);
                            holder.fl_video.setVisibility(View.GONE);
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
                            holder.fl_video.setVisibility(View.GONE);
                            break;
                        case "video":
                            ScreenUtils.accordHeight(holder.iv_video, width, 2, 5);//设置video图片高度
                            ScreenUtils.accordWidth(holder.iv_video, width, 1, 2);//设置video图片宽度
                            holder.fl_video.setVisibility(View.VISIBLE);
                            holder.gv_image.setVisibility(View.GONE);
                            holder.ll_music.setVisibility(View.GONE);
                            String imagePath = attachmentBean.getStore_path();
                            Glide.with(context).load(imagePath).error(R.mipmap.iv_advertisement).into(holder.iv_video);
                            break;
                    }
                } else if (attachmentBeanList == null || attachmentBeanList.size() == 0) {
                    holder.gv_image.setVisibility(View.GONE);
                    holder.ll_music.setVisibility(View.GONE);
                    holder.fl_video.setVisibility(View.GONE);
                }


                likeList.add(position, parseStatusesBean.getIs_like());
                UIUtil.showLog("parseStatusesBean", parseStatusesBean.getIs_like());
                if (likeList.get(position).equals("yes")) {
                    LikeAnimatorSet.setLikeImage(context, holder.tv_like, R.mipmap.like_yes);
                } else if (likeList.get(position).equals("no")) {
                    LikeAnimatorSet.setLikeImage(context, holder.tv_like, R.mipmap.like_no);
                }
                Map<String, Object> map = mapList.get(position);
                String type = map.get("type").toString();

                UIUtil.showLog("LikeClick1", likeList.get(position));
                holder.tv_like.setOnClickListener(new LikeClick(position, holder.tv_like, like_id, type));
                holder.ll_dynamic.setOnClickListener(new DynamicClick(position));
                holder.tv_share.setOnClickListener(new ShareClick(position, type, like_id));
                break;
        }
        return convertView;
    }

    private class LikeClick implements View.OnClickListener {
        int position;
        TextView tv_like;
        String type;
        int like_id;

        public LikeClick(int position, TextView tv_like, int like_id, String type) {
            this.position = position;
            this.tv_like = tv_like;
            this.like_id = like_id;
            this.type = type;
        }

        @Override
        public void onClick(View v) {
            like_position = position;
            UIUtil.showLog("GeneralBean", "likeList" + "--------" + likeList.get(position));
            UIUtil.showLog("LikeClick2", likeList.get(position));
            if (likeList.get(position).equals("no")) {
                UIUtil.showLog("LikeClick4", Config.ACCESS_TOKEN);
                if (Config.ACCESS_TOKEN == null || Config.ACCESS_TOKEN.equals("")) {
                    UIUtil.showLog("LikeClick3", "");
                    UIUtil.ToastshowShort(context, "请先登录...");
                } else {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("access_token", Config.ACCESS_TOKEN);
                    map.put("uid", Config.UID);
                    map.put("utype", Config.USER_TYPE);
                    map.put("like_id", like_id);

                    Callback<GeneralBean> callback = new Callback<GeneralBean>() {
                        @Override
                        public void onResponse(Call<GeneralBean> call, Response<GeneralBean> response) {
                            GeneralBean generalBean = response.body();
                            if (response.body() != null) {
                                if (generalBean.getError_code().equals("0")) {
                                    UIUtil.showLog("GeneralBean", "GeneralBean");
                                    LikeAnimatorSet.likeAnimatorSet(context, tv_like, R.mipmap.like_yes);
                                    likeList.add(position, "yes");
                                    parseStatusesBean = (ParseStatusesBean) mapList.get(position).get("data");
                                    parseStatusesBean.setIs_like("yes");
                                    parseStatusesBean.setLike_num(Integer.valueOf(tv_like.getText().toString()));
                                    likeNum.set(position, Integer.valueOf(tv_like.getText().toString()));
                                }
                            } else {
                                UIUtil.ToastshowLong(context, "点赞失败！");
                            }
                        }

                        @Override
                        public void onFailure(Call<GeneralBean> call, Throwable t) {
                            UIUtil.showLog("GeneralBean", "onFailure");
                        }
                    };
                    if (type.equals("work")) {
                        Call<GeneralBean> call = HttpRequest.getStatusesApi().statusesLikeCreateWork(map);
                        call.enqueue(callback);
                    } else if (type.equals("status")) {
                        Call<GeneralBean> call = HttpRequest.getStatusesApi().statusesLikeCreateBBS(map);
                        call.enqueue(callback);
                    }
                }
            } else if (likeList.get(position).equals("yes")) {
//                    tv_like.setClickable(false);
                if (TimeDelayClick.isFastClick(500)) {
                    return;
                } else {
                    UIUtil.ToastshowShort(context, "已点赞！");
                }
            }
        }
    }

    public void changeCount(int changecount) {
        count = changecount;
    }

    private class DynamicClick implements View.OnClickListener {
        int position;

        public DynamicClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Map<String, Object> statusMap = mapList.get(position);
            ParseStatusesBean parseStatusesBean = (ParseStatusesBean) statusMap.get("data");//一条数据
            Intent intent = new Intent(context, DynamicContent.class);
            intent.putExtra("stus_type", parseStatusesBean.getStus_type());
            intent.putExtra("status_id", String.valueOf(parseStatusesBean.getStus_id()));
            context.startActivity(intent);
        }
    }

    public int getSelfId() {
        ParseStatusesBean parseStatusesBean = (ParseStatusesBean) mapList.get(count - 1).get("data");
        return parseStatusesBean.getStus_id();
    }

    private class ShareClick implements View.OnClickListener {
        int position;
        String type;
        int favorite_id;

        public ShareClick(int position, String type, int favorite_id) {
            this.position = position;
            this.type = type;
            this.favorite_id = favorite_id;
        }

        @Override
        public void onClick(View v) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("access_token", Config.ACCESS_TOKEN);
            map.put("uid", Config.UID);
            map.put("type", type);
            map.put("favorite_id", favorite_id);
            map.put("user_title", Config.USER_TITLE);

            Callback<GeneralBean> callback = new Callback<GeneralBean>() {
                @Override
                public void onResponse(Call<GeneralBean> call, Response<GeneralBean> response) {
                    GeneralBean generalBean = response.body();
                    if (response.body() != null) {
                        if (generalBean.getError_code().equals("0")) {
                            UIUtil.ToastshowLong(context, "收藏成功！");
                        }
                    } else {
                        UIUtil.ToastshowLong(context, "已收藏！");
                    }
                }

                @Override
                public void onFailure(Call<GeneralBean> call, Throwable t) {
                    UIUtil.showLog("GeneralBean", "onFailure");
                }
            };
            Call<GeneralBean> call = HttpRequest.getStatusesApi().statusesFavoritesCreate(map);
            call.enqueue(callback);
        }
    }

    class ViewHolder {
        LinearLayout ll_dynamic;
        ImageView iv_header;
        VipTextView tv_vip;
        TextView tv_ordinary;
        TextView tv_review;
        TextView tv_time;
        TextView tv_city;
        TextView tv_identity;
        TextView tv_content;
        EmptyGridView gv_image;
        LinearLayout ll_music;
        ImageView iv_video;
        TextView tv_like;
        TextView tv_comment;
        TextView tv_browse;
        TextView tv_share;
        FrameLayout fl_video;
    }
}
