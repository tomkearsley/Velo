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

    ArrayList<Hotspot> hotspots = reader.readHotspots("InitialHotspots");
    ArrayList<Hotspot> expectedHotspots;

    assertEquals(1, 1);
  }

  @Test
  public void readManyHotspots() throws Exception {

    assertEquals(1, 1);
  }

}