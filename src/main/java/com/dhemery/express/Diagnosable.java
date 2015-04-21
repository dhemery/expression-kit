package com.dhemery.express;

import java.util.Optional;

/**
 * Represents the success or failure of some operation.
 *
 * @see Diagnosis
 */
public interface Diagnosable {
    /**
     * Describe the subject of the operation.
     * <p>
     * Not every operation has an identifiable subject.
     *
     * @return a description of the subject of the operation, if any
     */
    default Optional<String> subject() {
        return Optional.empty();
    }

    /**
     * Describe the expected result of the operation.
     *
     * @return a description of the expected result of the operation
     */
    String expectation();

    /**
     * Describe the operation's most recent failure.
     * <p>
     * If the operation has not been performed, or the most recent operation
     * succeeded, the description is meaningless. Not every diagnosable
     * operation describes its failures.
     *
     * @return a description of the failure of the most recent operation
     */
    default Optional<String> failure() {
        return Optional.empty();
    }
}
