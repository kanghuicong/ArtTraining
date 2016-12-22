package com.example.kk.arttraining.ui.valuation.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.utils.GlideCircleTransform;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

/**
 * Created by kanghuicong on 2016/10/31.
 * QQ邮箱:515849594@qq.com
 */
public class ValuationListViewAdapter extends BaseAdapter {
    Context context;
    static List<TecInfoBean> tecInfoBeanList;
    TecInfoBean tecInfoBean;
    static int isClickNum;
    CallBack callBack;
    private static int count;

    public ValuationListViewAdapter(Context context, List<TecInfoBean> tecInfoBeanList, int isClickNum, CallBack callBack) {
        this.context = context;
        this.tecInfoBeanList = tecInfoBeanList;
        this.callBack = callBack;
        this.isClickNum = isClickNum;
        count = tecInfoBeanList.size();

    }

    @Override
    public int getCount() {
        return tecInfoBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return tecInfoBeanList.get(position);
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
            convertView = View.inflate(context, R.layout.valuation_choose_teacher_item, null);
            holder = new ViewHolder();
            holder.iv_header = (ImageView) convertView.findViewById(R.id.iv_teacher_list_header);
            holder.iv_isClick = (ImageView) convertView.findViewById(R.id.iv_teacher_isClick);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_teacher_list_name);
            holder.tv_professor = (TextView) convertView.findViewById(R.id.tv_teacher_list_professor);
            holder.tv_specialty = (TextView) convertView.findViewById(R.id.tv_teacher_list_specialty);
            holder.tv_comment = (TextView) convertView.findViewById(R.id.tv_teacher_list_comment);
            holder.tv_focus = (TextView) convertView.findViewById(R.id.tv_teacher_list_focus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (tecInfoBean.isClick()) {
            holder.iv_isClick.setBackgroundResource(R.mipmap.clean_check);
        } else if (!tecInfoBean.isClick()) {
            holder.iv_isClick.setBackgroundResource(R.mipmap.clean_uncheck);
        }


        Glide.with(context).load(tecInfoBean.getPic()).transform(new GlideCircleTransform(context)).error(R.mipmap.default_user_header).into(holder.iv_header);
        holder.tv_name.setText(tecInfoBean.getName());
        holder.tv_professor.setText(tecInfoBean.getTitle());
        holder.tv_comment.setText("点评:" + tecInfoBean.getComment());
        holder.tv_focus.setText("粉丝:" + tecInfoBean.getFans_num());
        holder.tv_specialty.setText("擅长:" + tecInfoBean.getSpecialty());
        holder.iv_isClick.setOnClickListener(new isClickImage(position));

        return convertView;
    }

    class ViewHolder {
        ImageView iv_isClick;
        ImageView iv_header;
        TextView tv_name;
        TextView tv_professor;
        TextView tv_specialty;
        TextView tv_comment;
        TextView tv_focus;
    }

    private class isClickImage implements View.OnClickListener {
        int position;

        public isClickImage(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            ImageView iv = (ImageView) v.findViewById(R.id.iv_teacher_isClick);
            TecInfoBean tecInfoBean = tecInfoBeanList.get(position);
            if (!tecInfoBean.isClick()) {
                if (isClickNum < 3) {
                    iv.setBackgroundResource(R.mipmap.clean_check);
                    tecInfoBean.setClick(true);
                    isClickNum++;
                    callBack.callbackAdd(isClickNum, tecInfoBean);
                } else {
                    UIUtil.ToastshowShort(context, "最多选三位名师测评~");
                }
            } else if (tecInfoBean.isClick()) {
                iv.setBackgroundResource(R.mipmap.clean_uncheck);
                tecInfoBean.setClick(false);
                isClickNum--;
                callBack.callbackSub(isClickNum, tecInfoBean);
            }
        }
    }

    public interface CallBack {
        void callbackAdd(int isClickNum, TecInfoBean tecInfoBean);

        void callbackSub(int isClickNum, TecInfoBean tecInfoBean);
    }

    public static void Count(int count) {
        isClickNum = count;
    }

    public void Refresh(int count) {
        this.count = count;
    }

    public int self_id() {
        return tecInfoBeanList.get(count - 1).getTec_id();
    }
}
