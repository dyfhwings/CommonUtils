package com.dyfh.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ZipUtil
{

    /**
     * 压缩字符串
     * @param str   待压缩的字符串
     * @return      压缩后的字符串
     * @throws IOException
     */
    public static String compress(String str) throws IOException
    {
        if (str == null || str.length() == 0)
        {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        return out.toString("ISO-8859-1");
    }

    /**
     * 解压缩字符串
     * @param str   待解压的字符串
     * @return      解压后的字符串
     * @throws IOException
     */
    public static String uncompress(String str) throws IOException
    {
        if (str == null || str.length() == 0)
        {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0)
        {
            out.write(buffer, 0, n);
        }
        // toString()使用平台默认编码，也可以显式的指定如toString("GBK")
        return out.toString();
    }
}
