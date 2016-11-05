package com.example.kk.arttraining.ui.homePage.function.posting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.ChooseImageDialogUtil;
import com.example.kk.arttraining.ui.homePage.activity.PostingImgFileList;
import com.example.kk.arttraining.utils.CompressImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by kanghuicong on 2016/11/5.
 * QQ邮箱:515849594@qq.com
 */
public class ImageUtil {

    public static String Randompictrue() {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(date);
        Random random = new Random();
        int randNum = random.nextInt(100000 - 1) + 1;
        String Randompictrue = time + randNum;
        return Randompictrue;
    }


    //对取回来的图片进行压缩
    public static ArrayList<String> compressImage(Context context, List<String> list) throws IOException {
        ArrayList<String> imageList = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            File file = new File(list.get(i));
            String compressimage = null;

            String imagepath = list.get(i);
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            long size = fis.available();

            //当图片小于524kb时 不进行图片压缩
            if (size < 524288) {
                compressimage = imagepath;
            } else {
                compressimage = CompressImage.compressBitmap(context, imagepath, 300, 300, true);
            }
            imageList.add(compressimage);
        }
        return imageList;
    }


    public static void showDialog(ChooseImageDialogUtil dialog) {
        // 设置dialog弹出框显示在底部，并且宽度和屏幕一样
        Window window = dialog.getWindow();
        dialog.show();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }
}
