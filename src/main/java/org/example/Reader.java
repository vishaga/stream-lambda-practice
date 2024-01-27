package org.example;

import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.vishaga.model.*;
import com.vishaga.utils.DataLoaderUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Reader {

    public static void main(String[] args) throws Exception{

        System.out.println(
                //DataLoaderUtils.loadMovie(9).size()
        );

//        System.out.println(
//                DataLoaderUtils.loadEmployee(10010).size()
//        );

//        List<String> list = DataLoaderUtils.loadCountries().stream()
//                .filter(c -> c.region() == Region.ASIA)
//                .map(Country::name).toList();
//        System.out.println("list = " + list);

//        System.out.println(
//                DataLoaderUtils.loadCountries().size()
//        );
//        System.out.println(
//                DataLoaderUtils.loadPopulation().size()
//        );


//        System.out.println(DataLoaderUtils.loadUniversity().size());
//        write(DataLoaderUtils.loadUniversity().stream().collect(Collectors.toSet()));
//
//        System.out.println(
//                DataLoaderUtils.loadUniversity().stream().collect(Collectors.toSet()).size()
//        );
        //System.out.println(DataLoaderUtils.loadMovie2(100000).size());
        //System.out.println(DataLoaderUtils.loadMovie2(100000).stream().collect(Collectors.toSet()).size());

        //System.out.println(DataLoaderUtils.loadPopulation().size());
        System.out.println(DataLoaderUtils.loadArticles().size());
        //write(DataLoaderUtils.loadArticles());
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