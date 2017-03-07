package com.dyfh.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.dyfh.log.CommonLog;

/**
 * 公共工具类
 * @author tanjx
 * @version 1.0
 */
public class PubUtils
{

    /**
     * 获取当前路径
     * @return 应用当前路径
     */
    public static String getRealPath()
    {
        String realPath = PubUtils.class.getClassLoader().getResource("").getFile();
        java.io.File file = new java.io.File(realPath);
        realPath = file.getAbsolutePath();
        try
        {
            realPath = java.net.URLDecoder.decode(realPath, "utf-8");
            int index = realPath.lastIndexOf("/WEB-INF");
            if (index > 0)
            {
                realPath = realPath.substring(0, realPath.lastIndexOf("/WEB-INF"));
            }
        }
        catch (Exception e)
        {
            CommonLog.error("获取当前路径发生异常. cause by=" + e.getMessage(), e);
        }
        return realPath;

    }

    /**
     * 获取当前路径的应用目录名称
     * @return 应用名称
     */
    public static String getProjectName()
    {
        String projectDir = getRealPath();
        return projectDir.substring(projectDir.lastIndexOf(File.separator) + 1, projectDir.length());
    }

    /**
     * 对象序列化
     * @param object
     * @return
     * @throws PersistException
     */
    public static byte[] serialize(Object object) throws Exception
    {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try
        {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        }
        catch (Exception e)
        {
            CommonLog.error("对象序列化异常. cause by=" + e.getMessage(), e);
            throw new Exception("对象序列化异常. cause by=" + e.getMessage(), e);
        }
        finally
        {
            if (oos != null)
            {
                try
                {
                    oos.close();
                }
                catch (IOException e)
                {
                    CommonLog.error("序列化关闭对象输出流异常. cause by=" + e.getMessage(), e);
                }
            }
            if (baos != null)
            {
                try
                {
                    baos.close();
                }
                catch (IOException e)
                {
                    CommonLog.error("序列化关闭字节数组输出流异常. cause by=" + e.getMessage(), e);
                }
            }
        }
    }

    /**
     * byte数组反序列化为对象
     * @param bytes
     * @return
     * @throws Exception
     * @throws PersistException
     */
    public static Object deserialize(byte[] bytes) throws Exception
    {
        ObjectInputStream ois = null;
        ByteArrayInputStream bais = null;
        try
        {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        }
        catch (Exception e)
        {
            CommonLog.error("字节数组反序列化为对象时异常. cause by=" + e.getMessage(), e);
            throw new Exception("字节数组反序列化为对象时异常. cause by=" + e.getMessage(), e);
        }
        finally
        {
            if (ois != null)
            {
                try
                {
                    ois.close();
                }
                catch (IOException e)
                {
                    CommonLog.error("反序列化关闭对象输入流异常. cause by=" + e.getMessage(), e);
                }
            }
            if (bais != null)
            {
                try
                {
                    bais.close();
                }
                catch (IOException e)
                {
                    CommonLog.error("反序列化关闭字节数组输入流异常. cause by=" + e.getMessage(), e);
                }
            }
        }
    }
}
