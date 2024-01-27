package com.vishaga.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
Creating Thread Safe ConcurrentHashSet is not possible before JDK 8 because of the java.util.concurrent package
does not have a class called ConcurrentHashSet, but starting with JDK 8,
the newly added keySet (the default) and newKeySet() methods to create a
ConcurrentHashSet in Java that is supported by ConcurrentHashMap.
 **/
public class ConcurrentHashSetTest {

    @Test
    @DisplayName("Create ConcurrentHashSet using static method called newKeySet()")
    public void test_1(){
        Set<String> set = ConcurrentHashMap.newKeySet();
        set.add("1");
        set.add("2");
        set.add("3");
        assertThat(set).contains("1","2","3");
    }

    @Test
    @DisplayName("Create ConcurrentHashSet using non-static method called keySet(DefaultValue)")
    public void test_2(){
        ConcurrentHashMap<String,String> map = new ConcurrentHashMap<>();
        map.put("P","I");
        Set<String> set = map.keySet("DEFAULT_VALUE");
        set.add("1");
        set.add("2");
        set.add("3");
        assertThat(set).contains("P","1","2","3");
        assertThat(map).containsAllEntriesOf(
                Map.ofEntries(
                        Map.entry("P", "I"),
                        Map.entry("1", "DEFAULT_VALUE"),
                        Map.entry("2", "DEFAULT_VALUE"),
                        Map.entry("3", "DEFAULT_VALUE")
                )
        );
    }

    @Test
    @DisplayName("Create View only ConcurrentHashSet using non-static method called keySet()")
    public void test_3(){
        ConcurrentHashMap<String,String> map = new ConcurrentHashMap<>();
        map.put("P","I");
        Set<String> set = map.keySet();

        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> set.add("1"));
    }
}
