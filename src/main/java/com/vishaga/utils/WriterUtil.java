package com.vishaga.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class WriterUtil {

    public static <T> void write(List<T> universities, String fileName) throws Exception{
        String path = WriterUtil.class.getResource("/"+fileName).getPath();
        File file = new File(path);
        if(!file.exists())
            file.createNewFile();
        FileWriter fileWriter = new FileWriter(fileName);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for(T e: universities){
            printWriter.write(e.toString() +"\n");
        }
        printWriter.close();
    }
}
