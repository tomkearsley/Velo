package filehandlertest;


import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
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
   * It ensure the application is in a state that you desire (A fresh game state).
   */
  @Before
  public void setUp() {
    reader = new Reader();
  }

  @Test
  public void readHotspots() {
    assertEquals(1, 1);
  }


  /**
   * Tests the Reader to verify that it collects the right information from a csv
   * and does not miss any entries
   */
  @Test
  public void readRetailers() throws FileNotFoundException{
    List<Retailer> Retailers = reader.readRetailers("src/test/testResources/retailers.csv");
    Retailer r1, r2, r3;
    r1 = new Retailer("Starbucks Coffee", "3 New York Plaza", "", "New York", "NY", 10004, "8-32", "Casual Eating & Takeout", "F-Coffeehouse");
    r2 = new Retailer("New York Health & Racquet Club", "39 Whitehall Street", "", "New York", "NY", 10004, "8-32", "Personal and Professional Services", "P-Athletic Clubs/Fitness");
    r3 = new Retailer("A.J. Kelly's", "6 Stone Street", "", "New York", "NY", 10004, "10-32", "Full Service Dining", "F-Irish Pub");
    List<Retailer> actualRetailers = Arrays.asList(r1, r2, r3);
    for (int i = 0; i < Retailers.size(); i++){
      assertTrue(Retailers.get(i).equals(actualRetailers.get(i)));
    }

  }

  /**
   * This function is called at the end of every test that is run.
   */
  @After
  public void tearDown() {
    reader = null;
  }

}