package com.dyfh.utils;

import java.util.Random;

public class RandomUtils
{
    private static final String RANDOM_CODE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 生成4位随机编码，可支撑36^4=1679616
     * @return
     */
    public static String getRandomShopCode()
    {
        Random random = new Random();
        char[] array = new char[4];
        for (int i = 0; i < 4; i++)
        {
            array[i] = RANDOM_CODE.charAt(random.nextInt(36));
        }
        return new String(array);
    }

}
