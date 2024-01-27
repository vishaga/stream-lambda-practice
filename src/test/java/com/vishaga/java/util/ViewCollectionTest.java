package com.vishaga.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
