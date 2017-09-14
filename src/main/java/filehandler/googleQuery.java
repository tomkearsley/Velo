package filehandler;

import com.google.api.client.json.Json;
import java.io.InputStream;

public class googleQuery {

  String apiKey = "AIzaSyAnsKL3XnguaCwUM9kICe223bxI2KAoQkM";

  public String stringToJsonString(String string) {
    InputStream input = null;
    return "https://maps.googleapis.com/maps/api/geocode/json?address=" + string.replaceAll(" ","+") + "&key=" + apiKey;
  }

  public static void main(String args[]) {

  }
}
