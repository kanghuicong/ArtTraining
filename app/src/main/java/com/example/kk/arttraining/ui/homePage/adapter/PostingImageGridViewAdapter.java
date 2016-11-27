package com.example.kk.arttraining.ui.homePage.adapter;
//有问题发邮箱:wschenyongyin@qq.com

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.activity.PostingMain;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class PostingImageGridViewAdapter extends BaseAdapter {
    //	private ArrayList<String> listfile = null;
    List<String> listfile = new ArrayList<String>();
    private Context context;
    private Bitmap bitmap;
    BitmapFactory.Options options;
    int width;
    String type;
    PostingCallBack callback;


    public PostingImageGridViewAdapter(Context context, List<String> listfile, Bitmap bitmap,String type,PostingCallBack callback) {
        this.context = context;
        this.listfile = listfile;
        this.bitmap = bitmap;
        this.type = type;
        this.callback = callback;
        options = new BitmapFactory.Options();
        width = ScreenUtils.getScreenWidth(context);
    }




    @Override
    public int getCount() {
        int n;
        if (listfile.size()<3){
            n = listfile.size() + 1;
        }else {
            n = listfile.size();
        }
        return n;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Bitmap bm = null;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.homepage_posting_gridview_item, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.gridvieww_image);
            holder.fork = (ImageView) convertView.findViewById(R.id.gridvieww_fork);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ScreenUtils.accordHeight(holder.image,width,2,7);

        if (listfile != null) {
            if (position == listfile.size()) {
                holder.image.setTag(position);
                holder.image.setImageBitmap(bitmap);
                holder.fork.setVisibility(View.GONE);
            } else {
                String path = listfile.get(position);
                options.inSampleSize = 2;
                if (bm != null && !bm.isRecycled()) {
                    bm.recycle();
                    bm = null;
                    System.gc();
                }
                bm = BitmapFactory.decodeFile(path, options);
                holder.image.setTag(position);
                holder.image.setImageBitmap(bm);
            }
        } else {
            holder.image.setTag(position);
            holder.image.setImageBitmap(bitmap);
        }

        holder.fork.setOnClickListener(new ForkClick(position));
        return convertView;
    }

    private class ViewHolder {
        public ImageView image;
        public ImageView fork;
    }

    private class ForkClick implements View.OnClickListener {
        int position;
        public ForkClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            listfile.remove(position);
            if (type.equals("valuation")) {
                Config.ProductionImageList.remove(position);
            }else {
                Config.ShowImageList.remove(position);
            }
            notifyDataSetChanged();
            callback.subResult(listfile);
            if (listfile.size()==0){
                callback.backResult();
            }
        }
    }

    public interface PostingCallBack {
        void backResult();

        void subResult(List<String> listfile);
    }
}
