package service;

import exception.WrongInputFormatException;
import org.apache.commons.validator.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Class purpose is to decide what kind of input come from user: IP or URL.
 */
public class AddressChecker {

    private static final String TEXT_PATTERN = ".*[a-zA-Z]+.*";
    private static final String DIGIT_PATTERN = "[^\\d.]";

    public boolean isDomainName(String inputURL) {
        return inputURL.matches(TEXT_PATTERN);
    }

    public boolean isIP(String inputURL) {
        return (inputURL.matches(DIGIT_PATTERN));
    }

    public boolean formatCheckURL(String inputURL) {
        UrlValidator validator = new UrlValidator();
        return validator.isValid(inputURL);
    }

    public boolean formatCheckIP(String inputURL) throws WrongInputFormatException {
        List<String> urlParts = Arrays.asList(inputURL.split("."));
        for (String urlPart : urlParts) {
            if (Integer.valueOf(urlPart) > 255) {
                throw new WrongInputFormatException("wrong input. cannot be higher than 255");
            }
        }
        if (urlParts.size() != 4) {
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
