package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kanghuicong on 2016/10/31.
 * QQ邮箱:515849594@qq.com
 */
public class TeacherListViewAdapter extends BaseAdapter{
    Context context;
    List<Boolean> isClick = new ArrayList<Boolean>();
    List<TecInfoBean> tecInfoBeanList;
    TecInfoBean tecInfoBean;
    int isClickNum = 0;

    public TeacherListViewAdapter(Context context, List<TecInfoBean> tecInfoBeanList) {
        this.context = context;
        this.tecInfoBeanList = tecInfoBeanList;
        for (int i=0;i<15;i++) {
            isClick.add(i, false);
        }
    }

    @Override
    public int getCount() {
        return 15;
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
//        tecInfoBean = tecInfoBeanList.get(position);
        if (convertView == null){
            convertView = View.inflate(context, R.layout.homepage_teacher_item, null);
            holder = new ViewHolder();
            holder.iv_isClick = (ImageView) convertView.findViewById(R.id.iv_teacher_isClick);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.iv_isClick.setOnClickListener(new isClickImage(position));

        return convertView;
    }

    class ViewHolder {
        ImageView iv_isClick;
    }

    private class isClickImage implements View.OnClickListener {
        int position;
        public isClickImage(int position) {
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            ImageView iv = (ImageView)v.findViewById(R.id.iv_teacher_isClick);

            if (!isClick.get(position)){
                Log.i("isClick", isClick.get(position) + "-----"+position);
                if (isClickNum < 3) {
                    iv.setBackgroundResource(R.drawable.clean_ischeck);
                    isClick.add(position, true);
                    isClickNum++;
                    Log.i("isClickNum+", isClickNum + "-----"+position);
                }else {
                    UIUtil.ToastshowShort(context,"最多选三位名师测评~");
                }
            } else if (isClick.get(position)) {
                if (isClickNum > 0) {
                    iv.setBackgroundResource(R.drawable.clean_uncheck);
                    isClick.add(position, false);
                    isClickNum--;
                    Log.i("isClickNum-", isClickNum + "-----"+position);
                }
            }
        }
    }
}
