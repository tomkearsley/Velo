package seng202.team9.FileHandlers;

import seng202.team9.Models.Hotspot;
import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The class Reader is used to load files and data
 */

public class Reader {

  //Reader attributes
  private String filename;
  private ArrayList<Hotspot> Hotspots;

  /**
   * Reads WiFi hotspots from .csv file
   * @author andrewspearman
   * @param
   * @return ArrayList<Hotspot> Hotspots
   */
  public ArrayList<Hotspot> readHotspots(String filename) throws FileNotFoundException {

    //Scanner method
    Scanner scanner = new Scanner(new File(filename));
    scanner.useDelimiter(",");
    ArrayList<Hotspot> Hotspots = new ArrayList<Hotspot>();

    /*while(scanner.hasNext()){
      Hotspots.add();
      System.out.print(scanner.next()+"|"); //this part needs reworking!
    }*/

    scanner.close();

    return Hotspots;
  }





}
