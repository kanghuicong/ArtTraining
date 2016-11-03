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
    List<Boolean> isClick;
    List<TecInfoBean> tecInfoBeanList;
    TecInfoBean tecInfoBean;
    static int isClickNum;
    CallBack callBack;
    String type;

    public ValuationListViewAdapter(Context context, List<TecInfoBean> tecInfoBeanList, List<Boolean> isClick, int isClickNum, String type,CallBack callBack) {
        this.context = context;
        this.tecInfoBeanList = tecInfoBeanList;
        this.callBack = callBack;
        this.isClickNum = isClickNum;
        this.isClick = isClick;
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
        Log.i("list的isClickNum", isClickNum + "-----");
        final ViewHolder holder;
        tecInfoBean = tecInfoBeanList.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.homepage_teacher_list_item, null);
            holder = new ViewHolder();
            holder.iv_isClick = (ImageView) convertView.findViewById(R.id.iv_teacher_isClick);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_teacher_list_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (type.equals("teacher")) {
            holder.iv_isClick.setVisibility(View.GONE);
        }else {
            holder.iv_isClick.setVisibility(View.VISIBLE);
        }

        holder.tv_name.setText(tecInfoBean.getName());
        holder.iv_isClick.setOnClickListener(new isClickImage(position));

        if(isClick.get(tecInfoBean.getTec_id())){
            holder.iv_isClick.setBackgroundResource(R.drawable.clean_ischeck);
        }else if(!isClick.get(tecInfoBean.getTec_id())){
            holder.iv_isClick.setBackgroundResource(R.drawable.clean_uncheck);
        }
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

            if (!isClick.get(tecInfoBean.getTec_id())) {
                if (isClickNum < 3) {
                    iv.setBackgroundResource(R.drawable.clean_ischeck);
                    isClick.set(tecInfoBean.getTec_id(), true);
                    isClickNum++;
                    callBack.callbackAdd(isClickNum,tecInfoBean.getTec_id(),tecInfoBean.getName());
                } else {
                    UIUtil.ToastshowShort(context, "最多选三位名师测评~");
                }
            } else if (isClick.get(tecInfoBean.getTec_id())) {
                iv.setBackgroundResource(R.drawable.clean_uncheck);
                isClick.set(tecInfoBean.getTec_id(), false);
                isClickNum--;
                callBack.callbackSub(isClickNum,tecInfoBean.getTec_id(),tecInfoBean.getName());
            }
        }
    }

    public interface CallBack{
        void callbackAdd(int isClickNum, int id, String name);
        void callbackSub(int isClickNum, int id, String name);
    }

    public static void Count(int count){
        isClickNum = count;
    }
}
