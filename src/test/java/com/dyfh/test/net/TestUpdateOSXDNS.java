package com.dyfh.test.net;

import com.dyfh.net.UpdateOSXDNS;

public class TestUpdateOSXDNS
{

    public static void main(String[] args) throws Exception
    {
        String[] dnsarray = new String[] { "192.168.5.200", "8.8.8.8", "4.4.4.4" };
        System.out.println(UpdateOSXDNS.setDNS(dnsarray, "Wi-Fi"));
    }
}
