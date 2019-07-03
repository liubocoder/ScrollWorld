package com.lb.ui.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.lb.scroll.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiuBo
 * @date 2019-02-14
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<ViewHolderData> mDataList;
    public MyRecyclerAdapter() {
        mDataList = new ArrayList<>();
    }

    public void updateDataAndNotify(List<ViewHolderData> list) {
        mDataList.clear();
        if (list != null) {
            mDataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(inflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.onBindData(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
