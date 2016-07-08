package com.dh.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dh.baseactivity.AdapterBehavior;
import com.dh.baseactivity.AdapterClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yancai.liu on 2016/7/8.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements AdapterBehavior<MainModel> {
    private List<MainModel> mList;
    private Context context;
    private AdapterClickListener mAdapterClickListener;

    public MainAdapter(Context context, AdapterClickListener l) {
        this.mList = new ArrayList<>();
        this.context = context;
        this.mAdapterClickListener = l;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_main_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final MainModel model = mList.get(position);
        holder.title.setText(model.getTitle());
        holder.des.setText(model.getDes());
        holder.mConvertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapterClickListener != null) {
                    mAdapterClickListener.onItemClick(v, mList.get(position), position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void clearItem() {
        mList.clear();
    }

    @Override
    public void addItem(MainModel mainModel) {
        mList.add(mainModel);
    }

    @Override
    public void addItem(List<MainModel> list) {
        mList.addAll(list);
    }

    @Override
    public MainModel removeItem(int position) {
        return mList.remove(position);
    }

    @Override
    public void addItem(int postion, MainModel mainModel) {
        mList.add(postion, mainModel);
    }

    @Override
    public List<MainModel> getList() {
        return mList;
    }

    @Override
    public void removeAll(List<MainModel> list) {
        mList.remove(list);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView des;
        View mConvertView;

        public ViewHolder(View convertView) {
            super(convertView);
            title = (TextView) convertView.findViewById(R.id.title);
            des = (TextView) convertView.findViewById(R.id.des);
            mConvertView = convertView;
        }
    }
}
