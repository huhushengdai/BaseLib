package com.blizzmi.bxlib.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.blizzmi.bxlib.log.BLog;

/**
 * Date： 2016/9/6
 * Description:
 *
 * @author WangLizhi
 * @version 1.0
 */
public class PermissionUtils {
    private static final String TAG = "PermissionUtils";
    /**
     * 查询一组权限是否授权
     *
     * @param activity       activity
     * @param permissionName 查询权限的名称数组
     * @return 返回一个boolean类型数组，与permissionName一一相对应
     */
    public static boolean[] isGranted(Activity activity, @NonNull String... permissionName) {
        int count = permissionName.length;
        boolean[] isGranted = new boolean[count];
        for (int i = 0; i < count; i++) {
            isGranted[i] = isGranted(activity, permissionName[i]);
        }
        return isGranted;
    }

    /**
     * 查询是否授权
     *
     * @param activity       Activity
     * @param permissionName 需要查询的权限名称
     * @return true 表示有权限
     */
    public static boolean isGranted(Activity activity, @NonNull String permissionName) {
        boolean isGranted = ContextCompat.checkSelfPermission(activity, permissionName)
                == PackageManager.PERMISSION_GRANTED;
        BLog.d(TAG,permissionName+":"+isGranted);
        return isGranted;
    }

    /**
     * （先放着，暂时不研究）
     * 1. 第一次请求权限时，用户拒绝了，下一次：shouldShowRequestPermissionRationale()
     * 返回 true，应该显示一些为什么需要这个权限的说明
     * 2.第二次请求权限时，用户拒绝了，并选择了“不在提醒”的选项时：
     * shouldShowRequestPermissionRationale()  返回 false
     * 3. 设备的策略禁止当前应用获取这个权限的授权：
     * shouldShowRequestPermissionRationale()  返回 false
     *
     * @param activity       Activity
     * @param permissionName 需要查询的权限名称
     * @return t
     */
    public static boolean isRefuse(Activity activity, @NonNull String permissionName) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity,
                permissionName);
    }

    /**
     * 申请权限
     *
     * @param activity       Activity
     * @param requestCode    请求码
     * @param permissionName 需要查询的权限名称
     */
    public static void requestPermissions(Activity activity, int requestCode, @NonNull String... permissionName) {
        BLog.d(TAG,"申请权限的数量:"+permissionName.length);
        int count = permissionName.length;
        for (int i = 0; i < count; i++) {
            BLog.d(TAG,"申请权限:"+permissionName[i]);
        }
        ActivityCompat.requestPermissions(activity,
                permissionName,
                requestCode);
    }
}
