package com.dyfh.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class CommonUtils
{

    /**
     * 读取网页内容
     * @param str   网页地址
     * @return      网页内容
     * @throws java.io.IOException
     */
    public static String getHtmlCodeByURL(String str) throws java.io.IOException
    {
        URL url = new URL(str);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream in = urlConnection.getInputStream();
        byte[] data = new byte[in.available()];
        in.read(data);
        return new String(data);
    }

    /**
     * 获取项目物理路径
     * @return              项目路径
     * @throws Exception    未找到路径
     */
    public static String getProjectLocalPath() throws UnsupportedEncodingException
    {
        String path = CommonUtils.class.getClassLoader().getResource("").getFile();
        path = URLDecoder.decode(path, "UTF-8");
        return path.substring(0, path.lastIndexOf("/WEB-INF"));
    }

    /**
     * 获取配置文件，该配置文件必须放在“.../WEB-INF/classes/”文件夹下或者本实体类所在的文件夹下
     * @param filename  配置文件名称
     * @return          InputStream
     */
    public static InputStream getConfigFileInputStream(String filename)
    {
        try
        {
            return new FileInputStream(getProjectLocalPath() + "/WEB-INF/classes/" + filename);
        }
        catch (Exception e)
        {
            //解决在CS项目中没有classes文件夹的问题
            return CommonUtils.class.getResourceAsStream(filename);
        }
    }

    /**
     * 获取客户端IP
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        if (ip == null)
        {
            return "";
        }
        String[] str = ip.split(",");
        if (str == null || str.length == 0)
        {
            return "";
        }
        else if (str[0].length() > 15)
        {
            return str[0].substring(0, 15);
        }
        else
        {
            return str[0];
        }
    }

    /**
     * 获取本机IP
     */
    public static InetAddress getLocalAddress() throws SocketException
    {
        InetAddress inetAddress = null;
        for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements();)
        {
            NetworkInterface networkInterface = interfaces.nextElement();
            if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp())
            {
                continue;
            }
            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
            while (addresses.hasMoreElements())
            {
                inetAddress = addresses.nextElement();
                if (inetAddress.isSiteLocalAddress())
                {
                    return inetAddress;
                }
            }
        }
        return inetAddress;
    }

    /**
     * 获取运行应用的名称（test.jar/test.war）
     * @return
     */
    public static String getAppName()
    {
        String itemtype_war = "war";
        String itemtype_jar = "jar";
        String unknow = "unknow";
        String itemtype = null;
        String itemname = unknow;
        try
        {
            String realPath = CommonUtils.class.getClassLoader().getResource("").getFile();
            java.io.File file = new java.io.File(realPath);
            realPath = file.getAbsolutePath();

            int wit = realPath.indexOf(File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "WEB-INF" + File.separator);
            if (wit > 0)
            {
                itemtype = itemtype_war;
                int wist = realPath.substring(0, wit).lastIndexOf(File.separator);
                if (wist > -1)
                {
                    itemname = realPath.substring(wist + 1, wit);
                }
                return itemname + "." + itemtype;
            }

            int wi = realPath.indexOf(File.separator + "WEB-INF" + File.separator);
            if (wi > 0)
            {
                itemtype = itemtype_war;
                int wis = realPath.substring(0, wi).lastIndexOf(File.separator);
                if (wis > -1)
                {
                    itemname = realPath.substring(wis + 1, wi);
                }
                return itemname + "." + itemtype;
            }
            int ti = realPath.indexOf(File.separator + "target" + File.separator);
            if (ti > 0)
            {
                itemtype = itemtype_war;
                int tis = realPath.substring(0, ti).lastIndexOf(File.separator);
                if (tis > -1)
                {
                    itemname = realPath.substring(tis + 1, ti);
                }
                return itemname + "." + itemtype;
            }
            if (itemtype == null)
            {
                java.net.URL url = CommonUtils.class.getProtectionDomain().getCodeSource().getLocation();
                String filePath = java.net.URLDecoder.decode(url.getPath(), "utf-8");
                if (filePath.endsWith(".jar"))
                {
                    itemtype = itemtype_jar;
                    java.io.File f = new java.io.File(filePath);
                    String n = f.getName();
                    int lj = n.lastIndexOf(".jar");
                    if (lj > 0)
                    {
                        itemname = n.substring(0, lj);
                    }
                }
            }
        }
        catch (Exception e)
        {
            itemname = unknow;
        }
        return itemname + "." + itemtype;
    }
}
