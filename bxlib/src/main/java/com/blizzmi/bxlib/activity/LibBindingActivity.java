package com.blizzmi.bxlib.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.blizzmi.bxlib.annotation.LayoutId;
import com.blizzmi.bxlib.utils.PermissionUtils;

import java.util.ArrayList;

/**
 * Date： 2016/9/8
 * Description:
 * 框架层Activity
 * 这里使用Google 提供的Mvvm框架  data binding
 * <p>
 * 使用@LayoutId 注解指定布局
 * <p>
 * 泛型 T ：用于指定 binding的类型
 *
 * @author WangLizhi
 * @version 1.0
 */
public abstract class LibBindingActivity<T extends ViewDataBinding> extends AppCompatActivity {

    public T mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        before();
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            try {
                setBinding(DataBindingUtil.<T>setContentView(this, layoutId));
            } catch (Exception e) {
                e.printStackTrace();
                setContentView(getLayoutId());
            }
        }
        init();
        after();
    }

    /**
     * 绑定数据之前所做的操作
     */
    protected void before() {
    }

    /**
     * 初始化数据的操作
     */
    protected void init() {
    }

    /**
     * 初始化之后的操作
     */
    protected void after() {
    }

    public void setBinding(T binding) {
        mBinding = binding;
    }

    /**
     * 获取布局id
     *
     * @return layoutId
     */
    public int getLayoutId() {
        LayoutId layoutId = this.getClass().getAnnotation(LayoutId.class);
        if (layoutId == null) {
            return 0;
        } else {
            return layoutId.value();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 请求权限
     * @param requestCode 权限亲求码
     * @param permission 需要的权限
     */
    public void requirePermission(int requestCode, String... permission) {
        if (Build.VERSION.SDK_INT < 23 ||permission == null) {
            //如果sdk 低于23 即手机版本不是6.0以上，则不需要申请
            return;
        }
        //查询是否已经获取该权限
        boolean[] isGranted = PermissionUtils.isGranted(this, permission);
        int size = isGranted.length;
        ArrayList<String> p = new ArrayList<>(permission.length);//需要申请的权限
        for (int i = 0; i < size; i++) {
            if (!isGranted[i]) {
                //如果没有获取，则申请该权限
                p.add(permission[i]);
                PermissionUtils.requestPermissions(this, requestCode, permission[i]);
            }
        }
        int requireSize = p.size();//请求权限的数量
        if (requireSize==0){
            return;
        }
        String[] temp = new String[requireSize];
        for (int i = 0; i < requireSize; i++) {
            temp[i] = p.get(i);
        }
        PermissionUtils.requestPermissions(this, requestCode, temp);
    }

    /**
     * 是否获取授权
     * @param permission 申请权限
     * @return true  获取授权
     */
    public boolean isGranted(String permission){
        return PermissionUtils.isGranted(this, permission);
    }
}
