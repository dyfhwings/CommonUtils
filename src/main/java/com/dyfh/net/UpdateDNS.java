package com.dyfh.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UpdateDNS
{
    private final static String EOL = System.getProperty("line.separator");

    public static void main(String[] args) throws Exception
    {
        updateWidnowDNS("192.168.5.200");
        System.out.println(flushWindowDNS());
    }

    public static void updateWidnowDNS(String dns) throws Exception
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
