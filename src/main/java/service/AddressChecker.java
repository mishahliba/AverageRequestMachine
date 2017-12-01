package service;

import exception.WrongInputFormatException;
import org.apache.commons.validator.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class purpose is to decide what kind of input come from user: IP or URL.
 */
public class AddressChecker {

    private static final Logger LOG = LoggerFactory.getLogger(AddressChecker.class);
    private static final String TEXT_PATTERN = "^[a-zA-Z.]+$";
    private static final String DIGIT_PATTERN = "[^\\d.]";
    private static final String URL_PREFIX = "http://";

    public boolean isDomainName(String inputURL) {
        return inputURL.matches(TEXT_PATTERN);
    }

    public boolean isIP(String inputURL) {
        return (!inputURL.matches(DIGIT_PATTERN));
    }

    public boolean formatCheckURL(String inputURL) throws WrongInputFormatException {
        UrlValidator validator = new UrlValidator();
        if (validator.isValid("http://" + inputURL)) {
            return true;
        } else {
            LOG.error("wrong url format: {} ", inputURL);
            throw new WrongInputFormatException("wrong url format");
        }
    }

    public boolean formatCheckIP(String inputIP) throws WrongInputFormatException {
        List<String> urlParts = Arrays.asList(inputIP.split(Pattern.quote(".")));

        for (String urlPart : urlParts) {
            if (Integer.valueOf(urlPart) > 255) {
                LOG.error("wrong ip address: {} ", inputIP);
                throw new WrongInputFormatException("wrong input. cannot be higher than 255");
            }
        }
        if (urlParts.size() != 4) {
            LOG.error("wrong ip address: {} ", inputIP);
            throw new WrongInputFormatException("ip address must have 4 groups");
        }
        return true;
    }

    public boolean checkInputFormat(ResponseTimeMeter responseMeter, String inputURL) throws WrongInputFormatException {
        if (responseMeter instanceof IPResponseTimeMeter) {
            return formatCheckIP(inputURL);
        } else {
            return formatCheckURL(inputURL);
        }
    }
}
