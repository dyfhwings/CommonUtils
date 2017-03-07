package com.dyfh.utils;

import java.math.BigInteger;
import java.util.regex.Pattern;

/**
 * 任意数字2到32 进制位之间相互转换
 * @author LH
 *
 */
public class MathHexUtils
{
    private static final String hex_reg = "^((-|\\+)?[a-zA-Z0-9]+)$";

    /**
     * 16进制转6进制(仅用于卡号转换)
     * @param sourceHex16  长度=8
     * @return 输出为14位
     * @throws Exception
     */
    public static String Hex16To6(String sourceHex16) throws Exception
    {
        if (sourceHex16 == null || sourceHex16.length() != 8)
        {
            throw new Exception("入参为空或非8位字符,sourceHex16=" + sourceHex16);
        }
        return formatNum(coverHex(sourceHex16, 16, 6), 14);
    }

    /**
     * 6进制转16进制(仅用于卡号转换)
     * @param sourceHex6 长度=14
     * @return 输出为8位
     * @throws Exception
     */
    public static String Hex6To16(String sourceHex6) throws Exception
    {
        if (sourceHex6 == null || sourceHex6.length() != 14)
        {
            throw new Exception("入参为空或非8位字符,sourceHex6=" + sourceHex6);
        }
        if (Long.parseLong(sourceHex6) > 1550104015503L)
        {
            throw new Exception("不支持大于1550104015503的6进制数,sourceHex6=" + sourceHex6);
        }
        return formatNum(coverHex(sourceHex6, 6, 16), 8);
    }

    /**
     * 
     * @param resource 转换前的数字
     * @param resourcerRadix 转换前的数字为几进制
     * @param targetRadix 转换后的数字为几进制
     * @return
     * @throws Exception 
     */
    public static String coverHex(String resource, int resourcerRadix, int targetRadix) throws Exception
    {
        String result = resource.startsWith("-") ? "-" : "";
        if (resourcerRadix < Character.MIN_RADIX || resourcerRadix > Character.MAX_RADIX || targetRadix < Character.MIN_RADIX || targetRadix > Character.MAX_RADIX)
        {
            throw new NumberFormatException("进制参数错误暂时只支持" + Character.MIN_RADIX + "到" + Character.MAX_RADIX + "的整数进制进制转换");
        }
        if (Pattern.compile(hex_reg).matcher(resource).find() == false)
        {
            throw new Exception(resource + "不是标准的数字,或修改REG的值");
        }
        try
        {
            result += (getHexResource10RadixToTarget(getHexResourceRadixTo10(resource.replaceFirst("-", "").replaceFirst("\\+", ""), resourcerRadix), targetRadix) + "").toUpperCase();
        }
        catch (Exception e)
        {
            throw new Exception("转换前的数据" + resource + "写法错误:" + e.getMessage());
        }
        return result;
    }

    /**
     * 转为10进制数字
     * @param resource 转换前的数字
     * @param resourcerRadix  转换前的数字为几进制
     * @return
     */
    private static BigInteger getHexResourceRadixTo10(String resource, int resourcerRadix)
    {
        return new BigInteger(resource, resourcerRadix);
    }

    /**
     * 10进制数字转为其他进制数字
     * @param resource 10进制数字
     * @param targetRadix 转换后的数字进制
     * @return
     */
    private static String getHexResource10RadixToTarget(BigInteger sourceH10, int targetRadix)
    {
        return getDivision(sourceH10, targetRadix, "");
    }

    /**
     * 取余数
     * @param sourceH10
     * @param targetRadix
     * @param result
     * @return
     */
    private static String getDivision(BigInteger sourceH10, int targetRadix, String result)
    {
        if (sourceH10.compareTo(new BigInteger(targetRadix + "")) == -1)
        {
            result += Character.forDigit(sourceH10.intValue(), targetRadix);
            return new StringBuffer(result).reverse().toString().trim();
        }
        else
        {
            BigInteger[] bigs = sourceH10.divideAndRemainder(new BigInteger(targetRadix + ""));
            int beginIndex = bigs[1].intValue();
            result += Character.forDigit(beginIndex, targetRadix);
            return getDivision(bigs[0], targetRadix, result);
        }
    }

    /**
     * 格式化字符串长度，前段补0
     * @param srt  字符串
     * @param length 要输出的字符串长度
     * @return
     * @throws Exception
     */
    private static String formatNum(String srt, int length) throws Exception
    {
        if (length - srt.length() < 0)
        {
            throw new Exception(srt + "转换后的位数" + length + "小于转换前的位数" + srt.length() + "，导致精度丢失！");
        }
        String lengthStr = "00000000000000000000000000000000";//共32位
        srt = lengthStr.substring(0, length - srt.length()) + srt;
        return srt.toUpperCase();
    }

    public static void main(String[] args) throws Exception
    {
        System.out.println(MathHexUtils.Hex6To16("00024033401223"));
        System.out.println(MathHexUtils.Hex16To6("09abcdef"));
        //        System.out.println("==========================================");
        //        String  str = "+AsdfFsd";
        //        System.out.println(str.length());
        //        System.out.println("6-16=\t"+h.coverHex(str,32,2).equals("1010111000110101111011111110001101"));
        //        System.out.println("6-16=\t"+h.coverHex(str,32,8).equals("127065737615"));
        //        System.out.println("6-16=\t"+h.coverHex(str,32,10).equals("11691081613"));
        //        System.out.println("6-16=\t"+h.coverHex(str,32,16).equals("2B8D7BF8D"));
        //        System.out.println("6-16=\t"+h.coverHex(str,32,24).equals("2D45L0GD"));
        //        try {
        //            System.out.println("16-6=\t"+h.coverHex(str,10,1));
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }
        //        try {
        //            System.out.println("16-6=\t"+h.coverHex(str,10,37));
        //        } catch (Exception e) {
        //            e.printStackTrace();     // TODO: handle exception
        //        }
        //        try {
        //            System.out.println("16-6=\t"+h.coverHex(str+"a",10,8));
        //        } catch (Exception e) {
        //            e.printStackTrace(); // TODO: handle exception
        //        }
        //        }
    }

}
