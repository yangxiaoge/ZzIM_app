package net.bruce.zzim.common.widget.recycler;

/**
 * @author yang.jianan 2017/07/21 12:28. Email:yang.jianan0926@gmail.com
 * @version 1.0.0
 */

public interface AdapterCallback<Data> {
    void updateData(Data data,RecyclerAdapter.ViewHolder<Data> holder);
}
