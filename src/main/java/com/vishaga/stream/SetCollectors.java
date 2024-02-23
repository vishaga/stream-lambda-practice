package com.vishaga.stream;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetCollectors<T> implements Collector<T, Set<T>, Set<T>> {

    private SetCollectors(){
       //
    }

    public static <T> SetCollectors<T> toSet(){
        return new SetCollectors<>();
    }
    @Override
    public Supplier<Set<T>> supplier() {
        return HashSet::new;
    }

    public BiConsumer<Set<T>, T> accumulator() {
        return Set::add;
    }

    @Override
    public BinaryOperator<Set<T>> combiner() {
        return (set1, set2) -> Stream.concat(set1.stream(), set2.stream())
                .collect(Collectors.toSet());
    }

    @Override
    public Function<Set<T>, Set<T>> finisher() {
        return Collections::unmodifiableSet;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of();
    }
}
