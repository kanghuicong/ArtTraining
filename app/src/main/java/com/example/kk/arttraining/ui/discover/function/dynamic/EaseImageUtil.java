package com.example.kk.arttraining.ui.discover.function.dynamic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.IOException;

/**
 * Created by kanghuicong on 2017/1/16.
 * QQ邮箱:515849594@qq.com
 */
public class EaseImageUtil {

    public static Bitmap decodeScaleImage(String var0, int var1, int var2) {

        BitmapFactory.Options var3 = getBitmapOptions(var0);
        int var4 = calculateInSampleSize(var3, var1, var2);
        var3.inSampleSize = var4;
        var3.inJustDecodeBounds = false;
        Bitmap var5 = BitmapFactory.decodeFile(var0, var3);
        int var6 = readPictureDegree(var0);
        Bitmap var7 = null;
        if(var5 != null && var6 != 0) {
            var7 = rotateImageView(var6, var5);
            var5.recycle();
            var5 = null;
            return var7;
        } else {
            return var5;
        }
    }

    public static BitmapFactory.Options getBitmapOptions(String var0) {
        BitmapFactory.Options var1 = new BitmapFactory.Options();
        var1.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(var0, var1);
        return var1;
    }

    public static int calculateInSampleSize(BitmapFactory.Options var0, int var1, int var2) {
        int var3 = var0.outHeight;
        int var4 = var0.outWidth;
        int var5 = 1;
        if(var3 > var2 || var4 > var1) {
            int var6 = Math.round((float)var3 / (float)var2);
            int var7 = Math.round((float)var4 / (float)var1);
            var5 = var6 > var7?var6:var7;
        }

        return var5;
    }

    public static int readPictureDegree(String var0) {
        short var1 = 0;

        try {
            ExifInterface var2 = new ExifInterface(var0);
            int var3 = var2.getAttributeInt("Orientation", 1);
            switch(var3) {
                case 3:
                    var1 = 180;
                    break;
                case 6:
                    var1 = 90;
                    break;
                case 8:
                    var1 = 270;
            }
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return var1;
    }

    public static Bitmap rotateImageView(int var0, Bitmap var1) {
        Matrix var2 = new Matrix();
        var2.postRotate((float)var0);
        Bitmap var3 = Bitmap.createBitmap(var1, 0, 0, var1.getWidth(), var1.getHeight(), var2, true);
        return var3;
    }
}
