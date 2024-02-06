package com.vishaga.java.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsumerTest {

    @Test
    @DisplayName("Custom Consumer test")
    public void consumerTest_1(){
        List<String> input = new ArrayList<>();

        Consumer<String> consumer1 = str -> {
            input.add("1: " + str);
            input.add("2: " + str);
        };
        Consumer<String> consumer2 = consumer1.andThen(str -> input.add("3: " + str));
        consumer2.accept("GAURAV VISHAL");

        assertThat(input).containsExactly("1: GAURAV VISHAL", "2: GAURAV VISHAL", "3: GAURAV VISHAL");
    }

    @Test
    @DisplayName("Custom Consumer test")
    public void consumerTest_2(){
        List<String> list = new ArrayList<>(List.of("a", "b", "c"));
        // Consumer<List<String>> consumer1 = list -> list.clear();
        Consumer<List<String>> consumer = List::clear;
        consumer.accept(list);

        assertThat(list.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Custom Consumer test")
    public void consumerTest_3(){
        List<String> alphabets = new ArrayList<>(List.of("a", "b", "c"));
        Consumer<List<String>> consumer1 = list -> list.add("d");
        Consumer<List<String>> consumer2 = list -> list.add("e");
        Consumer<List<String>> listConsumer = consumer1.andThen(consumer2);
        listConsumer.accept(alphabets);

        assertThat(alphabets).contains("a", "b", "c", "d", "e");
    }
}
