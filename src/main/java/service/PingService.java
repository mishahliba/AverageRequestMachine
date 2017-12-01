package service;

import exception.WrongInputFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for processing results of input check and ping of host.
 */
public class PingService {

    private static final Logger LOG = LoggerFactory.getLogger(PingService.class);
    private static AddressChecker checker;
    private static ResponseTimeMeter responseMeter;
    private static List<Long> requestTime = new ArrayList<>();

    public PingService() {
        checker = new AddressChecker();
    }

    public void pingHost(String host) {
        validateHost(host);
        try {
            ping(host);
            int average = getAverageResponseTime();
            LOG.debug("average response time: {} ", average);
            System.out.format("average time of request was: %d ", average);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            LOG.error("cannot execute ping: {} ", e.getMessage());
        }
    }

    void validateHost(String host) {
        resolveResponseTimeMeter(host, checker);
        isValidInput(host);
    }

    public boolean isValidInput(String inputURL) {
        boolean validInput = false;
        try {
            validInput = checker.checkInputFormat(responseMeter, inputURL);
        } catch (WrongInputFormatException e) {
            LOG.error("wrong input format, {} ", inputURL);
            System.out.println(e.getMessage());
        }
        return validInput;
    }

    private int getAverageResponseTime() {
        return (int) requestTime.stream().mapToLong(value -> value).average().getAsDouble();
    }

    private void ping(String inputURL) throws Exception {
        for (int i = 0; i < 10; i++) {
            long responseTime = responseMeter.pingHost(inputURL);
            System.out.format("request %d : %d ms \n", i, responseTime);
            requestTime.add(responseTime);
        }
    }

    private void resolveResponseTimeMeter(String inputURL, AddressChecker checker) {
        if (checker.isDomainName(inputURL)) {
            responseMeter = new DomainNameResponseTimeMeter();
        } else if (checker.isIP(inputURL)) {
            responseMeter = new IPResponseTimeMeter();
        }
    }
}
