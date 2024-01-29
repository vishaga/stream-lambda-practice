package org.example;

import com.vishaga.model.*;
import com.vishaga.utils.DataLoaderUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class Reader {

    public static void main(String[] args) throws Exception{
        System.out.println(DataLoaderUtils.loadUniversity().size());
    }

    private static void write(List<Article> universities) throws Exception{
        String path = Reader.class.getResource("/_articles.txt").getPath();
        File fileName = new File(path);
        fileName.createNewFile();
        FileWriter fileWriter = new FileWriter(fileName);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for(Article e: universities){
            printWriter.write(e.toString() +"\n");
        }
        printWriter.close();
    }
}