package com.dyfh.test.net;

import com.dyfh.net.UpdateWindowDNS;

public class TestUpdateWindowDNS
{
    public static void main(String[] args) throws Exception
    {
        String[] dnsArray = new String[] { "192.168.5.200", "8.8.8.8" };
        UpdateWindowDNS.setWindowDNS(dnsArray);
        System.out.println(UpdateWindowDNS.flushWindowDNS());
    }
}
