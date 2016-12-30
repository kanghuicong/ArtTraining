package com.example.kk.arttraining.utils;

import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kanghuicong on 2016/9/22.
 * QQ邮箱:515849594@qq.com
 * 说明:时间戳与时间格式变换
 */
public class DateUtils {

    private static SimpleDateFormat sf = null;
    //获取系统时间 格式为："yyyy-MM-dd HH:mm:ss"
    public static String getCurrentDate() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

    //时间戳转换成字符窜
    public static String getDateToString(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    //将字符串转为时间戳
    public static long getStringToDate(String time) {
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try{
            date = sf.parse(time);
        }
        catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String getDate(String time){
        long m;
        String time_unit = null;
        long time_issuance = getStringToDate(time);
        long time_now = new Date().getTime();
        long time_difference = time_now - time_issuance;
        if (time_difference < 86400000*7) {
            if (time_difference < 60000) {
                time_unit = "刚刚";
            }
            if (time_difference >= 60000 && time_difference < 3600000) {
                m = time_difference / 60000;
                time_unit = m + "分钟前";
            }
            if (time_difference >= 3600000 && time_difference < 86400000) {
                m = time_difference / 3600000;
                time_unit = m + "小时前";
            }
            if (time_difference >= 86400000 && time_difference < 86400000 * 7) {
                m = time_difference / 86400000;
                time_unit = m+"天前";
            }
        }else {
            time_unit = time;
        }
        return time_unit;
    }

    //3''20'
    public static void getDurationTime(TextView tv, String duration) {
        if (duration != null && !duration.equals("")) {
            float time = Float.parseFloat(duration);
            int mTime = (int) time;
            tv.setText(getMusicTime(mTime));
        } else {
            tv.setVisibility(View.GONE);
        }
    }

    public static String getMusicTime(int time) {
        String mTime = null;
        int n = time / 60;
        if (n < 1) {
            mTime = time + "''";
        } else {
            mTime = n + "'" + (time - n * 60) + "''";
        }
        return mTime;
    }

    //1.3万
    public static String getBrowseNumber(int number) {
        DecimalFormat df = new DecimalFormat("###.0");
        String BrowseNumber = "";
        if (number < 10000) {
            BrowseNumber = String.valueOf(number);
        }else {
            BrowseNumber = df.format((double)number / 10000) + "万";
        }
        return BrowseNumber;
    }


}
