package com.example.kk.arttraining.utils;

import android.util.Log;

import com.example.kk.arttraining.bean.AdvertisBean;
import com.example.kk.arttraining.bean.AttachmentBean;
import com.example.kk.arttraining.bean.GroupBean;
import com.example.kk.arttraining.bean.OrgBean;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.bean.InfoBean;
import com.example.kk.arttraining.bean.parsebean.ParseStatusesBean;
import com.example.kk.arttraining.receiver.bean.JpushBean;
import com.example.kk.arttraining.receiver.bean.JpushMessageBean;
import com.example.kk.arttraining.ui.homePage.bean.WorkComment;

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
//    private static ParseStatusesBean statusesBean;
    //话题列表
    private static InfoBean infoBean;
    //广告
    private static AdvertisBean advertisBean;


    //解析动态
    public static List<Map<String, Object>> ParseStatuses(String JsonString) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        UIUtil.showLog("statusesBean-JsonString", JsonString + "----");
        try {
//            JSONObject jsonObject = new JSONObject(JsonString);

            String type;
            JSONArray jsonArray = new JSONArray(JsonString);
            Log.i("jsonArray.length()", jsonArray.length() + "---123");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                if(jsonObject.equals(StatusesBean.class))
//                    if( jsonObject instanceof StatusesBean);
                type = jsonObject.getString("stus_type");
                Map<String, Object> map = new HashMap<String, Object>();
                switch (type) {
                    case "work":
                        try {
                            ParseStatusesBean statusesBean = new ParseStatusesBean();
                            statusesBean.setStus_id(jsonObject.getInt("stus_id"));
                            statusesBean.setStus_type(type);
                            statusesBean.setOwner(jsonObject.getInt("owner"));
                            statusesBean.setOwner_name(jsonObject.getString("owner_name"));
                            statusesBean.setOwner_type(jsonObject.getString("owner_type"));
                            statusesBean.setOwner_head_pic(jsonObject.getString("owner_head_pic"));
                            statusesBean.setCreate_time(jsonObject.getString("create_time"));
                            statusesBean.setCity(jsonObject.getString("city"));
                            statusesBean.setTag(jsonObject.getString("tag"));
                            statusesBean.setIdentity(jsonObject.getString("identity"));
                            statusesBean.setTitle(jsonObject.getString("title"));
                            statusesBean.setContent(jsonObject.getString("content"));
                            statusesBean.setBrowse_num(jsonObject.getInt("browse_num"));
                            statusesBean.setComment_num(jsonObject.getInt("comment_num"));
                            statusesBean.setLike_num(jsonObject.getInt("like_num"));
                            statusesBean.setIs_like(jsonObject.getString("is_like"));
                            statusesBean.setIs_comment(jsonObject.getString("is_comment"));
                            statusesBean.setArt_type(jsonObject.getString("art_type"));
                            statusesBean.setRemarks(jsonObject.getString("remarks"));

                            JSONArray attArray = jsonObject.getJSONArray("att");
                            List<AttachmentBean> attachmentBeanList = new ArrayList<AttachmentBean>();
                            for (int j = 0; j < attArray.length(); j++) {
                                AttachmentBean attBean = new AttachmentBean();
                                JSONObject attObject = attArray.getJSONObject(j);
                                attBean.setAtt_id(attObject.getInt("att_id"));
                                attBean.setDuration(attObject.getString("duration"));
                                attBean.setAtt_type(attObject.getString("att_type"));
                                attBean.setThumbnail(attObject.getString("thumbnail"));
                                attBean.setStore_path(attObject.getString("store_path"));
                                attachmentBeanList.add(attBean);
                            }
                            statusesBean.setAtt(attachmentBeanList);

                            JSONArray commentArray = jsonObject.getJSONArray("tec_comment_list");
                            List<WorkComment> workCommentList = new ArrayList<WorkComment>();
                            for (int n = 0; n < commentArray.length(); n++) {
                                WorkComment workBean = new WorkComment();
                                JSONObject workObject = commentArray.getJSONObject(n);
                                workBean.setTec_id(workObject.getInt("tec_id"));
                                workBean.setName(workObject.getString("name"));
                                workBean.setTitle(workObject.getString("title"));
                                workBean.setIdentity(workObject.getString("identity"));
                                workBean.setCity(workObject.getString("city"));
                                workBean.setSchool(workObject.getString("school"));
                                if (!workObject.isNull("tec_pic"))
                                    workBean.setTec_pic(workObject.getString("tec_pic"));

                                workBean.setComm_time(workObject.getString("comm_time"));
                                workBean.setType(workObject.getString("type"));
                                workBean.setComm_type(workObject.getString("comm_type"));
                                workBean.setContent(workObject.getString("content"));
                                workBean.setDuration(workObject.getString("duration"));
                                workBean.setThumbnail(workObject.getString("thumbnail"));
                                workBean.setComm_id(workObject.getInt("comm_id"));
                                workBean.setListen_num(workObject.getInt("listen_num"));
                                workCommentList.add(workBean);
                            }
                            statusesBean.setTec_comment_list(workCommentList);

                            map.put("type", "work");
                            map.put("data", statusesBean);
                            mapList.add(map);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;

                    case "status":
                        try {
                            ParseStatusesBean statusesBean = new ParseStatusesBean();
                            statusesBean.setStus_id(jsonObject.getInt("stus_id"));
                            statusesBean.setStus_type(type);
                            statusesBean.setOwner(jsonObject.getInt("owner"));
                            statusesBean.setOwner_name(jsonObject.getString("owner_name"));
                            statusesBean.setOwner_type(jsonObject.getString("owner_type"));
                            statusesBean.setOwner_head_pic(jsonObject.getString("owner_head_pic"));
                            statusesBean.setCreate_time(jsonObject.getString("create_time"));
                            statusesBean.setCity(jsonObject.getString("city"));
                            statusesBean.setTag(jsonObject.getString("tag"));
                            statusesBean.setIdentity(jsonObject.getString("identity"));
                            statusesBean.setTitle(jsonObject.getString("title"));
                            statusesBean.setContent(jsonObject.getString("content"));
                            statusesBean.setBrowse_num(jsonObject.getInt("browse_num"));
                            statusesBean.setLike_num(jsonObject.getInt("like_num"));
                            statusesBean.setComment_num(jsonObject.getInt("comment_num"));
                            statusesBean.setIs_like(jsonObject.getString("is_like"));
//                            statusesBean.setComment_tec(jsonObject.getString("comment_tec"));
//                            statusesBean.setComment_tec_uni(jsonObject.getString("comment_tec_uni"));
//                            statusesBean.setTitle(jsonObject.getString("title"));
                            statusesBean.setIs_comment(jsonObject.getString("is_comment"));
                            statusesBean.setArt_type(jsonObject.getString("art_type"));

//                        statusesBean.setAtt(jsonObject.getJSONArray(""));
                            JSONArray attArray = jsonObject.getJSONArray("att");
                            List<AttachmentBean> attachmentBeanList = new ArrayList<AttachmentBean>();
                            for (int j = 0; j < attArray.length(); j++) {
                                AttachmentBean attBean = new AttachmentBean();
                                JSONObject attObject = attArray.getJSONObject(j);
                                attBean.setAtt_id(attObject.getInt("att_id"));
                                attBean.setDuration(attObject.getString("duration"));
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
                        Log.i("statusesBean-work", "status");
                        break;
                    case "ad":
                        try {
                            advertisBean = new AdvertisBean();
                            advertisBean.setAd_id(jsonObject.getInt("ad_id"));
                            advertisBean.setAd_pic(jsonObject.getString("ad_pic"));
                            advertisBean.setAd_url(jsonObject.getString("ad_url"));
                            map.put("type", "ad");
                            map.put("data", advertisBean);
                            mapList.add(map);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "info":
                        try {
                            JSONArray themeArray = jsonObject.getJSONArray("info_list");
                            List<InfoBean> infoList = new ArrayList<InfoBean>();
                            for (int k = 0; k < themeArray.length(); k++) {
                                JSONObject themeObject = themeArray.getJSONObject(k);
                                infoBean = new InfoBean();
                                infoBean.setPic(themeObject.getString("pic"));
                                infoBean.setTitle(themeObject.getString("title"));
                                infoBean.setInfo_id(themeObject.getInt("info_id"));
                                infoBean.setInfo_type(themeObject.getString("info_type"));
                                infoBean.setBrowse_num(themeObject.getInt("browse_num"));
                                infoBean.setUrl(themeObject.getString("url"));
                                infoBean.setCreate_time(themeObject.getString("create_time"));
                                infoList.add(infoBean);
                            }
                            map.put("type", "info");
                            map.put("data", infoList);
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
        UIUtil.showLog("statusesBean-list", mapList.size() + "");
        return mapList;
    }

    public static List<Map<String, Object>> ParseMyWork(String JsonString) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        UIUtil.showLog("statusesBean-JsonString", JsonString + "----");
        try {

            String type;
            JSONArray jsonArray = new JSONArray(JsonString);
            Log.i("jsonArray.length()", jsonArray.length() + "---123");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                if(jsonObject.equals(StatusesBean.class))
//                    if( jsonObject instanceof StatusesBean);
                type = jsonObject.getString("stus_type");
                Map<String, Object> map = new HashMap<String, Object>();
                switch (type) {
                    case "work":
                        try {
                            ParseStatusesBean statusesBean = new ParseStatusesBean();
                            statusesBean.setStus_id(jsonObject.getInt("stus_id"));
                            statusesBean.setStus_type(type);
                            statusesBean.setOwner(jsonObject.getInt("owner"));
                            statusesBean.setOwner_name(jsonObject.getString("owner_name"));
                            statusesBean.setOwner_type(jsonObject.getString("owner_type"));
                            statusesBean.setOwner_head_pic(jsonObject.getString("owner_head_pic"));
                            statusesBean.setCreate_time(jsonObject.getString("create_time"));
                            statusesBean.setCity(jsonObject.getString("city"));
                            statusesBean.setTag(jsonObject.getString("tag"));
                            statusesBean.setIdentity(jsonObject.getString("identity"));
                            statusesBean.setTitle(jsonObject.getString("title"));
                            statusesBean.setContent(jsonObject.getString("content"));
                            statusesBean.setBrowse_num(jsonObject.getInt("browse_num"));
                            statusesBean.setComment_num(jsonObject.getInt("comment_num"));
                            statusesBean.setLike_num(jsonObject.getInt("like_num"));
                            statusesBean.setIs_like(jsonObject.getString("is_like"));
                            statusesBean.setIs_comment(jsonObject.getString("is_comment"));
                            statusesBean.setArt_type(jsonObject.getString("art_type"));
                            statusesBean.setRemarks(jsonObject.getString("remarks"));

                            JSONArray attArray = jsonObject.getJSONArray("att");
                            List<AttachmentBean> attachmentBeanList = new ArrayList<AttachmentBean>();
                            for (int j = 0; j < attArray.length(); j++) {
                                AttachmentBean attBean = new AttachmentBean();
                                JSONObject attObject = attArray.getJSONObject(j);
                                attBean.setAtt_id(attObject.getInt("att_id"));
                                attBean.setDuration(attObject.getString("duration"));
                                attBean.setAtt_type(attObject.getString("att_type"));
                                attBean.setThumbnail(attObject.getString("thumbnail"));
                                attBean.setStore_path(attObject.getString("store_path"));
                                attachmentBeanList.add(attBean);
                            }
                            statusesBean.setAtt(attachmentBeanList);

                            map.put("type", "work");
                            map.put("data", statusesBean);
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
        UIUtil.showLog("statusesBean-list", mapList.size() + "");
        return mapList;
    }

    //解析极光推送Notification
    public static JpushBean ParseJpushExtras(String key, String extras) {
        JpushBean jpushBean = new JpushBean();
        try {
            JSONObject object = new JSONObject(extras);
            String jsonObject = object.getString(key);
            JSONObject jsonObject1 = new JSONObject(jsonObject);
            jpushBean.setType(jsonObject1.getString("type"));
            jpushBean.setValue(jsonObject1.getString("value"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return jpushBean;
    }


    //解析极光推送message
    public static JpushMessageBean ParseJpushMessage(String key, String extras) {
        JpushMessageBean jpushMessageBean = new JpushMessageBean();

        try {
            JSONObject object = new JSONObject(extras);
            String jsonObject = object.getString(key);
            JSONObject jsonObject1 = new JSONObject(jsonObject);
            UIUtil.showLog("jsonObject1------->", jsonObject1.toString() + "----->");
            UIUtil.showLog("type", jsonObject1.getString("type") + "");
            jpushMessageBean.setType(jsonObject1.getString("type"));

            String value = jsonObject1.getString("value");
            UIUtil.showLog("value---->", value + "");
            JSONObject jsonObject2 = new JSONObject(value);
            jpushMessageBean.setBbs_num(jsonObject2.getInt("bbs_num"));
            jpushMessageBean.setFans_num(jsonObject2.getInt("fans_num"));
            jpushMessageBean.setFollow_num(jsonObject2.getInt("follow_num"));
            jpushMessageBean.setWorks_num(jsonObject2.getInt("works_num"));
            jpushMessageBean.setMsg_num(jsonObject2.getInt("msg_num"));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return jpushMessageBean;
    }
}
