package service;

import exception.WrongResponseCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;

/**
 * Class represents implementation of response time measurement in case if user input is domain name.
 */
public class DomainNameResponseTimeMeter implements ResponseTimeMeter {

    private static final Logger LOG = LoggerFactory.getLogger(DomainNameResponseTimeMeter.class);
    private static final String URL_PREFIX = "http://";
    private static HttpURLConnection connection;

    public long pingHost(String input) throws Exception {
        URL url = new URL(URL_PREFIX + input);
        long startTime = System.nanoTime();
        int responseCode = getResponseCode(url);
        LOG.debug("response code: {} ", responseCode);
        if (!String.valueOf(responseCode).startsWith("2")) {
            throw new WrongResponseCodeException("wrong response code: " + responseCode);
        }
        long endTime = System.nanoTime();
        return getDuration(startTime, endTime);
    }

    public int getResponseCode(URL url) throws IOException {
        connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(4000);
        connection.setRequestMethod("GET");
        return connection.getResponseCode();
    }
}
