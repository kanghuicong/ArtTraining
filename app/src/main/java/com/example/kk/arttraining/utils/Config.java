package com.example.kk.arttraining.utils;

/**
 * Created by kanghuicong on 2016/9/21.
 * QQ邮箱:515849594@qq.com
 * 配置信息
 */

public class Config {


    public final static String testapi="HTML/dynamictest.html";
    /* 接口相关-start */
//    public final static String SERVER_IP = "192.168.188.245";

        public final static String SERVER_IP = "121.43.172.150";
    public final static String SERVER_MH = ":";
    public final static String SYSTEM_PORT = "8080";
    public final static String SYSTEM_NAME = "/LeRunManager/";
    public final static String BASE_URL = "http://" + SERVER_IP + SERVER_MH + SYSTEM_PORT + SYSTEM_NAME;

    public final static String URL_TEST= "servlet/LeRunServlet";//登陆

    //登陆接口
    public final static String URL_LOGIN = "login/login";//登陆
    public final static String URL_LOGIN_EXIT = "login/exit";//退出登录
    //用户注册接口
    public final static String URL_REGISTER_CREATE = "register/create";//用户注册
    public final static String URL_REGISTER_ISREG = "register/is_reg";//判断手机号码是否注册过
    public final static String URL_SMS_SEND = "sms/verification_code/send";//获取手机验证码
    public final static String URL_SMS_VERIFY = "sms/verification_code/verify";//判断手机号码是否注册过
    //用户相关接口
    public final static String URL_USERS_GET_INFO = "users/show";//获取用户信息
    public final static String URL_USERS_SET_INFO = "users/set_info";//设置个人资料
    public final static String URL_USERS_UPDATE_HEAD = "users/update_head";//根据用户ID修改用户头像
    public final static String URL_USERS_UPDATE_PWD = "users/update_pwd";//修改用户登录密码
    public final static String URL_USERS_UPDATE_MOIBLE = "users/change_mobile";//更换手机号
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
    public final static String URL_STATUSES_PUBLIC_TIMELINE = "statuses/public_timeline";//获取最新动态（精品动态）列表
    public final static String URL_STATUSES_USER_TIMELINE = "statuses/user_timeline";//获取用户发布的动态
    public final static String URL_STATUSES_SHOW = "statuses/show";//获取动态详情
    public final static String URL_STATUSES_REPORT = "statuses/report";//转发动态
    public final static String URL_STATUSES_PUBLISH = "statuses/publish";//发布动态
    public final static String URL_STATUSES_DELETE = "statuses/delete";//删除动态
    //评论接口
    public final static String URL_COMMENTS_LIST = "comments/list";//获取动态的评论列表
    public final static String URL_COMMENTS_CREATE = "comments/create";//发表一条评论
    public final static String URL_COMMENTS_DELETE = "comments/delete";//删除一条评论
    public final static String URL_COMMENTS_REPLY = "comments/reply";//回复一条评论
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
    public final static String URL_LIKE_LIST = "like/list";//获取点赞列表
    public final static String URL_LIKE_LIST_PIC = "like/list/pic";//获取点赞用户头像列表
    public final static String URL_LIKE_CREATE = "like/create";//添加点赞
    public final static String URL_LIKE_DELETE = "like/delete";//删除点赞
    //支付接口
    public final static String URL_PAY_PAY = "pay/pay";//获取点赞列表
    public final static String URL_PAY_REFUND = "pay/refund";//获取点赞用户头像列表
    public final static String URL_PAY_PRE_CREATE = "pay/pre_create";//添加点赞
    public final static String URL_PAY_CREATE = "pay/create";//删除点赞
    public final static String URL_PAY_CANCEL = "pay/cancel";//获取点赞用户头像列表
    public final static String URL_PAY_CLOSE = "pay/close";//添加点赞
    public final static String URL_PAY_ORDER_SETTLE = "pay/order/settle";//删除点赞
    //机构
    public final static String URL_ORG_LIST = "pay/close";//获取机构列表
    public final static String URL_ORG_SHOW = "pay/order/settle";//获取机构详情
    //艺术家/名师接口
    public final static String URL_TECHER_LIST = "techer/list";//获取名师列表
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
    public final static String URL_INSTITUTIONS_LIST = "institutions/list";//获取院校列表
    public final static String URL_INSTITUTIONS_SHOW = "institutions/show";//获取院校详情

    /* 接口相关-start */
    public final static String URL_PAY_ASYNC = BASE_URL + "";//服务器异步通知页面接口

     /* 数据库相关-start */

    /* 数据库相关-end */


    /* 全局变量-start */
    public static String ACCESS_TOKEN = "";
    public static String User_Id = "123";
    public static int PermissionsState = 0;//权限状态
    public final static String USER_HEADER_Url = "http://img.fuwo.com/attachment/1608/09/245f26de5e1811e6abcf00163e00254c.jpg";
    /* 全局变量-end */

}
