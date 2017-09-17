package filehandler;

import com.google.api.client.json.Json;
import com.google.api.client.json.JsonParser;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Arrays;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleQuery {

  //String apiKey = "AIzaSyAnsKL3XnguaCwUM9kICe223bxI2KAoQkM";

  public static double[] stringToLocation(String string) {
    String apiKey = "AIzaSyAnsKL3XnguaCwUM9kICe223bxI2KAoQkM";
    String JsonString =
        "https://maps.googleapis.com/maps/api/geocode/json?address=" + string.replaceAll(" ", "+")
            + "&key=" + apiKey;
    InputStream input = null;
    StringBuilder sb = new StringBuilder();

    try {
      URL oracle = new URL(JsonString);
      BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        sb.append(inputLine);
      }
      in.close();

      JSONObject jsonObj = new JSONObject(sb.toString());

      //Double latitude =   jsonObj.getJSONObject("results").getJSONObject("geometry").getJSONObject("location").getDouble("latitude");
      //Double longitude = jsonObj.getJSONObject("results").getJSONObject("geometry").getJSONObject("location").getDouble("longitude");

      JSONArray resultArray = jsonObj.getJSONArray("results");
      //JSONObject result1 = resultArray.getJSONObject(1);
      JSONObject addressObject = resultArray.getJSONObject(0);

      JSONObject geometryObject = addressObject.getJSONObject("geometry");

      JSONObject locationObject = geometryObject.getJSONObject("location");

      double lat = locationObject.getDouble("lat");
      double lng = locationObject.getDouble("lng");

      double[] location = {lat,lng};
      //JSONObject resultArray = jsonObj.getJSONObject("results");
      //resultArray.getJSONObject("address_components");
      return location;
    }
    catch(Exception e) {
      System.out.print("Something went wrong");     //TODO Add more specific things
      return null;
    }
  }

  public static void main(String[] args) {
    System.out.println(Arrays.toString(stringToLocation("Upper Manhattan")));
  }
}
