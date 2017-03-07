package com.dyfh.utils;

import java.net.InetAddress;

import com.dyfh.log.CommonLog;

public class HostUtils
{
    /**本机的InetAddress**/
    private static InetAddress inetAddress = null;

    /**本机IP地址，例如：192.168.9.131**/
    private static String hostAddress = null;

    /**本机名称，例如：feizhigao-PC**/
    private static String hostName = null;

    static
    {
        try
        {
            inetAddress = CommonUtils.getLocalAddress();
            hostAddress = inetAddress.getHostAddress();
            hostName = inetAddress.getHostName();
        }
        catch (Exception e)
        {
            CommonLog.error("获取本地IP异常", e);
        }
    }

    public static void init(InetAddress address)
    {
        inetAddress = address;
        try
        {
            CommonLog.info("本地IP地址修正前：hostAddress=" + hostAddress + "，hostName=" + hostName);
            hostAddress = inetAddress.getHostAddress();
            hostName = inetAddress.getHostName();
            CommonLog.info("本地IP地址修正后：hostAddress=" + hostAddress + "，hostName=" + hostName);
        }
        catch (Exception e)
        {
            CommonLog.error("获取本地IP异常", e);
        }
    }

    /**
     * 获取本机的inetAddress
     * @return inetAddress
     */
    public static InetAddress getInetAddress()
    {
        return inetAddress;
    }

    /**
     * 获取本机IP地址，例如：192.168.9.131
     * @return hostAddress
     */
    public static String getHostAddress()
    {
        return hostAddress;
    }

    /**
     * 获取本机名称，例如：feizhigao-PC
     * @return hostName
     */
    public static String getHostName()
    {
        return hostName;
    }
}
