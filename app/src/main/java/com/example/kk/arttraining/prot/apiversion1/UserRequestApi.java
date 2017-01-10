package com.example.kk.arttraining.prot.apiversion1;


import com.example.kk.arttraining.bean.GeneralBean;
import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.bean.OrderBean;
import com.example.kk.arttraining.bean.UpdateBean;
import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.bean.parsebean.ParseOrderListBean;
import com.example.kk.arttraining.ui.live.bean.RoomBean;
import com.example.kk.arttraining.ui.me.bean.ParseCouponBean;
import com.example.kk.arttraining.ui.me.bean.ParseIdentityBean;
import com.example.kk.arttraining.ui.me.bean.ParseMessageBean;
import com.example.kk.arttraining.ui.me.bean.UserCountBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.upload.bean.TokenBean;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 作者：wschenyongyin on 2016/8/30 16:13
 * 说明:用户相关网络调用接口
 */
public interface UserRequestApi {
//    @POST(Config.URL_TEST)
//    @FormUrlEncoded
//    Call<testBean> test(@FieldMap Map<String, String> map);

    //用户登陆
    @POST(Config.API_LOGIN_V2)
    @FormUrlEncoded
    Call<UserLoginBean> Login(@FieldMap Map<String, String> map);


    //第三方登陆
    @POST(Config.API_UMLOGIN)
    @FormUrlEncoded
    Call<UserLoginBean> UmLogin(@FieldMap Map<String, String> map);

    //第三方注册
    @POST(Config.API_REGISTER_CREATE)
    @FormUrlEncoded
    Call<UserLoginBean> UmRegister(@FieldMap Map<String, Object> map);

    //第三方登陆（验证手机号码是否绑定过）
    @POST(Config.API_VERIFY_PHONE)
    @FormUrlEncoded
    Call<NoDataResponseBean> UmVerifyPhone(@FieldMap Map<String, String> map);

    //用户退出登陆
    @POST(Config.URL_LOGIN_EXIT)
    @FormUrlEncoded
    Call<NoDataResponseBean> Login_Exit(@FieldMap Map<String, String> map);

    //用户注册
    @POST(Config.URL_REGISTER_CREATE)
    @FormUrlEncoded
    Call<UserLoginBean> register(@FieldMap Map<String, String> map);

    //用户找回密码
    @POST(Config.URL_FORGOT_PWD)
    @FormUrlEncoded
    Call<GeneralBean> forgotPWD(@FieldMap Map<String, String> map);

    //判断手机号码是否注册过
    @POST(Config.URL_REGISTER_ISREG)
    @FormUrlEncoded
    Call<NoDataResponseBean> checkMobile(@FieldMap Map<String, String> map);

    //获取手机验证码
    @POST(Config.URL_SMS_SEND)
    @FormUrlEncoded
    Call<GeneralBean> sendSMS(@FieldMap Map<String, String> map);

    //校验验证码
    @POST(Config.URL_SMS_VERIFY)
    @FormUrlEncoded
    Call<GeneralBean> verifySMS(@FieldMap Map<String, String> map);

    //校验邀请码
    @POST(Config.URL_INVITE_CODE_VERIFY)
    @FormUrlEncoded
    Call<NoDataResponseBean> inviteCode(@FieldMap Map<String, String> map);

//    //设置密码
//    @POST(Config.URL_REGISTER_CREATE)
//    @FormUrlEncoded
//    Call<NoDataResponseBean> setPwd(@FieldMap Map<String, String> map);


    //获取用户信息
    @POST(Config.URL_USERS_GET_INFO)
    @FormUrlEncoded
    Call<UserLoginBean> userinfo(@FieldMap Map<String, Object> map);

    //用户修改头像
    @FormUrlEncoded
    @POST(Config.URL_USERS_UPDATE_HEAD)
    Call<UpdateBean> updateHead(@FieldMap Map<String, Object> map);


    //修改用户登录密码
    @POST(Config.URL_USERS_UPDATE_PWD)
    @FormUrlEncoded
    Call<UpdateBean> updatePwd(@FieldMap Map<String, Object> map);

    //修改用户手机号码
    @POST(Config.URL_USERS_UPDATE_MOIBLE)
    @FormUrlEncoded
    Call<UpdateBean> updateMobile(@FieldMap Map<String, Object> map);

    //修改用户信息
    @POST(Config.URL_USERS_SET_INFO)
    @FormUrlEncoded
    Call<UpdateBean> setUserInfo(@FieldMap Map<String, Object> map);

    //多文件上传
    @Multipart
    @POST("servlet/UploadServlet")
    Call<ResponseBody> upload(@Part("description") RequestBody description,
                              @Part MultipartBody.Part file);


    //获取订单信息列表
    @POST(Config.URL_ORDERS_LIST)
    @FormUrlEncoded
    Call<ParseOrderListBean> getOrderList(@FieldMap Map<String, Object> map);

    //获取订单信息列表
    @POST(Config.URL_ORDERS_SHOW)
    @FormUrlEncoded
    Call<OrderBean> getOrderShow(@FieldMap Map<String, String> map);

    //创建订单
    @POST(Config.URL_ORDERS_CREATE)
    @FormUrlEncoded
    Call<GeneralBean> createOrder(@FieldMap Map<String, String> map);


    //获取优惠券列表
    @POST(Config.URL_COUPONS_LIST)
    @FormUrlEncoded
    Call<ParseCouponBean> getCouponList(@FieldMap Map<String, Object> map);

    //兑换优惠券
    @POST(Config.URL_EXCHANGE_COUPONS)
    @FormUrlEncoded
    Call<NoDataResponseBean> exchangeCoupon(@FieldMap Map<String, Object> map);

    //获取七牛云上传token
    @FormUrlEncoded
    @POST(Config.URL_UPLOAD_QINIU_GETTOKEN)
    Call<TokenBean> getQiNiuToken(@FieldMap Map<String, Object> map);


    //获取身份列表
    @FormUrlEncoded
    @POST(Config.URL_IDENTITY_LIST)
    Call<ParseIdentityBean> getIdentityList(@FieldMap Map<String, Object> map);

    //获取身份列表
    @FormUrlEncoded
    @POST(Config.URL_USERS_COUNT_NUM)
    Call<UserCountBean> getCountNum(@FieldMap Map<String, Object> map);


    //用户消息列表
    @FormUrlEncoded
    @POST(Config.URL_PUSH_NEW_LIST)
    Call<ParseMessageBean> getPushNewList(@FieldMap Map<String, Object> map);

    //用户消息列表
    @FormUrlEncoded
    @POST(Config.URL_PUSH_ALL_LIST)
    Call<ParseMessageBean> getPushAllList(@FieldMap Map<String, Object> map);

    //标记消息为已读
    @FormUrlEncoded
    @POST(Config.URL_MESSAGE_RED_ONE)
    Call<NoDataResponseBean> messageRadOne(@FieldMap Map<String, Object> map);

    //标记全部消息为已读
    @FormUrlEncoded
    @POST(Config.URL_MESSAGE_RED_ALL)
    Call<NoDataResponseBean> messageRadAll(@FieldMap Map<String, Object> map);


}
