package net.bruce.zzim.common.widget.recycler;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.bruce.zzim.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author yang.jianan 2017/07/21 12:31. Email:yang.jianan0926@gmail.com
 * @version 1.0.0
 */

public abstract class RecyclerAdapter<Data>
        extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>>
        implements View.OnClickListener, View.OnLongClickListener, AdapterCallback<Data> {
    private final List<Data> mDataList;
    private AdapterListener<Data> mListener;

    /**
     * 构造函数模块
     */
    public RecyclerAdapter() {
        this(null);
    }

    public RecyclerAdapter(AdapterListener<Data> adapterListener) {
        this(new ArrayList<Data>(), adapterListener);
    }

    public RecyclerAdapter(List<Data> dataList, AdapterListener<Data> adapterListener) {
        this.mDataList = dataList;
        this.mListener = adapterListener;
    }

    /**
     * 复写默认的布局类型返回
     *
     * @param position 坐标
     * @return 类型，其实复写后返回的都是XML文件的Id
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    /**
     * 得到布局的类型
     *
     * @param position 坐标
     * @param data     当期的数据
     * @return XML文件的Id，用于创建ViewHolder
     */
    @LayoutRes
    protected abstract int getItemViewType(int position, Data data);

    /**
     * 创建一个ViewHolder
     *
     * @param parent   RecyclerView
     * @param viewType 界面安的类型，约定为XML布局的Id
     * @return ViewHolder
     */
    @Override
    public ViewHolder<Data> onCreateViewHolder(ViewGroup parent, int viewType) {
        // 得到LayoutInflater用于把XML初始化为View
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // 把XML id为viewType的文件初始化为一个root View
        View root = inflater.inflate(viewType, parent, false);
        // 通过子类必须实现的方法，得到一个ViewHolder
        ViewHolder<Data> holder = onCreateViewHolder(root, viewType);


        // 设置View的Tag为ViewHolder，进行双向绑定
        root.setTag(R.id.tag_recycler_holder, holder);
        // 设置事件点击
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);

        // 进行界面注解绑定
        holder.unbinder = ButterKnife.bind(holder, root);
        // 绑定callback
        holder.adapterCallback = this;

        return holder;
    }

    /**
     * 得到一个新的ViewHolder
     *
     * @param root     根布局
     * @param viewType 布局类型，其实就是XML的ID
     * @return ViewHolder
     */
    protected abstract ViewHolder<Data> onCreateViewHolder(View root, int viewType);

    /**
     * 绑定数据到一个Holder上
     *
     * @param holder   ViewHolder
     * @param position 坐标
     */
    @Override
    public void onBindViewHolder(ViewHolder<Data> holder, int position) {
        //得到需要绑定的数据
        Data data = mDataList.get(position);
        //触发Holder的绑定方法
        holder.bind(data);
    }

    /**
     * 得到当前集合的数据量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 插入一条数据并通知插入
     *
     * @param data data
     */
    public void add(Data data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    /**
     * 插入一堆数据，并通知这段集合更新
     *
     * @param dataList dataList
     */
    public void add(Data... dataList) {
        if (dataList != null && dataList.length > 0) {
            int startPos = mDataList.size();//原来数据的位置
            Collections.addAll(mDataList, dataList);
            notifyItemRangeChanged(startPos, dataList.length); //插入数据的范围，局部更新
        }
    }

    /**
     * 插入一堆数据，并通知这段集合更新
     *
     * @param dataList dataList
     */
    public void add(Collection<Data> dataList) {
        if (dataList != null && dataList.size() > 0) {
            int startPos = mDataList.size();//原来数据的位置
            mDataList.addAll(dataList);
            notifyItemRangeChanged(startPos, dataList.size()); //插入数据的范围，局部更新
        }
    }

    /**
     * 删除操作
     */
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 替换为一个新的集合，其中包括了清空
     *
     * @param dataList 一个新的集合
     */
    public void replace(Collection<Data> dataList) {
        mDataList.clear();

        if (dataList == null || dataList.size() == 0)
            return;

        mDataList.addAll(dataList);
    }

    @Override
    public void onClick(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            //得到ViewHolder当前对应的适配器中的坐标
            int pos = viewHolder.getAdapterPosition();
            //回掉方法
            mListener.onItemClick(viewHolder, mDataList.get(pos));
        }
    }

    @Override
    public boolean onLongClick(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            //得到ViewHolder当前对应的适配器中的坐标
            int pos = viewHolder.getAdapterPosition();
            //回掉方法
            mListener.onItemLongClick(viewHolder, mDataList.get(pos));
            return true;
        }
        return false;
    }

    /**
     * 设置适配器的监听
     *
     * @param adapterListener AdapterListener
     */
    public void setListener(AdapterListener<Data> adapterListener) {
        this.mListener = adapterListener;
    }

    /**
     * 我们的自定义监听器
     *
     * @param <Data> 泛型
     */
    public interface AdapterListener<Data> {
        void onItemClick(RecyclerAdapter.ViewHolder<Data> holder, Data data);

        void onItemLongClick(RecyclerAdapter.ViewHolder<Data> holder, Data data);
    }

    /**
     * 自定义的ViewHolder
     *
     * @param <Data> 泛型类型
     */
    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder {
        protected Data mData;
        private Unbinder unbinder;
        private AdapterCallback<Data> adapterCallback;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 用于绑定数据的触发
         *
         * @param data
         */
        void bind(Data data) {
            this.mData = data;
            onBind(data);
        }

        /**
         * 当触发绑定数据的时候，的回掉；必须复写
         *
         * @param data 绑定的数据
         */
        protected abstract void onBind(Data data);

        /**
         * Holder自己对自己对应的Data进行更新操作
         *
         * @param data Data数据
         */
        public void updateData(Data data) {
            if (this.adapterCallback != null) {
                this.adapterCallback.updateData(data, this);
            }
        }
    }
}
