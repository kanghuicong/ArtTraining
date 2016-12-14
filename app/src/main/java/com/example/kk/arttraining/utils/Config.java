package com.example.kk.arttraining.utils;

import android.os.Environment;

import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.bean.UserLoginBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/6 10:24
 * 说明:配置类
 */
public class Config {


    public final static String testapi = "HTML/dynamictest.html";
    /* 接口相关-start */
//    public final static String SERVER_IP = "192.168.188.246";

    public final static String SERVER_IP = "192.168.188.152";
    public final static String SERVER_MH = ":";
    public final static String SYSTEM_PORT = "8080";
    public final static String SYSTEM_NAME = "/api/";
    public final static String BASE_URL = "http://" + SERVER_IP + SERVER_MH + SYSTEM_PORT + SYSTEM_NAME;
//            public final static String BASE_URL="http://118.178.136.110/api/";
//API测试服务地址：
//    public final static String BASE_URL = " http://118.178.136.110:8088/api/";
//    public final static String BASE_URL = "http://" + SERVER_IP + SERVER_MH + SYSTEM_PORT + SYSTEM_NAME;
//            public final static String BASE_URL="http://118.178.136.110/api/";
    //API测试服务地址：
//    public final static String BASE_URL = " http://118.178.136.110:8088/api/";

    //登陆接口
    public final static String URL_LOGIN = "login/login";//登陆
    public final static String URL_LOGIN_EXIT = "login/exit";//退出登录
    //用户注册接口
    public final static String URL_REGISTER_CREATE = "register/create";//用户注册
    public final static String URL_REGISTER_ISREG = "register/is_reg";//判断手机号码是否注册过
    public final static String URL_SMS_SEND = "sms/verification_code/send";//获取手机验证码
    public final static String URL_SMS_VERIFY = "sms/verification_code/verify";//检验验证码
    public final static String URL_INVITE_CODE_VERIFY = "invite_code/verify";//检验推荐码

    //用户找回密码
    public final static String URL_FORGOT_PWD = "forgot_pwd/create";//

    //用户相关接口
    public final static String URL_USERS_GET_INFO = "users/show";//获取用户信息
    public final static String URL_USERS_SET_INFO = "users/set_info";//设置个人资料
    public final static String URL_USERS_UPDATE_HEAD = "users/update_head";//根据用户ID修改用户头像
    public final static String URL_USERS_UPDATE_PWD = "users/update_pwd";//修改用户登录密码
    public final static String URL_USERS_UPDATE_MOIBLE = "users/change_mobile";//更换手机号
    public final static String URL_USERS_COUNT_NUM = "users/num";//更换手机号
    //测评接口
    public final static String URL_ASSESSMENTS_LIST = "assessments/list";//根据用户id获取测评列表
    public final static String URL_ASSESSMENTS_SHOW = "assessments/show";//根据id获取测评详情
    //小组接口
    public final static String URL_GROUP_LIST = "group/list";//获取小组列表
    public final static String URL_GROUP_LIST_MY = "group/list_my";//获取我的小组列表
    public final static String URL_GROUP_SHOW = "group/show";//获取小组详情
    public final static String URL_GROUP_USERS = "group/users";//获取小组成员列表
    public final static String URL_GROUP_CREATE = "group/create";//创建小组
    public final static String URL_GROUP_JOIN = "group/join";//加入小组
    public final static String URL_GROUP_EXIT = "group/exit";//退出小组
    //动态接口
    public final static String URL_STATUSES_PUBLIC_TIMELINE_BBS = "statuses/public_timeline/bbs";//获取首页最新动态、帖子列表
    public final static String URL_STATUSES_USER_TIMELINE_BBS = "statuses/user_timeline/bbs";//获取用户发布的帖子
    public final static String URL_STATUSES_SHOW_BBS = "statuses/show/bbs";//获取帖子详情
    public final static String URL_STATUSES_REPORT_BBS = "statuses/report/bbs";//转发帖子
    public final static String URL_STATUSES_PUBLISH_BBS = "statuses/publish/bbs";//发布帖子
    public final static String URL_STATUSES_DELETE = "statuses/delete/bbs";//删除动态
    public final static String URL_STATUSES_TIMELINE_GROUP = "statuses/public_timeline/g_stus";//获取小组最新动态（精品动态）列表
    public final static String URL_STATUSES_USER_TIMELINE_GROUP = "statuses/user_timeline/g_stus";//获取小组用户发布的动态
    public final static String URL_STATUSES_SHOW_GROUP = "statuses/show/g_stus";//获取小组动态详情
    public final static String URL_STATUSES_REPORT_GROUP = "statuses/report/g_stus";//转发小组动态
    public final static String URL_STATUSES_PUBLISH_GROUP = "statuses/publish/g_stus";//发布小组动态
    public final static String URL_STATUSES_USER_TIMELINE_WORK = "statuses/user_timeline/work";//获取用户的作品列表
    public final static String URL_STATUSES_SHOW_WORK = "statuses/show/work";//获取作品详情
    public final static String URL_STATUSES_SHOW_MY_BBS = "statuses/show_my/bbs";//查看我评论过的帖子


    //评论接口
    public final static String URL_COMMENTS_LIST_BBS = "comments/list/bbs";//获取动态的评论列表
    public final static String URL_COMMENTS_CREATE_BBS = "comments/create/bbs";//发表一条评论
    public final static String URL_COMMENTS_DELETE_BBS = "comments/delete/bbs";//删除一条评论
    public final static String URL_COMMENTS_REPLY_BBS = "comments/reply/bbs";//回复一条评论

    public final static String URL_COMMENTS_LIST_GROUP = "comments/list/g_stus";//发表一条评论
    public final static String URL_COMMENTS_CREATE_GROUP = "comments/create/g_stus";//发表一条评论
    public final static String URL_COMMENTS_DELETE_GROUP = "comments/delete/g_stus";//删除一条评论
    public final static String URL_COMMENTS_REPLY_GROUP = "comments/reply/g_stus";//回复一条评论

    public final static String URL_COMMENTS_LIST_WORK = "comments/list/work";//获取动态的评论列表
    public final static String URL_COMMENTS_CREATE_WORK = "comments/create/work";//发表一条评论
    public final static String URL_COMMENTS_DELETE_WORK = "comments/delete/work";//删除一条评论
    public final static String URL_COMMENTS_REPLY_WORK = "comments/reply/work";//回复一条评论
    //名师点评接口
    public final static String URL_TECH_COMMENTS_LIST = "tech_comments/list";//获取动态的名师点评列表
    public final static String URL_TECH_COMMENTS_CREATE = "tech_comments/list";//发表点评
    public final static String URL_TECH_COMMENTS_REPLY = "tech_comments/list";//发表追问
    //搜索接口
    public final static String URL_SEARCH_PUBLIC = "search/public";//首页搜索
    public final static String URL_SEARCH_ORG = "search/org";//机构搜索
    public final static String URL_SEARCH_TEC = "search/tec";//名师搜索
    public final static String URL_SEARCH_STATUSES = "search/statuses";//动态搜索
    public final static String URL_SEARCH_GROUP = "search/group";//小组搜索

    //
    public final static String URL_SEARCH_CITY = "common/get_city";//获取城市列表

    public final static String URL_SEARCH_CITY_BYPROVINCE = "common/get_city/by_province";//按省来查找 我的页面
    public final static String URL_COMMON_PROVINCE = "common/get_province";//获取省份列表
    //提醒接口
    public final static String URL_REMIND_LIST = "remind/list";//获取提醒列表
    public final static String URL_REMIND_SHOW = "remind/show";//提醒详情
    public final static String URL_REMIND_UNREAD_COUNT = "remind/unread_cont";//未读提醒数量
    //收藏接口
    public final static String URL_FAVORITES_LIST = "favorites/list";//获取用户收藏列表
    public final static String URL_FAVORITES_SHOW = "favorites/show";//获取收藏详情
    public final static String URL_FAVORITES_CREATE = "favorites/create";//添加收藏
    public final static String URL_FAVORITES_DELETE = "favorites/delete";//删除收藏
    //点赞接口
    public final static String URL_LIKE_LIST_BBS = "like/list/bbs";//获取帖子点赞列表
    public final static String URL_LIKE_LIST_PIC_BBS = "like/list/pic/bbs";//获取帖子点赞用户头像列表
    public final static String URL_LIKE_CREATE_BBS = "like/create/bbs";//添加帖子点赞
    public final static String URL_LIKE_LIST_GROUP = "like/list/g_stus";//获取小组点赞列表
    public final static String URL_LIKE_LIST_PIC_GROUP = "like/list/pic/g_stus";//获取小组点赞用户头像列表
    public final static String URL_LIKE_CREATE_GROUP = "like/create/g_stus";//添加小组点赞
    public final static String URL_LIKE_LIST_WORK = "like/list/work";//获取作品点赞列表
    public final static String URL_LIKE_LIST_PIC_WORK = "like/list/pic/work";//获取作品点赞用户头像列表
    public final static String URL_LIKE_CREATE_WORK = "like/create/work";//添加作品点赞
    public final static String URL_LIKE_DELETE = "like/delete";//删除点赞
    //支付接口
    public final static String URL_PAY_REWORK = "pay/mobile/do_pay";//获取支付信息
    //机构
    public final static String URL_ORG_LIST = "org/list";//获取机构列表
    public final static String URL_ORG_SHOW = "org/show";//获取机构详情
    //艺术家/名师接口
    public final static String URL_TECHER_LIST = "techer/list";//获取名师列表
    public final static String URL_TECHER_LIST_INDEX = "techer/list/index";//首页测评权威
    public final static String URL_TECHER_SHOW = "techer/show";//获取名师详情

    //活动
    public final static String URL_ACTIVITYIES_LIST = "activities/list";//获取活动列表
    public final static String URL_ACTIVITYIES_SHOW = "activities/show";//获取活动详情
    //专题
    public final static String URL_TOPICS_LIST = "topics/list";//获取专题列表
    public final static String URL_TOPICS_SHOW = "topics/show";//获取专题详情
    //帮助
    public final static String URL_HELP_LIST = "help/list";//获取帮助列表
    public final static String URL_HELP_SHOW = "help/show";//获取帮助详情
    //建议反馈
    public final static String URL_RECOMMEND_BY_ME = "recommend/by_me";//获取我的反馈列表
    public final static String URL_RECOMMEND_SHOW = "recommend/show";//反馈详情
    public final static String URL_RECOMMEND_CREATE = "recommend/create";//发送反馈
    //艺培头条
    public final static String URL_INFORMATION_LIST = "information/list";//获取头条列表
    public final static String URL_INFORMATION_SHOW = "information/show";//获取头条详情
    //广告
    public final static String URL_ADVERTISING_LIST = "advertising/list";//获取广告列表
    public final static String URL_ADVERTISING_SHOW = "advertising/show";//获取广告详情
    //院校
    public final static String URL_INSTITUTIONS_CONDITIONS = "institutions/conditions";////获取院校左边列表
    public final static String URL_INSTITUTIONS_LIST = "institutions/list";//获取院校列表
    public final static String URL_INSTITUTIONS_SHOW = "institutions/show";//获取院校详情

    //订单
    public final static String URL_ORDERS_LIST = "orders/list_my";//获取订单列表
    public final static String URL_ORDERS_SHOW = "orders/show";//获取订单详情
    public final static String URL_ORDERS_CREATE = "orders/create/assessment";//下订单
    public final static String URL_ORDERS_UPDATE = "orders/update/assessment";//更新订单状态
    public final static String URL_ORDERS_CANCEL = "orders/cancel";//取消订单
    //轮播接口
    public final static String URL_BANNER_LIST = "banner/list";//获取轮播列表
    public final static String URL_BANNER_SHOW = "banner/show";//根据广告ID获取轮播详情信息
    //专业接口
    public final static String URL_MAJOR_LIST = "major/list";
    public final static String URL_MAJOR_LIST_LEVEL_ONE = "major/list/level_one";
    //关注
    public final static String URL_FOLLOW_CREATE = "follow/create";
    public final static String URL_FOLLOW_FANS_LIST = "follow/fans/list";
    public final static String URL_FOLLOW_FOLLOW_LIST = "follow/follow/list";

    //七牛云上传
    public final static String URL_UPLOAD_QINIU_GETTOKEN = "upload/get_token/qiniu";//从服务器获取token
    public final static String URL_UPLOAD_QINIU_PUTURL = "upload/createqiniu";//将上传的文件连接返回给服务器

    //获取优惠券
    public final static String URL_COUPONS_LIST = "coupons/list";
    public final static String URL_EXCHANGE_COUPONS = "invite_code/verify";//兑换优惠券
    /* 接口相关-start */
    public final static String URL_ALIPAY_ASYNC = BASE_URL + "";//支付宝支付服务器异步通知页面接口
    public final static String URL_WECHAT_PAY_ASYNC = BASE_URL + "";//微信支付服务器异步通知页面接口

    //获取身份列表
    public final static String URL_IDENTITY_LIST = "identity/list";//获取身份列表

    //检查更新
    public final static String URL_UPDATE_APP = "version/update";//获取身份列表


     /* 数据库相关-start */

    /* 数据库相关-end */


    /* 全局变量-start */
    public static final String BASE_LOCAL_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    //拍照保存的路径
    public static final String IMAGE_SAVE_PATH = BASE_LOCAL_PATH + "/image/";
    public static String ACCESS_TOKEN = null;

    public static String TEST_ACCESS_TOKEN = "2092d2b7a6845fcadde57ee079bcd714";
    public static String TEST_UID = "8";
    public static String User_Name = "hehe";
    public static String User_Id = "123";

    public static int UID = 0;
    //用户类型
    public static String USER_TYPE = "stu";
    public static String CITY = "";
    public static String USER_TITLE = "";
    //七牛云token
    public static String QINIUYUN_WORKS_TOKEN = null;
    public static String QINIUYUN_PIC_TOKEN = null;
    public static String QINIUYUN_BBS_TOKEN = null;
    public static String QINIUYUN_COURSE_TOKEN = null;
    public static String QINIUYUN_GOURP_TOKEN = null;
    public static String QINIUYUN_ADVERT_TOKEN = null;
    public static String QINIUYUN_USER_HEADER_TOKEN = null;
    //用于保存用户信息
    public static UserLoginBean userBean = null;
    public static int PermissionsState = 0;//权限状态
    public static String REQUEST_FAILURE = "请求网络失败";
    public static String test_video = "http://flv2.bn.netease.com/tvmrepo/2016/4/G/O/EBKQOA8GO/SD/EBKQOA8GO-mobile.mp4";
    public static String SCHOOL_PIC = "http://g.hiphotos.baidu.com/baike/w%3D268/sign=e4b93743f5d3572c66e29bdab2126352/f7246b600c33874405904fd6560fd9f9d62aa0c7.jpg";
    public final static String USER_HEADER_Url = "http://awb.img.xmtbang.com/img/uploadnew/201510/23/8bd0ba8fb90d4d0d99aefef22d8b4034.jpg";
    public final static String TEST_COURSE = "http://118.178.136.110/course/";
    /* 全局变量-end */
    //选择图片
    public static ArrayList<String> ShowImageList = null;
    public static ArrayList<String> ProductionImageList = null;

    //错误代码
    public final static String System_Error = "10001";//系统错误
    public final static String Service_Unavailable = "10002";//服务暂停
    public final static String Job_Expired = "10010";//任务超时
    public final static String Connection_NO_CONTENT = "20007";//连接失败
    public final static String Connection_Failure = "400";//连接失败
    public final static String TOKEN_INVALID = "20028";//token失效  重新登陆

    //错误提示语

    public final static String Connection_ERROR_TOAST = "网络连接失败";

    //khc
    public static int HeadlinesPosition = 0;

    //支付
    public static String order_num = null;
    public static String order_att_path = null;
    public static String att_type = null;


}