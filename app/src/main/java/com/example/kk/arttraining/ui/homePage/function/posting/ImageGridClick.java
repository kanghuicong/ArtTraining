package com.example.kk.arttraining.ui.homePage.function.posting;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.ChooseImageDialogUtil;
import com.example.kk.arttraining.ui.homePage.activity.PostingImgFileList;
import com.example.kk.arttraining.ui.homePage.activity.ShareDynamicImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanghuicong on 2016/11/6.
 * QQ邮箱:515849594@qq.com
 */
public class ImageGridClick  implements AdapterView.OnItemClickListener {
    Activity activity;
    int count;
    ArrayList<String> compressfile = new ArrayList<String>();
    ChooseImageDialogUtil dialog;
    List<String> listfile = new ArrayList<String>();
    String tv;

    public ImageGridClick(Activity activity,int count, ArrayList<String> compressfile,List<String> listfile,String tv) {
        this.activity = activity;
        this.compressfile = compressfile;
        this.count = count;
        this.listfile = listfile;
        this.tv = tv;
        Log.i("ImageGridClick", "ImageGridClick");
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView imageView = (ImageView)view.findViewById(R.id.gridvieww_image);
        if (position + 1 == count) {
            showDialog();
        } else {
            if (compressfile.size() != 0) {
                String image_path = compressfile.get(position);
                Intent intent = new Intent(activity, ShareDynamicImage.class);
                intent.putExtra("image_path", image_path);
                intent.putExtra("position", 0);
                int[] location = new int[2];
                imageView.getLocationOnScreen(location);
                intent.putExtra("locationX", location[0]);
                intent.putExtra("locationY", location[1]);
                intent.putExtra("width", imageView.getWidth());
                intent.putExtra("height", imageView.getHeight());
                activity.startActivity(intent);
                activity.overridePendingTransition(0, 0);
            }
        }
    }

    public void showDialog() {
        dialog = new ChooseImageDialogUtil(activity, R.layout.homepage_posting_dialog_chooseimg,
                R.style.Dialog, new ChooseImageDialogUtil.LeaveMyDialogListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_takephoto:
                        String pictruename = ImageUtil.Randompictrue();
                        String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + pictruename + ".jpg";
                        listfile.add(imageFilePath);

                        File temp = new File(imageFilePath);
                        Uri imageFileUri = Uri.fromFile(temp);// 获取文件的Uri
                        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 跳转到相机Activity
                        it.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);// 告诉相机拍摄完毕输出图片到指定的Uri
                        activity.startActivityForResult(it, 102);
                        dialog.dismiss();
                        break;
                    case R.id.btn_picture:
                        Intent intent = new Intent(activity, PostingImgFileList.class);
                        intent.putExtra("evaluate_content", tv);
                        activity.startActivity(intent);
                        dialog.dismiss();
                        activity.finish();
                        break;
                    case R.id.btn_cancel:
                        dialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });

        ImageUtil.showDialog(dialog);
    }
}
