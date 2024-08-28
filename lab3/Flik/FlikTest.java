import org.junit.Test;
import static org.junit.Assert.*;

public class FlikTest {
    @Test
    public void testFlik() {
        for (int i = 0; i < 500; i++) {
            assertTrue("i is " + i + ", false -> fail", Flik.isSameNumber(i, i));
        }
    }
}
