package com.vishaga.java.util;

@FunctionalInterface
public interface Function<T, R> {

    R apply(T t);

    default <V> Function<T, V> andThen(Function<R, V> other){
        return (t) -> other.apply(apply(t));
    }

    static <T> Function<T, T> identity(){
        return (t) -> t;
    }
}
