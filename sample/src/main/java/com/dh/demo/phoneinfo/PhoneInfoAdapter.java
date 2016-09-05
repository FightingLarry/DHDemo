package com.dh.demo.phoneinfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dh.baseactivity.AdapterBehavior;
import com.dh.baseactivity.AdapterClickListener;
import com.dh.demo.MainModel;
import com.dh.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yancai.liu on 2016/9/5.
 */

public class PhoneInfoAdapter extends RecyclerView.Adapter<PhoneInfoAdapter.ViewHolder>
        implements
            AdapterBehavior<PhoneInfo> {
    private List<PhoneInfo> mList;
    private Context context;
    private AdapterClickListener mAdapterClickListener;

    public PhoneInfoAdapter(Context context, AdapterClickListener l) {
        this.mList = new ArrayList<>();
        this.context = context;
        this.mAdapterClickListener = l;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_phoneinfo_item, parent, false);
        return new PhoneInfoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final PhoneInfo model = mList.get(position);
        holder.mTitle.setText(model.getName());
        holder.mDes.setText(model.getInfo());
        holder.mCopy.setOnClickListener(new View.OnClickListener() {
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
    public void addItem(PhoneInfo phoneInfo) {
        mList.add(phoneInfo);
    }

    @Override
    public void addItem(List<PhoneInfo> list) {
        mList.addAll(list);
    }

    @Override
    public PhoneInfo removeItem(int position) {
        return mList.remove(position);
    }

    @Override
    public void addItem(int postion, PhoneInfo phoneInfo) {
        mList.add(postion, phoneInfo);
    }

    @Override
    public List<PhoneInfo> getList() {
        return mList;
    }

    @Override
    public void removeAll(List<PhoneInfo> list) {
        mList.remove(list);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View mConvertView;
        TextView mTitle;
        TextView mDes;
        Button mCopy;

        public ViewHolder(View convertView) {
            super(convertView);
            mConvertView = convertView;
            mTitle = (TextView) convertView.findViewById(R.id.title);
            mDes = (TextView) convertView.findViewById(R.id.des);
            mCopy = (Button) convertView.findViewById(R.id.copy);
        }
    }
}
