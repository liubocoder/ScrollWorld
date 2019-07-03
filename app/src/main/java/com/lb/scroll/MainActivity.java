package com.lb.scroll;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.lb.net.BroadcastAddr;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN";
    private OverScrollerView osv;

    private String multiIp = "239.0.0.255";

    private WifiManager.MulticastLock lock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        lock= manager.createMulticastLock("localWifi");
        lock.acquire();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        osv = findViewById(R.id.osv1);
        //osv.setDerictSize(1, 2);
        //osv.setSc(0, 0);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doServerThread();
            }
        });


        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doClientThread();
            }
        });
    }

    private volatile boolean mServerScaned;

    @SuppressWarnings("all")
    private void doServerThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: server start send");
                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);

                try {
                    MulticastSocket socket = new MulticastSocket(9999);
                    socket.setTimeToLive(32);
                    socket.joinGroup(InetAddress.getByName(multiIp));
                    byte[] start = "scan".getBytes();
                    DatagramPacket packetStart = new DatagramPacket(start, start.length);
                    packetStart.setData(start);
                    packetStart.setPort(9999);
                    packetStart.setAddress(InetAddress.getByName(multiIp));
                    while (true) {
                        socket.send(packetStart);
                        Log.d(TAG, "run: server send done!!!");
                        if (mServerScaned) {
                            break;
                        }
                        Thread.sleep(1000);
                    }
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: server reveived");
                try {
                    DatagramSocket responseSocket = new DatagramSocket(8888);
                    byte[] buf = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    while (true) {
                        responseSocket.receive(packet);
                        String msg = new String(packet.getData()).trim();
                        Log.d(TAG, "run: server received msg="+msg);
                        if ("hello".equals(msg)) {
                            mServerScaned = true;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    osv.setDerictSize(1, 2);
                                    osv.setSc(0, 1);
                                    osv.startScroll();
                                }
                            });
                            break;
                        }
                        responseSocket.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @SuppressWarnings("all")
    private void doClientThread() {
        final String brodAddr = BroadcastAddr.getBroadcastAddr();
        if (brodAddr == null) {
            Log.e(TAG, "doClientThread: error broad");
            return;
        }



        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                try {
                    MulticastSocket socket = new MulticastSocket(9999);
                    socket.joinGroup(InetAddress.getByName(multiIp));

                    while (true) {
                        socket.receive(packet);
                        String msg = new String(packet.getData()).trim();
                        if ("scan".equals(msg)) {
                            String backData = "hello";
                            byte[] data = backData.getBytes();
                            System.out.println("Send " + backData + " to " + packet.getSocketAddress());
                            DatagramPacket backPacket = new DatagramPacket(data, 0, data.length);
                            backPacket.setAddress(packet.getAddress());
                            backPacket.setPort(8888);
                            socket.send(backPacket);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    osv.setDerictSize(1, 2);
                                    osv.setSc(0, 0);
                                    osv.startScroll();
                                }
                            });
                            break;
                        } else {
                            Log.w(TAG, "run:client receive err msg format: "+msg);
                        }
                    }
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lock.release();
    }
}
