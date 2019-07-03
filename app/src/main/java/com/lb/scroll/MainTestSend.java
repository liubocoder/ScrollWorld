package com.lb.scroll;

import android.util.Log;

import com.lb.net.BroadcastAddr;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @author LiuBo
 * @date 2019-07-02
 */
public class MainTestSend {
    private static String multiIp = "239.0.0.255";
    public static void main(String[] args) throws Exception {
        testMulticastSend();
    }

    private static void testMulticastSend() throws Exception {
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
                if (true) {
                    break;
                }
                Thread.sleep(1000);
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testSend() throws Exception {
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        @SuppressWarnings("resource")
        DatagramSocket responseSocket = new DatagramSocket();
        while (true) {
            // Send a response packet to sender
            String backData = "xxxxxxxxxxxxxxx";
            byte[] data = backData.getBytes();
            packet.setData(data);
            packet.setAddress( InetAddress.getByName(BroadcastAddr.getBroadcastAddr()));
            packet.setPort(9999);
            System.out.println("Send " + backData + " to " + packet.getSocketAddress());
            responseSocket.send(packet);
        }
    }
}
