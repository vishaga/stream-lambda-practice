package com.vishaga.java.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsumerTest {

    @Test
    @DisplayName("Custom Consumer test")
    public void consumerTest_1(){
        Consumer<String> consumer1 = str -> {
            System.out.println("DEBUG: " + str);
            System.out.println("INFO: " + str);
        };
        Consumer<String> consumer2 = consumer1.andThen(s -> System.out.println("ERROR: "+s));

        consumer1.accept("GAURAV VISHAL");
        System.out.println("========================== ");
        consumer2.accept("Vivek Vishal");
    }
}
