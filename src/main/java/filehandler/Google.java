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

  public static JSONObject urlToJson(String string) throws NullPointerException{
    String apiKey = "AIzaSyAnsKL3XnguaCwUM9kICe223bxI2KAoQkM";
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

  public static double[] jsonToLocation(JSONObject jsonObj) throws NullPointerException{
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
    try {
      JSONObject jsonObj = urlToJson(string);
      return jsonToLocation(jsonObj);
    }
    catch(NullPointerException e) {
      create("Invalid location","Could not find location");
      return new double[]{0,0};
    }
      //Double latitude =   jsonObj.getJSONObject("results").getJSONObject("geometry").getJSONObject("location").getDouble("latitude");
      //Double longitude = jsonObj.getJSONObject("results").getJSONObject("geometry").getJSONObject("location").getDouble("longitude");
  }

  /*
  public static String locationToString(double lat,double lng) {
    try {
      JSONObject jsonObj = urlToJson()
    }
  }
  */

  public static void main(String[] args) {
    System.out.println(stringToLocation("1 Liberty Plaza")[0]);
    System.out.println(stringToLocation("1 Liberty Plaza")[1]);

//    Reader rdr = new Reader();
//    Bridge aBridge = new Bridge();
//    ArrayList<Retailer> retailerArrayList = new ArrayList<Retailer>();
//    try {
//      retailerArrayList = rdr.readRetailers("/file/InitialRetailers.csv", false);
//    }
//    catch (FileNotFoundException e) {
//
//    }
//    double[] location;
//    BufferedWriter bw;
//    BufferedReader br;
//    ArrayList<String> lines = new ArrayList<>();
//    String line;
//    try {
//
//      br = new BufferedReader(new FileReader(new File("src/main/resources/file/InitialRetailers.csv")));
//      int counter = 0;
//      while ((line = br.readLine()) != null) {
//        lines.add(line);
//        counter++;
//      }
//      bw = new BufferedWriter(
//          new FileWriter(new File("src/main/resources/file/InitialRetailersBU.csv"), true));
//
//      for (int i=730; i < retailerArrayList.size(); i++) {
//        System.out.println("A");
//        System.out.println(i);
//        System.out.println(retailerArrayList.get(i).getAddress());
//        location = aBridge.getRetailerLocation(retailerArrayList.get(i));
//
//        bw.write(lines.get(i) + "," + location[0] + "," + location[1]);
//        bw.newLine();
//      }
//      System.out.println(stringToLocation(retailerArrayList.get(730).getAddress())[0]);
//      System.out.println(stringToLocation(retailerArrayList.get(730).getAddress())[1]);
//    bw.close();
//    } catch (IOException e){
//      e.printStackTrace();
//      System.out.println("ASDASD");
//    }




  }
}
