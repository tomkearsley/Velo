package filehandlertest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import filehandler.GoogleQuery;
import java.util.Arrays;
import org.junit.Test;


public class GoogleQueryTest {
    @Test
    public void testConversion() {
      double[] testLocation = {40.8240478, -73.9447643};  //Fix values
      double[] testALocation = GoogleQuery.stringToLocation("Upper Manhattan");
      assertTrue(Arrays.equals(testLocation,testALocation));
    }

}