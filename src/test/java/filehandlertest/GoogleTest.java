package filehandlertest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import filehandler.Google;
import java.util.Arrays;
import org.junit.Test;


public class GoogleTest {
    @Test
    public void testConversion() {
      //Create a google methods that takes the JSON and returns the lat and long
      double[] testLocation = {40.8240478, -73.9447643};  //Fix values
      double[] testALocation = Google.stringToLocation("Upper Manhattan");
      assertTrue(Arrays.equals(testLocation,testALocation));
    }
}