package com.lb.scroll;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MainTest {

    //组播224.0.0.0至239.255.255.255之间的IP地址。

    public static void main(String[] args)throws IOException {
        String multiIp = "239.0.0.255";
        int listenPort = 9999;
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, 0, buf.length);
        MulticastSocket socket = new MulticastSocket(listenPort);
        socket.joinGroup(InetAddress.getByName(multiIp));
        @SuppressWarnings("resource")
        DatagramSocket responseSocket = new DatagramSocket();
        System.out.println("Server started, Listen port: " + listenPort);
        while (true) {
            socket.receive(packet);
            String rcvd = "Received "
                    + new String(packet.getData(), 0, packet.getLength())
                    + " from address: " + packet.getSocketAddress();
            System.out.println(rcvd);

            // Send a response packet to sender
            String backData = "hello";
            byte[] data = backData.getBytes();
            System.out.println("Send " + backData + " to " + packet.getSocketAddress());
            DatagramPacket backPacket = new DatagramPacket(data, 0, data.length);
            backPacket.setAddress(packet.getAddress());
            backPacket.setPort(8888);
            responseSocket.send(backPacket);
        }

    }
}
