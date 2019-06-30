package com.lb.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

public class BroadcastAddr {
    private String netmask = "";
    private String netaddr = "";
    private String netbroadcastaddr = "";

    public static String getBroadcastAddr() {
        BroadcastAddr info = new BroadcastAddr();
        getLocalIPList(info);
        info.execCalc();
        return info.getNetbroadcastaddr();
    }


    public static void getLocalIPList(BroadcastAddr addr) {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    ip = inetAddress.getHostAddress();
                    if (inetAddress != null && inetAddress instanceof Inet4Address && !"127.0.0.1".equals(ip)) { // IPV4
                        final NetworkInterface ni = NetworkInterface.getByInetAddress(inetAddress);


                        List<InterfaceAddress> list = ni.getInterfaceAddresses();// 获取此网络接口的全部或部分
                        // InterfaceAddresses
                        // 所组成的列表
                        if (list != null && list.size() > 0) {

                            for (InterfaceAddress address : list) {
                                if (address.getAddress() instanceof Inet4Address) {
                                    int mask = address.getNetworkPrefixLength(); // 子网掩码的二进制1的个数
                                    StringBuilder maskStr = new StringBuilder();
                                    int[] maskIp = new int[4];
                                    for (int i = 0; i < maskIp.length; i++) {
                                        maskIp[i] = (mask >= 8) ? 255 : (mask > 0 ? (mask & 0xff) : 0);
                                        mask -= 8;
                                        maskStr.append(maskIp[i]);
                                        if (i < maskIp.length - 1) {
                                            maskStr.append(".");
                                        }
                                    }
                                    addr.setNetaddr(ip);
                                    addr.setNetmask(maskStr.toString());
                                    break;
                                }
                            }
                        }

                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public String getNetaddr() {
        return netaddr;
    }

    public void setNetaddr(String netaddr) {
        this.netaddr = netaddr;
    }

    public String getNetbroadcastaddr() {
        return netbroadcastaddr;
    }

    public void setNetbroadcastaddr(String netbroadcastaddr) {
        this.netbroadcastaddr = netbroadcastaddr;
    }

    public String getNetmask() {
        return netmask;
    }

    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }

    public void execCalc() {
        String[] tm = this.getNetmask().split("\\.");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tm.length; i++) {
            tm[i] = String.valueOf(~(Integer.parseInt(tm[i])));
        }
        String[] tm2 = this.getNetaddr().split("\\.");
        for (int i = 0; i < tm.length; i++) {
            tm[i] = String.valueOf((Integer.parseInt(tm2[i]))
                    | (Integer.parseInt(tm[i])));
        }
        for (int i = 0; i < tm.length; i++) {
            sb.append(intTOstr(tm[i]));
            sb.append(".");
        }
        // sb.delete(sb.length()-1,sb.length());
        sb.deleteCharAt(sb.length() - 1);
        this.netbroadcastaddr = parseIp(sb.toString());
    }

    private String intTOstr(int num) {
        String tm = "";
        tm = Integer.toBinaryString(num);
        int c = 8 - tm.length();
        // 如果二进制数据少于8位,在前面补零.
        for (int i = 0; i < c; i++) {
            tm = "0" + tm;
        }
        // 1111 1111 1111 1111 1111 1111 1101 1110
        // 如果小于零,则只取最后的8位.
        if (c < 0)
            tm = tm.substring(24, 32);
        return tm;
    }

    private String intTOstr(String num) {
        return intTOstr(Integer.parseInt(num));
    }

    private String parseIp(String fbg) {
        String[] tm = fbg.split("\\.");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tm.length; i++) {
            sb.append(strToint(tm[i]));
            sb.append(".");
        }
        // sb.delete(sb.length()-1,1);
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 把二进制数转换为十进制.
     *
     * @param str
     * @return
     */
    private int strToint(String str) {
        int total = 0;
        int top = str.length();
        for (int i = 0; i < str.length(); i++) {
            // System.out.println(str.charAt(i)+str.substring(i,i+1));
            String h = String.valueOf(str.charAt(i));
            // System.out.println(h+":"+top+":"+total);
            top--;
            total += ((int) Math.pow(2, top)) * (Integer.parseInt(h));
        }
        return total;
    }
}