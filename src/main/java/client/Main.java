package client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.PingService;

import java.util.Scanner;

/**
 * Class for interaction with user.
 */
public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static String userInput;

    public static void main(String[] args) {
        PingService pingService = new PingService();
        Scanner scanner = new Scanner(System.in);
        userInput = scanner.nextLine();
        LOG.debug("user input: {} ", userInput);
        pingService.pingHost(userInput);
    }
}

