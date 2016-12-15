package com.example.kk.arttraining.utils;

import android.util.Log;

import com.example.kk.arttraining.bean.AdvertisBean;
import com.example.kk.arttraining.bean.AttachmentBean;
import com.example.kk.arttraining.bean.GroupBean;
import com.example.kk.arttraining.bean.OrgBean;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.bean.ThemesBean;
import com.example.kk.arttraining.bean.parsebean.ParseStatusesBean;
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
    private static ThemesBean themesBean;
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
        UIUtil.showLog("statusesBean-list", mapList.size() + "");
        return mapList;
    }


    public static List<Map<String, Object>> SearchStatuses(String JsonString) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        try {
            String type;
            JSONArray jsonArray = new JSONArray(JsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                type = jsonObject.getString("search_type");
                Map<String, Object> map = new HashMap<String, Object>();

                switch (type) {
                    case "org":
                        try {
                            JSONArray orgArray = jsonObject.getJSONArray("org");
                            List<OrgBean> orgBeanList = new ArrayList<OrgBean>();

                            for (int j = 0; j < orgArray.length(); j++) {
                                JSONObject orgObject = orgArray.getJSONObject(j);
                                OrgBean orgBean = new OrgBean();
                                orgBean.setOrg_id(orgObject.getInt("org_id"));
                                orgBean.setName(orgObject.getString("name"));
                                orgBean.setPic(orgObject.getString("pic"));
                                orgBean.setComment(orgObject.getInt("comment"));
                                orgBean.setFans_num(orgObject.getInt("fans_num"));
                                orgBean.setAuth(orgObject.getString("auth"));
                                orgBean.setSign_up(orgObject.getInt("sign_up"));
                                orgBeanList.add(orgBean);
                            }
                            map.put("search_type", "org");
                            map.put("data", orgBeanList);
                            mapList.add(map);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return null;
                        }
                        break;
                    case "tec":
                        try {
                            JSONArray tecArray = jsonObject.getJSONArray("tec");
                            List<TecInfoBean> tecBeanList = new ArrayList<TecInfoBean>();

                            for (int j = 0; j < tecArray.length(); j++) {
                                JSONObject tecObject = tecArray.getJSONObject(j);
                                TecInfoBean tecInfoBean = new TecInfoBean();
                                tecInfoBean.setTec_id(tecObject.getInt("tec_id"));
                                tecInfoBean.setName(tecObject.getString("name"));
                                tecInfoBean.setPic(tecObject.getString("pic"));
                                tecInfoBean.setComment(tecObject.getInt("comment"));
                                tecInfoBean.setFans_num(tecObject.getInt("fans_num"));
                                tecInfoBean.setAuth(tecObject.getString("auth"));
                                tecBeanList.add(tecInfoBean);
                            }
                            map.put("search_type", "tec");
                            map.put("data", tecBeanList);
                            mapList.add(map);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return null;
                        }
                        break;
                    case "statuses":
                        List<Map<String, Object>> statusesMapList = new ArrayList<Map<String, Object>>();
                        JSONArray statusesArray = jsonObject.getJSONArray("statuses");
                        for (int k = 0; k < statusesArray.length(); k++) {
                            JSONObject statusesObject = statusesArray.getJSONObject(k);
                            type = statusesObject.getString("stus_type");
                            Map<String, Object> statusesMap = new HashMap<String, Object>();
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
                                        statusesBean.setLike_num(jsonObject.getInt("like_num"));
                                        statusesBean.setIs_like(jsonObject.getString("is_like"));
//                            statusesBean.setComment_tec(jsonObject.getString("comment_tec"));
//                            statusesBean.setComment_tec_uni(jsonObject.getString("comment_tec_uni"));
//                            statusesBean.setTitle(jsonObject.getString("title"));
                                        statusesBean.setIs_comment(jsonObject.getString("is_comment"));

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
                                        statusesMap.put("type", "work");
                                        statusesMap.put("data", statusesBean);
                                        statusesMapList.add(map);
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
                                        statusesBean.setIs_like(jsonObject.getString("is_like"));
//                            statusesBean.setComment_tec(jsonObject.getString("comment_tec"));
//                            statusesBean.setComment_tec_uni(jsonObject.getString("comment_tec_uni"));
//                            statusesBean.setTitle(jsonObject.getString("title"));
                                        statusesBean.setIs_comment(jsonObject.getString("is_comment"));

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
                                        statusesMap.put("type", "status");
                                        statusesMap.put("data", statusesBean);
                                        statusesMapList.add(map);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case "ad":
                                    try {
                                        advertisBean = new AdvertisBean();
                                        advertisBean.setAd_id(jsonObject.getInt("ad_id"));
                                        advertisBean.setAd_pic(jsonObject.getString("ad_pic"));
                                        statusesMap.put("type", "ad");
                                        statusesMap.put("data", advertisBean);
                                        statusesMapList.add(map);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case "theme":
                                    try {
                                        JSONArray themeArray = jsonObject.getJSONArray("themes");
                                        List<ThemesBean> themeList = new ArrayList<ThemesBean>();
                                        for (int n = 0; n < themeArray.length(); n++) {
                                            JSONObject themeObject = themeArray.getJSONObject(n);
                                            themesBean = new ThemesBean();
                                            themesBean.setPic(themeObject.getString("pic"));
                                            themesBean.setTitle(themeObject.getString("title"));
                                            themesBean.setThm_id(themeObject.getInt("thm_id"));
                                            themesBean.setNum(themeObject.getInt("num"));
                                            themeList.add(themesBean);
                                        }
                                        statusesMap.put("type", "theme");
                                        statusesMap.put("data", themeList);
                                        statusesMapList.add(map);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                            }
                        }
                        map.put("search_type", "status");
                        map.put("data", statusesMapList);
                        mapList.add(map);
                        break;
                    case "groups":
                        try {
                            JSONArray groupsArray = jsonObject.getJSONArray("groups");
                            List<GroupBean> groupsBeanList = new ArrayList<GroupBean>();

                            for (int j = 0; j < groupsArray.length(); j++) {
                                JSONObject groupsObject = groupsArray.getJSONObject(j);
                                GroupBean groupBean = new GroupBean();
                                groupBean.setGroup_id(groupsObject.getInt("group_id"));
                                groupBean.setName(groupsObject.getString("name"));
                                groupBean.setIntroduce(groupsObject.getString("introduce"));
                                groupBean.setGrade(groupsObject.getInt("grade"));
                                groupBean.setGrade(groupsObject.getInt("users_num"));
                                groupBean.setPic(groupsObject.getString("pic"));
                                groupBean.setOrder_code(groupsObject.getString("order_code"));
                                groupsBeanList.add(groupBean);
                            }
                            map.put("search_type", "groups");
                            map.put("data", groupsBeanList);
                            mapList.add(map);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return null;
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
}
