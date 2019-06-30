package com.lb.scroll;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.lb.net.BroadcastAddr;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;

public class MainActivity extends AppCompatActivity {
    private int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        final OverScrollerView osv = findViewById(R.id.osv);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                osv.setCount(counter++);


                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String brod = BroadcastAddr.getBroadcastAddr();
                        String msg = "xxxxxxddddddddddddddd";
                        byte[] buf = msg.getBytes();
                        DatagramSocket detectSocket = null;
                        try {
                            int port = 9999;
                            detectSocket = new DatagramSocket(port);

                            DatagramPacket out = new DatagramPacket(buf,
                                    buf.length, InetAddress.getByName(brod), port);
                            detectSocket.send(out);
                            detectSocket.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();*/
            }
        });
    }
}
