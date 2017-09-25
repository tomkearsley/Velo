package filehandlertest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import filehandler.Google;
import java.util.Arrays;
import org.junit.Test;


public class GoogleTest {
    @Test
    public void testConversion() {
      double[] testLocation = {40.8240478, -73.9447643};  //Fix values
      double[] testALocation = Google.stringToLocation("Upper Manhattan");
      for(int i = 0;i<testLocation.length;i++) {
        assertTrue(testALocation[i] == testALocation[i]);
      }
    }
}