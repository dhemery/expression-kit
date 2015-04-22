package com.dhemery.express.expressions;

import com.dhemery.express.Expressions;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.dhemery.express.helpers.Throwables.present;
import static com.dhemery.express.helpers.Throwables.throwableThrownByRunning;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class AssertThatTests {
    @Test
    public void returnsWithoutThrowingIfTheSupplierIsSatisfied() {
        Expressions.assertThat(() -> true);
    }

    @Test
    public void throwsADiagnosticAssertionErrorIfTheConditionIsNotSatisfied() {
        BooleanSupplier supplier = () -> false;
        Runnable expression = () -> Expressions.assertThat(supplier);

        Optional<Throwable> thrown = throwableThrownByRunning(expression);

        assertThat(thrown, is(present()));
        assertThat(thrown.get(), instanceOf(AssertionError.class));
        String detail = new StringJoiner(System.lineSeparator())
                .add("")
                .add("Expected: " + supplier)
                .toString();
        assertThat(thrown.get().getMessage(), is(detail));
    }

    @Test
    public void returnsWithoutThrowingIfThePredicateAcceptsTheSubject() {
        Expressions.assertThat("", String::isEmpty);
    }

    @Test
    public void throwsADiagnosticAssertionErrorIfThePredicateRejectsTheSubject() {
        Predicate<String> predicate = String::isEmpty;
        String subject = "subject";
        Runnable expression = () -> Expressions.assertThat(subject, predicate);

        Optional<Throwable> thrown = throwableThrownByRunning(expression);

        assertThat(thrown, is(present()));
        assertThat(thrown.get(), instanceOf(AssertionError.class));
        String detail = new StringJoiner(System.lineSeparator())
                .add("subject")
                .add("Expected: " + predicate)
                .toString();
        assertThat(thrown.get().getMessage(), is(detail));
    }

    @Test
    public void returnsWithoutThrowingIfTheMatcherAcceptsTheFunctionOfTheSubject() {
        Function<? super String, String> toUpperCase = String::toUpperCase;
        Matcher<String> isFOO = is("FOO");
        Expressions.assertThat("foo", toUpperCase, isFOO);
    }

    @Test
    public void throwsADiagnosticAssertionErrorIfTheMatcherRejectsTheFunctionOfTheSubject() {
        String subject = "subject";
        Function<String, String> function = String::toUpperCase;
        Matcher<String> matcher = is("bar");
        Runnable expression = () -> Expressions.assertThat(subject, function, matcher);

        Optional<Throwable> thrown = throwableThrownByRunning(expression);

        assertThat(thrown, is(present()));
        assertThat(thrown.get(), instanceOf(AssertionError.class));
        String detail = new StringJoiner(System.lineSeparator())
                .add("subject")
                .add("Expected: " + function + " " + matcher)
                .add("     but: was \"SUBJECT\"")
                .toString();
        assertThat(thrown.get().getMessage(), is(detail));
    }

    @Test
    public void returnsWithoutThrowingIfPredicateAcceptsTheFunctionOfTheSubject() {
        Function<? super String, String> toUpperCase = String::toUpperCase;
        Predicate<String> isFOO = "FOO"::equals;
        Expressions.assertThat("foo", toUpperCase, isFOO);
    }

    @Test
    public void throwsADiagnosticAssertionErrorIfThePredicateRejectsTheFunctionOfTheSubject() {
        String subject = "subject";
        Function<String, String> function = String::toUpperCase;
        Predicate<String> predicate = "bar"::equals;
        Runnable expression = () -> Expressions.assertThat(subject, function, predicate);

        Optional<Throwable> thrown = throwableThrownByRunning(expression);

        assertThat(thrown, is(present()));
        assertThat(thrown.get(), instanceOf(AssertionError.class));
        String detail = new StringJoiner(System.lineSeparator())
                .add("subject")
                .add("Expected: " + function + " " + predicate)
                .add("     but: was \"SUBJECT\"")
                .toString();
        assertThat(thrown.get().getMessage(), is(detail));
    }
}
