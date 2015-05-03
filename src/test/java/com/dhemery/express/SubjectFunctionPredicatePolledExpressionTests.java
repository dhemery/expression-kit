package com.dhemery.express;

import com.dhemery.express.helpers.PolledExpressionTestSetup;
import com.dhemery.express.helpers.PollingSchedules;
import org.jmock.Expectations;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static com.dhemery.express.helpers.Throwables.messageThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(Enclosed.class)
public class SubjectFunctionPredicatePolledExpressionTests {

    public static final SelfDescribingFunction<String, String> FUNCTION = Named.function("function", String::toUpperCase);
    public static final SelfDescribingPredicate<String> PREDICATE = Named.predicate("predicate", t -> true);
    public static final PollingSchedule SCHEDULE = PollingSchedules.random();

    public static class AssertThat extends PolledExpressionTestSetup {
        @Test
        public void returnsWithoutThrowing_ifPollEvaluationResultIsSatisfied() {
            context.checking(new Expectations() {{
                allowing(poller).poll(SCHEDULE, "subject", FUNCTION, PREDICATE);
                will(returnValue(new PollEvaluationResult<>(FUNCTION.apply("subject"), true)));
            }});

            expressions.assertThat(SCHEDULE, "subject", FUNCTION, PREDICATE);
        }

        @Test(expected = AssertionError.class)
        public void throwsAssertionError_ifPollEvaluationResultIsDissatisfied() {
            context.checking(new Expectations() {{
                allowing(poller).poll(SCHEDULE, "subject", FUNCTION, PREDICATE);
                will(returnValue(new PollEvaluationResult<>(FUNCTION.apply("subject"), false)));
            }});

            expressions.assertThat(SCHEDULE, "subject", FUNCTION, PREDICATE);
        }

        @Test
        public void errorMessage_describesSubjectFunctionPredicateDerivedValueAndSchedule() {
            context.checking(new Expectations() {{
                allowing(poller).poll(SCHEDULE, "subject", FUNCTION, PREDICATE);
                will(returnValue(new PollEvaluationResult<>(FUNCTION.apply("subject"), false)));
            }});

            String message = messageThrownBy(() -> expressions.assertThat(SCHEDULE, "subject", FUNCTION, PREDICATE));

            assertThat(message, is(Diagnosis.of(SCHEDULE, "subject", FUNCTION, PREDICATE, FUNCTION.apply("subject"))));
        }
    }

    public static class SatisfiedThat extends PolledExpressionTestSetup {

        @Test
        public void returnsTrue_ifPollEvaluationResultIsSatisfied() {
            context.checking(new Expectations() {{
                allowing(poller).poll(SCHEDULE, "subject", FUNCTION, PREDICATE);
                will(returnValue(new PollEvaluationResult<>(FUNCTION.apply("subject"), true)));
            }});

            boolean result = expressions.satisfiedThat(SCHEDULE, "subject", FUNCTION, PREDICATE);

            assertThat(result, is(true));
        }

        @Test
        public void returnsFalse_ifPollEvaluationResultIsDissatisfied() {
            context.checking(new Expectations() {{
                allowing(poller).poll(SCHEDULE, "subject", FUNCTION, PREDICATE);
                will(returnValue(new PollEvaluationResult<>(FUNCTION.apply("subject"), false)));
            }});

            boolean result = expressions.satisfiedThat(SCHEDULE, "subject", FUNCTION, PREDICATE);

            assertThat(result, is(false));
        }
    }
}
