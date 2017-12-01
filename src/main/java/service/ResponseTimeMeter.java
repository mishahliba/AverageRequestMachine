package service;

import java.util.concurrent.TimeUnit;

/**
 * Interface declares a contract for measurement of response time of specified host.
 */
public interface ResponseTimeMeter {

    long pingHost(String host) throws Exception;

    default long getDuration(long startTime, long endTime) {
        long duration = endTime - startTime;
        long durationInMs = TimeUnit.NANOSECONDS.toMillis(duration);
        return durationInMs;
    }
}
