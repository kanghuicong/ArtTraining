package com.example.kk.arttraining.playvideo.activity;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：wschenyongyin on 2016/9/21 14:06
 * 说明:list javabean
 */
public class VideoListData implements Serializable {

    private List<VideoItemData> list;

    public List<VideoItemData> getList() {
        return list;
    }

    public void setList(List<VideoItemData> list) {
        this.list = list;
    }
}
