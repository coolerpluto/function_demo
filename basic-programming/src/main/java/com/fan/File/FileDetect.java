package com.fan.File;

import java.io.File;

public class FileDetect {

    public static void main(String[] args) {
        FileDetect2(new File("D:\\a"),0);
    }


    public static void FileDetect1(File file, int level){
        File[] files = file.listFiles();
        int currentLevel = level;
        for (File sonFile : files) {
            for (int i = 0; i < currentLevel; i++){
                System.out.print(" ");
            }
            System.out.println(sonFile.getName());
            if (sonFile.isDirectory()){
                FileDetect1(sonFile, ++level);
            }
        }
    }


    public static void FileDetect2(File file, int level){
        int currentLevel = level;
        for (int i = 0; i < currentLevel; i++){
            System.out.print(" ");
        }
        System.out.println(level+"级"+file.getName());
        if (file.isDirectory()){
            ++level;
            File[] files = file.listFiles();
            for (File sonFile : files) {
                FileDetect2(sonFile, level);
            }
        }
    }
}
