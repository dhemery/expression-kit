package com.dhemery.express;

import com.dhemery.express.helpers.ExpressionsPolledBy;
import com.dhemery.express.helpers.PollingSchedules;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.dhemery.express.helpers.Actions.selfDescribersDescribeThemselves;
import static com.dhemery.express.helpers.Throwables.messageThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BooleanSupplierPolledAssertionTests {
    @Mock
    public Poller poller;
    public PolledExpressions expressions;
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    SelfDescribingBooleanSupplier supplier;
    PollingSchedule schedule = PollingSchedules.random();

    @Before
    public void setup() {
        expressions = new ExpressionsPolledBy(poller);
        context.checking(selfDescribersDescribeThemselves());
    }

    @Test
    public void returnsWithoutThrowing_ifPollReturnsTrue() {
        context.checking(new Expectations() {{
            allowing(poller).poll(schedule, supplier);
            will(returnValue(true));
        }});

        expressions.assertThat(schedule, supplier);
    }

    @Test(expected = AssertionError.class)
    public void throwsAssertionError_ifPollReturnsFalse() {
        context.checking(new Expectations() {{
            allowing(poller).poll(schedule, supplier);
            will(returnValue(false));
        }});

        expressions.assertThat(schedule, supplier);
    }

    @Test
    public void errorMessageIncludesDiagnosis() {
        context.checking(new Expectations() {{
            allowing(poller).poll(schedule, supplier);
            will(returnValue(false));
        }});

        String message = messageThrownBy(() -> expressions.assertThat(schedule, supplier));

        assertThat(message, is(Diagnosis.of(schedule, supplier)));
    }
}
