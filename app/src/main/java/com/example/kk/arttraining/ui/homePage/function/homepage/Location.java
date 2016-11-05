package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.Manifest;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.ListView;
import java.util.ArrayList;
import static android.support.v4.app.ActivityCompat.requestPermissions;
import static android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale;
import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * Created by kanghuicong on 2016/10/29.
 * QQ邮箱:515849594@qq.com
 */
public class Location {

    public static final int SDK_PERMISSION_REQUEST = 127;
    public static ListView FunctionList;
    public static String permissionInfo;

    public static void getPersimmions(Activity context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if(checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if(checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
			/*
			 * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读写权限
            if (addPermission(context,permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(context,permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }

            if (permissions.size() > 0) {
                requestPermissions(context,permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    public static boolean addPermission(Activity context,ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale( context,permission)){
                return true;
            }else{
                permissionsList.add(permission);
                return false;
            }
        }else{
            return true;
        }
    }

}
