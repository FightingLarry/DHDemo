package com.larry.light;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class LightAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> mList;

    protected Context mContext;

    protected LightAdapter(Context context) {
        this.mContext = context;
        mList = new ArrayList<>();
    }

    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addItem(T t) {

        // int end = mList.size() - 1;

        mList.add(t);

        // notifyItemInserted(end);
    }

    public void addItem(List<T> list) {

        // int startIndex = mList.size() - 1;
        // int start = startIndex < 0 ? 0 : startIndex;

        mList.addAll(list);

        // int itemCount = list.size();
        // notifyItemRangeInserted(start, itemCount);

    }

    public T removeItem(int position) {
        T t = mList.remove(position);
        // notifyItemRemoved(position);

        return t;
    }

    public void addItem(int postion, T t) {

        mList.add(postion, t);

        // notifyItemInserted(postion);
    }

    public List<T> getList() {
        return mList;
    }

    public void removeAll(List<T> list) {

        mList.removeAll(list);

        // notifyDataSetChanged();
    }

    public void clearItem() {

        mList.clear();

        // notifyItemRangeRemoved(0, mList.size());

    }

}
