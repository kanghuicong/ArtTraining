package com.example.kk.arttraining.ui.me.adapter;

import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Message;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.Media.recodevideo.PlayAudioListenter;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.AttachmentBean;
import com.example.kk.arttraining.bean.parsebean.ParseStatusesBean;
import com.example.kk.arttraining.custom.view.EmptyGridView;
import com.example.kk.arttraining.custom.view.JustifyText;
import com.example.kk.arttraining.custom.view.VipTextView;
import com.example.kk.arttraining.ui.homePage.activity.DynamicContent;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicImageAdapter;
import com.example.kk.arttraining.ui.homePage.function.homepage.LikeAnimatorSet;
import com.example.kk.arttraining.ui.homePage.function.homepage.MusicAnimator;
import com.example.kk.arttraining.ui.homePage.prot.IMusic;
import com.example.kk.arttraining.ui.me.bean.CollectBean;
import com.example.kk.arttraining.ui.me.view.PersonalHomePageActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DateUtils;
import com.example.kk.arttraining.utils.GlideCircleTransform;
import com.example.kk.arttraining.utils.GlideRoundTransform;
import com.example.kk.arttraining.utils.PlayAudioUtil;
import com.example.kk.arttraining.utils.ScreenUtils;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：wschenyongyin on 2016/11/9 16:37
 * 说明:收藏adapter
 */
public class CollectAdapter extends BaseAdapter implements PlayAudioListenter,IMusic {
    Context context;
    List<CollectBean> collectBeanList;
    ViewHolder holder;
    ParseStatusesBean parseStatusesBean;
    AttachmentBean attachmentBean;
    String att_type;
    private int count;
    int width;
    AnimatorSet MusicArtSet = null;
    List<Boolean> musicPosition = new ArrayList<Boolean>();
    int MusicStart = -1;
    PlayAudioUtil playAudioUtil = null;
    AnimationDrawable MusicAnim = null;
    MusicCallBack musicCallBack;

    public CollectAdapter(Context context, List<CollectBean> collectBeanList,MusicCallBack musicCallBack) {
        this.collectBeanList = collectBeanList;
        count = collectBeanList.size();
        this.context = context;
        this.musicCallBack = musicCallBack;
        width = ScreenUtils.getScreenWidth(context);
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
        parseStatusesBean = collectBeanList.get(position).getStatuses();
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.me_collect_item, null);
            holder = new ViewHolder();
            holder.ll_mark = (LinearLayout) convertView.findViewById(R.id.ll_work_mark);
            holder.ll_dynamic = (LinearLayout) convertView.findViewById(R.id.ll_homepage_dynamic);
            holder.iv_header = (ImageView) convertView.findViewById(R.id.iv_homepage_dynamic_header);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_time);
            holder.tv_ordinary = (TextView) convertView.findViewById(R.id.tv_homepage_ordinary_name);
            holder.tv_city = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_address);
            holder.tv_review = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_teacher);
            holder.tv_identity = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_identity);
            holder.tv_content = (JustifyText) convertView.findViewById(R.id.tv_dynamic_content);
            holder.gv_image = (EmptyGridView) convertView.findViewById(R.id.gv_dynamic_content_image);
            holder.fl_music = (FrameLayout) convertView.findViewById(R.id.fl_dynamic_music);
            holder.ll_music = (LinearLayout) convertView.findViewById(R.id.ll_music);
            holder.iv_music = (ImageView)convertView.findViewById(R.id.ivAdam) ;
            holder.tv_music_time = (TextView) convertView.findViewById(R.id.tv_music_time);
            holder.iv_music_art = (ImageView) convertView.findViewById(R.id.iv_music_art);
            holder.iv_music_command = (ImageView) convertView.findViewById(R.id.iv_music_command);
            holder.tv_like = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_like);
            holder.tv_comment = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_comment);
            holder.tv_browse = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_browse);
            holder.tv_share = (TextView) convertView.findViewById(R.id.tv_homepage_dynamic_share);
            holder.iv_video = (ImageView) convertView.findViewById(R.id.iv_dynamic_video);
            holder.fl_video = (FrameLayout) convertView.findViewById(R.id.fl_dynamic_video);
            holder.tv_art_type = (TextView) convertView.findViewById(R.id.tv_art_type);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //获取精品动态数据

        //作品标志
        if (parseStatusesBean.getStus_type().toString().equals("work")) {
            holder.ll_mark.setVisibility(View.VISIBLE);
        } else {
            holder.ll_mark.setVisibility(View.GONE);
        }

        String headerPath = parseStatusesBean.getOwner_head_pic();
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

        musicPosition.add(position, false);

        //获取附件信息
        List<AttachmentBean> attachmentBeanList = parseStatusesBean.getAtt();

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

                    if (attachmentBean.getDuration() != null && !attachmentBean.getDuration().equals("")) {
                        float time = Float.parseFloat(attachmentBean.getDuration());
                        int mTime;
                        mTime = (int)time ;
                        holder.tv_music_time.setText(DateUtils.getMusicTime(mTime));
                    }

                    MusicAnimator musicAnimatorSet = new MusicAnimator(this);
                    holder.ll_music.setOnClickListener(new MusicClick(position, attachmentBean.getStore_path(),musicAnimatorSet,holder.iv_music));
                    break;
                case "video":
                    ScreenUtils.accordHeight(holder.iv_video, width, 2, 5);//设置video图片高度
                    ScreenUtils.accordWidth(holder.iv_video, width, 1, 2);//设置video图片宽度
                    holder.fl_video.setVisibility(View.VISIBLE);
                    holder.gv_image.setVisibility(View.GONE);
                    holder.ll_music.setVisibility(View.GONE);
                    String imagePath = attachmentBean.getThumbnail();
                    Glide.with(context).load(imagePath).error(R.mipmap.ic_launcher).into(holder.iv_video);
                    break;
            }
        } else if (attachmentBeanList == null || attachmentBeanList.size() == 0) {
            holder.gv_image.setVisibility(View.GONE);
            holder.ll_music.setVisibility(View.GONE);
            holder.fl_video.setVisibility(View.GONE);
        }


        holder.iv_header.setOnClickListener(new HeaderClick(parseStatusesBean.getOwner()));
        holder.ll_dynamic.setOnClickListener(new DynamicClick(position));

        return convertView;
    }


    public void RefreshCount(int count) {
        this.count = count;
    }

    public int getSelfId() {
        return collectBeanList.get(count - 1).getFav_id();
    }

    @Override
    public void playCompletion() {

    }

    @Override
    public void StopArtMusic(AnimatorSet MusicArtSet) {
        this.MusicArtSet = MusicArtSet;
    }

    @Override
    public void StopMusic(AnimationDrawable MusicAnim) {
        this.MusicAnim = MusicAnim;
    }

    private class HeaderClick implements View.OnClickListener {
        int uid;

        public HeaderClick(int uid) {
            this.uid = uid;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, PersonalHomePageActivity.class);
            intent.putExtra("uid", uid);
            context.startActivity(intent);
        }
    }

    private class DynamicClick implements View.OnClickListener {
        int position;

        public DynamicClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {

            ParseStatusesBean parseStatusesBean = collectBeanList.get(position).getStatuses();//一条数据
            Intent intent = new Intent(context, DynamicContent.class);
            intent.putExtra("stus_type", parseStatusesBean.getStus_type());
            UIUtil.showLog("status_id",String.valueOf(parseStatusesBean.getStus_id()));
            intent.putExtra("status_id", String.valueOf(parseStatusesBean.getStus_id()));
            context.startActivity(intent);
        }
    }

    private class MusicClick implements View.OnClickListener {
        String path;
        int position;
        MusicAnimator musicAnimatorSet;
        ImageView ivMusicArt;

        public MusicClick(int position, String path, MusicAnimator musicAnimatorSet, ImageView ivMusicArt) {
            this.path = path;
            this.position = position;
            this.musicAnimatorSet = musicAnimatorSet;
            this.ivMusicArt = ivMusicArt;
        }

        @Override
        public void onClick(View v) {
            if (path != null && !path.equals("")) {
                if (MusicStart != position) {
                    if (playAudioUtil != null) {
                        playAudioUtil.stop(1);
                        if (MusicAnim != null) {
                            MusicAnim.stop();
                        }

                        MusicStart = position;
                        musicAnimatorSet.doMusicAnimator(ivMusicArt);

//                        if (playAudioUtil == null) {
//                            playAudioUtil = new PlayAudioUtil(new PlayAudioListenter() {
//                                @Override
//                                public void playCompletion() {
//                                    if (MusicAnim != null) {
//                                        MusicAnim.stop();
//                                    }
//                                    musicPosition.set(position, false);
//                                }
//                            });
//                        }
                        UIUtil.showLog("playAudioUtilpath", path);
                        playAudioUtil.playUrl(path);
                        musicPosition.set(position, true);
                        musicCallBack.backPlayAudio(playAudioUtil, MusicArtSet, position);

                    } else {
                        if (!musicPosition.get(position)) {
                            musicAnimatorSet.doMusicAnimator(ivMusicArt);

                            if (playAudioUtil == null) {
                                playAudioUtil = new PlayAudioUtil(new PlayAudioListenter() {
                                    @Override
                                    public void playCompletion() {
                                        if (MusicAnim != null) {
                                            MusicAnim.stop();
                                        }
                                        playAudioUtil.stop(1);
                                        musicPosition.set(position, false);
                                    }
                                });
                            }
                            playAudioUtil.playUrl(path);
                            musicPosition.set(position, true);
                            MusicStart = position;
                            musicCallBack.backPlayAudio(playAudioUtil, MusicArtSet, position);
                        } else if (musicPosition.get(position)) {
                            UIUtil.showLog("MusicStart", "2");
                            if (MusicAnim != null) {
                                MusicAnim.stop();
                            }
                            playAudioUtil.stop(1);
                            musicPosition.set(position, false);
                        }
                    }
                    MusicStart = position;
                } else {
                    if (!musicPosition.get(position)) {
                        UIUtil.showLog("MusicStart", "3");
                        musicAnimatorSet.doMusicAnimator(ivMusicArt);

//                        if (playAudioUtil == null) {
//                            playAudioUtil = new PlayAudioUtil(new PlayAudioListenter() {
//                                @Override
//                                public void playCompletion() {
//                                    if (MusicAnim != null) {
//                                        MusicAnim.stop();
//                                    }
//                                    musicPosition.set(position, false);
//                                }
//                            });
//                        }
                        playAudioUtil.playUrl(path);
                        musicPosition.set(position, true);
                        MusicStart = position;
                        musicCallBack.backPlayAudio(playAudioUtil, MusicArtSet, position);
                    } else if (musicPosition.get(position)) {
                        UIUtil.showLog("MusicStart", "4");

                        if (MusicAnim != null) {
                            MusicAnim.stop();
                        }
                        playAudioUtil.stop(1);
                        musicPosition.set(position, false);
                    }
                }
            } else {
                UIUtil.ToastshowShort(context, "发生错误，无法播放！");
            }
        }
    }

    public interface MusicCallBack {
        void backPlayAudio(PlayAudioUtil playAudioUtil, AnimatorSet MusicArtSet, int position);
    }

    class ViewHolder {
        LinearLayout ll_mark;
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
        FrameLayout fl_music;
        LinearLayout ll_music;
        TextView tv_music_time;
        ImageView iv_music;
        ImageView iv_music_art;
        ImageView iv_music_command;
        ImageView iv_video;
        TextView tv_like;
        TextView tv_comment;
        TextView tv_browse;
        TextView tv_share;
        FrameLayout fl_video;
        TextView tv_art_type;
    }
}
