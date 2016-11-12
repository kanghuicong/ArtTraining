package com.example.kk.arttraining.prot;


import com.example.kk.arttraining.bean.GeneralBean;
import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.bean.OrderBean;
import com.example.kk.arttraining.bean.UpdateBean;
import com.example.kk.arttraining.bean.UpdateHeadBean;
import com.example.kk.arttraining.bean.UserInfoBean;
import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.bean.parsebean.ParseOrderListBean;
import com.example.kk.arttraining.bean.testBean;
import com.example.kk.arttraining.ui.me.bean.ParseCouponBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.upload.bean.TokenBean;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 作者：wschenyongyin on 2016/8/30 16:13
 * 说明:用户相关网络调用接口
 */
public interface UserRequestApi {
    //用户登陆
    @POST(Config.URL_TEST)
    @FormUrlEncoded
    Call<testBean> test(@FieldMap Map<String, String> map);

    //用户登陆
    @POST(Config.URL_LOGIN)
    @FormUrlEncoded
    Call<UserLoginBean> Login(@FieldMap Map<String, String> map);

    //用户退出登陆
    @POST(Config.URL_LOGIN_EXIT)
    @FormUrlEncoded
    Call<NoDataResponseBean> Login_Exit(@FieldMap Map<String, String> map);

    //用户注册
    @POST(Config.URL_REGISTER_CREATE)
    @FormUrlEncoded
    Call<NoDataResponseBean> register(@FieldMap Map<String, String> map);

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
    Call<NoDataResponseBean> sendSMS(@FieldMap Map<String, String> map);

    //校验验证码
    @POST(Config.URL_SMS_VERIFY)
    @FormUrlEncoded
    Call<NoDataResponseBean> verifySMS(@FieldMap Map<String, String> map);

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
    @Multipart
    @POST(Config.URL_USERS_UPDATE_HEAD)
    Call<UpdateHeadBean> updateHead(@Query("access_token") String access_token,
                                    @Query("uid") int uid,
                                    @Part("description") RequestBody description,
                                    @Part RequestBody params);


    //修改用户登录密码
    @POST(Config.URL_USERS_UPDATE_PWD)
    @FormUrlEncoded
    Call<UpdateBean> updatePwd(@FieldMap Map<String, Object> map);

    //修改用户手机号码
    @POST(Config.URL_USERS_UPDATE_MOIBLE)
    @FormUrlEncoded
    Call<UpdateBean> updateMobile(@FieldMap Map<String, String> map);

    //修改用户手机号码
    @POST(Config.URL_USERS_UPDATE_MOIBLE)
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
    Call<ParseCouponBean> getCouponList(@FieldMap Map<String, String> map);

    //获取七牛云上传token
    @FormUrlEncoded
    @POST(Config.URL_UPLOAD_QINIU_GETTOKEN)
    Call<TokenBean> getQiNiuToken(@FieldMap Map<String, Object> map);


}
