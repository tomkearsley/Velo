package filehandler;

import com.google.api.client.json.Json;
import com.google.api.client.json.JsonParser;
import com.google.gson.JsonObject;
import helper.Bridge;
import helper.tableOnClickPopup;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import jdk.nashorn.internal.parser.JSONParser;
import model.Retailer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static helper.tableOnClickPopup.create;

/**
 * The class Google defines the type to query the Google APIs
 */
public class Google {

  public static String toGoogleString(String string) {
    return string.replaceAll(" ", "+");
  }

  public static JSONObject locationToJson(double lat, double lng) throws NullPointerException{
    String apiKey = "AIzaSyAnsKL3XnguaCwUM9kICe223bxI2KAoQkM";
    String JsonString =
        "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng
            + "&key=" + apiKey;
    return urlToJson(JsonString);
    }

    public static JSONObject stringToJson(String string) throws NullPointerException{
      String apiKey = "AIzaSyAnsKL3XnguaCwUM9kICe223bxI2KAoQkM";
      String JsonString =
          "https://maps.googleapis.com/maps/api/geocode/json?address=" + toGoogleString(string)
              + "&key=" + apiKey;

      return urlToJson(JsonString);
    }

    public static JSONObject urlToJson(String url) {
      InputStream input = null;
      StringBuilder sb = new StringBuilder();

      try {
        URL googleURL = new URL(url);
        BufferedReader in = new BufferedReader(new InputStreamReader(googleURL.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
          sb.append(inputLine);
        }
        in.close();
      }

      catch (MalformedURLException e) {
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

    return new JSONObject(sb.toString());
  }

  public static String jsonToString(JSONObject jsonObj) throws NullPointerException{
    JSONArray resultArray = jsonObj.getJSONArray("results");

    JSONObject addressObject = resultArray.getJSONObject(0);

    return addressObject.getString("formatted_address");
  }

  public static double[] jsonToLocation(JSONObject jsonObj) throws NullPointerException {
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
  }


  public static double[] stringToLocation(String string) {
    return jsonToLocation(stringToJson(string));
  }

  public static String locationToString(double lat,double lng) {
    return jsonToString(locationToJson(lat,lng));
  }
}
