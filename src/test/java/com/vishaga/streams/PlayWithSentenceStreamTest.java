package com.vishaga.streams;

import com.vishaga.utils.DataLoaderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
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
    @DisplayName("sorted words based on its frequency")
    public void countTest0(){
        Pattern compile = Pattern.compile("[ ,.':\\-]+");
        TreeMap<Long, ArrayList<String>> sortedWordFrequency = SENTENCES.stream()
                .flatMap(compile::splitAsStream)
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(
                                        Function.identity(),
                                        Collectors.counting()),
                                map -> map.entrySet().stream()
                                        .collect(
                                                Collectors.toMap(
                                                        Map.Entry::getValue,
                                                        (entry) -> new ArrayList<>(Collections.singletonList(entry.getKey())),
                                                        (l1, l2) -> {
                                                            l1.addAll(l2);
                                                            return l1;
                                                        },
                                                        TreeMap::new))));

        assertThat(sortedWordFrequency.lastEntry().getKey()).isEqualTo(56);
        assertThat(sortedWordFrequency.lastEntry().getValue()).contains("of");
    }
}
