package filehandler;

import com.google.api.client.json.Json;
import com.google.api.client.json.JsonParser;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Arrays;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The class Google defines the type to query the Google APIs
 */
public class Google {

  public static String toGoogleString(String string) {
    return string.replaceAll(" ", "+");
  }


  public static double[] stringToLocation(String string) {
    String apiKey = "AIzaSyD_iIkRqntinnZupQBcn7Ka1HsE3n0MuX8";
    String JsonString =
        "https://maps.googleapis.com/maps/api/geocode/json?address=" + toGoogleString(string)
            + "&key=" + apiKey;
    InputStream input = null;
    StringBuilder sb = new StringBuilder();

    try {
      URL googleURL = new URL(JsonString);
      BufferedReader in = new BufferedReader(new InputStreamReader(googleURL.openStream()));
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

      double[] location = {lat, lng};
      //JSONObject resultArray = jsonObj.getJSONObject("results");
      //resultArray.getJSONObject("address_components");
      return location;
    } catch (MalformedURLException e) {
      System.out.println("Something went wrong!\nCheck that you've entered a valid location");
      return null;
    } catch (IOException e) {
      System.out.println(
          "Something went wrong!\nThere was an error in reading the location. Please retry");
      return null;
    } catch (JSONException e) {
      System.out.println(
          "Something went wrong!\nThere was an error in parsing the location details. Please retry");
      return null;
    } catch (Exception e) {
      System.out.print("Whoops! Something went wrong:\n" + e);
      return null;
    }
  }

  public static void main(String[] args) {
    System.out.println(Arrays.toString(stringToLocation("University of Canterbury")));
  }
}
