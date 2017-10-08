package helper;


import java.util.logging.Filter;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.xml.soap.Text;
import model.Hotspot;
import model.PublicPOI;
import model.Retailer;
import model.Route;
import model.Station;
import model.UserPOI;

/**
 * static class containing all filters used by the different data tables
 */
public class filters {
  /**
   * Determines if string is an integer
   *
   * @param s String  to test
   * @return true if String is an integer
   */
  private static boolean isInteger(String s) {
    try {
      Integer i = Integer.parseInt(s);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return true;
  }
  public static FilteredList<Hotspot> hotspotFilter(TextField HotspotFilterField, FilteredList<Hotspot> fListHotspots) {
    HotspotFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
      fListHotspots.setPredicate(Hotspot -> {
        //if filter is empty, show all
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();
        // Add more Hotspot.get__'s below to include more things in the search

        if (Hotspot.getBorough().toLowerCase().contains(lowerCaseFilter) || Hotspot.getType()
            .toLowerCase().contains(lowerCaseFilter) || Hotspot.getProvider()
            .toLowerCase().contains(lowerCaseFilter)) {
          return true;
        }
        return false;
      });
    });

    return fListHotspots;
  }

  public static FilteredList<Retailer> retailerFilter(TextField RetailerFilterField, FilteredList<Retailer> fListRetailers) {
    RetailerFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
      fListRetailers.setPredicate(Retailer -> {
        //if filter is empty, show all
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();
        /*
         * Add more Retailer.get__'s below to include more things in the search
         */
        if (Retailer.getAddress().toLowerCase().contains(lowerCaseFilter) || Retailer.getName()
            .toLowerCase().contains(lowerCaseFilter)) {
          return true;
        }
        //checking for zipcode. entire zipcode must be entered before a match is found
        if (isInteger(lowerCaseFilter)) {
          Integer input = Integer.parseInt(lowerCaseFilter);
          if (input == Retailer.getZipcode()) {
            return true;
          }
        }
        return false;
      });
    });
    return fListRetailers;
  }


  public static FilteredList<PublicPOI> publicPOIFilter (TextField PublicPOIFilterField, FilteredList<PublicPOI> fListPublicPOIs){
    PublicPOIFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
      fListPublicPOIs.setPredicate(PublicPOI -> {
        //if filter is empty, show all
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();
        // Add more Hotspot.get__'s below to include more things in the search

        if (PublicPOI.getName().toLowerCase().contains(lowerCaseFilter)) {
          return true;
        }
        return false;
      });
    });
    return  fListPublicPOIs;
  }

  public static FilteredList<UserPOI> userPOIFilter (TextField UserPOIFilterField, FilteredList<UserPOI> fListUserPOIs) {
    UserPOIFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
      fListUserPOIs.setPredicate(UserPOI -> {
        //if filter is empty, show all
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();
        /*
         * Add more UserPOI.get__'s below to include more things in the search
         */
        if (UserPOI.getName().toLowerCase().contains(lowerCaseFilter)) {
          return true;
        }
        return false;
      });
    });
    return fListUserPOIs;
  }

  public static FilteredList<Station> stationFilter (TextField StationFilterField, FilteredList<Station> fListStations) {
    StationFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
      fListStations.setPredicate(Station -> {
        //if filter is empty, show all
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();
        /*
         * Add more Station.get__'s below to include more things in the search
         */
        if (Station.getName().toLowerCase().contains(lowerCaseFilter)) {
          return true;
        }
        return false;
      });
    });

    return fListStations;
  }


  public static FilteredList<Route> routeFilter (TextField RouteFilterField, FilteredList<Route> fListRoutes) {
    RouteFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
      fListRoutes.setPredicate(Route -> {
        //if filter is empty, show all
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();
        /*
         * Add more Route.get__'s below to include more things in the search
         */
        if (Route.getStartStation().getName().toLowerCase().contains(lowerCaseFilter) ||
            Route.getStopStation().getName().toLowerCase().contains(lowerCaseFilter)) {
          return true;
        }
        //filtering by gender of rider or bike ID. needs to be an exact match before anything is shown
        if (isInteger(lowerCaseFilter)) {
          Integer input = Integer.parseInt(lowerCaseFilter);
          if (Route.getBikeID() == input || Route.getGender() == input) {
            return true;
          }
        }
        return false;
      });
    });
    return fListRoutes;
  }
}
