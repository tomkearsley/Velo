package controller;

import filehandler.Reader;
import filehandler.Writer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Hotspot;
import model.POI;
import model.PublicPOI;
import model.Retailer;
import model.Route;
import model.Station;
import model.UserPOI;

public class MainAnalystController {

  // Window attributes
  @FXML private PieChart hotspotsChart;
//  @FXML private ?? retailersChart;
//  @FXML private ?? publicPOIsChart;
//  @FXML private ?? stationsChart;
  @FXML private LineChart routesChart;


  // Window methods
  /** Initialises the mainAnalyst window */
  public void initialize() {
    GUIManager.getInstanceGUIManager().populateArrayLists();

    // Set chart data
    setHotspotsChart();
  }

  public ArrayList<Retailer> getRetailers() {
    return GUIManager.getInstanceGUIManager().getRetailers();
  }

  public ArrayList<Hotspot> getHotspots() {
    return GUIManager.getInstanceGUIManager().getHotspots();
  }

  public ArrayList<Route> getRoutes() {
    return GUIManager.getInstanceGUIManager().getRoutes();
  }

  public ArrayList<PublicPOI> getPublicPOIs() {
    return GUIManager.getInstanceGUIManager().getPublicPOIs();
  }

  public ArrayList<Station> getStations() {
    return GUIManager.getInstanceGUIManager().getStations();
  }

  /* Chart Methods */
  /** Generates and populates the data for the hotspots chart */
  private void setHotspotsChart() {
    ArrayList<String> cities = new ArrayList<>();
    ArrayList<Integer> cityCounts = new ArrayList<>();
    String city;
    for (Hotspot hotspot : getHotspots()) {
      city = hotspot.getCity();
      if (cities.contains(city)) {
        Integer count = cityCounts.get(cities.indexOf(city));
        cityCounts.set(cities.indexOf(city), new Integer(count.intValue() + 1));
      } else {
        cities.add(city);
        cityCounts.add(new Integer(1));
      }
    }
    ArrayList<Data> dataPoints = new ArrayList<>();
    for (int i=0; i<cities.size(); i++) {
      dataPoints.add(new PieChart.Data(cities.get(i), cityCounts.get(i)));
    }

    ObservableList<Data> hotspotsChartData = FXCollections.observableArrayList(dataPoints);

//    ObservableList<Data> hotspotsChartData = FXCollections.observableArrayList(
//            new PieChart.Data("Grapefruit", 13),
//            new PieChart.Data("Oranges", 25),
//            new PieChart.Data("Plums", 10),
//            new PieChart.Data("Pears", 22),
//            new PieChart.Data("Apples", 30)
//    );

    hotspotsChart.setData(hotspotsChartData);

  }

  /** Generates and populates the data for the retailers chart */
  private void setRetailersChart() {

  }

  /** Generates and populates the data for the public POIs chart */
  private void setPublicPOIsChart() {

  }
  /** Generates and populates the data for the stations chart */
  private void setStationsChart() {

  }

  /** Generates and populates the data for the routes chart */
  private void setRoutesChart() {

  }

  /**
   * Tells GUIManager the user wants to log out
   */
  @FXML void logOut() throws Exception {
    GUIManager.getInstanceGUIManager().logOut();
  }

  /**
   * Allows an analyst to export the routes to a custom file
   */
  public void exportRoutes() {
    Alert alert = null;
    if (getRoutes().size() > 0) {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Export CSV File");
      fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV FILES", "*.csv"));
      File saveFile = fileChooser.showSaveDialog(null);

      if (saveFile != null) {
        try {
          Writer writer = new Writer();
          if (saveFile.getPath().endsWith(".csv")) {
            writer.writeRoutesToFile(getRoutes(), saveFile.getPath());
          } else {
            writer.writeRoutesToFile(getRoutes(), saveFile.getPath() + ".csv");
          }
          alert = new Alert(AlertType.NONE, "Route(s) successfully exported", ButtonType.OK);
        } catch (IOException e) {
          alert = new Alert(AlertType.ERROR, "Error exporting routes",
              ButtonType.OK);
        } finally {
          alert.showAndWait();
        }
      }
    } else {
      alert = new Alert(AlertType.ERROR, "No " + "Routes to export", ButtonType.OK);
      alert.showAndWait();
    }
  }

  /**
   * Allows an analyst to export the Retailers to a file
   */
  public void exportRetailers() {
    exportPOIs(getRetailers(), "Retailers");
  }

  /**
   * Allows an analyst to export the Hotspots to a file
   */
  public void exportHotspots() {
    exportPOIs(getHotspots(), "Hotspots");
  }

  /**
   * Allows an analyst to export the Public POIs to a file
   */
  public void exportPublicPOIs() {
    exportPOIs(getPublicPOIs(), "Public POIs");
  }

  /**
   * Allows an analyst to export the Stations to a file
   */
  public void exportStations() {
    exportPOIs(getStations(), "Stations");
  }

  /**
   * Exports a given POI subclass list to a custom file
   * @param POIs  The list of POI child instances
   * @param poiString The String for the alert messages
   */
  public void exportPOIs(ArrayList<? extends POI> POIs, String poiString) {
    Alert alert = null;
    System.out.println(POIs.size());
    if (POIs.size() > 0) {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Export CSV File");
      fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV FILES", "*.csv"));
      File saveFile = fileChooser.showSaveDialog(null);

      if (saveFile != null) {
        try {
          Writer writer = new Writer();
          if (saveFile.getPath().endsWith(".csv")) {
            writer.writePOIsToFile(POIs, saveFile.getPath());
          } else {
            writer.writePOIsToFile(POIs, saveFile.getPath() + ".csv");
          }
          alert = new Alert(AlertType.NONE, poiString + " successfully exported", ButtonType.OK);
        } catch (IOException e) {
          alert = new Alert(AlertType.ERROR, "Error exporting " + poiString.toLowerCase(),
              ButtonType.OK);
        } finally {
          alert.showAndWait();
        }
      }
    } else {
      alert = new Alert(AlertType.ERROR, "No " + poiString.toLowerCase() + " to export", ButtonType.OK);
      alert.showAndWait();
    }
  }

  public File getImportFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open CSV File");
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"));
    //fileChooser.setInitialDirectory(new File("~$USER")); //TODO Default directory
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
      return selectedFile;
    }
    return null;
  }

  public void importRetailers() {
    Reader reader = new Reader();
    File inFile = getImportFile();
    try {
      ArrayList<Retailer> retailersToAdd = reader.readRetailers(inFile.getPath(), true);
      int prevSize = getRetailers().size();
      GUIManager.getInstanceGUIManager().addRetailers(retailersToAdd);
      Alert alert = new Alert(AlertType.NONE,
          getRetailers().size() - prevSize + " Retailer(s) successfully imported", ButtonType.OK);
      alert.showAndWait();
    } catch (IOException| ArrayIndexOutOfBoundsException e) {
      System.out.println("Error loading retailers");
    }
  }

  public void importHotspots() {
    Reader reader = new Reader();
    File inFile = getImportFile();
    try {
      ArrayList<Hotspot> hotspotsToAdd = reader.readHotspots(inFile.getPath(), true);
      int prevSize = getHotspots().size();
      GUIManager.getInstanceGUIManager().addHotspots(hotspotsToAdd);
      Alert alert = new Alert(AlertType.NONE,
          getHotspots().size() - prevSize + " Hotspot(s) successfully imported", ButtonType.OK);
      alert.showAndWait();
    } catch (IOException| ArrayIndexOutOfBoundsException e) {
      System.out.println("Error loading hotspots");
    }
  }

  public void importPublicPOIs() {
    Reader reader = new Reader();
    File inFile = getImportFile();
    try {
      ArrayList<PublicPOI> publicPOIsToAdd = reader.readPublicPOIS(inFile.getPath(), true);
      int prevSize = getPublicPOIs().size();
      GUIManager.getInstanceGUIManager().addPublicPOIs(publicPOIsToAdd);
      Alert alert = new Alert(AlertType.NONE,
          getPublicPOIs().size() - prevSize + " Public POI(s) successfully imported", ButtonType.OK);
      alert.showAndWait();
    } catch (IOException| ArrayIndexOutOfBoundsException e) {
      System.out.println("Error loading public POIs");
    }
  }

  public void importRoutes() {
    Reader reader = new Reader();
    File inFile = getImportFile();
    try {
      ArrayList<Route> routesToAdd = reader.readRoutes(inFile.getPath(), getStations(),true);
      int prevSize = getRoutes().size();
      GUIManager.getInstanceGUIManager().addRoutes(routesToAdd);
      Alert alert = new Alert(AlertType.NONE,
          getRoutes().size() - prevSize + " Route(s) successfully imported", ButtonType.OK);
      alert.showAndWait();
    } catch (IOException| ArrayIndexOutOfBoundsException e) {
      System.out.println("Error loading routes");
    }
  }

}
