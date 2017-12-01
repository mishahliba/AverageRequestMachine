
import org.junit.Test;
import service.AddressChecker;
import static org.junit.Assert.*;

public class AddressCheckerTest {

    AddressChecker checker = new AddressChecker();

    @Test
    public void isURLCorrectInputTest() {

        String input = "google.com";
        assertTrue(checker.isDomainName(input));
    }

    @Test
    public void isURLIncorrectInputTest() {
        String input = "google.com.1";
        assertFalse(checker.isDomainName(input));
    }

    @Test
    public void isIPCorrectInputTest() {
        AddressChecker checker = new AddressChecker();
        String input = "ght10";
        assertTrue(checker.isIP(input));
    }
}



