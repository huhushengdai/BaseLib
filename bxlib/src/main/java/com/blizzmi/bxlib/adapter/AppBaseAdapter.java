package com.blizzmi.bxlib.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blizzmi.bxlib.annotation.LayoutId;

import java.util.List;

/**
 * Date： 2016/7/14
 * Description:
 * ListView的公共适配器
 * 只需要实现 initView方法，进行UI更新
 * 不需要每一个adapter都写ViewHolder
 * 更新子view时，只需要调用BaseViewHolder里面的方法，指定需要更新的子viewID跟内容即可
 *
 * @author WangLizhi
 * @version 1.0
 */
public abstract class AppBaseAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mData;
    protected int mLayoutId;

    protected ChildClickListener mChildClickListener;//子view监听事件
    protected ChildLongClickListener mChildLongListener;//子view长按点击事件

    public AppBaseAdapter(Context context, List<T> data, @LayoutRes int layoutId) {
        this.mContext = context;
        this.mData = data;
        this.mLayoutId = layoutId;
    }
    public AppBaseAdapter(Context context, List<T> data){
        this.mContext = context;
        this.mData = data;
        this.mLayoutId = getLayoutId();
    }

    /**
     * 获取布局id
     * @return layoutId
     */
    private int getLayoutId(){
        LayoutId layoutId = this.getClass().getAnnotation(LayoutId.class);
        if (layoutId == null){
            return 0;
        }else {
            return layoutId.value();
        }
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        BaseViewHolder holder = null;
        if (view == null) {
            view = View.inflate(mContext, getItemLayout(position), null);
            holder = new BaseViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (BaseViewHolder) view.getTag();
        }
        holder.setPosition(position);
        refreshView(position, holder, parent);
        return view;
    }

    /**
     * 获取布局id
     * 如果需要多布局，则重写此方法即可
     *
     * @param position
     * @return layout id
     */
    public int getItemLayout(int position) {
        return mLayoutId;
    }

    /**
     * 更新Item
     */
    public abstract void refreshView(int position, BaseViewHolder holder, ViewGroup parent);

    /**
     * 设置子view点击监听事件
     *
     * @param listener 子view点击监听事件
     */
    public void setChildClickListener(ChildClickListener listener) {
        mChildClickListener = listener;
    }

    /**
     * 设置子View 长按点击监听事件
     *
     * @param listener 监听事件
     */
    public void setChildLongListener(ChildLongClickListener listener) {
        this.mChildLongListener = listener;
    }

    /**
     * ListView 的  ViewHolder
     */
    public class BaseViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private View mConvertView;
        private int mPosition;
        private ViewDataBinding mBinding;

        public BaseViewHolder(View itemView) {
            mConvertView = itemView;
            try {
                //防止View 没有使用data binding
                mBinding = DataBindingUtil.bind(itemView);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public ViewDataBinding getBinding() {
            return mBinding;
        }

        /**
         * 设置当前选项
         *
         * @param position 当前选项
         */
        public void setPosition(int position) {
            mPosition = position;
        }


        //----------------------子view设置--------------------------------

        public View findViewById(@IdRes int childId) {
            return mConvertView.findViewById(childId);
        }

        public void setText(@IdRes int childId, CharSequence text) {
            View child = mConvertView.findViewById(childId);
            if (child != null && child instanceof TextView) {
                ((TextView) child).setText(text);
            }
        }

        public void setText(@IdRes int childId, byte text) {
            View child = mConvertView.findViewById(childId);
            if (child != null && child instanceof TextView) {
                ((TextView) child).setText(text);
            }
        }

        public void setText(@IdRes int childId, int text) {
            View child = mConvertView.findViewById(childId);
            if (child != null && child instanceof TextView) {
                ((TextView) child).setText(text);
            }
        }

        public void setTextColor(@IdRes int childId, @ColorInt int color) {
            View child = mConvertView.findViewById(childId);
            if (child != null && child instanceof TextView) {
                ((TextView) child).setTextColor(color);
            }
        }

        public void setTextSize(@IdRes int childId, float size) {
            View child = mConvertView.findViewById(childId);
            if (child != null && child instanceof TextView) {
                ((TextView) child).setTextSize(size);
            }
        }

        public void setImage(@IdRes int childId, @DrawableRes int resId) {
            View child = mConvertView.findViewById(childId);
            if (child != null && child instanceof ImageView) {
                ((ImageView) child).setImageResource(resId);
            }
        }

        public void setVisibility(@IdRes int childId, int visibility) {
            View child = mConvertView.findViewById(childId);
            if (child != null) {
                child.setVisibility(visibility);
            }
        }

        public View getView(@IdRes int childId) {
            return mConvertView.findViewById(childId);
        }


        public void setImage(@IdRes int childId, Bitmap bm) {
            View child = mConvertView.findViewById(childId);
            if (child != null && child instanceof ImageView) {
                ((ImageView) child).setImageBitmap(bm);
            }
        }

        /**
         * 设置子view宽高
         *
         * @param childId
         * @param width
         * @param height
         */
        public void setWidthAndHeight(@IdRes int childId, int width, int height) {
            View child = mConvertView.findViewById(childId);
            if (child != null) {
                ViewGroup.LayoutParams params = child.getLayoutParams();
                params.width = width;
                params.height = height;
                child.setLayoutParams(params);
            }
        }

        public void setDrawables(@IdRes int childId,
                                 @DrawableRes int left,
                                 @DrawableRes int top,
                                 @DrawableRes int right,
                                 @DrawableRes int bottom) {
            View child = mConvertView.findViewById(childId);
            if (child != null && child instanceof TextView) {
                ((TextView) child).
                        setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
            }
        }

        //----------------------------------监听事件-----------------------------

        /**
         * 给子view添加点击事件监听
         *
         * @param childId 需要添加监听的子view
         */
        public void setChildListener(@IdRes int childId) {
            View child = mConvertView.findViewById(childId);
            if (child != null) {
                mConvertView.findViewById(childId).setOnClickListener(this);
            }
        }

        /**
         * 设置子view 长按点击事件
         *
         * @param childId 子view
         */
        public void setChildLongListener(@IdRes int childId) {
            View child = mConvertView.findViewById(childId);
            if (child != null) {
                child.setOnLongClickListener(this);
            }
        }

        /**
         * 取消子view的点击监听
         *
         * @param childId 需要取消监听的子view
         */
        public void cancelChildListener(@IdRes int childId) {
            View child = mConvertView.findViewById(childId);
            if (child != null) {
                child.setOnClickListener(null);
            }
        }

        @Override
        public void onClick(View v) {
            if (mChildClickListener != null) {
                mChildClickListener.childOnClick(mConvertView, v, mPosition);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mChildLongListener != null) {
                return mChildLongListener.childLongClick(mConvertView, v, mPosition);
            }
            return false;
        }
    }

    public interface ChildClickListener {
        void childOnClick(View parent, View childView, int position);
    }

    public interface ChildLongClickListener {
        boolean childLongClick(View parent, View childView, int position);
    }
}
