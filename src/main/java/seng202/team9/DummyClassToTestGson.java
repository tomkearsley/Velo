package seng202.team9;

import com.google.gson.Gson;

/**
 * Created by andrewspearman on 8/3/17.
 */
public class DummyClassToTestGson {

    public static void main( String[] args) {
      System.out.println( "Hello World!" );

      DummyClassToTestGson myObject = new DummyClassToTestGson();
      Gson gson = new Gson();
      String jsonString = gson.toJson(myObject);

      System.out.println(jsonString);
    }
}