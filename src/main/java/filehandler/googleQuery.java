package filehandler;

import com.google.api.client.json.Json;
import java.io.BufferedReader;
import java.io.InputStream;
import javax.print.DocFlavor.URL;

public class googleQuery {

  //String apiKey = "AIzaSyAnsKL3XnguaCwUM9kICe223bxI2KAoQkM";

  public Json readURL(String string) {
    String apiKey = "AIzaSyAnsKL3XnguaCwUM9kICe223bxI2KAoQkM";
    String JsonString = "https://maps.googleapis.com/maps/api/geocode/json?address=" + string.replaceAll(" ","+") + "&key=" + apiKey;
    InputStream input = null;
    try{
      URL url = new URL(JsonString);
      //BufferedReader reader = new BufferedReader(url);
    }
    finally {

    }
    return null;
  }

  public static void main(String args[]) {
    //System.out.println(readURL("Upper Manhattan"));
  }
}
