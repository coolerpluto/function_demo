package com.fan.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class CdMd5Calculate {

    private static String[] cdFileSuffix = new String[]{"rpm"};

    private static String md5InFileProvided = " ";

    private static String cdDisk = "d:\\a";

    //是否下潜查找指定后缀文件，默认下潜
    private static Boolean isDive = true;

    private static Map<String, String> cdFilePahAndMd5StrMap = new HashMap<>();

    public static void main(String[] args) {
        doIt(cdDisk,cdFileSuffix,cdFilePahAndMd5StrMap);
    }

    public static void doIt(String cdDisk, String[] cdFileSuffix, Map<String, String> cdFilePahAndMd5StrMap){
        Boolean cdShouldIn = true;
        Boolean cdShouldOut = true;
        while (true){
            File file = new File(cdDisk);
            if (file.isDirectory()){
                if (cdShouldIn){
                    System.out.println("=====================CD盘已放入=====================");
                    Map<String, String> filePathWithAllSuffix = getFilePathWithAllSuffix(cdFileSuffix, cdDisk, cdFilePahAndMd5StrMap,isDive);
                    for (Map.Entry<String, String> entry : filePathWithAllSuffix.entrySet()){
                        String md5 = calculateMd5(entry.getKey());
                        String md5Provided = getMd5InMd5SuffixFile(entry.getValue());
                        if (!md5.equals(md5Provided)){
                            System.out.println("此文件md5不一致"+entry.getKey());
                        }else {
                            System.out.println("此文件md5一致"+entry.getKey());
                        }
                    }
                    cdShouldIn = false;
                    cdShouldOut = true;
                }
            }
            if (!file.isDirectory()){
                if (cdShouldOut){
                    cdShouldIn = true;
                    cdShouldOut = false;
                    System.out.println("=====================CD盘已拿出，请放入CD盘=====================");
                }
            }
        }
    }

    public static Map<String, String> getFilePathWithSingSuffix(String suffix, String dirPath, Map<String, String> map, Boolean isDive){
        File dir = new File(dirPath);
        if (dir.isDirectory()){
            File[] sonFile = dir.listFiles();
            for (File file : sonFile) {
                if (file.isDirectory() && isDive){
                    getFilePathWithSingSuffix(suffix,file.getAbsolutePath(),map, isDive);
                }
                String fileSuffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                if (fileSuffix.equals(suffix)){
                    map.put(file.getAbsolutePath(), file.getAbsolutePath()+".md5");
                }
            }
        }
        return map;
    }

    public static Map<String, String> getFilePathWithAllSuffix(String[] allSuffix, String dirPath, Map<String, String > map, Boolean isDive){
        for (String suffix : allSuffix) {
            Map<String, String> filePathWithSingSuffix = getFilePathWithSingSuffix(suffix, dirPath, map, isDive);
            map.putAll(filePathWithSingSuffix);
        }
        return map;
    }

    public static String getMd5InMd5SuffixFile(String md5SuffixFilePath) {
        String fileContent = "";
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(new File(md5SuffixFilePath));
            bufferedReader = new BufferedReader(fileReader);
            fileContent = bufferedReader.readLine();
            bufferedReader.close();
        } catch(IOException ex) {
            System.out.println("Error");
        }
        int index = -1;
        try {
            index = fileContent.indexOf(md5InFileProvided);
        }catch (NullPointerException exception){
            System.out.println("提供的md5文件间隔和md5InFileProvided不对");
            System.exit(1);
        }
        if (index > -1) {
            return fileContent.substring(0, index);
        } else {
            return fileContent;
        }
    }

    public static String calculateMd5(String fileAbsolutePath){
        try {
            // 打开文件输入流
            InputStream inputStream = new FileInputStream(fileAbsolutePath);
            // 创建MD5哈希对象
            MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            // 创建一个缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            // 创建一个文件通道
            FileChannel channel = ((FileInputStream)inputStream).getChannel();
            // 读取文件内容并更新哈希对象
            while (channel.read(buffer) != -1) {
                buffer.flip();
                md5Digest.update(buffer);
                buffer.clear();
            }
            // 计算哈希值
            byte[] md5Bytes = md5Digest.digest();
            // 将哈希值转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : md5Bytes) {
                sb.append(String.format("%02x", b));
            }
            // 打印计算出的MD5哈希值
            return sb.toString();
        } catch (Exception ex) {
            System.out.println("Error calculating MD5 hash for file named '" + fileAbsolutePath + "'");
            return null;
        }
    }
}
