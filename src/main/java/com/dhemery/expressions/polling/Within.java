package com.dhemery.expressions.polling;

import com.dhemery.expressions.PollingSchedule;
import com.dhemery.expressions.TimeFrames;

import java.time.Duration;
import java.time.temporal.TemporalUnit;

/**
 * A polling schedule with a given duration and a default interval.
 * <p>
 * This class is not intended for direct use. Instead, call {@link
 * TimeFrames#within}.
 */
public class Within extends PollingSchedule {
    /**
     * Creates a polling schedule with the given interval and duration.
     * <p>
     * This constructor is not intended for direct use. Instead, call {@link
     * TimeFrames#within}.
     *
     * @param interval
     *         the interval on which to poll
     * @param duration
     *         the duration to poll
     */
    public Within(Duration interval, Duration duration) {
        super(interval, duration);
    }

    /**
     * Creates a polling schedule with the given interval and this schedule's
     * duration.
     *
     * @param interval
     *         the interval on which to poll
     *
     * @return a polling schedule with the given interval and this schedule's
     * duration
     */
    public PollingSchedule checkedEvery(Duration interval) {
        return new PollingSchedule(interval, duration());
    }

    /**
     * Creates a polling schedule with the specified interval and this schedule's
     * duration.
     *
     * @param amount
     *         the interval on which to poll, measured in terms of the unit
     * @param unit
     *         the unit that the polling interval is measured in
     *
     * @return a polling schedule with the given interval and this schedule's
     * duration
     */
    public PollingSchedule checkedEvery(int amount, TemporalUnit unit) {
        return checkedEvery(Duration.of(amount, unit));
    }
}
