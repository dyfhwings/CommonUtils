package com.dyfh.utils;

/**
 * 处理int型及long型与byte数组的转换
 * @author tanjx
 * @version 1.0
 */

public class NumberUtils
{
    /**
     * 32位的int整数转换为4字节的byte数组
     *
     * @param i
     *            整数
     * @return byte数组
     */
    public static byte[] intToByte4(int i)
    {
        byte[] targets = new byte[4];
        targets[3] = (byte) (i & 0xFF); //取最低8位放到3下标
        targets[2] = (byte) (i >> 8 & 0xFF); //取次低8位放到2下标
        targets[1] = (byte) (i >> 16 & 0xFF); //取次高8为放到1下标
        targets[0] = (byte) (i >> 24 & 0xFF); //取最高8位放到0下标，&0xff将byte值无差异转成int,避免Java自动类型提升后,会保留高位的符号位
        return targets;
    }

    /**
     * byte数组转换为int整数
     *
     * @param bytes
     *            byte数组
     * @param off
     *            开始位置
     * @return int整数
     */
    public static int byte4ToInt(byte[] bytes, int off)
    {
        int b0 = bytes[off] & 0xFF;
        int b1 = bytes[off + 1] & 0xFF;
        int b2 = bytes[off + 2] & 0xFF;
        int b3 = bytes[off + 3] & 0xFF;
        return (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
    }

    /**
     * long整数转换为8字节的byte数组
     *
     * @param lo
     *            long整数
     * @return byte数组
     */
    public static byte[] longToByte8(long lo)
    {
        byte[] targets = new byte[8];
        for (int i = 0; i < 8; i++)
        {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte) ((lo >>> offset) & 0xFF);
        }
        return targets;
    }

    /**
     * 8字节的byte数组转成long
     *
     * @param bytes
     *            byte数组
     * @return long
     */
    public static long byte8ToLong(byte[] b)
    {
        long s = 0;
        long s0 = b[0] & 0xff;// 最低位
        long s1 = b[1] & 0xff;
        long s2 = b[2] & 0xff;
        long s3 = b[3] & 0xff;
        long s4 = b[4] & 0xff;// 最低位
        long s5 = b[5] & 0xff;
        long s6 = b[6] & 0xff;
        long s7 = b[7] & 0xff;

        // s7不变
        s6 <<= 8;
        s5 <<= 16;
        s4 <<= 24;
        s3 <<= 8 * 4;
        s2 <<= 8 * 5;
        s1 <<= 8 * 6;
        s0 <<= 8 * 7;
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;

        return s;
    }

    /**
     * short整数转换为2字节的byte数组
     *
     * @param s
     *            short整数
     * @return byte数组
     */
    public static byte[] unsignedShortToByte2(int s)
    {
        byte[] targets = new byte[2];
        targets[0] = (byte) (s >> 8 & 0xFF);
        targets[1] = (byte) (s & 0xFF);
        return targets;
    }

    /**
     * byte数组转换为无符号short整数
     *
     * @param bytes
     *            byte数组
     * @return short整数
     */
    public static int byte2ToUnsignedShort(byte[] bytes)
    {
        return byte2ToUnsignedShort(bytes, 0);
    }

    /**
     * byte数组转换为无符号short整数
     *
     * @param bytes
     *            byte数组
     * @param off
     *            开始位置
     * @return short整数
     */
    public static int byte2ToUnsignedShort(byte[] bytes, int off)
    {
        int high = bytes[off];
        int low = bytes[off + 1];
        return (high << 8 & 0xFF00) | (low & 0xFF);
    }
}