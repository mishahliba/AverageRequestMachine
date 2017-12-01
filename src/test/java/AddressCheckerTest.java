import exception.WrongInputFormatException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import service.AddressChecker;
import service.DomainNameResponseTimeMeter;
import service.ResponseTimeMeter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AddressCheckerTest {

    private AddressChecker checker = new AddressChecker();


    @Test
    public void isURLCorrectInputTest() {
        String input = "google.com";
        assertTrue(checker.isDomainName(input));
    }

    @Test
    public void isURLIncorrectInputTest() {
        String input = "abc.1";
        assertFalse(checker.isDomainName(input));
    }

    @Test
    public void isIPCorrectInputTest() {
        String input = "192.168.0.1";
        boolean res = checker.isIP(input);
        System.out.println(res);
        assertTrue(checker.isIP(input));
    }

    @Test
    public void isIPIncorrectInputTest() {
        String input = "192.168.0.1a";
        assertTrue(checker.isIP(input));
    }

    @Test
    public void checkInputFormatTestCorrectInput() throws WrongInputFormatException {
        ResponseTimeMeter timeMeter = new DomainNameResponseTimeMeter();
        checker.checkInputFormat(timeMeter, "www.google.com");
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test(expected = WrongInputFormatException.class)
    public void checkInputFormatTestIncorrectInput() throws WrongInputFormatException {
        ResponseTimeMeter timeMeter = null;
        checker.checkInputFormat(timeMeter, "ababa.ababa");
        thrown.expect(WrongInputFormatException.class);
        thrown.expectMessage("wrong url format");

    }
}



