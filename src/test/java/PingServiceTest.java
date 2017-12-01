import org.junit.Test;
import service.PingService;

import static org.junit.Assert.assertTrue;

public class PingServiceTest {

    @Test
    public void isValidInputTestCorrectInput(){
        PingService service = new PingService();
        boolean isValid = service.isValidInput("www.google.com");
        assertTrue(isValid);
    }
}
