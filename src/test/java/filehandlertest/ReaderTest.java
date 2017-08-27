package filehandlertest;


import java.util.ArrayList;
import model.Hotspot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
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
  public void readSomeHotspots() throws Exception {

    ArrayList<Hotspot> hotspots = reader.readHotspots("TestInitialHotspots");


    Hotspot hotspot998 = new Hotspot();
    hotspot998.setID(998);
    hotspot998.setLatitude(40.745968);
    hotspot998.setLongitude(-73.994039);
    hotspot998.setLocationAdress("179 WEST 26 STREET");
    hotspot998.setBorough("Manhattan");
    hotspot998.setCity("New York");
    hotspot998.setPostcode(10001);
    hotspot998.setType("Free");
    hotspot998.setSSID("LinkNYC Free Wi-Fi");
    hotspot998.setName("LinkNYC - Citybridge");
    hotspot998.setProvider("LinkNYC - Citybridge");
    hotspot998.setRemarks("Tablet Internet -phone , Free 1 GB Wi-FI Service");

    Hotspot hotspot26 = new Hotspot();
    hotspot26.setID(26);
    hotspot26.setLatitude(40.657452);
    hotspot26.setLongitude(-73.963991);
    hotspot26.setLocationAdress("Skate Rental Area");
    hotspot26.setBorough("Brooklyn");
    hotspot26.setCity("Brooklyn");
    hotspot26.setPostcode(11215);
    hotspot26.setType("Limited Free");
    hotspot26.setSSID("GuestWiFi");
    hotspot26.setName("Prospect Park");
    hotspot26.setProvider("ALTICEUSA");
    hotspot26.setRemarks("3 free 10 min sessions");

    ArrayList<Hotspot> expectedHotspots = new ArrayList<Hotspot>();
    expectedHotspots.add(hotspot998);
    expectedHotspots.add(hotspot26);


    assertEquals(expectedHotspots, hotspots);
  }

}