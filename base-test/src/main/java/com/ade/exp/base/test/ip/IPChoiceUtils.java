package com.ade.exp.base.test.ip;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IP选择工具，用于在本机多个IP中，选择一个提供给ESB-Client进行访问的IP
 * Created by liyang on 2017/4/6.
 */
public class IPChoiceUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(IPChoiceUtils.class);

    /** 内网IP范围 */
    private static final String LOCALIP_RANGES[] = new String[] {
        "10.0.0.0-10.255.255.255",
        "172.16.0.0-172.131.255.255",
        "192.168.0.0-192.168.255.255"
    };

    /** A、B、C类IP范围，不包括广播IP，但是包括内网IP范围 */
    private static final String REMOTEIP_RANGES[] = new String[]{
        "1.0.0.1-126.255.255.254",
        "128.0.0.1-191.255.255.254",
        "192.0.0.1-223.255.255.254"
    };

    /**
     * 该方法用于在本机多个IP中，选择一个提供给ESB-Client进行访问的IP
     *
     * @return
     */
    public static String getSourceIp() {
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        // 首先拿到本机可用的IPV4地址。
        List<String> allIPs = new ArrayList<String>();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();

            // 注意，一个网络设备可能绑定了多个IP，也可能一个IP都没有
            // 还有可能是IPV6的格式
            // 就算是IPV4的格式，也可能是loop形式的ip
            Enumeration<InetAddress> inetAddresss = networkInterface.getInetAddresses();
            while (inetAddresss.hasMoreElements()) {
                InetAddress inetAddress = inetAddresss.nextElement();
                if (!(inetAddress instanceof Inet4Address)
                        || inetAddress.isLoopbackAddress()
                        || inetAddress.isMulticastAddress()) {
                    continue;
                }
                String ip = inetAddress.getHostAddress();
                allIPs.add(ip);
            }
        }

        // 开始从可用的IP中优先选择一个IP
        // 选择规则为：首先选择一个低位的内网IP。如果内网IP无效，则依次优先选择A类、B类、C类IP
        // 开发人员可以视自己的需求对选择规则进行更改
        for (String sourceIP : allIPs) {
            for (String localip_range : LOCALIP_RANGES) {
                if (ipExistsInRange(sourceIP, localip_range)) {
                    LOGGER.info("======选择到IP:" + sourceIP);
                    return sourceIP;
                }
            }
        }
        for (String sourceIP : allIPs) {
            for (String remoteip_range : REMOTEIP_RANGES) {
                if (ipExistsInRange(sourceIP, remoteip_range)) {
                    LOGGER.info("======选择到IP:" + sourceIP);
                    return sourceIP;
                }
            }
        }
        return null;
    }

    /**
     * 判断给定的IP是否在一个范围内
     *
     * @param ip
     * @param ipSection
     * @return 如果是在一个范围内，则返回true；其它情况返回false
     */
    private static boolean ipExistsInRange(String ip, String ipSection) {
        String[] ipSections = ipSection.split("\\-");
        String beginIP = ipSections[0];
        String endIP = ipSections[1];
        return getIp2long(beginIP) <= getIp2long(ip) && getIp2long(ip) <= getIp2long(endIP);
    }

    /**
     * 这个私有方法将字符串形式的ip，转换为一个对应的长整型。
     * 为什么？请参见IP结构的基础知识
     * 以便进行比较
     *
     * @param ip
     * @return
     */
    private static long getIp2long(String ip) {
        String[] ips = ip.split("\\.");
        long ip2long = 0L;
        for (int i = 0; i < 4; ++i) {
            ip2long = ip2long << 8 | Integer.parseInt(ips[i]);
        }
        return ip2long;
    }

    public static void main(String[] args) {
        System.out.println(getSourceIp());
    }

}
