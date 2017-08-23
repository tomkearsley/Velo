package seng202.team9.FileHandlers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import seng202.team9.Models.Hotspot;
import java.util.ArrayList;
import java.io.FileNotFoundException;

/**
 * The class Reader is used to load files and data
 */

public class Reader {

  /**
   * Reads WiFi hotspots from a csv file
   *
   * <p>
   *   Needs to be tested to ensure it works. Add print statements to print each hotspot attribute
   *   as it is parsed.
   * </p>
   * @author andrewspearman
   *
   * @param filename  the name of file to open
   * @throws FileNotFoundException  if the file cannot be found
   * @return ArrayList<Hotspot> Hotspots
   */
  public ArrayList<Hotspot> readHotspots(String filename) throws FileNotFoundException {

    // Initialize scanner and Hotspots array
    BufferedReader br = null;
    String line;
    ArrayList<Hotspot> Hotspots = new ArrayList<Hotspot>();

    try {

      br = new BufferedReader(new FileReader(filename));
      while ((line = br.readLine()) != null) {
        Hotspot newHotspot = new Hotspot();

        // Separate by comma
        String[] csvHotspot = line.split(",");

        newHotspot.setID(Integer.valueOf(csvHotspot[0]));
        newHotspot.setType(csvHotspot[3]);
        newHotspot.setProvider(csvHotspot[4]);
        newHotspot.setName(csvHotspot[5]);
        newHotspot.setLocationAdress(csvHotspot[6]);
        newHotspot.setLatitude(Double.parseDouble(csvHotspot[7]));
        newHotspot.setLongitude(Double.parseDouble(csvHotspot[8]));
        newHotspot.setRemarks(csvHotspot[12]);
        newHotspot.setCity(csvHotspot[13]);
        newHotspot.setSSID(csvHotspot[14]);
        newHotspot.setBorough(csvHotspot[18]);
        newHotspot.setPostcode(Integer.valueOf(csvHotspot[22]));

        //add newHotspot to Hotspots array
        Hotspots.add(newHotspot);

      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return Hotspots;
  }





}
