package com.dhemery.express;

import org.hamcrest.Matcher;

import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Expressive methods to evaluate composed conditions.
 *
 * @see PolledExpressions
 * @see Named
 */

public interface Expressions {
    /**
     * Assert that the condition is satisfied.
     *
     * @param condition
     *         the condition to evaluate
     */
    static void assertThat(SelfDescribingBooleanSupplier condition) {
        if (!condition.getAsBoolean())
            throw new AssertionError(Diagnosis.of(condition));
    }

    /**
     * Assert that the predicate accepts the subject.
     *
     * @param <T>
     *         the type of the subject
     * @param subject
     *         the subject to evaluate
     * @param predicate
     *         the predicate that evaluates the subject
     */
    static <T> void assertThat(T subject, SelfDescribingPredicate<? super T> predicate) {
        if (!predicate.test(subject))
            throw new AssertionError(Diagnosis.of(subject, predicate));
    }

    /**
     * Assert that the matcher accepts the subject.
     *
     * @param <T>
     *         the type of the subject
     * @param subject
     *         the subject to evaluate
     * @param matcher
     *         the matcher that evaluates the subject
     */
    static <T> void assertThat(T subject, Matcher<? super T> matcher) {
        if (!matcher.matches(subject))
            throw new AssertionError(Diagnosis.of(subject, matcher));
    }

    /**
     * Assert that the predicate accepts the value that the function derives
     * from the subject.
     *
     * @param <T>
     *         the type of the subject
     * @param <R>
     *         the type of the result of the function
     * @param subject
     *         the subject to evaluate
     * @param function
     *         the function that derives the value of interest
     * @param predicate
     *         the predicate that evaluates the derived value
     */
    static <T, R> void assertThat(T subject, SelfDescribingFunction<? super T, R> function, SelfDescribingPredicate<? super R> predicate) {
        R value = function.apply(subject);
        if (!predicate.test(value))
            throw new AssertionError(Diagnosis.of(subject, function, predicate, value));
    }

    /**
     * Assert that the matcher accepts the value that the function derives from
     * the subject.
     *
     * @param <T>
     *         the type of the subject
     * @param <R>
     *         the type of the result of the function
     * @param subject
     *         the subject to evaluate
     * @param function
     *         the function that derives the value of interest
     * @param matcher
     *         the matcher that evaluates the derived value
     */
    static <T, R> void assertThat(T subject, SelfDescribingFunction<? super T, R> function, Matcher<? super R> matcher) {
        R value = function.apply(subject);
        if (!matcher.matches(value))
            throw new AssertionError(Diagnosis.of(subject, function, matcher, value));
    }

    /**
     * Indicate whether the condition is satisfied.
     *
     * @param condition
     *         the condition to evaluate
     */
    static boolean satisfiedThat(BooleanSupplier condition) {
        return condition.getAsBoolean();
    }

    /**
     * Indicate whether the predicate accepts the subject.
     *
     * @param <T>
     *         the type of the subject
     * @param subject
     *         the subject to evaluate
     * @param predicate
     *         the predicate that evaluates the subject
     */
    static <T>
    boolean satisfiedThat(T subject, Predicate<? super T> predicate) {
        return predicate.test(subject);
    }

    /**
     * Indicate whether the matcher accepts the subject.
     *
     * @param <T>
     *         the type of the subject
     * @param subject
     *         the subject to evaluate
     * @param matcher
     *         the matcher that evaluates the subject
     */
    static <T>
    boolean satisfiedThat(T subject, Matcher<? super T> matcher) {
        return matcher.matches(subject);
    }

    /**
     * Indicate whether the predicate accepts the value that the function
     * derives from the subject.
     *
     * @param <T>
     *         the type of the subject
     * @param <R>
     *         the type of the result of the function
     * @param subject
     *         the subject to evaluate
     * @param function
     *         the function that derives the value of interest
     * @param predicate
     *         the predicate that evaluates the derived value
     */
    static <T, R>
    boolean satisfiedThat(T subject, Function<? super T, R> function, Predicate<? super R> predicate) {
        return predicate.test(function.apply(subject));
    }

    /**
     * Indicate whether the matcher accepts the value that the function derives
     * from the subject.
     *
     * @param <T>
     *         the type of the subject
     * @param <R>
     *         the type of the result of the function
     * @param subject
     *         the subject to evaluate
     * @param function
     *         the function that derives the value of interest
     * @param matcher
     *         the matcher that evaluates the derived value
     */
    static <T, R>
    boolean satisfiedThat(T subject, Function<? super T, R> function, Matcher<? super R> matcher) {
        return matcher.matches(function.apply(subject));
    }
}
