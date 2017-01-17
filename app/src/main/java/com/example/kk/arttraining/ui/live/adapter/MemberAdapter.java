package com.example.kk.arttraining.ui.live.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.GlideCircleTransform;
import com.example.kk.arttraining.ui.homePage.activity.ThemeTeacherContent;
import com.example.kk.arttraining.ui.live.bean.MemberBean;
import com.example.kk.arttraining.ui.me.view.PersonalHomePageActivity;
import com.example.kk.arttraining.utils.Config;

import java.util.List;

/**
 * 作者：wschenyongyin on 2017/1/10 16:45
 * 说明:成员列表adapter
 */
public class MemberAdapter extends BaseAdapter {
    private Context context;
    private List<MemberBean> dataList;
    private int count;
    private ViewHoler viewHoler;
    private MemberBean memberBean;
    private String name;

    public MemberAdapter(Context context, List<MemberBean> dataList) {
        this.context = context;
        this.dataList = dataList;
        count = dataList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        memberBean = dataList.get(position);
        if (convertView == null) {
            viewHoler = new ViewHoler();
            convertView = View.inflate(context, R.layout.item_live_member, null);
            viewHoler.iv_head_pic = (ImageView) convertView.findViewById(R.id.tv_member_head_pic);
            viewHoler.tv_name = (TextView) convertView.findViewById(R.id.tv_member_name);
            viewHoler.ll_member = (LinearLayout) convertView.findViewById(R.id.ll_member);
            convertView.setTag(viewHoler);
        } else {
            viewHoler = (ViewHoler) convertView.getTag();
        }
        Glide.with(context).load(memberBean.getHead_pic()).diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.mipmap.default_user_header).transform(new GlideCircleTransform(context)).into(viewHoler.iv_head_pic);
        name = memberBean.getName();
        if (name != null && !name.equals(""))
            viewHoler.tv_name.setText(name);
        //点击用户头像跳转
        viewHoler.iv_head_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemberBean memberBean = dataList.get(position);
                toPersonPager(memberBean.getUtype(), memberBean.getUid());
            }
        });
        //点击item跳转
        viewHoler.ll_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemberBean memberBean = dataList.get(position);
                toPersonPager(memberBean.getUtype(), memberBean.getUid());
            }
        });


        return convertView;
    }

    void toPersonPager(String utype, int uid) {

        switch (utype) {
            case "tec":
                Intent tecIntent = new Intent(context, ThemeTeacherContent.class);
                tecIntent.putExtra("tec_id", uid);
                tecIntent.putExtra("type","member");
                context.startActivity(tecIntent);
                break;
            case "stu":
                Intent stuIntent = new Intent(context, PersonalHomePageActivity.class);
                stuIntent.putExtra("uid", uid);
                context.startActivity(stuIntent);
                break;
        }
    }


    public int getSelfId() {
        return dataList.get(count - 1).getMember_id();
    }

    public void RefreshCount(int count) {
        this.count = count;
    }


    class ViewHoler {
        TextView tv_name;
        ImageView iv_head_pic;
        LinearLayout ll_member;

    }
}
