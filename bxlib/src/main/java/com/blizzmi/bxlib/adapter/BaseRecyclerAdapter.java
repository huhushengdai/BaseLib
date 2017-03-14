package com.blizzmi.bxlib.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blizzmi.bxlib.annotation.LayoutId;

import java.util.List;

/**
 * Date： 2017/2/5
 * Description:
 *
 * @author WangLizhi
 * @version 1.0
 */
public abstract class BaseRecyclerAdapter<T> extends
        RecyclerView.Adapter<BaseRecyclerAdapter.BaseHolder> {
    protected Context mContext;
    protected List<T> mData;
    private int mLayoutId;

    protected ChildClickListener mChildClickListener;//子view监听事件
    private ItemClickListener mItemClick;//item 点击事件
    private ItemLongClickListener mItemLongClick;//item 长按点击事件

    public BaseRecyclerAdapter(Context context, List<T> data, @LayoutRes int layoutId) {
        this.mContext = context;
        this.mData = data;
        this.mLayoutId = layoutId;
    }

    public BaseRecyclerAdapter(Context context, List<T> data){
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
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseHolder(View.inflate(mContext, mLayoutId, null));
    }

    @Override
    public void onBindViewHolder(BaseRecyclerAdapter.BaseHolder holder, int position) {
        refreshView(holder, position);
    }

    /**
     * 更新Item
     */
    public abstract void refreshView(BaseRecyclerAdapter.BaseHolder holder, int position);

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setItemClick(ItemClickListener itemClick) {
        this.mItemClick = itemClick;
    }

    public void setItemLongClick(ItemLongClickListener itemLongClick) {
        this.mItemLongClick = itemLongClick;
    }

    /**
     * 设置子view点击监听事件
     *
     * @param listener 子view点击监听事件
     */
    public void setChildClickListener(ChildClickListener listener) {
        mChildClickListener = listener;
    }


    //---------------------------interface---------------------------------

    /**
     * item点击事件
     */
    public interface ItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public interface ItemLongClickListener {
        boolean onItemLongClick(View itemView, int position);
    }


    public interface ChildClickListener {
        void childOnClick(View parent, View childView, int position);
    }

    public class BaseHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public BaseHolder(View itemView) {
            super(itemView);
            //item 点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClick != null) {
                        mItemClick.onItemClick(v, getAdapterPosition());
                    }
                }
            });
            //item 长按点击事件
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemLongClick != null) {
                        return mItemLongClick.onItemLongClick(v, getAdapterPosition());
                    }
                    return false;
                }
            });
        }

        //----------------------子view设置--------------------------------

        public View findViewById(@IdRes int childId) {
            return itemView.findViewById(childId);
        }

        public void setText(@IdRes int childId, CharSequence text) {
            View child = itemView.findViewById(childId);
            if (child != null && child instanceof TextView) {
                ((TextView) child).setText(text);
            }
        }

        public void setText(@IdRes int childId, byte text) {
            View child = itemView.findViewById(childId);
            if (child != null && child instanceof TextView) {
                ((TextView) child).setText(text);
            }
        }

        public void setText(@IdRes int childId, int text) {
            View child = itemView.findViewById(childId);
            if (child != null && child instanceof TextView) {
                ((TextView) child).setText(text);
            }
        }

        public void setTextColor(@IdRes int childId, @ColorInt int color) {
            View child = itemView.findViewById(childId);
            if (child != null && child instanceof TextView) {
                ((TextView) child).setTextColor(color);
            }
        }

        public void setTextSize(@IdRes int childId, float size) {
            View child = itemView.findViewById(childId);
            if (child != null && child instanceof TextView) {
                ((TextView) child).setTextSize(size);
            }
        }

        public void setImage(@IdRes int childId, @DrawableRes int resId) {
            View child = itemView.findViewById(childId);
            if (child != null && child instanceof ImageView) {
                ((ImageView) child).setImageResource(resId);
            }
        }

        public void setVisibility(@IdRes int childId, int visibility) {
            View child = itemView.findViewById(childId);
            if (child != null) {
                child.setVisibility(visibility);
            }
        }

        public View getView(@IdRes int childId) {
            return itemView.findViewById(childId);
        }


        public void setImage(@IdRes int childId, Bitmap bm) {
            View child = itemView.findViewById(childId);
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
            View child = itemView.findViewById(childId);
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
            View child = itemView.findViewById(childId);
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
            View child = itemView.findViewById(childId);
            if (child != null) {
                itemView.findViewById(childId).setOnClickListener(this);
            }
        }


        @Override
        public void onClick(View v) {
            if (mChildClickListener != null) {
                mChildClickListener.childOnClick(itemView, v, getAdapterPosition());
            }
        }
    }
}
