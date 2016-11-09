package com.example.kk.arttraining.utils;

import android.util.Log;

import com.example.kk.arttraining.bean.AdvertisBean;
import com.example.kk.arttraining.bean.AttachmentBean;
import com.example.kk.arttraining.bean.ThemesBean;
import com.example.kk.arttraining.bean.parsebean.ParseStatusesBean;
import com.example.kk.arttraining.bean.parsebean.StatusesBean;
import com.nostra13.universalimageloader.utils.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：wschenyongyin on 2016/9/21 10:13
 * 说明:json解析工具类
 */
public class JsonTools {
    //小组动态列表
    private static ParseStatusesBean statusesBean;
    //话题列表
    private static ThemesBean themesBean;
    //广告
    private static AdvertisBean advertisBean;


    //解析动态
    public static List<Map<String, Object>> ParseStatuses(String JsonString) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        try {
//            JSONObject jsonObject = new JSONObject(JsonString);

            String type;
            JSONArray jsonArray = new JSONArray(JsonString);
            Log.i("jsonArray.length()", jsonArray.length() + "---123");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                if(jsonObject.equals(StatusesBean.class))
//                    if( jsonObject instanceof StatusesBean);
                Log.i("data", jsonArray.length() + "---123");
                type = jsonObject.getString("stus_type");
                Log.i("type", type + "---123");
                Map<String, Object> map = new HashMap<String, Object>();
                switch (type) {
                    case "status":

                        try {
                            statusesBean = new ParseStatusesBean();
                            statusesBean.setStus_id(jsonObject.getInt("stus_id"));
                            statusesBean.setStus_type(type);
                            statusesBean.setOwner(jsonObject.getInt("owner"));
                            statusesBean.setOwner_name(jsonObject.getString("owner_name"));
                            statusesBean.setOwner_type(jsonObject.getString("owner_type"));
                            statusesBean.setCreate_time(jsonObject.getString("create_time"));
                            statusesBean.setCity(jsonObject.getString("city"));
                            statusesBean.setTag(jsonObject.getString("tag"));
                            statusesBean.setIdentity(jsonObject.getString("identity"));
                            statusesBean.setContent(jsonObject.getString("content"));
                            statusesBean.setBrowse_num(jsonObject.getInt("browse_num"));
                            statusesBean.setLike_num(jsonObject.getInt("like_num"));
                            statusesBean.setIs_like(jsonObject.getString("is_like"));
                            statusesBean.setComment_tec(jsonObject.getString("comment_tec"));
                            statusesBean.setComment_tec_uni(jsonObject.getString("comment_tec_uni"));
                            statusesBean.setTitle(jsonObject.getString("title"));
                            statusesBean.setIs_comment(jsonObject.getString("is_comment"));

//                        statusesBean.setAtt(jsonObject.getJSONArray(""));
                            JSONArray attArray = jsonObject.getJSONArray("att");
                            List<AttachmentBean> attachmentBeanList = new ArrayList<AttachmentBean>();
                            for (int j = 0; j < attArray.length(); j++) {
                                AttachmentBean attBean = new AttachmentBean();
                                JSONObject attObject = attArray.getJSONObject(j);
                                attBean.setAtt_id(attObject.getInt("att_id"));
                                attBean.setDuration(attObject.getInt("duration"));
                                attBean.setAtt_type(attObject.getString("att_type"));
                                attBean.setThumbnail(attObject.getString("thumbnail"));
                                attBean.setStore_path(attObject.getString("store_path"));
                                attachmentBeanList.add(attBean);
                            }
                            statusesBean.setAtt(attachmentBeanList);
                            map.put("type", "status");
                            map.put("data", statusesBean);
                            mapList.add(map);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    case "ad":
                        try {
                            advertisBean = new AdvertisBean();
                            advertisBean.setAd_id(jsonObject.getInt("ad_id"));
                            advertisBean.setAd_pic(jsonObject.getString("ad_pic"));
                            map.put("type", "ad");
                            map.put("data", advertisBean);
                            mapList.add(map);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        break;
                    case "theme":
                        try {
                            JSONArray themeArray = jsonObject.getJSONArray("themes");
                            List<ThemesBean> themeList = new ArrayList<ThemesBean>();
                            for (int k = 0; k < themeArray.length(); k++) {
                                JSONObject themeObject = themeArray.getJSONObject(k);
                                themesBean = new ThemesBean();
                                themesBean.setPic(themeObject.getString("pic"));
                                themesBean.setTitle(themeObject.getString("title"));
                                themesBean.setThm_id(themeObject.getInt("thm_id"));
                                themesBean.setNum(themeObject.getInt("num"));
                                themeList.add(themesBean);


                            }
                            map.put("type", "theme");
                            map.put("data", themeList);
                            mapList.add(map);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
            return null;

        }
        return mapList;
    }
}
