package com.example.kk.arttraining.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kanghuicong on 2016/9/22.
 * QQ邮箱:515849594@qq.com
 * 正则表达式
 */
public class RegularExpressions {
//    Pattern p_phone = Pattern.compile(RegularExpressions.Regular_Name);
//    Matcher m_phone = p_phone.matcher(enroll_number.getText().toString());
//    m_phone.matches()

    //中文姓名
    public String Regular_Name = "^[\u4e00-\u9fa5]+$";
    //电话号码
    public String Regular_Phone ="^1[34578]\\d{9}$";
    //身份证
    public String Regular_ID1 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
    public String Regular_ID2 = "^[1-9]\\\\d{7}((0\\\\d)|(1[0-2]))(([0|1|2]\\\\d)|3[0-1])\\\\d{3}$";
    //护照
    public String Regular_Passport1 = "/^[a-zA-Z]{5,17}$/";
    public String Regular_Passport2 = "/^[a-zA-Z0-9]{5,17}$/";
    //港澳通行证
    public String Regular_HMPass = "/^[HMhm]{1}([0-9]{10}|[0-9]{8})$/";
    //台湾通行证
    public String Regular_TPass1 = "/^[0-9]{8}$/";
    public String Regular_TPass2 = "/^[0-9]{10}$/";
}
