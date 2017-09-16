package filehandlertest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import filehandler.googleQuery;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class googleQueryTest {
    @Test
    public void testConversion() {
      double[] testLocation = {40.8240478, -73.9447643};  //Fix values
      double[] testALocation = googleQuery.stringToLocation("Upper Manhattan");
      assertTrue(Arrays.equals(testLocation,testALocation));
    }

}