package com.example.kk.arttraining.ui.discover.adapter;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.Media.recodevideo.PlayAudioListenter;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.AdvertisBean;
import com.example.kk.arttraining.bean.AttachmentBean;
import com.example.kk.arttraining.bean.GeneralBean;
import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.bean.parsebean.ParseStatusesBean;
import com.example.kk.arttraining.custom.dialog.PopWindowDialogUtil;
import com.example.kk.arttraining.custom.view.EmptyGridView;
import com.example.kk.arttraining.custom.view.JustifyText;
import com.example.kk.arttraining.custom.view.LetterSpacingTextView;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.custom.view.VipTextView;
import com.example.kk.arttraining.ui.homePage.activity.DynamicContent;
import com.example.kk.arttraining.ui.homePage.activity.DynamicContentTeacherVideo;
import com.example.kk.arttraining.ui.homePage.activity.ThemeTeacherContent;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicImageAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.TopicAdapter;
import com.example.kk.arttraining.ui.homePage.bean.WorkComment;
import com.example.kk.arttraining.ui.homePage.function.homepage.FindTitle;
import com.example.kk.arttraining.ui.homePage.function.homepage.LikeAnimatorSet;
import com.example.kk.arttraining.ui.homePage.function.homepage.MusicAnimator;
import com.example.kk.arttraining.ui.homePage.function.homepage.MusicTouch;
import com.example.kk.arttraining.ui.homePage.function.homepage.ReadTecComment;
import com.example.kk.arttraining.ui.homePage.function.homepage.TokenVerfy;
import com.example.kk.arttraining.ui.homePage.prot.IMusic;
import com.example.kk.arttraining.ui.homePage.prot.ITokenVerfy;
import com.example.kk.arttraining.ui.me.bean.CollectBean;
import com.example.kk.arttraining.ui.me.view.PersonalHomePageActivity;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DateUtils;
import com.example.kk.arttraining.utils.GlideCircleTransform;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.NetUtils;
import com.example.kk.arttraining.utils.PlayAudioUtil;
import com.example.kk.arttraining.utils.ScreenUtils;
import com.example.kk.arttraining.utils.TimeDelayClick;
import com.example.kk.arttraining.utils.UIUtil;

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
public class DynamicAdapter extends BaseAdapter implements PlayAudioListenter, IMusic {

    Context context;
    List<String> likeList = new ArrayList<String>();
    List<Integer> likeNum = new ArrayList<Integer>();

    String att_type;
    int like_position, width;
    List<Map<String, Object>> mapList;
    ParseStatusesBean parseStatusesBean = new ParseStatusesBean();
    AttachmentBean attachmentBean;
    int count;

    //    PlayAudioUtil playAudioUtil = null;
    MusicCallBack musicCallBack;
    AnimatorSet MusicArtSet = new AnimatorSet();
    AnimationDrawable MusicAnim = new AnimationDrawable();
    String from = "";
    List<CollectBean> collectBeanList;
    MusicAnimator musicAnimatorSet = new MusicAnimator(this);
    String voicePath = "voicePath";
    PopWindowDialogUtil popWindowDialogUtil;

    public DynamicAdapter(Context context, List<Map<String, Object>> mapList, MusicCallBack musicCallBack) {
        this.context = context;
        this.mapList = mapList;
        this.musicCallBack = musicCallBack;
        width = ScreenUtils.getScreenWidth(context);
        count = mapList.size();
        likeList.clear();
    }

    public DynamicAdapter(Context context, List<Map<String, Object>> mapList, MusicCallBack musicCallBack, String from) {
        this.context = context;
        this.mapList = mapList;
        this.musicCallBack = musicCallBack;
        width = ScreenUtils.getScreenWidth(context);
        count = mapList.size();
        likeList.clear();
        this.from = from;
    }


    @Override
    public int getCount() {
        return count;
    }

    @Override
    public ParseStatusesBean getItem(int position) {
        return (ParseStatusesBean) mapList.get(position).get("data");
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
                likeNum.add(position, 0);
                AdvertisBean advertisBean = (AdvertisBean) adMap.get("data");
                Glide.with(context).load(advertisBean.getAd_pic()).error(R.mipmap.default_advertisement).into(iv_advertisement);
                break;

            case 2:
                convertView = View.inflate(context, R.layout.homepage_dynamic_topic_list, null);
                View view_title = (View) convertView.findViewById(R.id.layout_dynamic_topic_title);
                FindTitle.findTitle(view_title, context, "话题", R.mipmap.arrow_right_topic, "topic");
                MyListView lv_topic = (MyListView) convertView.findViewById(R.id.lv_dynamic_topic);
                likeList.add(position, "no");
                likeNum.add(position, 0);
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
                    holder.iv_type = (ImageView) convertView.findViewById(R.id.iv_homepage_dynamic_type);
                    holder.tv_identity = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_identity);
                    holder.tv_content = (JustifyText) convertView.findViewById(R.id.tv_dynamic_content);
                    holder.gv_image = (EmptyGridView) convertView.findViewById(R.id.gv_dynamic_content_image);
                    holder.ll_music = (LinearLayout) convertView.findViewById(R.id.ll_music);
                    holder.iv_music = (ImageView) convertView.findViewById(R.id.ivAdam);
                    holder.tv_music_time = (TextView) convertView.findViewById(R.id.tv_music_time);
                    holder.iv_music_art = (ImageView) convertView.findViewById(R.id.iv_music_art);
                    holder.tv_like = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_like);
                    holder.tv_comment = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_comment);
                    holder.tv_browse = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_browse);
                    holder.tv_share = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_share);

                    holder.iv_video = (ImageView) convertView.findViewById(R.id.iv_dynamic_video);
                    holder.fl_video = (FrameLayout) convertView.findViewById(R.id.fl_dynamic_video);
                    holder.iv_video_other = (ImageView) convertView.findViewById(R.id.iv_dynamic_video_other);
                    holder.tv_video_time = (TextView) convertView.findViewById(R.id.tv_dynamic_video_time);

                    holder.ll_comment_music = (LinearLayout) convertView.findViewById(R.id.ll_comment_music);
                    holder.iv_comment_voice_header = (ImageView) convertView.findViewById(R.id.iv_comment_music_header);
                    holder.tv_comment_voice_name = (TextView) convertView.findViewById(R.id.iv_comment_music_name);
                    holder.iv_comment_voice = (ImageView) convertView.findViewById(R.id.iv_comment_music);
                    holder.tv_comment_voice_time = (TextView) convertView.findViewById(R.id.iv_comment_music_time);
                    holder.tv_comment_voice_number = (TextView) convertView.findViewById(R.id.iv_comment_music_number);

                    holder.ll_comment_video = (LinearLayout) convertView.findViewById(R.id.ll_comment_video);
                    holder.iv_comment_video_header = (ImageView) convertView.findViewById(R.id.iv_comment_video_header);
                    holder.tv_comment_video_name = (TextView) convertView.findViewById(R.id.iv_comment_video_name);
                    holder.tv_comment_video_title = (TextView) convertView.findViewById(R.id.tv_comment_video_title);
                    holder.tv_comment_video_number = (TextView) convertView.findViewById(R.id.iv_comment_video_number);
                    holder.iv_comment_video_pic = (ImageView) convertView.findViewById(R.id.iv_comment_video_pic);

                    holder.ll_comment_word = (LinearLayout) convertView.findViewById(R.id.ll_comment_word);
                    holder.iv_comment_word_header = (ImageView) convertView.findViewById(R.id.iv_comment_word_header);
                    holder.tv_comment_word_name = (TextView) convertView.findViewById(R.id.iv_comment_word_name);
                    holder.tv_comment_word_content = (TextView) convertView.findViewById(R.id.iv_comment_word_content);

                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                //获取精品动态数据
                Map<String, Object> statusMap = mapList.get(position);
                parseStatusesBean = (ParseStatusesBean) statusMap.get("data");//一条数据

                int like_id = parseStatusesBean.getStus_id();
                String headerPath = parseStatusesBean.getOwner_head_pic();

                Glide.with(context).load(headerPath).transform(new GlideCircleTransform(context)).error(R.mipmap.default_user_header).into(holder.iv_header);

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

                if (attachmentBeanList != null && attachmentBeanList.size() != 0) {
                    attachmentBean = attachmentBeanList.get(0);
                    att_type = attachmentBean.getAtt_type();
                    //判断附件类型
                    switch (att_type) {
                        case "pic":
                            holder.gv_image.setVisibility(View.VISIBLE);
                            holder.fl_video.setVisibility(View.GONE);
                            holder.ll_music.setVisibility(View.GONE);
                            switch (attachmentBeanList.size()) {
                                case 1:
                                    holder.gv_image.setNumColumns(1);
                                    holder.gv_image.setSelector(new ColorDrawable(Color.TRANSPARENT));
                                    ScreenUtils.accordWidth(holder.gv_image, width, 1, 2);//设置gv的高度
                                    holder.gv_image.setOnItemClickListener(new GvDynamicClick(position));
                                    break;
                                case 2:
                                    holder.gv_image.setNumColumns(2);
                                    break;
                                case 3:
                                    holder.gv_image.setNumColumns(3);
                                    break;
                            }
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

                            if (statusMap.get("type").toString().equals("status")) {
                                holder.gv_image.setVisibility(View.GONE);
                                holder.fl_video.setVisibility(View.GONE);
                                holder.ll_music.setVisibility(View.VISIBLE);

                                DateUtils.getDurationTime(holder.tv_music_time, attachmentBean.getDuration());

                                holder.ll_music.setOnClickListener(new FlMusicClick(position, attachmentBean.getStore_path(), holder.iv_music, "dynamic"));
                            } else {
                                holder.gv_image.setVisibility(View.GONE);
                                holder.fl_video.setVisibility(View.VISIBLE);
                                holder.ll_music.setVisibility(View.GONE);

                                ScreenUtils.accordHeight(holder.iv_video, width, 1, 3);//设置video图片高度
                                ScreenUtils.accordWidth(holder.iv_video, width, 1, 2);//设置video图片宽度
                                holder.iv_video.setBackgroundResource(R.mipmap.dynamic_music_pic);

                                DateUtils.getDurationTime(holder.tv_video_time, attachmentBean.getDuration());

                                holder.iv_video_other.setBackgroundResource(R.mipmap.dynamic_vioce);
                            }

                            break;
                        case "video":
                            ScreenUtils.accordHeight(holder.iv_video, width, 1, 3);//设置video图片高度
                            ScreenUtils.accordWidth(holder.iv_video, width, 1, 2);//设置video图片宽度
                            holder.fl_video.setVisibility(View.VISIBLE);
                            holder.gv_image.setVisibility(View.GONE);
                            holder.ll_music.setVisibility(View.GONE);

                            holder.iv_video_other.setBackgroundResource(R.mipmap.dynamic_camere);


                            String imagePath = attachmentBean.getThumbnail();
                            Glide.with(context).load(imagePath).error(R.mipmap.comment_video_pic).into(holder.iv_video);
                            break;
                    }
                } else if (attachmentBeanList == null || attachmentBeanList.size() == 0) {
                    holder.gv_image.setVisibility(View.GONE);
                    holder.fl_video.setVisibility(View.GONE);
                    holder.ll_music.setVisibility(View.GONE);
                }

                likeList.add(position, parseStatusesBean.getIs_like());
                if (likeList.get(position).equals("yes")) {
                    LikeAnimatorSet.setLikeImage(context, holder.tv_like, R.mipmap.like_yes);
                } else if (likeList.get(position).equals("no")) {
                    LikeAnimatorSet.setLikeImage(context, holder.tv_like, R.mipmap.like_no);
                }
                Map<String, Object> map = mapList.get(position);
                String type = map.get("type").toString();

                holder.iv_header.setOnClickListener(new StudentHeaderClick(parseStatusesBean.getOwner()));
                holder.tv_like.setOnClickListener(new LikeClick(position, holder.tv_like, like_id, type));
                holder.ll_dynamic.setOnClickListener(new DynamicClick(position));
//                holder.tv_share.setOnClickListener(new ShareClick(position, type, like_id));
                holder.tv_share.setOnClickListener(new ShareClick(position, type, like_id));


                if (statusMap.get("type").toString().equals("status")) {
                    holder.iv_type.setBackgroundResource(R.mipmap.dynamic_status);
                    holder.ll_comment_music.setVisibility(View.GONE);
                    holder.ll_comment_video.setVisibility(View.GONE);
                    holder.ll_comment_word.setVisibility(View.GONE);
                } else {
                    holder.iv_type.setBackgroundResource(R.mipmap.dynamic_work);

                    List<WorkComment> workCommentList = parseStatusesBean.getTec_comment_list();
                    if (workCommentList != null && workCommentList.size() != 0) {
                        WorkComment workComment = workCommentList.get(0);
                        switch (workComment.getType()) {
                            case "voice":
                                holder.ll_comment_music.setVisibility(View.VISIBLE);
                                holder.ll_comment_video.setVisibility(View.GONE);
                                holder.ll_comment_word.setVisibility(View.GONE);

                                Glide.with(context).load(workComment.getTec_pic()).transform(new GlideCircleTransform(context)).error(R.mipmap.default_user_header).into(holder.iv_comment_voice_header);
                                holder.tv_comment_voice_name.setText(workComment.getName());
                                DateUtils.getDurationTime(holder.tv_comment_voice_time, workComment.getDuration());

                                holder.tv_comment_voice_number.setText("收听乐评\u3000" + workComment.getListen_num() + "次收听");
                                holder.ll_comment_music.setOnClickListener(new FlMusicClick(position, workComment.getContent(), holder.iv_comment_voice, "comment"));
                                holder.iv_comment_voice_header.setOnClickListener(new TeacherHeaderClick(workComment.getTec_id()));

                                break;
                            case "video":
                                holder.ll_comment_music.setVisibility(View.GONE);
                                holder.ll_comment_video.setVisibility(View.VISIBLE);
                                holder.ll_comment_word.setVisibility(View.GONE);

                                Glide.with(context).load(workComment.getTec_pic()).transform(new GlideCircleTransform(context)).error(R.mipmap.default_user_header).into(holder.iv_comment_video_header);
                                holder.tv_comment_video_name.setText(workComment.getName());
                                holder.tv_comment_video_title.setText(workComment.getTitle());
                                holder.tv_comment_video_number.setText("观看乐评\u3000" + workComment.getListen_num() + "次观看");


                                Glide.with(context).load(workComment.getThumbnail()).error(R.mipmap.comment_video_pic).into(holder.iv_comment_video_pic);
                                holder.ll_comment_video.setOnClickListener(new CommentVideoClick(workComment.getContent(), workComment.getThumbnail(), workComment.getComm_id(), workComment.getTec_id(), workComment.getComm_type()));
                                holder.iv_comment_video_header.setOnClickListener(new TeacherHeaderClick(workComment.getTec_id()));

                                break;
                            case "word":
                                holder.ll_comment_music.setVisibility(View.GONE);
                                holder.ll_comment_video.setVisibility(View.GONE);
                                holder.ll_comment_word.setVisibility(View.VISIBLE);

                                Glide.with(context).load(workComment.getTec_pic()).transform(new GlideCircleTransform(context)).error(R.mipmap.default_user_header).into(holder.iv_comment_word_header);
                                holder.tv_comment_word_name.setText(workComment.getName());
                                holder.tv_comment_word_content.setText(workComment.getContent());
                                holder.iv_comment_word_header.setOnClickListener(new TeacherHeaderClick(workComment.getTec_id()));

                                break;
                        }
                    } else {
                        holder.ll_comment_music.setVisibility(View.GONE);
                        holder.ll_comment_video.setVisibility(View.GONE);
                        holder.ll_comment_word.setVisibility(View.GONE);
                    }
                }
                break;
        }
        return convertView;
    }

    @Override
    public void playCompletion() {

    }

    //停止音乐播放回调
    @Override
    public void StopArtMusic(AnimatorSet MusicArtSet) {
        this.MusicArtSet = MusicArtSet;
    }

    @Override
    public void StopMusic(AnimationDrawable anim) {
        this.MusicAnim = anim;
    }

    private class StudentHeaderClick implements View.OnClickListener {
        int uid;

        public StudentHeaderClick(int uid) {
            this.uid = uid;
        }

        @Override
        public void onClick(View v) {
//            if (Config.ACCESS_TOKEN == null || Config.ACCESS_TOKEN.equals("")) {
//                UIUtil.ToastshowShort(context, context.getResources().getString(R.string.toast_user_login));
//                context.startActivity(new Intent(context, UserLoginActivity.class));
//            } else {
            if (from.equals("personal")) {
            } else {
                Intent intent = new Intent(context, PersonalHomePageActivity.class);
                intent.putExtra("uid", uid);
                context.startActivity(intent);
//                }
            }
        }
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
                    TokenVerfy.Login(context,2);
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
                                    parseStatusesBean.setLike_num(likeNum.get(position) + 1);
                                    likeNum.set(position, likeNum.get(position) + 1);
                                } else {
                                    if (generalBean.getError_code().equals("20028")) {
                                        TokenVerfy.Login(context,0);
                                    }else {
                                        UIUtil.ToastshowShort(context, generalBean.getError_msg());
                                    }
                                }
                            } else {
                                UIUtil.ToastshowLong(context, "点赞失败！");
                            }
                        }

                        @Override
                        public void onFailure(Call<GeneralBean> call, Throwable t) {
                            TokenVerfy.Login(context,1);
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
                if (TimeDelayClick.isFastClick(500)) {
                    return;
                } else {
                    UIUtil.ToastshowShort(context, "已点赞！");
                }
            }
        }
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
            if (from.equals("myWork")){
                intent.putExtra("type", from);
            }else {
                intent.putExtra("type", "dynamic");
            }
            context.startActivity(intent);
        }
    }

    private class FlMusicClick implements View.OnClickListener {
        String path;
        int position;
        ImageView ivMusicArt;
        String type;

        public FlMusicClick(int position, String path, ImageView ivMusicArt, String type) {
            this.path = path;
            this.position = position;
            this.ivMusicArt = ivMusicArt;
            this.type = type;
        }

        @Override
        public void onClick(View v) {
            if (NetUtils.isConnected(context)) {
                if (type.equals("comment")) {
                    if (Config.ACCESS_TOKEN == null || Config.ACCESS_TOKEN.equals("")) {
                        TokenVerfy.Login(context,2);
                    } else {
                        TokenVerfy tokenVerfy = new TokenVerfy(new ITokenVerfy() {
                            @Override
                            public void TokenSuccess() {
                                Map<String, Object> statusMap = mapList.get(position);
                                parseStatusesBean = (ParseStatusesBean) statusMap.get("data");
                                List<WorkComment> workCommentList = parseStatusesBean.getTec_comment_list();
                                WorkComment workComment = workCommentList.get(0);

                                MusicClick();
                                ReadTecComment.getReadTecComment(workComment.getComm_id(), workComment.getTec_id(), workComment.getComm_type());
                            }
                            @Override
                            public void TokenFailure(int flag) {
                                TokenVerfy.Login(context,flag);
                            }
                        });
                        tokenVerfy.getTokenVerfy();
                    }
                } else {
                    MusicClick();
                }
            } else {
                UIUtil.ToastshowShort(context, "网络连接失败！");
            }
        }

        public void MusicClick() {
            if (path != null && !path.equals("")) {
                if (!voicePath.equals(path)) {
                    if (Config.playAudioUtil != null) {
                        UIUtil.showLog("playAudioUtil", "地址不同，playAudioUtil不为空");
                        MusicTouch.stopMusicAnimation(Config.playAudioUtil, MusicAnim);

                        musicAnimatorSet.doMusicAnimator(ivMusicArt);
                        Config.playAudioUtil.playUrl(path);
                        voicePath = path;
                        musicCallBack.backPlayAudio(Config.playAudioUtil, MusicArtSet, MusicAnim, position);
                    } else {
                        UIUtil.showLog("playAudioUtil", "地址不同，playAudioUtil为空");
                        musicAnimatorSet.doMusicAnimator(ivMusicArt);

                        Config.playAudioUtil = new PlayAudioUtil(new PlayAudioListenter() {
                            @Override
                            public void playCompletion() {
                                MusicTouch.stopMusicAnimation(Config.playAudioUtil, MusicAnim);
                            }
                        });

                        Config.playAudioUtil.playUrl(path);
                        voicePath = path;
                        musicCallBack.backPlayAudio(Config.playAudioUtil, MusicArtSet, MusicAnim, position);
                    }
                } else {
                    UIUtil.showLog("playAudioUtil","地址相同");
                    MusicTouch.stopMusicAnimation(Config.playAudioUtil, MusicAnim);
                    voicePath = "voicePath";
                }
            } else {
                UIUtil.ToastshowShort(context, "发生错误，无法播放！");
            }
        }
    }

    private class CommentVideoClick implements View.OnClickListener {
        String path;
        String thumbnail;
        int comm_id;
        int tec_id;
        String comm_type;

        public CommentVideoClick(String content, String thumbnail, int comm_id, int tec_id, String comm_type) {
            this.comm_id = comm_id;
            this.tec_id = tec_id;
            this.comm_type = comm_type;
            path = content;
            this.thumbnail = thumbnail;
        }

        @Override
        public void onClick(View v) {
            if (Config.ACCESS_TOKEN == null || Config.ACCESS_TOKEN.equals("")) {
                TokenVerfy.Login(context,2);
            } else {
                TokenVerfy tokenVerfy = new TokenVerfy(new ITokenVerfy() {
                    @Override
                    public void TokenSuccess() {
                        ReadTecComment.getReadTecComment(comm_id, tec_id, comm_type);
                        Intent intent = new Intent(context, DynamicContentTeacherVideo.class);
                        intent.putExtra("path", path);
                        intent.putExtra("thumbnail", thumbnail);
                        context.startActivity(intent);
                    }
                    @Override
                    public void TokenFailure(int flag) {
                        TokenVerfy.Login(context,flag);
                    }
                });
                tokenVerfy.getTokenVerfy();
            }
        }
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
            popWindowDialogUtil = new PopWindowDialogUtil(context,R.style.transparentDialog, R.layout.dialog_homepage_share,"share", new PopWindowDialogUtil.ChosePicDialogListener() {
                @Override
                public void onClick(View view) {
                    popWindowDialogUtil.dismiss();
                    switch (view.getId()) {
                        case R.id.bt_homepage_share_collect:
                            if (Config.ACCESS_TOKEN == null || Config.ACCESS_TOKEN.equals("")) {
                                TokenVerfy.Login(context,2);
                            } else {
                                HashMap<String, Object> map = new HashMap<String, Object>();
                                map.put("access_token", Config.ACCESS_TOKEN);
                                map.put("uid", Config.UID);
                                map.put("type", type);
                                map.put("utype", Config.USER_TYPE);
                                map.put("favorite_id", favorite_id);

                                Callback<GeneralBean> callback = new Callback<GeneralBean>() {
                                    @Override
                                    public void onResponse(Call<GeneralBean> call, Response<GeneralBean> response) {
                                        GeneralBean generalBean = response.body();

                                        if (response.body() != null) {
                                            if (generalBean.getError_code().equals("0")) {
                                                UIUtil.ToastshowShort(context, "收藏成功！");
                                            } else {
                                                UIUtil.ToastshowShort(context, generalBean.getError_msg());
                                                if (generalBean.getError_code().equals("20028")) {
                                                    context.startActivity(new Intent(context, UserLoginActivity.class));
                                                }
                                            }
                                        } else {
                                            UIUtil.ToastshowShort(context, "OnFailure");
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<GeneralBean> call, Throwable t) {
                                        UIUtil.ToastshowShort(context, "网络连接失败！");
                                    }
                                };
                                Call<GeneralBean> call = HttpRequest.getStatusesApi().statusesFavoritesCreate(map);
                                call.enqueue(callback);
                            }
                            break;
                        case R.id.bt_homepage_share_report:
                            break;
                    }
                }
            });
            //设置从底部显示
            Window window = popWindowDialogUtil.getWindow();
            popWindowDialogUtil.show();
            window.setGravity(Gravity.BOTTOM);
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }
    }

    private class TeacherHeaderClick implements View.OnClickListener {
        int tec_id;

        public TeacherHeaderClick(int tec_id) {
            this.tec_id = tec_id;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ThemeTeacherContent.class);
            intent.putExtra("tec_id", tec_id + "");
            context.startActivity(intent);
        }
    }

    private class GvDynamicClick implements AdapterView.OnItemClickListener {
        int mposition;

        public GvDynamicClick(int position) {
            this.mposition = position;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String, Object> statusMap = mapList.get(mposition);
            ParseStatusesBean parseStatusesBean = (ParseStatusesBean) statusMap.get("data");//一条数据
            Intent intent = new Intent(context, DynamicContent.class);
            intent.putExtra("stus_type", parseStatusesBean.getStus_type());
            intent.putExtra("status_id", String.valueOf(parseStatusesBean.getStus_id()));
            intent.putExtra("type", "dynamic");
            context.startActivity(intent);
        }
    }

    public void changeCount(int changeCount) {
        count = changeCount;
    }

    public int getSelfId() {
        if (count > 1) {
            ParseStatusesBean parseStatusesBean = (ParseStatusesBean) mapList.get(count - 1).get("data");
            return parseStatusesBean.getStus_id();
        } else {
            return -1;
        }
    }

    public interface MusicCallBack {
        void backPlayAudio(PlayAudioUtil playAudioUtil, AnimatorSet MusicArtSet, AnimationDrawable MusicAnim, int position);
    }

    class ViewHolder {
        LinearLayout ll_dynamic;
        ImageView iv_header;
        VipTextView tv_vip;
        TextView tv_ordinary;
        ImageView iv_type;
        TextView tv_time;
        TextView tv_city;
        TextView tv_identity;
        JustifyText tv_content;
        EmptyGridView gv_image;
        LinearLayout ll_music;
        ImageView iv_music;
        TextView tv_music_time;
        ImageView iv_music_art;
        ImageView iv_video;
        TextView tv_like;
        TextView tv_comment;
        TextView tv_browse;
        TextView tv_share;
        FrameLayout fl_video;
        ImageView iv_video_other;
        TextView tv_video_time;

        LinearLayout ll_comment_music;
        ImageView iv_comment_voice_header;
        TextView tv_comment_voice_name;
        TextView tv_comment_voice_time;
        ImageView iv_comment_voice;
        TextView tv_comment_voice_number;

        LinearLayout ll_comment_video;
        ImageView iv_comment_video_header;
        TextView tv_comment_video_name;
        TextView tv_comment_video_title;
        TextView tv_comment_video_number;
        ImageView iv_comment_video_pic;

        LinearLayout ll_comment_word;
        ImageView iv_comment_word_header;
        TextView tv_comment_word_name;
        TextView tv_comment_word_content;

    }



}
