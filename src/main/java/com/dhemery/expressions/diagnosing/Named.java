package com.dhemery.expressions.diagnosing;

import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Factory methods to decorate {@link BooleanSupplier}, {@link Predicate}, and
 * {@link Function} objects to make them self-describing.
 */
public class Named {
    private final String name;

    /**
     * Creates an object that describes itself with the given name.
     *
     * @param name
     *         the name of the object
     */
    public Named(String name) {
        this.name = name;
    }

    /**
     * Returns this object's name.
     *
     * @return this object's name
     */
    @Override
    public final String toString() {
        return name;
    }

    /**
     * Decorates the supplier to describe itself with the given name.
     * <pre>{@literal
     * SelfDescribingBooleanSupplier jvmIsJava8 =
     *          Named.booleanSupplier("jvm is java 8",
     *                  () -> System.getProperty("java.version").startsWith("1.8."));
     * }</pre>
     *
     * @param name
     *         the name of the supplier
     * @param supplier
     *         the underlying supplier
     *
     * @return a {@code SelfDescribingBooleanSupplier} that functions like the
     * given supplier and describes itself with the given name.
     */
    public static BooleanSupplier booleanSupplier(String name, BooleanSupplier supplier) {
        return new NamedBooleanSupplier(name, supplier);
    }

    /**
     * Decorates the function to describe itself with the given name.
     *
     * <pre>{@literal
     * SelfDescribingFunction<String,Integer> length =
     *          Named.function("length", String::length);
     * }</pre>
     *
     * @param name
     *         the name of the function
     * @param function
     *         the underlying function
     * @param <T>
     *         the type of the input to the function
     * @param <V>
     *         the type of the result of the function
     *
     * @return a {@code SelfDescribingFunction} that functions like the given
     * function and describes itself with the given name.
     */
    public static <T, V> Function<T, V> function(String name, Function<T, V> function) {
        return new NamedFunction<>(name, function);
    }

    /**
     * Decorates the predicate to describe itself with the given name.
     *
     * <pre>{@literal
     * SelfDescribingPredicate<String> empty =
     *          Named.predicate("empty", String::isEmpty);
     * }</pre>
     *
     * @param description
     *         the name of the function
     * @param predicate
     *         the underlying predicate
     * @param <T>
     *         the type of the input to the predicate
     *
     * @return a {@code SelfDescribingPredicate} that functions like the given
     * predicate and describes itself with the given name.
     */
    public static <T> Predicate<T> predicate(String description, Predicate<T> predicate) {
        return new NamedPredicate<>(description, predicate);
    }
}