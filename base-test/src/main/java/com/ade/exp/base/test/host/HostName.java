package com.ade.exp.base.test.host;

import java.net.InetAddress;

/**
 *
 * Created by liyang on 2017/4/14.
 */
public class HostName {

    public static void main(String[] args) {
        System.out.println(System.getenv("COMPUTERNAME"));
        try {
            System.out.println((InetAddress.getLocalHost()).getHostName());
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

}
