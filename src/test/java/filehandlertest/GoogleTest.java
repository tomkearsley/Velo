package filehandlertest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import filehandler.Google;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import org.json.JSONObject;
import org.junit.Test;


public class GoogleTest {
    @Test
    public void testConversion() throws IOException{
      //Create a google methods that takes the JSON and returns the lat and long
      double[] testLocation = {40.8240478, -73.9447643};  //Fix values
      //double[] testALocation = Google.stringToLocation("Upper Manhattan");

      StringBuilder sb = new StringBuilder();
      BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(
          "/test/json")));
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        sb.append(inputLine);
      }
      in.close();

      double[] testALocation = Google.jsonToLocation(new JSONObject(sb.toString()));
      assertTrue(Arrays.equals(testLocation,testALocation));
    }
}