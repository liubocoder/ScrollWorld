package com.lb.ui.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.lb.scroll.R;

/**
 * @author LiuBo
 * @date 2019-07-02
 */
public class MyViewHolder extends RecyclerView.ViewHolder {
    private Button mBtn;
    public MyViewHolder(View itemView) {
        super(itemView);
        mBtn = itemView.findViewById(R.id.btn_title);
    }

    void onBindData(ViewHolderData data) {
        mBtn.setText(data.desc);
    }
}
