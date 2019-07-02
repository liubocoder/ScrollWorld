package com.lb.scroll;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class ConfigActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

       final OverScrollerView osv1 = findViewById(R.id.osv1);
        osv1.setDerictSize(1, 2);
        osv1.setSc(0, 0);
        final OverScrollerView osv2 = findViewById(R.id.osv2);
        osv2.setDerictSize(1, 2);
        osv2.setSc(0, 1);
    }
}
