package com.dhemery.express.helpers;

public class Throwables {
    public static Throwable throwableThrownBy(Runnable expression) {
        try {
            expression.run();
        } catch (AssertionError thrown) {
            return thrown;
        }
        throw new AssertionError("The assertion did not throw an AssertionError");
    }

    public static String[] linesOfMessageThrownBy(Runnable runnable) {
        return messageThrownBy(runnable).split(System.lineSeparator());
    }

    public static String messageThrownBy(Runnable runnable) {
        return throwableThrownBy(runnable).getMessage();
    }
}
