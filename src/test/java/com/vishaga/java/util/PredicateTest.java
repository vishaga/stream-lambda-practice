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
}
