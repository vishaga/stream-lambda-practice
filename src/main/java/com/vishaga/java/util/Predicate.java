package com.vishaga.java.util;

@FunctionalInterface
public interface Predicate<T> {

    boolean test(T t);

    default public Predicate<T> and(Predicate<T> other){
        return (T t) -> test(t) && other.test(t);
    }

    default public Predicate<T> negate(){
        return (t) -> !this.test(t);
    }

    default public Predicate<T> or(Predicate<T> other){
        return (T t) -> test(t) || other.test(t);
    }

    default public Predicate<T> xOr(Predicate<T> other){
        return (T t) -> test(t) ^ other.test(t);
    }
}
