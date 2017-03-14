package com.blizzmi.bxlib.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.blizzmi.bxlib.annotation.LayoutId;

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
}
