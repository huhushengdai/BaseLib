package com.blizzmi.bxlib.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.blizzmi.bxlib.annotation.LayoutId;

import java.util.List;

/**
 * Date： 2016/9/26
 * Description:
 *
 * @author WangLizhi
 * @version 1.0
 */
public abstract class BaseRecyclerBindingAdapter<T, B extends ViewDataBinding> extends
        RecyclerView.Adapter<BaseRecyclerBindingAdapter<T, B>.BaseViewHolder<B>> {
    protected Context mContext;
    protected List<T> mData;
    private int mLayoutId;
    private ItemClickListener mItemClick;//item 点击事件
    private ItemLongClickListener mItemLongClick;//item 长按点击事件


    public BaseRecyclerBindingAdapter(Context context, List<T> data, @LayoutRes int layoutId) {
        this.mContext = context;
        this.mData = data;
        this.mLayoutId = layoutId;
    }

    public BaseRecyclerBindingAdapter(Context context, List<T> data) {
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
    /**
     * 创建ViewHolder
     */
    public BaseViewHolder<B> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<B>(View.inflate(mContext, mLayoutId, null));
    }

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

    /**
     * BaseViewHolder
     *
     * @param <Binding>
     */
    public class BaseViewHolder<Binding extends ViewDataBinding> extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        public Binding binding;

        public BaseViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            binding = DataBindingUtil.bind(itemView);
        }

        @Override//item长按点击事件
        public boolean onLongClick(View v) {
            if (mItemLongClick != null) {
                return mItemLongClick.onItemLongClick(v, getAdapterPosition());
            }
            return false;
        }

        @Override//item点击事件
        public void onClick(View v) {
            if (mItemClick != null) {
                mItemClick.onItemClick(v, getAdapterPosition());
            }
        }
    }
}
