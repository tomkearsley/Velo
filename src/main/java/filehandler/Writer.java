package filehandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Hotspot;
import model.POI;
import model.PublicPOI;
import model.Retailer;
import model.Route;
import model.Station;
import model.UserPOI;

/**
 * The class Writer defines the object type Writer
 * It is used to write data to files
 */
public class Writer {

  private String[] getAttributeFormat (String[] attributes) {
    for (int i = 0; i < attributes.length; i++) {
      try {
        if (attributes[i].contains(",")) {
          attributes[i] = "\"" + attributes[i] + "\"";
        }
      } catch (NullPointerException e) {
        System.out.println();
      }
    }
    return attributes;
  }

  private String[] getAttributesFromPOI (POI poi) {
    if (poi instanceof Retailer) {
      Retailer ret = (Retailer) poi;
      String zipcode = ret.getZipcode() == -1 ? "":String.valueOf(ret.getZipcode());
      String[] rAttributes = {ret.getName(), ret.getAddress(), ret.getFloor(),
          ret.getCity(), ret.getState(), zipcode,
          ret.getBlock(), ret.getDescription(), ret.getSecondaryDescription()};
      return getAttributeFormat(rAttributes);

    } else if (poi instanceof Hotspot) {
      Hotspot hot = (Hotspot) poi;
      String[] hAttributes = {String.valueOf(hot.getID()), hot.getType(), hot.getProvider(),
          hot.getName(), hot.getLocationAddress(), String.valueOf(hot.getLatitude()),
          String.valueOf(hot.getLongitude()), hot.getDescription(), hot.getCity(), hot.getSSID(),
          hot.getBorough(), String.valueOf(hot.getPostcode())};
      return getAttributeFormat(hAttributes);

    } else if (poi instanceof UserPOI) {
      UserPOI usr = (UserPOI) poi;
      String[] uAttributes = {usr.getName(), String.valueOf(usr.getLatitude()),
          String.valueOf(usr.getLongitude()), usr.getDescription()};
      return getAttributeFormat(uAttributes);

    } else if (poi instanceof PublicPOI) {
      PublicPOI pblc = (PublicPOI) poi;
      String[] pAttributes = {pblc.getName(), String.valueOf(pblc.getLatitude()),
          String.valueOf(pblc.getLongitude()), pblc.getDescription()};
      return getAttributeFormat(pAttributes);
    }
    return null;
  }

  private String[] getAttributesFromRoute(Route rte) {
    return new String[]{String.valueOf(rte.getDuration()), rte.getStartDate().toString(),
        rte.getStopDate().toString(), String.valueOf(rte.getStartStation().getID()),
        rte.getStartStation().getName(), String.valueOf(rte.getStartStation().getLatitude()),
        String.valueOf(rte.getStartStation().getLongitude()), String.valueOf(rte.getStopStation().getID()),
        rte.getStopStation().getName(), String.valueOf(rte.getStopStation().getLatitude()),
        String.valueOf(rte.getStopStation().getLongitude()), String.valueOf(rte.getBikeID()),
        rte.getUserType(), String.valueOf(rte.getBirthYear()), String.valueOf(rte.getGender())
    };
  }

  private void writeLinesToFile(BufferedWriter writer, String[] attributes) throws IOException {
    for (int i = 0; i < attributes.length; i++) {
      writer.write(attributes[i]);
      if (i < attributes.length - 1) {
        writer.write(',');
      } else {
        writer.newLine();
      }
    }
  }

  /**
   * Writes a list of retailer objects to a given CSV file
   * @param POIs The list of retailer objects
   * @param filename  The file to write to
   * @throws IOException  If there is an error opening the file
   */
  private void writePOIsToFile (List<? extends POI> POIs, String filename) throws IOException {
    FileWriter outFile = new FileWriter(filename, true);
    BufferedWriter writer = new BufferedWriter(outFile);

    for (POI poi : POIs) {
      String[] attributes = getAttributesFromPOI(poi);

      writeLinesToFile(writer, attributes);
    }

    writer.close();
  }

  public void writeRoutesToFile (List<Route> routes, String filename) throws IOException {
    FileWriter outFile = new FileWriter(filename, true);
    BufferedWriter writer = new BufferedWriter(outFile);

    for (Route route : routes) {
      String[] attributes = getAttributesFromRoute(route);

      writeLinesToFile(writer, attributes);
    }

    writer.close();
  }

  public static void main(String[] args) {
    Writer testW = new Writer();
    Reader testR = new Reader();

    ArrayList<Retailer> retailers;
    ArrayList<Hotspot> hotspots;
    ArrayList<Station> stations;
    ArrayList<UserPOI> userPOIS;
    ArrayList<PublicPOI> publicPOIS;

    try {
      retailers = testR.readRetailers("/file/InitialRetailers.csv", false);
      hotspots = testR.readHotspots("/file/InitialHotspots.csv", false);
      stations = testR.readStations("/file/stations.json");
      userPOIS = testR.readUserPOIS("/file/UserPOIdata_smallsample.csv", false);
      publicPOIS = testR.readPublicPOIS("/file/PublicPOIdata_smallsample.csv", false);

      testW.writePOIsToFile(retailers, "src/main/java/filehandler/aRet.csv");
      testW.writePOIsToFile(hotspots, "src/main/java/filehandler/aHot.csv");
      testW.writePOIsToFile(userPOIS, "src/main/java/filehandler/aUser.csv");
      testW.writePOIsToFile(publicPOIS, "src/main/java/filehandler/aPub.csv");

    } catch (IOException e) {
      System.out.println("oops");
    }
  }

}
