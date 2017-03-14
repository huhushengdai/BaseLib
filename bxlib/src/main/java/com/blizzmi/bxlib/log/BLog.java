package com.blizzmi.bxlib.log;

import android.util.Log;


/**
 * Date： 2016/7/19
 * Description:
 *
 * @author WangLizhi
 * @version 1.0
 */
public class BLog {

    private static boolean IS_DEBUG = true;

    public static void setIsDebug(boolean isDebug){
        IS_DEBUG = isDebug;
    }


    public static void i(String tag, String msg) {
        if (IS_DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (IS_DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (IS_DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (IS_DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (IS_DEBUG) {
            Log.v(tag, msg);
        }
    }

}
