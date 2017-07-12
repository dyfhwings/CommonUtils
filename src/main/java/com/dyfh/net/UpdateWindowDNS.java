package com.dyfh.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateWindowDNS
{
    private final static String EOL = System.getProperty("line.separator");
    //校验IP地址的正则表达式
    private final static Pattern pattern = Pattern.compile(
            "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])"
                    + "\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");

    public static void autoDNS() throws Exception
    {
        //完整命令行 netsh interface ip set dns name="本地连接" source=dhcp
        List<String> cmd = new ArrayList<String>();
        cmd.add("cmd");
        cmd.add("/c");
        cmd.add("netsh");
        cmd.add("interface");
        cmd.add("ip");
        cmd.add("set");
        cmd.add("dns");
        cmd.add("name=\"本地连接\"");
        cmd.add("source=dhcp");

        ProcessBuilder pb = new ProcessBuilder();
        pb.command(cmd);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        int w = process.waitFor();
        if (w == 0)// 0代表正常退出
        {
        }
        else
        {
            throw new RuntimeException("errorResult=" + w);
        }
    }

    public static void setWindowDNS(String[] dnsArray) throws Exception
    {
        if (dnsArray == null || dnsArray.length == 0)
        {
            throw new RuntimeException("设置DNS异常，未指定新DNS地址。");
        }
        autoDNS();
        ProcessBuilder pb = new ProcessBuilder();
        List<String> cmd;
        for (int i = 0; i < dnsArray.length; i++)
        {
            Matcher matcher = pattern.matcher(dnsArray[i]);
            if (matcher.matches())
            {
                if (i == 0)
                {
                    cmd = buildPrimaryDNSCmd(dnsArray[i]);
                }
                else
                {
                    cmd = buildAddDNSCmd(dnsArray[i], i + 1);

                }
                pb.command(cmd);
                pb.redirectErrorStream(true);
                Process process = pb.start();
                int w = process.waitFor();
                if (w != 0)
                {
                    throw new RuntimeException("设置DNS异常。 errorResult=" + w);
                }
            }
            else
            {
                throw new RuntimeException("设置DNS异常。非法的DNS地址。");
            }

        }

    }

    private static List<String> buildPrimaryDNSCmd(String dns) throws Exception
    {
        //完整命令行 netsh interface ip set dns name="本地连接" source=static addr=192.168.5.200 register=primary
        List<String> cmd = new ArrayList<String>();
        cmd.add("cmd");
        cmd.add("/c");
        cmd.add("netsh");
        cmd.add("interface");
        cmd.add("ip");
        cmd.add("set");
        cmd.add("dns");
        cmd.add("name=\"本地连接\"");
        cmd.add("source=static");
        cmd.add("addr=" + dns);
        cmd.add("register=primary");

        return cmd;
    }

    private static List<String> buildAddDNSCmd(String dns, int index) throws Exception
    {
        //完整命令行 netsh interface ip set dns name="本地连接" source=static addr=192.168.5.200 register=primary
        List<String> cmd = new ArrayList<String>();
        cmd.add("cmd");
        cmd.add("/c");
        cmd.add("netsh");
        cmd.add("interface");
        cmd.add("ip");
        cmd.add("add");
        cmd.add("dns");
        cmd.add("name=\"本地连接\"");
        cmd.add("addr=" + dns);
        cmd.add("index=" + index);
        return cmd;
    }

    public static String flushWindowDNS() throws Exception
    {
        StringBuffer strB = new StringBuffer();
        List<String> cmd = new ArrayList<String>();
        cmd.add("cmd");
        cmd.add("/c");
        cmd.add("ipconfig /flushdns");

        ProcessBuilder pb = new ProcessBuilder();
        pb.command(cmd);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        int w = process.waitFor();
        if (w == 0)// 0代表正常退出
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String str;

            while ((str = in.readLine()) != null)
            {
                strB.append(str).append(EOL);
            }
            in.close();
        }
        else
        {
            throw new RuntimeException("errorResult=" + w);
        }
        //转换为一行输出
        return strB.toString().replaceAll("\\s*", "");
    }
}
