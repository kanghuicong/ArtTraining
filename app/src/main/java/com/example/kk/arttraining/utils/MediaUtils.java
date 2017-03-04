package com.example.kk.arttraining.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;
import android.util.Log;

import com.example.kk.arttraining.bean.modelbean.MusicInfoBean;
import com.example.kk.arttraining.bean.modelbean.VideoInfoBean;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/31 14:10
 * 说明:
 */
public class MediaUtils {

    //获取视频略缩图
    public static Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


    //扫描视频文件
    public static List<VideoInfoBean> scanAllVideoFiles(Context context) {
        List<VideoInfoBean> videoInfoBeanList = new ArrayList<VideoInfoBean>();
        ContentResolver contentResolver = context.getContentResolver();
        String[] projection = new String[]{MediaStore.Video.Media.TITLE};
        Cursor cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null,
                null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        int fileNum = cursor.getCount();

        for (int counter = 0; counter < fileNum; counter++) {
            String video_name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
            String video_url = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            long size = cursor.getLong(cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));

            float a;
            float b;
            if (size > 1024 * 800) {// 大于800K
                VideoInfoBean videoInfoBean = new VideoInfoBean();

                String video_size;
                if (size / (1014 * 1024) > 0) {

                    a = (float) (size / 1048576.0);
                    b = (float) (Math.round(a * 10)) / 10;

                    video_size = b + " MB";
                } else {
                    a = (float) (size / 1024.0);
                    b = (float) (Math.round(a * 10)) / 10;

                    video_size = b + " kb";
                }
                Bitmap video_pic=getVideoThumbnail(video_url);
                long updateTime = cursor
                        .getLong(cursor
                                .getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED));
                updateTime = updateTime * 1000;
                // 方法2：获取最新修改时间
                File file = new File(video_url);
                long time = file.lastModified();
                // 格式化时间，获取年，月，日
                String[] times = getTimeInfo(updateTime);
                String date = times[0] + times[1] + times[2] + times[3]
                        + times[4];

                videoInfoBean.setVideo_name(video_name);
                videoInfoBean.setVideo_size(video_size);
                videoInfoBean.setVideo_time(date);
                videoInfoBean.setVideo_url(video_url);
                videoInfoBean.setVideo_pic(video_pic);

                videoInfoBeanList.add(videoInfoBean);
                Log.i("info--->", "uri-->" + video_url + "   name---->" + video_name
                        + "--title----->" + ":size:" + video_size
                        + "YEAR--->" + date);
            }

            cursor.moveToNext();
        }
        cursor.close();
        return videoInfoBeanList;
    }

    //扫描音频文件
    public static List<MusicInfoBean> scanAllAudioFiles(Context context) {
        // 生成动态数组，并且转载数据

        List<MusicInfoBean> list = new ArrayList<MusicInfoBean>();
        // 查询媒体数据库
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        // 遍历媒体数据库
        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {

                // 歌曲编号
                int id = cursor.getInt(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                // 歌曲标题
                String tilte = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));

                String name = cursor
                        .getString(cursor
                                .getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                // 歌曲的专辑名：MediaStore.Audio.Media.ALBUM
                String album = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                // 歌曲的歌手名： MediaStore.Audio.Media.ARTIST
                String artist = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                // 歌曲文件的路径 ：MediaStore.Audio.Media.DATA
                String url = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                // 歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
                int duration = (cursor
                        .getInt(cursor
                                .getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)))/1000;
                // 歌曲文件的大小 ：MediaStore.Audio.Media.SIZE
                long size = cursor.getLong(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                String YEAR = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR));
                float a;
                float b;
                if (size > 1024 * 800) {// 大于800K
                    MusicInfoBean musicBean = new MusicInfoBean();

                    String music_size;
                    if (size / (1014 * 1024) > 0) {

                        a = (float) (size / 1048576.0);
                        b = (float) (Math.round(a * 10)) / 10;

                        music_size = b + " MB";
                    } else {
                        a = (float) (size / 1024.0);
                        b = (float) (Math.round(a * 10)) / 10;

                        music_size = b + " kb";
                    }

                    long updateTime = cursor
                            .getLong(cursor
                                    .getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED));
                    updateTime = updateTime * 1000;
                    // 方法2：获取最新修改时间
                    File file = new File(url);
                    long time = file.lastModified();
                    // 格式化时间，获取年，月，日
                    String[] times = getTimeInfo(updateTime);
                    String date = times[0] + times[1] + times[2] + times[3]
                            + times[4];

                    musicBean.setMusic_name(name);
                    musicBean.setMusic_size(music_size);
                    musicBean.setMusic_time(date);
                    musicBean.setMusic_url(url);
                    musicBean.setDuration(duration+"");
                    list.add(musicBean);
                    Log.i("info--->", "uri-->" + url + "   name---->" + name
                            + "--title----->" + tilte + ":size:" + music_size
                            + "YEAR--->" + date+"duration--->"+duration);
                }
                cursor.moveToNext();
            }
        }
        return list;
    }

    public static String[] getTimeInfo(long time) {
        Calendar cal = Calendar.getInstance();
        String[] times = new String[5];
        Date date = new Date(time);
        cal.setTime(date);
        times[0] = cal.get(Calendar.YEAR) + "-";
        times[1] = (cal.get(Calendar.MONTH) + 1) + "-";// calendar月份从0-11
        times[2] = cal.get(Calendar.DAY_OF_MONTH) + " ";
        times[3] = cal.get(Calendar.HOUR_OF_DAY) + ":";
        times[4] = cal.get(Calendar.MINUTE) + "";

        return times;
    }
}
