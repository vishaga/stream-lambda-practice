package com.vishaga.java.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PredicateTest {

    @Test
    @DisplayName("Custom Predicate test")
    public void predicateTest_1(){
        Predicate<Integer> predicate1 = x -> x % 2 == 0;

        assertThat(predicate1.test(5)).isFalse();
        assertThat(predicate1.test(16)).isTrue();
        assertThat(predicate1.negate().test(16)).isFalse();
    }

    @Test
    @DisplayName("Custom Predicate test")
    public void predicateTest_2(){
        Predicate<Integer> predicate1 = x -> x % 2 == 0;
        Predicate<Integer> predicate2 = predicate1.and(x -> x>20);

        assertThat(predicate2.test(5)).isFalse();
        assertThat(predicate2.test(16)).isFalse();
        assertThat(predicate2.test(26)).isTrue();
    }

    @Test
    @DisplayName("Custom Predicate test")
    public void predicateTest_3(){
        Predicate<String> predicate1 = str -> !str.isEmpty();
        Predicate<String> predicate2 = predicate1.and(str -> str.startsWith("G"));

        assertThat(predicate2.test("Vishal")).isFalse();
        assertThat(predicate2.test("")).isFalse();
        assertThat(predicate2.test("Gaurav")).isTrue();
    }
}
