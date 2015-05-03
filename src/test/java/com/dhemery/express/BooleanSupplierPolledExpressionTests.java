package com.dhemery.express;

import com.dhemery.express.helpers.ExpressionsPolledBy;
import com.dhemery.express.helpers.PollingSchedules;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.dhemery.express.helpers.Throwables.messageThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BooleanSupplierPolledExpressionTests {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock Poller poller;

    PolledExpressions expressions;
    PollingSchedule schedule = PollingSchedules.random();
    SelfDescribingBooleanSupplier supplier = Named.booleanSupplier("supplier", () -> true);

    @Before
    public void setup() {
        expressions = new ExpressionsPolledBy(poller);
    }

    @Test
    public void assertThat_returnsWithoutThrowing_ifPollReturnsTrue() {
        context.checking(new Expectations() {{
            allowing(poller).poll(schedule, supplier);
            will(returnValue(true));
        }});

        expressions.assertThat(schedule, supplier);
    }

    @Test(expected = AssertionError.class)
    public void assertThat_throwsAssertionError_ifPollReturnsFalse() {
        context.checking(new Expectations() {{
            allowing(poller).poll(schedule, supplier);
            will(returnValue(false));
        }});

        expressions.assertThat(schedule, supplier);
    }

    @Test
    public void assertThat_errorMessageIncludesDiagnosis() {
        context.checking(new Expectations() {{
            allowing(poller).poll(schedule, supplier);
            will(returnValue(false));
        }});

        String message = messageThrownBy(() -> expressions.assertThat(schedule, supplier));

        assertThat(message, is(Diagnosis.of(schedule, supplier)));
    }

    @Test
    public void satisfiedThat_returnsTrue_ifPollReturnsTrue() {
        context.checking(new Expectations() {{
            allowing(poller).poll(schedule, supplier);
            will(returnValue(true));
        }});

        boolean result = expressions.satisfiedThat(schedule, supplier);

        assertThat(result, is(true));
    }

    @Test
    public void satisfiedThat_returnsFalse_ifPollReturnsFalse() {
        context.checking(new Expectations() {{
            allowing(poller).poll(schedule, supplier);
            will(returnValue(false));
        }});

        boolean result = expressions.satisfiedThat(schedule, supplier);

        assertThat(result, is(false));
    }
}
