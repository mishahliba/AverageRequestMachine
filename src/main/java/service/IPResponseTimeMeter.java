package service;

import exception.WrongResponseCodeException;
import java.net.InetAddress;

/**
 * Class represents implementation of response time measurement in case if user input is IP-address.
 */
public class IPResponseTimeMeter implements ResponseTimeMeter {

    @Override
    public long pingHost(String input) throws Exception {
        long startTime = System.nanoTime();
        InetAddress address = InetAddress.getByName(input);
        boolean reacheable = address.isReachable(4000);
        if(reacheable) {
            long endTime = System.nanoTime();
            return getDuration(startTime, endTime);
        }else {
            throw new WrongResponseCodeException("address is not reachable");
        }
    }
}
