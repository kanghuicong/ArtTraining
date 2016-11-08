package com.example.kk.arttraining.ui.homePage.function.posting;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.ChooseImageDialogUtil;
import com.example.kk.arttraining.ui.homePage.activity.PostingImgFileList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanghuicong on 2016/11/7.
 * QQ邮箱:515849594@qq.com
 */
public class PostingDialog {

    static ChooseImageDialogUtil dialog;
    public static void showDialog(final Activity activity, final List<String> listfile, final String tv) {
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
