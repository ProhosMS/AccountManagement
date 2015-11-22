package util;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class HandleArgumentsTest {
    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void testArgs_onlyAcceptOneArgument() {
        List args = Arrays.asList("a", "b");
        HandleArguments.process(args);
    }

    @Test
    public void testArgs_returnsFilename() {
        List args = Arrays.asList("/tmp/accts.txt");
        String filename = HandleArguments.process(args);

        assertEquals(filename, "/tmp/accts.txt");
    }
}
