package com.blizzmi.bxlib.vm;

import android.app.Activity;
import android.databinding.BaseObservable;

/**
 * Date： 2017/3/8
 * Description:
 * 框架层vm
 *
 * @author WangLizhi
 * @version 1.0
 */
public class LibVm extends BaseObservable {

    private Activity activity;

    public void onCreate(){}
    public void onStart(){}
    public void onResume(){}
    public void onPause(){}
    public void onStop(){}
    public void onRestart(){}
    public void onDestroy(){}

    public void clear(){
        activity = null;
    }
}
