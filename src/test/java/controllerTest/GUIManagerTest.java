package controllerTest;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import com.sun.org.apache.regexp.internal.RE;
import controller.GUIManager;
import java.util.ArrayList;
import model.Retailer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GUIManagerTest {
  private GUIManager guiManager;
  @Before
  public void setUp() throws Exception {
    guiManager = new GUIManager();
    guiManager.populateArrayLists();
  }

  @After
  public void tearDown() throws Exception {
  }

  public Retailer getTestRetailer(int index) {
    ArrayList<Retailer> retailers = new ArrayList<Retailer>();
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
    retailers.add(r1);
    retailers.add(r2);
    retailers.add(r3);
    return retailers.get(index);
  }

  //TODO figure out the AssertArrayEquals for ArrayList<Retailer> (using retailer.equals override)

  @Test
  public void testSubMatch() {
    ArrayList<Retailer> expected = new ArrayList<Retailer>();
    expected.add(getTestRetailer(0));
    ArrayList<Retailer> matched = new ArrayList<Retailer>();
    matched = guiManager.filterRetailers(matched, 'n', "Starbucks");

    for (int i = 0; i < matched.size(); i++) {
      assertTrue(matched.get(i).equals(expected.get(i)));
    }
  }

  @Test
  public void testEqualMatch() {
    ArrayList<Retailer> expected = new ArrayList<Retailer>();
    expected.add(getTestRetailer(0));
    expected.add(getTestRetailer(1));
    expected.add(getTestRetailer(2));
    ArrayList<Retailer> matched = new ArrayList<Retailer>();
    matched = guiManager.filterRetailers(matched, 's', "NY");

    for (int i = 0; i < matched.size(); i++) {
      assertTrue(matched.get(i).equals(expected.get(i)));
    }
  }

  @Test
  public void testNoMatches() {
    ArrayList<Retailer> matched = new ArrayList<Retailer>();
    matched = guiManager.filterRetailers(matched, 's', "CA");

    assertTrue(matched.size() == 0);
  }

  @Test
  public void testZipcodeMatch() {
    ArrayList<Retailer> matched = new ArrayList<Retailer>();
    matched = guiManager.filterRetailers(matched, 'z', "10004");
    assertTrue(matched.size() == 3);
  }

}
