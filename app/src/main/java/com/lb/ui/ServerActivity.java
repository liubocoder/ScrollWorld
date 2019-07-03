package com.lb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lb.scroll.R;
import com.lb.ui.list.MyRecyclerAdapter;

public class ServerActivity extends BaseActivity {
    public static void showThisPage(BaseActivity activity) {
        Intent intent = new Intent(activity, ServerActivity.class);
        activity.finish();
        activity.startActivity(intent);
    }

    private RecyclerView mRecycler;
    private MyRecyclerAdapter mAdater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        mRecycler = findViewById(R.id.recycler);
        mAdater = new MyRecyclerAdapter();

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdater);
    }
}
