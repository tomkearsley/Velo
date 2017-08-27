package modeltest;



import filehandler.Reader;
import java.util.ArrayList;
import model.Hotspot;
import model.POI;
import model.PublicPOI;
import model.UserPOI;
import window.App;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class POITest {
  @Test
  public void LocationTest(){
    double[] l1 = {40.719011, -73.908305};
    POI p1 = new POI(l1,"New York Center","");


    double[] l2 = {40.748652, -73.985773};
    String empireString = "Iconic, art deco office tower from 1931 with exhibits "
        + "& observatories on the 86th & 102nd floors.";
    PublicPOI pp1 = new PublicPOI(l2,"Empire State Building",empireString);

    double[] l3 = {};
    String userHouse = "My House";
    UserPOI up1 = new UserPOI(l3,"My House",userHouse);
    double[] testArray = {40.719011, -73.908305};
    double delta = 1.0;
    assertArrayEquals(testArray,p1.getLocation(),delta);

  }

}
