package com.blizzmi.bxlib.fragment;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blizzmi.bxlib.annotation.LayoutId;

/**
 * Date： 2016/9/8
 * Description:
 * 框架层Fragment
 * 这里使用Google 提供的Mvvm框架  data binding
 * <p>
 * 使用@LayoutId 注解指定布局
 * <p>
 * 泛型 T ：用于指定 binding的类型
 *
 * @author WangLizhi
 * @version 1.0
 */
public abstract class LibBindingFragment<T extends ViewDataBinding> extends Fragment {

    protected T mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        before();
        int layoutId = getLayoutId();
        View view;
        if (layoutId != 0) {
            view = inflater.inflate(layoutId, container, false);
            try {
                mBinding = DataBindingUtil.bind(view);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        } else {
            view = new View(getActivity());
        }
        init();
        after();
        return view;
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
}
