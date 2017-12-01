package client;

import exception.WrongInputFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.AddressChecker;
import service.IPResponseTimeMeter;
import service.ResponseTimeMeter;
import service.DomainNameResponseTimeMeter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class for interaction with user and processing results of input check and ping of host.
 */
public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static List<Long> requestTime = new ArrayList<>();
    private static ResponseTimeMeter responseMeter;
    private static AddressChecker checker = new AddressChecker();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputURL = scanner.nextLine();
        LOG.debug("user input: {} ", inputURL);
        resolveResponseTimeMeter(inputURL, checker);
        isValidInput(inputURL);
        ping(inputURL);
        int average = getAverageResponseTime();
        LOG.debug("average response time: {} ", average);
        System.out.format("average time of request was: %d ", average);
    }

    private static boolean isValidInput(String inputURL) {
        boolean validInput = false;
        try {
            validInput = checker.checkInputFormat(responseMeter, inputURL);
        } catch (WrongInputFormatException e) {
            System.out.println(e.getMessage());
        }
        return validInput;
    }

    private static int getAverageResponseTime() {
        return (int) requestTime.stream().mapToLong(value -> value).average().getAsDouble();
    }

    private static void ping(String inputURL) {
        for (int i = 0; i < 10; i++) {
            try {
                long responseTime = responseMeter.pingHost(inputURL);
                System.out.format("request %d : %d ms", i, responseTime);
                requestTime.add(responseTime);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void resolveResponseTimeMeter(String inputURL, AddressChecker checker) {
        if (checker.isDomainName(inputURL)) {
            responseMeter = new DomainNameResponseTimeMeter();
        } else if (checker.isIP(inputURL)) {
            responseMeter = new IPResponseTimeMeter();
        }
    }
}

