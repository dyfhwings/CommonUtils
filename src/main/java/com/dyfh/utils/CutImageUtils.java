package com.dyfh.utils;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class CutImageUtils
{
    public static void cutJPG(InputStream input, OutputStream out, int x,
            int y, int width, int height) throws IOException
    {
        ImageInputStream imageStream = null;
        try
        {
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");
            ImageReader reader = readers.next();
            imageStream = ImageIO.createImageInputStream(input);
            reader.setInput(imageStream, true);
            ImageReadParam param = reader.getDefaultReadParam();

            System.out.println(reader.getWidth(0));
            System.out.println(reader.getHeight(0));
            Rectangle rect = new Rectangle(x, y, width, height);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            ImageIO.write(bi, "jpg", out);
        }
        finally
        {
            imageStream.close();
        }
    }

    public static void cutPNG(InputStream input, OutputStream out, int x,
            int y, int width, int height) throws IOException
    {
        ImageInputStream imageStream = null;
        try
        {
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("png");
            ImageReader reader = readers.next();
            imageStream = ImageIO.createImageInputStream(input);
            reader.setInput(imageStream, true);
            ImageReadParam param = reader.getDefaultReadParam();

            System.out.println(reader.getWidth(0));
            System.out.println(reader.getHeight(0));

            Rectangle rect = new Rectangle(x, y, width, height);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            ImageIO.write(bi, "png", out);
        }
        finally
        {
            imageStream.close();
        }
    }

    public static void cutImage(InputStream input, OutputStream out, String type, int x,
            int y, int width, int height) throws IOException
    {
        ImageInputStream imageStream = null;
        try
        {
            String imageType = (null == type || "".equals(type)) ? "jpg" : type;
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(imageType);
            ImageReader reader = readers.next();
            imageStream = ImageIO.createImageInputStream(input);
            reader.setInput(imageStream, true);
            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(x, y, width, height);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            ImageIO.write(bi, imageType, out);
        }
        finally
        {
            imageStream.close();
        }
    }

    public static void main(String[] args) throws Exception
    {
        long s = System.currentTimeMillis();
        // 以当前路径来创建Path对象
        Path path = Paths.get("");
        // 获取path对应的绝对路径。
        String absolutePath = path.toAbsolutePath().toString();
        System.out.println("程序运行路径：" + absolutePath);

        // 获取当前目录所有jpg文件名称列表
        Stream<Path> stream = Files.list(path);
        List<Path> paths = stream.filter(new JpgPredicate()).collect(Collectors.toList());
        stream.close();
        Path cutPath = Paths.get(absolutePath + File.separator + "cut");
        System.out.println("cutPath=" + cutPath);
        try
        {
            Files.createDirectory(cutPath);
        }
        catch (FileAlreadyExistsException e)
        {
            // the directory already exists.
        }
        catch (IOException e)
        {
            //something else went wrong
            e.printStackTrace();
        }

        //  遍历截取
        for (Path p : paths)
        {
            String fileName = p.getFileName().toString();
            System.out.println("发现符合条件的文件=" + fileName);
            String newFileName = cutPath.toString() + File.separator + "cut_" + fileName;
            CutImageUtils.cutJPG(new FileInputStream(fileName),
                    new FileOutputStream(newFileName), 469, 770, 605, 58);
            System.out.println("截取完成。新文件名=" + newFileName);
        }
        System.out.println("处理图片数量=" + paths.size() + ", 用时=" + (System.currentTimeMillis() - s));
    }

    private static class JpgPredicate implements Predicate<Path>
    {
        @Override
        public boolean test(Path path)
        {
            String fileName = path.getFileName().toString();
            // 获取访问基本属性的BasicFileAttributeView
            BasicFileAttributeView basicView = Files.getFileAttributeView(path, BasicFileAttributeView.class);
            try
            {
                if (basicView.readAttributes().isRegularFile())
                {
                    System.out.println("fileName=" + fileName);
                    if (fileName.substring(fileName.lastIndexOf(".") + 1).equalsIgnoreCase("jpg")
                            || fileName.substring(fileName.lastIndexOf(".") + 1).equalsIgnoreCase("jpeg"))
                    {
                        return true;
                    }
                }
            }
            catch (IOException e)
            {
                System.out.println("cause=" + e.getClass().getName() + "——" + e.getMessage());
            }
            return false;
        }

    }
}
