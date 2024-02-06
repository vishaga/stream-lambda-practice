package com.vishaga.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ViewCollectionTest {

    @Test
    @DisplayName("Arrays.asList().")
    public void practiceTest_1(){
        List<String> words = Arrays.asList("ONE","TWO","THREE");
        words.replaceAll(String::toLowerCase);

        //Arrays.asList() values can be altered by not the size
        assertThat(words).contains("one", "two", "three");

        //Arrays.asList() is backed by array and size can't be altered.
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> words.add("four"));

        words.set(0, "First");
        assertThat(words).contains("First", "two", "three");
    }

    @Test
    @DisplayName("List.of().")
    public void practiceTest_2(){
        List<String> words = List.of("ONE","TWO","THREE");

        //List.of() is immutable by nature and not unmodifiable
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> words.replaceAll(String::toLowerCase));
    }

    @Test
    @DisplayName("List.sublist() on new ArrayList.")
    public void practiceTest_3(){
        List<String> words = new ArrayList<>(List.of("ONE","TWO","THREE"));
        List<String> subList = words.subList(0, 1);

        assertThat(words).contains("ONE", "TWO", "THREE");
        assertThat(subList).contains("ONE");

        subList.add("2nd");

        assertThat(words).contains("ONE", "2nd", "TWO", "THREE");
        assertThat(subList).contains("ONE","2nd");
    }

    @Test
    @DisplayName("List.sublist() on Arrays.asList().")
    public void practiceTest_4(){
        List<String> words = Arrays.asList("ONE","TWO","THREE");
        List<String> subList = words.subList(0, 1);

        assertThat(words).contains("ONE", "TWO", "THREE");
        assertThat(subList).contains("ONE");

        //sublist.add() on unmodifiable source is not allowed
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> subList.add("2nd"));
    }
}
