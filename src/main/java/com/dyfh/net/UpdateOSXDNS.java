package com.dyfh.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * OS X切换DNS方法，依赖于shell脚本
 * @author tanjixiang
 *
 */
public class UpdateOSXDNS
{
    private final static String EOL = System.getProperty("line.separator");
    //校验IP地址的正则表达式
    private final static Pattern pattern = Pattern.compile(
            "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])"
                    + "\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");

    public static String setDNS(String[] dnsArray, String networkName) throws Exception
    {
        if (dnsArray == null || dnsArray.length == 0)
        {
            throw new RuntimeException("设置DNS异常，未指定新DNS地址。");
        }

        ProcessBuilder pb = new ProcessBuilder();
        List<String> cmd;
        String dns = "";

        cmd = buildSetDNSCmd(dnsArray, networkName);
        pb.command(cmd);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        int w = process.waitFor();

        if (w == 0)// 0代表正常退出
        {
            StringBuffer strB = new StringBuffer();
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String str;

            while ((str = in.readLine()) != null)
            {
                strB.append(str).append(EOL);
            }
            in.close();
            //转换为一行输出
            return strB.toString();
        }
        else
        {
            throw new RuntimeException("设置DNS异常。 errorResult=" + w);
        }
    }

    private static List<String> buildSetDNSCmd(String[] dnsArray, String networkName) throws Exception
    {
        List<String> cmd = new ArrayList<String>();
        cmd.add("/Users/tanjixiang/shell/updatedns.sh");
        cmd.add(networkName);
        cmd.add("cctjx2");
        for (int i = 0; i < dnsArray.length; i++)
        {
            Matcher matcher = pattern.matcher(dnsArray[i]);
            if (matcher.matches())
            {
                cmd.add(dnsArray[i]);
            }
            else
            {
                throw new RuntimeException("设置DNS异常。非法的DNS地址。");
            }

        }
        return cmd;
    }

}
