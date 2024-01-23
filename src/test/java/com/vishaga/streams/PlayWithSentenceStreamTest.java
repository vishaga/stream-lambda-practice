package com.vishaga.streams;

import com.vishaga.utils.DataLoaderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayWithSentenceStreamTest {

    private static List<String> SENTENCES;

    @BeforeAll
    public static void setUp(){
        SENTENCES = DataLoaderUtils.loadLines();
    }

    @Test
    @DisplayName("Count Books")
    public void countTest(){
        assertThat((long) SENTENCES.size()).isEqualTo(93);
    }

    @Test
    @DisplayName("Group by  Books")
    public void countTest0(){

//        Pattern compile = Pattern.compile("[ ,.':\\-]+");
//        assertThat((long) SENTENCES.size()).isEqualTo(93);
//        Map<String, Long> collect = SENTENCES.stream()
//                .flatMap(compile::splitAsStream)
//                .collect(
//                        Collectors.collectingAndThen(
//                                Collectors.groupingBy(
//                                        Function.identity(),
//                                        Collectors.counting()),
//                                Collectors.mapping(
//
//                                )
//                        )
//                        );
        //System.out.println("collect = " + collect);
    }
}
