package filehandlertest;

//TODO Replace assertTrue (blah.equals(blah2)) with assertEquals (overriding issue)
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Hotspot;
import model.POI;
import model.Retailer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import filehandler.Reader;


public class ReaderTest {
  private Reader reader;

  /**
   * This function is called before running any of the following functions.
   */
  @Before
  public void setUp() {
    reader = new Reader();
  }

  /**
   * This function is called at the end of every test that is run.
   */
  @After
  public void tearDown() {
    reader = null;
  }

  @Test
  public void readHotspots() throws FileNotFoundException {
    ArrayList<Hotspot> hotspots = reader.readHotspots("src/test/testResources/TestInitialHotspots.csv");

    Hotspot hotspot998 = new Hotspot(998, 40.745968, -73.994039,
        "179 WEST 26 STREET", "MN17", "New York", 10001,
        "Free", "LinkNYC Free Wi-Fi", "mn-05-123662",
        "LinkNYC - Citybridge", "Tablet Internet -phone , Free 1 GB Wi-FI Service");

    Hotspot hotspot26 = new Hotspot(26, 40.657452, -73.963991,
        "Skate Rental Area", "BK99", "Brooklyn", 11215,
        "Limited Free", "GuestWiFi", "Prospect Park",
        "ALTICEUSA", "3 free 10 min sessions");

    ArrayList<Hotspot> expectedHotspots = new ArrayList<Hotspot>();
    expectedHotspots.add(hotspot998);
    expectedHotspots.add(hotspot26);

    for(int i=0; i<expectedHotspots.size(); i++) {
      assertTrue(expectedHotspots.get(i).equals(hotspots.get(i)));
    }
  }


  /**
   * Tests the Reader to verify that it collects the right information from a csv
   * and does not miss any entries
   */
  @Test
  public void readRetailers() throws FileNotFoundException{
    List<Retailer> Retailers = reader.readRetailers("src/test/testResources/retailers.csv");
    Retailer r1, r2, r3;
    r1 = new Retailer("Starbucks Coffee", "3 New York Plaza", "", "New York",
        "NY", 10004, "8-32", "Casual Eating & Takeout",
        "F-Coffeehouse");
    r2 = new Retailer("New York Health & Racquet Club", "39 Whitehall Street", "",
        "New York", "NY", 10004, "8-32",
        "Personal and Professional Services", "P-Athletic Clubs/Fitness");
    r3 = new Retailer("A.J. Kelly's", "6 Stone Street", "", "New York",
        "NY", 10004, "10-32", "Full Service Dining",
        "F-Irish Pub");

    List<Retailer> actualRetailers = Arrays.asList(r1, r2, r3);
    for (int i = 0; i < Retailers.size(); i++) {
      assertTrue(Retailers.get(i).equals(actualRetailers.get(i)));
    }

  }

  @Test
  public void readRetailerWithEmptyEndingCells() throws FileNotFoundException{
    List<Retailer> Retailers = reader.readRetailers("src/test/testResources/retailerEmptyEnds.csv");
    Retailer r1, r2;
    r1 = new Retailer("Starbucks Coffee", "3 New York Plaza", "", "New York",
        "NY", 10004, "", "",
        "");
    r2 = new Retailer("A.J. Kelly's", "6 Stone Street", "", "New York",
        "NY", 10004, "10-32", "Full Service Dining",
        "");
    List<Retailer> actualRetailers = Arrays.asList(r1, r2);
    for (int i=0; i < Retailers.size(); i++) {
      assertTrue(Retailers.get(i).equals(actualRetailers.get(i)));
    }
  }

  @Test
  public void readPOIS() throws FileNotFoundException {
    ArrayList<POI> POIs = reader.readUserPOIS("src/test/testResources/POIS.csv");
    ArrayList<POI> expectedPOIs = new ArrayList<POI>();

    POI p1, p2, p3;
    //Location,Name,Description
    p1 = new POI(40.7484,-73.9857,"Empire State Building","The Empire State Building is a 102-story skyscraper");
    p2 = new POI(40.7829,-73.9654,"Central Park","Central Park is an urban park in Manhattan");
    p3 = new POI(40.6892,-74.0445,"Statue Of Liberty","The Statue of Liberty is a colossal neoclassical sculpture on Liberty Island in New York Harbor");

    expectedPOIs.add(p1);
    expectedPOIs.add(p2);
    expectedPOIs.add(p3);

    for(int i = 0; i < expectedPOIs.size(); i++) {
      assertTrue(expectedPOIs.get(i).equals(POIs.get(i)));
    }
  }

}