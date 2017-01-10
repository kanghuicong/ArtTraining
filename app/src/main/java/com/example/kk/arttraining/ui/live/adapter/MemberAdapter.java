package com.example.kk.arttraining.ui.live.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.live.bean.MemberBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2017/1/10 16:45
 * 说明:
 */
public class MemberAdapter extends BaseAdapter {
    private Context context;
    private List<MemberBean> dataList;
    private int count;

    public MemberAdapter(Context context, List<MemberBean> dataList) {
        this.context = context;
        this.dataList = dataList;
        count= dataList.size();
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
        if(convertView==null){
            convertView=View.inflate(context, R.layout.item_live_member,null);
        }else {

        }

        return convertView;
    }



    class ViewHoler{
        TextView tv_name;
        ImageView iv_head_pic;
        LinearLayout ll_member;

    }
}
