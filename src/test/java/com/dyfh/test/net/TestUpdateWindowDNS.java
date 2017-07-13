package com.dyfh.test.net;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.dyfh.net.UpdateWindowDNS;

public class TestUpdateWindowDNS
{
    @Test
    public void testUpdateWindowDNS() throws Exception
    {
        String[] dnsArray = new String[] { "192.168.5.200", "8.8.8.8" };
        UpdateWindowDNS.setWindowDNS(dnsArray, "本地连接");
        String flushResult = UpdateWindowDNS.flushWindowDNS();
        System.out.println(flushResult);
        assertEquals("执行成功", "WindowsIP配置已成功刷新DNS解析缓存。", flushResult);
    }

    public static void main(String[] args) throws Exception
    {
        String[] dnsArray = new String[] { "192.168.5.200", "8.8.8.8" };
        UpdateWindowDNS.setWindowDNS(dnsArray, "本地连接");
        System.out.println(UpdateWindowDNS.flushWindowDNS());
    }
}
