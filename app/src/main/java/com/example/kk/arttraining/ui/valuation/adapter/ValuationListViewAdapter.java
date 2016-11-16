package com.example.kk.arttraining.ui.valuation.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecInfoBean;
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
    String type;
    private static int count;

    public ValuationListViewAdapter(Context context, List<TecInfoBean> tecInfoBeanList, int isClickNum, String type, CallBack callBack) {
        this.context = context;
        this.tecInfoBeanList = tecInfoBeanList;
        this.callBack = callBack;
        this.isClickNum = isClickNum;
        count = tecInfoBeanList.size();
        this.type = type;
    }

    @Override
    public int getCount() {
        return tecInfoBeanList.size();
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
            convertView = View.inflate(context, R.layout.valuation_choose_teacher_item, null);
            holder = new ViewHolder();
            holder.iv_isClick = (ImageView) convertView.findViewById(R.id.iv_teacher_isClick);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_teacher_list_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (type.equals("teacher")) {
            holder.iv_isClick.setVisibility(View.GONE);
        } else {
            holder.iv_isClick.setVisibility(View.VISIBLE);
            if (tecInfoBean.isClick()) {
                holder.iv_isClick.setBackgroundResource(R.drawable.clean_ischeck);
            } else if (!tecInfoBean.isClick()) {
                holder.iv_isClick.setBackgroundResource(R.drawable.clean_uncheck);
            }
        }

        holder.tv_name.setText(tecInfoBean.getName());
        holder.iv_isClick.setOnClickListener(new isClickImage(position));

        return convertView;
    }

    class ViewHolder {
        ImageView iv_isClick;
        TextView tv_name;
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
                    iv.setBackgroundResource(R.drawable.clean_ischeck);
                    tecInfoBean.setClick(true);
                    isClickNum++;
                    callBack.callbackAdd(isClickNum, tecInfoBean);
                } else {
                    UIUtil.ToastshowShort(context, "最多选三位名师测评~");
                }
            } else if (tecInfoBean.isClick()) {
                iv.setBackgroundResource(R.drawable.clean_uncheck);
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

    public  int self_id() {
        return tecInfoBeanList.get(count - 1).getTec_id();
    }
}
