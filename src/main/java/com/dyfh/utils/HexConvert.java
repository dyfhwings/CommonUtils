package com.dyfh.utils;

/**
 * mac地址10进制16进制互换
 * @author yanhuan
 *
 */
public class HexConvert
{
    /**
     * mac转换到10进制
     * @param mac
     * @return
     */
    public static String setMac(String mac)
    {
        String id = "";
        long rs = Long.parseLong(mac, 16);
        id = rs + id;
        return id;

    }

    /**
     * mac转换到16进制
     * @param mac
     * @return
     */
    public static String getMac(String mac)
    {
        String id = "";
        Long r = new Long(mac);
        String rs = Long.toHexString(r);
        if (rs.length() < 12)
        {
            rs = "000000000000" + rs;
            rs = rs.substring(rs.length() - 12, rs.length());
        }
        id = rs.toUpperCase();
        return id;
    }

}
