package controller;

import filehandler.MySQL;
import filehandler.Reader;
import filehandler.Writer;
import helper.Bridge;
import helper.filters;
import helper.tableOnClickPopup;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Worker.State;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Cyclist;
import model.Hotspot;
import model.PublicPOI;
import model.Retailer;
import model.Route;
import model.Station;
import model.UserPOI;
import netscape.javascript.JSObject;


/**
 * The controller class for the main.fxml file
 */
public class MainController {

  /* Main tabs */
  @FXML private TabPane mainPane;
  @FXML private Tab mapViewTab;
  @FXML private Tab dataViewTab;
  @FXML private Tab historyViewTab;
  @FXML private Tab userViewTab;

  @FXML private AnchorPane mapViewPane;
  @FXML private Pane userPane;
  @FXML private TabPane dataTabPane;
  @FXML private WebView mapWebView;


  // ArrayLists of all data types

//  private ArrayList<Hotspot> hotspots = new ArrayList<>();
//  private ArrayList<Retailer> retailers = new ArrayList<>();
//  private ArrayList<UserPOI> userPOIs = new ArrayList<>();
//  private ArrayList<PublicPOI> publicPOIs = new ArrayList<>();
//  private ArrayList<Route> routes = new ArrayList<>();
//  private ArrayList<Station> stations = new ArrayList<>();
//
//  private ArrayList<Route> userRouteHistory = new ArrayList<>(); //EXISTING route history

//  private ArrayList<Route> userRouteNew = new ArrayList<>(); //NEW routes. have been created in this session
  // and are to be added to history //TODO implement saving of this arrayList to database
  private ArrayList<ImageView> buttons = new ArrayList<>();

  /* Data tab attributes */
  // Toggling detailed view in table view
  private boolean hotspotIsDetailed = false;
  private boolean retailerIsDetailed = false;
  //private boolean userPOIIsDetailed = false;
  //private boolean publicPOIIsDetailed = false;
  private boolean routeIsDetailed = false;
  private boolean routeHistoryIsDetailed = false;
  //private boolean stationIsDetailed = false;

  //Data tables
  @FXML private TableView<Hotspot> dataTableHotspot;
  @FXML private TableView<Retailer> dataTableRetailer;
  @FXML private TableView<PublicPOI> dataTablePublicPOI;
  @FXML private TableView<UserPOI> dataTableUserPOI;
  @FXML private TableView<Station> dataTableStation;
  @FXML private TableView<Route> dataTableRoute;

  @FXML private TableView<Route> dataTableRouteHistory;

  // Table filter fields. one for each table view
  @FXML private TextField HotspotFilterField;
  @FXML private ChoiceBox<String> HotspotFilterSelector;
  @FXML private TextField RetailerFilterField;
  @FXML private ChoiceBox<String> RetailerFilterSelector;
  @FXML private TextField PublicPOIFilterField;
  @FXML private ChoiceBox<String> PublicPOIFilterSelector;
  @FXML private TextField UserPOIFilterField;
  @FXML private ChoiceBox<String> UserPOIFilterSelector;
  @FXML private TextField StationFilterField;
  @FXML private ChoiceBox<String> StationFilterSelector;
  @FXML private TextField RouteFilterField;
  @FXML private ChoiceBox<String> RouteFilterSelector;
  @FXML private TextField RouteHistoryFilterField;
  @FXML private ChoiceBox<String> RouteHistoryFilterSelector;


  /* Map tab attributes */
  private JSObject window;
  private Bridge aBridge = new Bridge();
  @FXML private ImageView hotspot_icon_primary;
  @FXML private ImageView retailer_icon_primary;
  @FXML private ImageView poi_icon_primary;
  @FXML private ImageView hotspot_icon_secondary;
  @FXML private ImageView retailer_icon_secondary;
  @FXML private ImageView poi_icon_secondary;
  @FXML private ImageView station_icon_primary;
  @FXML private ImageView station_icon_secondary;
  @FXML private ImageView ppoi_icon_primary;
  @FXML private ImageView ppoi_icon_secondary;

  @FXML private TextField locationFrom;
  @FXML private TextField locationTo;

  private boolean hotspotsLoaded = false;
  private boolean stationsLoaded = false;
  private boolean POISLoaded = false;
  private boolean retailersLoaded = false;
  private boolean PPOISLoaded = false;

  private boolean mapLoaded = false;

  /* Account tab attributes */
  @FXML private ChoiceBox importType; // ChoiceBox for the Account import button
  @FXML private Label accountTitle;
  @FXML private Label username;
  @FXML private Label birthDate;
  @FXML private Label height;
  @FXML private Label weight;
  @FXML private Label BMI;
  @FXML private PieChart userRoutesChart;


  /* METHODS */

  /**
   * Initializes the window Populates the model structure with data from .csv files Sets GUI element
   * features (i.e. images for the tabs) populateArrayLists()
   */
  public void initialize() throws URISyntaxException {

    boolean ArrayListsIsPopulated = GUIManager.getInstanceGUIManager().populateArrayLists();
    if (!ArrayListsIsPopulated) {
      Alert loadingError = new Alert(AlertType.ERROR, "Couldn't load initial data", ButtonType.OK);
      loadingError.showAndWait();
    }

    EventHandler<KeyEvent> mapCalculateListener = (keyEvent) -> {
      if (keyEvent.getCode() == KeyCode.ENTER)  {
        displayRouteClick();
      }
    };
    locationFrom.setOnKeyPressed(mapCalculateListener);
    locationTo.setOnKeyPressed(mapCalculateListener);

    // TABS INITIALIZATION / SET IMAGES
    // TODO set tabs to images from resources/images @Andrew
    mapViewTab.setText("");
    dataViewTab.setText("");
    historyViewTab.setText("");
    userViewTab.setText("");
    mapViewTab.setGraphic(new ImageView(new Image("/image//mainMap 2.png")));
    dataViewTab.setGraphic(new ImageView(new Image("/image/mainPlace 2.png")));
    historyViewTab.setGraphic(new ImageView(new Image("/image/mainHistory 2.png")));
    userViewTab.setGraphic(new ImageView(new Image("/image/mainAccount 2.png")));

    // MAPS TAB INITIALIZATION
    URL url = getClass().getResource("/googleMaps.html");
    WebEngine mapEngine = mapWebView.getEngine();
    mapEngine.setJavaScriptEnabled(true);
    String[] dataTypeStrings = new String[]{"User POI", "Route"};
    ObservableList<String> dataTypes = FXCollections.observableArrayList(dataTypeStrings);
    importType.setItems(dataTypes);
    importType.setValue("User POI");
    setImages(); // Set map images
    mapEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
      if (newState == State.SUCCEEDED) {
        window = (JSObject) mapEngine.executeScript("window");
        window.setMember("aBridge", aBridge);
        System.out.println(
            "Initialisation complete"); // Maybe don't let them switch to map view until this is initialised or just default to table view to give time for this to load.
      }
      initializeMap();
    });
    mapEngine.load(url.toExternalForm());
    mapEngine.setJavaScriptEnabled(true);

    // DATA TAB INITIALIZATION
    initRetailerTable(); // Create tables
    initHotspotTable();
    initPublicPOITable();
    initUserPOITable();
    initStationTable();
    initRouteTable();
    initUserRouteTable();

    /* ACCOUNT TAB INITIALIZATION */
    Cyclist cyclist = GUIManager.getInstanceGUIManager().getCyclistAccount();
    accountTitle.setText(cyclist.getFirstName() + "'s Account");
    username.setText(cyclist.getUsername());
    birthDate.setText(cyclist.getDOB().format(DateTimeFormatter.ofPattern("MM/d/uuuu")));
    height.setText(Integer.toString(cyclist.getHeight()) + "\"");
    weight.setText(String.format("%.1f", cyclist.getWeight()) + "lbs");
    BMI.setText(String.format("%.2f", cyclist.getBMI()));
    setDistanceChart();

  }

  public ArrayList<Retailer> getRetailers() {
    return GUIManager.getInstanceGUIManager().getRetailers();
  }

  public ArrayList<Hotspot> getHotspots() {
    return GUIManager.getInstanceGUIManager().getHotspots();
  }

  public ArrayList<PublicPOI> getPublicPOIs() {
    return GUIManager.getInstanceGUIManager().getPublicPOIs();
  }

  public ArrayList<UserPOI> getUserPOIs() {
    return GUIManager.getInstanceGUIManager().getUserPOIs();
  }

  public ArrayList<Station> getStations() {
    return GUIManager.getInstanceGUIManager().getStations();
  }

  public ArrayList<Route> getRoutes() {
    return GUIManager.getInstanceGUIManager().getRoutes();
  }

  public ArrayList<Route> getUserRouteHistory() {
    return GUIManager.getInstanceGUIManager().getUserRouteHistory();
  }

  /* Tab action handlers */
  public void viewData() {
    mainPane.getSelectionModel().select(dataViewTab);
    //dataTabPane.toFront();
  }

  public void viewMap() {
    // mapViewPane.();
    mainPane.getSelectionModel().select(mapViewTab);
  }

  public void viewUser() {
    mainPane.getSelectionModel().select(userViewTab);
    // userPane.toFront();
  }

  public void viewHistory() {
    mainPane.getSelectionModel().select(historyViewTab);
  }

  private void loadRetailers(){
    window.setMember("aBridge", aBridge);
    window.call("loadRetailers", getRetailers());
    retailersLoaded = true;
  }

  private void loadHotspots(){
    //System.out.println(getDistance(40.758896,-73.985130,40.7678,-73.9718));
    //Run both lines of code
    window.setMember("aBridge", aBridge);
    window.call("loadHotspots", getHotspots());
    hotspotsLoaded = true;
    //testPretty();
  }

  private void loadStations(){
    window.setMember("aBridge", aBridge);
    window.call("loadStations", getStations());
    stationsLoaded = true;
  }

  public void loadPOIS(){
    window.setMember("aBridge", aBridge);
    window.call("loadPOIS", getUserPOIs());
    POISLoaded = true;
  } // TODO implement Imas

  public void loadPPOIS(){
    window.setMember("aBridge",aBridge);
    window.call("loadPPOIS",getPublicPOIs());
    PPOISLoaded = true;
  }

  private void setImages() throws URISyntaxException{
    hotspot_icon_primary.setImage(new Image(getClass().getResource("/image/hotspot-icon.png").toURI().toString()));
    retailer_icon_primary.setImage(new Image(getClass().getResource("/image/retailer-icon.png").toURI().toString()));
    poi_icon_primary.setImage(new Image(getClass().getResource("/image/marker-icon.png").toURI().toString()));
    station_icon_primary.setImage(new Image(getClass().getResource("/image/station-icon.png").toURI().toString()));
    ppoi_icon_primary.setImage(new Image(getClass().getResource("/image/ppoi-icon.png").toURI().toString()));

    hotspot_icon_secondary.setImage(new Image(getClass().getResource("/image/hotspot-pressed-icon.png").toURI().toString()));
    retailer_icon_secondary.setImage(new Image(getClass().getResource("/image/retailer-pressed-icon.png").toURI().toString()));
    poi_icon_secondary.setImage(new Image(getClass().getResource("/image/marker-pressed-icon.png").toURI().toString()));
    station_icon_secondary.setImage(new Image(getClass().getResource("/image/station-pressed-icon.png").toURI().toString()));
    ppoi_icon_secondary.setImage(new Image(getClass().getResource("/image/ppoi-pressed-icon.png").toURI().toString()));

    buttons.add(retailer_icon_primary);
    buttons.add(hotspot_icon_primary);
    buttons.add(poi_icon_primary);
    buttons.add(station_icon_primary);
    buttons.add(ppoi_icon_primary);

    buttons.add(retailer_icon_secondary);
    buttons.add(hotspot_icon_secondary);
    buttons.add(poi_icon_secondary);
    buttons.add(station_icon_secondary);
    buttons.add(ppoi_icon_secondary);

    /*
    wifi_icon_primary.setVisible(false);
    retailer_icon_primary.setVisible(false);
    poi_icon_primary.setVisible(false);
    station_icon_primary.setVisible(false);
    */

    hotspot_icon_secondary.setVisible(false);
    retailer_icon_secondary.setVisible(false);
    poi_icon_secondary.setVisible(false);
    station_icon_secondary.setVisible(false);
    ppoi_icon_secondary.setVisible(false);
  }

  private void toggleButton(int buttonNo) {
    System.out.println("Toggling button" + (buttonNo + 1));
    buttons.get(buttonNo).setVisible(false);
    int halfButtonSize = buttons.size() / 2;
    //Check if button is primary or on-click
    if (buttonNo >= halfButtonSize) {
      buttons.get(buttonNo - halfButtonSize).setVisible(true);
    } else {
      buttons.get(buttonNo + halfButtonSize).setVisible(true);
    }
  } // TODO IMAS RENAME AND JAVADOC

  public void toggleRetailersOn() {
    try {
      if (retailersLoaded) {
        showRetailers();
      } else {
        loadRetailers();
      }
    } catch (Exception e) {
      System.out.println("Error occurred while reading retailers");
      System.out.println(e);
    }
    toggleButton(0);
  } // TODO IMAS RENAME AND JAVADOC

  public void toggleHotspotsOn() {
    try {
      if (hotspotsLoaded) {
        showHotspots();
      } else {
        loadHotspots();
      }
    } catch (Exception e) {
      System.out.println("Error occurred while reading hotspots");
      System.out.println(e);
    }
    toggleButton(1);
  } // TODO IMAS RENAME AND JAVADOC

  public void togglePOISOn() {
    try {
      if (POISLoaded) {
        showPOIS();
      } else {
        loadPOIS();
      }
    } catch (Exception e) {
      System.out.println("Error occurred while reading POIs");
      System.out.println(e);
    }
    toggleButton(2);
  } // TODO IMAS RENAME AND JAVADOC

  public void toggleStationsOn() {
    try {
      if (stationsLoaded) {
        showStations();
      } else {
        loadStations();
      }
    } catch (Exception e) {
      System.out.println("Error occurred while reading stations");
      System.out.println(e);
    }
    toggleButton(3);
  } // TODO IMAS RENAME AND JAVADOC

  public void togglePPOISOn() {
    try {
      if (PPOISLoaded) {
        showPPOIS();
      } else {
        loadPPOIS();
      }
    } catch(Exception e) {
      System.out.println(e);
    }
    toggleButton(4);
  }

  public void toggleRetailersOff() {
    try {
      hideRetailers();
    } catch (netscape.javascript.JSException e) {
      //null reference error. Should occur
      System.out.println(e);
    }
    toggleButton(5);

  } // TODO IMAS RENAME AND JAVADOC

  public void toggleHotspotsOff() {
    try {
      hideHotspots();
    } catch (netscape.javascript.JSException e) {
      //null reference error. Should occur
      //System.out.println("Internal error, please report to app devs");
    }
    toggleButton(6);
  } // TODO IMAS RENAME AND JAVADOC

  public void togglePOISOff() {
    try {
      hidePOIS();
    } catch (netscape.javascript.JSException e) {
      //null reference error. Should occur
    }
    toggleButton(7);
  } // TODO IMAS RENAME AND JAVADOC

  public void toggleStationsOff() {
    try {
      hideStations();
    } catch (netscape.javascript.JSException e) {
      //null error. should be occuring
      //System.out.println("Internal error, please report to app devs");
      //System.out.println(e);
    }
    toggleButton(8);
  } // TODO IMAS RENAME AND JAVADOC

  public void togglePPOISOff() {
    try {
      hidePPOIS();
    } catch(netscape.javascript.JSException e) {
      //null error. should be occuring
      //System.out.println(e);
    }
    toggleButton(9);
  }

  private void showHotspots() {
    window.setMember("aBridge", aBridge);
    window.call("showHotspots");
  }

  private void hideHotspots() {
    window.setMember("aBridge", aBridge);
    window.call("hideHotspots");
  }

  private void showStations() {
    window.setMember("aBridge", aBridge);
    window.call("showStations");
  }

  private void hideStations() {
    window.setMember("aBridge", aBridge);
    window.call("hideStations");
  }

  private void showRetailers() {
    window.setMember("aBridge", aBridge);
    window.call("showRetailers");
  }

  private void hideRetailers() {
    window.setMember("aBridge", aBridge);
    window.call("hideRetailers");
  }

  private void showPOIS() {
    window.setMember("aBridge", aBridge);
    window.call("showPOIS");
  } // TODO implement Imas

  private void hidePOIS() {
    window.setMember("aBridge", aBridge);
    window.call("hidePOIS");
  } // TODO implement Imas

  public void showPPOIS() {
    window.setMember("aBridge", aBridge);
    window.call("showPPOIS");
  }

  public void hidePPOIS() {
    window.setMember("aBridge", aBridge);
    window.call("hidePPOIS");
  }

  public void initializeMap() {
    try {
      window.setMember("aBridge", aBridge);
      window.call("initialize");
      loadHotspots();
      loadRetailers();
      loadPPOIS();
      loadPOIS();
      loadStations();
      hideHotspots();
      hideRetailers();
      hidePPOIS();
      hidePOIS();
      hideStations();
    }
    catch (NullPointerException e) {
      //Happens as bridge isn't loaded, not allowing the hotspots to be created
      System.out.println("Map initialized confirmation");
    }
  }

  /*
  public void hideAllMarkers() {
    window.setMember("aBridge", aBridge);
    window.call("hideAllMarkers");
  }
*/

  private void prettyMarker(double lat, double lng, String info, String markerType) {
    window.setMember("aBridge", aBridge);
    window.call("prettyMarker", lat, lng, info, markerType);
  }

  private void displayRoute(double startLat, double startLng, double endLat, double endLng) {
    window.setMember("aBridge", aBridge);
    window.call("displayRoute", startLat, startLng, endLat, endLng);
  }

  public void displayRouteClick() {
    window.setMember("aBridge", aBridge);
    window.call("displayRouteClick", locationFrom.getText(), locationTo.getText());
  }

  public void nearbyMarkers(double lat,double lng,double distance,String markerType) {
    window.setMember("aBridge", aBridge);
    window.call("nearbyMarkers", lat, lng, distance, markerType);
  }

  /* DATA TAB METHODS */

  /**
   * Method to create and remove columns containing additional details within the Hotspot table Uses
   * a global boolean hotspotIsDetailed to determine state
   */
  public void toggleDetailsHotspot() {
    //simple view: id, locAddress, borough, city, type, provider, remarks
    //advanced view: include above and lat, long, postcode, SSID
    if (hotspotIsDetailed) {
      //it is detailed, so remove columns
      dataTableHotspot.getColumns().remove(7, 11);
    } else {
      TableColumn<Hotspot, Double> latCol = new TableColumn<>("Latitude");
      latCol.setCellValueFactory(new PropertyValueFactory<>("latitude"));
      TableColumn<Hotspot, Double> longCol = new TableColumn<>("Longitude");
      longCol.setCellValueFactory(new PropertyValueFactory<>("longitude"));
      TableColumn<Hotspot, String> postCodeCol = new TableColumn<>("postcode");
      postCodeCol.setCellValueFactory(new PropertyValueFactory<>("postcode"));
      TableColumn<Hotspot, String> SSIDCol = new TableColumn<>("SSID");
      SSIDCol.setCellValueFactory(new PropertyValueFactory<>("SSID"));
      dataTableHotspot.getColumns().addAll(latCol, longCol, postCodeCol, SSIDCol);
    }
    hotspotIsDetailed = !hotspotIsDetailed;
  }


  /**
   * Method to create and remove columns containing additional details within the Retailer table
   * Uses a global boolean retailerIsDetailed to determine state
   */
  public void toggleDetailsRetailer() {
    if (retailerIsDetailed) {
      dataTableRetailer.getColumns().remove(3, 8);
    } else {
      TableColumn<Retailer, String> floorCol = new TableColumn<>("Floor");
      floorCol.setCellValueFactory(new PropertyValueFactory<>("floor"));
      TableColumn<Retailer, String> cityCol = new TableColumn<>("City");
      cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
      TableColumn<Retailer, String> zipcodeCol = new TableColumn<>("Zipcode");
      zipcodeCol.setCellValueFactory(new PropertyValueFactory<>("zipcode"));
      TableColumn<Retailer, String> stateCol = new TableColumn<>("State");
      stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
      TableColumn<Retailer, String> blockCol = new TableColumn<>("Block");
      blockCol.setCellValueFactory(new PropertyValueFactory<>("block"));
      dataTableRetailer.getColumns().addAll(floorCol, cityCol, zipcodeCol, stateCol, blockCol);
    }

    retailerIsDetailed = !retailerIsDetailed;
  }

  /**
   * Method to create and remove columns containing additional details within the Route table Uses a
   * global boolean routeIsDetailed to determine state
   */
  public void toggleDetailsRoute() {
    if (routeIsDetailed) {
      dataTableRoute.getColumns().remove(5, 9);
    } else {
      TableColumn<Route, Integer> bikeIDCol = new TableColumn<Route, Integer>("Bike ID");
      bikeIDCol.setCellValueFactory(new PropertyValueFactory<Route, Integer>("bikeID"));
      TableColumn<Route, String> userTypeCol = new TableColumn<Route, String>("User Type");
      userTypeCol.setCellValueFactory(new PropertyValueFactory<Route, String>("userType"));
      TableColumn<Route, Integer> birthYearCol = new TableColumn<Route, Integer>("Birth Year");
      birthYearCol.setCellValueFactory(new PropertyValueFactory<Route, Integer>("birthYear"));
      TableColumn<Route, Integer> genderCol = new TableColumn<Route, Integer>("Gender");
      genderCol.setCellValueFactory(new PropertyValueFactory<Route, Integer>("gender"));
      dataTableRoute.getColumns().addAll(bikeIDCol, userTypeCol, birthYearCol, genderCol);
    }
    routeIsDetailed = !routeIsDetailed;
  }

  public void toggleDetailsRouteHistory() {
    if (routeHistoryIsDetailed) {
      dataTableRouteHistory.getColumns().remove(5, 7);
    } else {
      TableColumn<Route, Integer> bikeIDCol = new TableColumn<Route, Integer>("Bike ID");
      bikeIDCol.setCellValueFactory(new PropertyValueFactory<Route, Integer>("bikeID"));
      TableColumn<Route, String> userTypeCol = new TableColumn<Route, String>("User Type");
      userTypeCol.setCellValueFactory(new PropertyValueFactory<Route, String>("userType"));

      dataTableRouteHistory.getColumns().addAll(bikeIDCol, userTypeCol);
    }
    routeHistoryIsDetailed = !routeHistoryIsDetailed;
  }


  /**
   * takes the arrayList retailers, creates table columns and sets these columns along with details
   * from retailers to be shown in the table. also enables filtering and sorting
   */
  private void initRetailerTable() {
    //converting the arraylist to an observable list
    ObservableList<Retailer> oListRetailers = FXCollections.observableArrayList(getRetailers());
    //each 2 line section creates one table heading and set of values
    TableColumn<Retailer, String> nameCol = new TableColumn<>(
        "Name");//title to be written above column
    nameCol.setCellValueFactory(
        new PropertyValueFactory<>("name"));//looks for retailer.getName()
    TableColumn<Retailer, String> addressCol = new TableColumn<>("Address");
    addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

    TableColumn<Retailer, String> secondaryDescCol = new TableColumn<>(
        "Secondary Description");
    secondaryDescCol
        .setCellValueFactory(new PropertyValueFactory<>("secondaryDescription"));

    FilteredList<Retailer> fListRetailers = new FilteredList<Retailer>(oListRetailers);
    /*
     * Filtering:
     * if this returns true, the object is shown. If the filter field is empty,
     * or the attributes below match, then the object is shown
     */
    fListRetailers = filters.retailerFilter(RetailerFilterField, fListRetailers);

    RetailerFilterSelector.getItems().clear();
    RetailerFilterSelector.getItems().addAll(FXCollections.observableArrayList("Name", "Address", "Zipcode"));
    RetailerFilterSelector.getSelectionModel().selectFirst();
    RetailerFilterSelector.getSelectionModel().selectedIndexProperty().addListener(
        new ChangeListener<Number>() {
          @Override
          public void changed(ObservableValue<? extends Number> observable, Number oldValue,
              Number newValue) {
            filters.RetailerSelectedIndex = newValue.intValue();
          }
        });
    fListRetailers = filters.retailerFilter(RetailerFilterField, fListRetailers);
    /*
     * Sorting:
     * wrapping the filtered list in a sorted list allows the user to click on the title
     * of a column and sort the entries in alphanumeric order
     */
    SortedList<Retailer> sListRetailers = new SortedList<>(fListRetailers);
    sListRetailers.comparatorProperty().bind(dataTableRetailer.comparatorProperty());
//simple: name, address, description
    //detailed: above and floor, city, zipcode, state, block
    //sets up simple view
    dataTableRetailer.getColumns()
        .setAll(nameCol, addressCol, secondaryDescCol);
    dataTableRetailer.setItems(sListRetailers);

    /*
     * on click behaviour
     */
    dataTableRetailer.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
          Retailer selected_item = dataTableRetailer.getSelectionModel().getSelectedItem();
          helper.tableOnClickPopup.create("Retailer", selected_item, false);
          if (tableOnClickPopup.return_value == 1) {
            try {
              prettyMarker(selected_item.getLatitude(),
                  selected_item.getLongitude(),
                  selected_item.getAddress(),
                  "retailer");
              viewMap();
            } catch (NullPointerException e) {
              System.out.println("Map not yet loaded");
            }
          }
        }
      }
    });
  }

  /**
   * takes the arrayList hotspots, creates table columns and sets these columns along with details
   * from hotspots to be shown in the table. also enables filtering and sorting
   */
  private void initHotspotTable() {
    ObservableList<Hotspot> oListHotspots = FXCollections.observableArrayList(getHotspots());

    TableColumn<Hotspot, String> idCol = new TableColumn<>("Name");
    idCol.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Hotspot, String> locAddressCol = new TableColumn<Hotspot, String>("Address");
    locAddressCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("locationAddress"));
    TableColumn<Hotspot, String> boroughCol = new TableColumn<Hotspot, String>("Borough");
    boroughCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("borough"));
    TableColumn<Hotspot, String> cityCol = new TableColumn<Hotspot, String>("City");
    cityCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("city"));

    TableColumn<Hotspot, String> typeCol = new TableColumn<>("Type");
    typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

    TableColumn<Hotspot, String> nameCol = new TableColumn<>("Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    TableColumn<Hotspot, String> providerCol = new TableColumn<>("Provider");
    providerCol.setCellValueFactory(new PropertyValueFactory<>("provider"));
    TableColumn<Hotspot, String> remarksCol = new TableColumn<>("Remarks");
    remarksCol.setCellValueFactory(new PropertyValueFactory<>("description"));

    FilteredList<Hotspot> fListHotspots = new FilteredList<>(oListHotspots);

    HotspotFilterSelector.getItems().clear();
    HotspotFilterSelector.getItems().addAll(FXCollections.observableArrayList("Name", "Borough", "Type", "Provider"));
    HotspotFilterSelector.getSelectionModel().selectFirst();
    HotspotFilterSelector.getSelectionModel().selectedIndexProperty().addListener(
        new ChangeListener<Number>() {
          @Override
          public void changed(ObservableValue<? extends Number> observable, Number oldValue,
              Number newValue) {
            filters.HotspotSelectedIndex = newValue.intValue();

          }
        });
    fListHotspots = filters.hotspotFilter(HotspotFilterField, fListHotspots);

    SortedList<Hotspot> sListHotspots = new SortedList<>(fListHotspots);
    sListHotspots.comparatorProperty().bind(dataTableHotspot.comparatorProperty());

    //sets up simple view.
    dataTableHotspot.getColumns()
        .setAll(idCol, locAddressCol, boroughCol, cityCol, typeCol, providerCol, remarksCol);

    dataTableHotspot.setItems(sListHotspots);

    /*
     * on click behaviour
     */
    dataTableHotspot.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
          Hotspot selected_item = dataTableHotspot.getSelectionModel().getSelectedItem();
          helper.tableOnClickPopup.create("Hotspot",  selected_item, false);
          if (tableOnClickPopup.return_value == 1) {
            try {
              prettyMarker(selected_item.getLatitude(), selected_item.getLongitude(),
                  selected_item.getLocationAddress(), "hotspot");
              viewMap();
            } catch (NullPointerException e) {
              System.out.println("Map not yet loaded");
            }
          }
        }
      }
    });
  }

  /**
   * takes the arrayList publicPOIs, creates table columns and sets these columns along with details
   * from publicPOIs to be shown in the table. also enables filtering and sorting
   */
  private void initPublicPOITable() {

    //lat, long, name, description
    ObservableList<PublicPOI> oListPublicPOIs = FXCollections.observableArrayList(getPublicPOIs());
    TableColumn<PublicPOI, Double> latCol = new TableColumn<>("Latitude");
    latCol.setCellValueFactory(new PropertyValueFactory<>("latitude"));
    TableColumn<PublicPOI, Double> longCol = new TableColumn<>("Longitude");
    longCol.setCellValueFactory(new PropertyValueFactory<>("longitude"));
    TableColumn<PublicPOI, String> nameCol = new TableColumn<>("Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    TableColumn<PublicPOI, String> descriptionCol = new TableColumn<>(
        "Description");
    descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

    FilteredList<PublicPOI> fListPublicPOIs = new FilteredList<>(oListPublicPOIs);
    PublicPOIFilterSelector.getItems().clear();
    PublicPOIFilterSelector.getItems().addAll(FXCollections.observableArrayList("Name"));
    PublicPOIFilterSelector.getSelectionModel().selectFirst();
    PublicPOIFilterSelector.getSelectionModel().selectedIndexProperty().addListener(
        new ChangeListener<Number>() {
          @Override
          public void changed(ObservableValue<? extends Number> observable, Number oldValue,
              Number newValue) {
            filters.PublicPOISelectedIndex = newValue.intValue();
          }
        });
    fListPublicPOIs = filters.publicPOIFilter(PublicPOIFilterField, fListPublicPOIs);


    SortedList<PublicPOI> sListPublicPOIs = new SortedList<>(fListPublicPOIs);
    sListPublicPOIs.comparatorProperty().bind(dataTablePublicPOI.comparatorProperty());

    dataTablePublicPOI.getColumns().setAll(nameCol, latCol, longCol, descriptionCol);
    dataTablePublicPOI.setItems(sListPublicPOIs);

    /*
     * on click behaviour
     */
    dataTablePublicPOI.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
          PublicPOI selected_item = dataTablePublicPOI.getSelectionModel().getSelectedItem();
          tableOnClickPopup.create("Public POI",  selected_item, false);
          if (tableOnClickPopup.return_value == 1) {
            try {
              prettyMarker(selected_item.getLatitude(), selected_item.getLongitude(),
                  selected_item.getName(), "public-poi");
              viewMap();
            } catch (NullPointerException e) {
              System.out.println("Map not yet loaded");
            }
          }
        }
      }
    });
  }

  /**
   * takes the arrayList userPOIs, creates table columns and sets these columns along with details
   * from userPOIs to be shown in the table. also enables filtering and sorting
   */
  public void initUserPOITable() {
    //lat, long, name, description
    ObservableList<UserPOI> oListUserPOIs = FXCollections.observableArrayList(getUserPOIs());

    TableColumn<UserPOI, Double> latCol = new TableColumn<>("Latitude");
    latCol.setCellValueFactory(new PropertyValueFactory<>("latitude"));
    TableColumn<UserPOI, Double> longCol = new TableColumn<>("Longitude");
    longCol.setCellValueFactory(new PropertyValueFactory<>("longitude"));
    TableColumn<UserPOI, String> nameCol = new TableColumn<>("Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    TableColumn<UserPOI, String> descriptionCol = new TableColumn<>("Description");
    descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

    FilteredList<UserPOI> fListUserPOIs = new FilteredList<>(oListUserPOIs);

    UserPOIFilterSelector.getItems().clear();
    UserPOIFilterSelector.getItems().addAll(FXCollections.observableArrayList("Name"));
    UserPOIFilterSelector.getSelectionModel().selectFirst();
    UserPOIFilterSelector.getSelectionModel().selectedIndexProperty().addListener(
        new ChangeListener<Number>() {
          @Override
          public void changed(ObservableValue<? extends Number> observable, Number oldValue,
              Number newValue) {
            filters.UserPOISelectedIndex = newValue.intValue();
          }
        });

    fListUserPOIs = filters.userPOIFilter(UserPOIFilterField, fListUserPOIs);



    SortedList<UserPOI> sListUserPOIs = new SortedList<>(fListUserPOIs);
    sListUserPOIs.comparatorProperty().bind(dataTableUserPOI.comparatorProperty());

    dataTableUserPOI.getColumns().setAll(nameCol, latCol, longCol, descriptionCol);
    dataTableUserPOI.setItems(sListUserPOIs);

    /*
     * on-click behaviour
     */
    dataTableUserPOI.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
          UserPOI selected_item = dataTableUserPOI.getSelectionModel().getSelectedItem();
          tableOnClickPopup.create("User POI",  selected_item, false);
          if (tableOnClickPopup.return_value == 1) {
            try {
              prettyMarker(selected_item.getLatitude(), selected_item.getLongitude(),
                  selected_item.getName(), "user-poi");
              viewMap();
            } catch (NullPointerException e) {
              System.out.println("Map not yet loaded");
            }
          }
        }
      }
    });
  }

  /**
   * takes the arrayList stations, creates table columns and sets these columns along with details
   * from stations to be shown in the table. also enables filtering and sorting
   */
  public void initStationTable() {
    //latitude, longitude, name, ID
    ObservableList<Station> oListStations = FXCollections.observableArrayList(getStations());

    TableColumn<Station, Double> latCol = new TableColumn<>("Latitude");
    latCol.setCellValueFactory(new PropertyValueFactory<>("latitude"));
    TableColumn<Station, Double> longCol = new TableColumn<>("Longitude");
    longCol.setCellValueFactory(new PropertyValueFactory<>("longitude"));
    TableColumn<Station, String> nameCol = new TableColumn<>("Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    TableColumn<Station, Integer> idCol = new TableColumn<>("ID");
    idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

    FilteredList<Station> fListStations = new FilteredList<>(oListStations);

    StationFilterSelector.getItems().clear();
    StationFilterSelector.getItems().addAll(FXCollections.observableArrayList("Name"));
    StationFilterSelector.getSelectionModel().selectFirst();
    StationFilterSelector.getSelectionModel().selectedIndexProperty().addListener(
        new ChangeListener<Number>() {
          @Override
          public void changed(ObservableValue<? extends Number> observable, Number oldValue,
              Number newValue) {
            filters.StationSelectedIndex = newValue.intValue();
          }
        });
    fListStations = filters.stationFilter(StationFilterField, fListStations);

    SortedList<Station> sListStations = new SortedList<>(fListStations);
    sListStations.comparatorProperty().bind(dataTableStation.comparatorProperty());

    dataTableStation.getColumns().setAll(nameCol, latCol, longCol, idCol);
    dataTableStation.setItems(sListStations);

    /*
     * on-click behaviour
     */
    dataTableStation.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
          Station selected_item = dataTableStation.getSelectionModel().getSelectedItem();
          tableOnClickPopup.create("Public POI",  selected_item, false);
          if (tableOnClickPopup.return_value == 1) {
            try {
              prettyMarker(selected_item.getLatitude(), selected_item.getLongitude(),
                  selected_item.getName(), "station");
              viewMap();
            } catch (NullPointerException e) {
              System.out.println("Map not yet loaded");
            }
          }
        }
      }
    });
  }

  /**
   * takes the arrayList routes, creates table columns and sets these columns along with details
   * from routes to be shown in the table. also enables filtering and sorting
   */
  public void initRouteTable() {
    //startStation, stopStation, startDateTime, endDateTime, bikeID, userType, birthYear, gender
    ObservableList<Route> oListRoutes = FXCollections.observableArrayList(getRoutes());

    TableColumn<Route, Station> startStationCol = new TableColumn<>("Start Station");
    startStationCol
        .setCellValueFactory(new PropertyValueFactory<>("startStationName"));
    TableColumn<Route, Station> stopStationCol = new TableColumn<>("Stop Station");
    stopStationCol.setCellValueFactory(new PropertyValueFactory<>("stopStationName"));
    TableColumn<Route, Date> startDateTimeCol = new TableColumn<>("Start Time");
    startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
    TableColumn<Route, Date> endDateTimeCol = new TableColumn<>("Stop Time");
    endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("stopDate"));
    TableColumn<Route, Integer> durationCol = new TableColumn<>("Duration");
    durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));

    FilteredList<Route> fListRoutes = new FilteredList<>(oListRoutes);

    RouteFilterSelector.getItems().clear();
    RouteFilterSelector.getItems().addAll(FXCollections.observableArrayList("Starts at:", "Ends at:", "Bike ID", "Gender"));
    RouteFilterSelector.getSelectionModel().selectFirst();
    RouteFilterSelector.getSelectionModel().selectedIndexProperty().addListener(
        new ChangeListener<Number>() {
          @Override
          public void changed(ObservableValue<? extends Number> observable, Number oldValue,
              Number newValue) {
            filters.RouteSelectedIndex = newValue.intValue();
          }
        });
    fListRoutes = filters.routeFilter(RouteFilterField, fListRoutes);


    SortedList<Route> sListRoutes = new SortedList<>(fListRoutes);
    sListRoutes.comparatorProperty().bind(dataTableRoute.comparatorProperty());
    //simple: start and end stations, start and end times
    //sets up simple view
    dataTableRoute.getColumns()
        .setAll(startStationCol, stopStationCol, startDateTimeCol, endDateTimeCol, durationCol);
    dataTableRoute.setItems(sListRoutes);

    dataTableRoute.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
          Route selected_item = dataTableRoute.getSelectionModel().getSelectedItem();
          tableOnClickPopup.create("Route",  selected_item, true);
          if (tableOnClickPopup.return_value == 1) {
            try {

              displayRoute(selected_item.getStartStation().getLatitude(),
                  selected_item.getStartStation().getLongitude(),
                  selected_item.getStopStation().getLatitude(),
                  selected_item.getStopStation().getLongitude());
              viewMap();
            } catch (NullPointerException e) {
              System.out.println("Map not yet loaded");
            }
          } else if (tableOnClickPopup.return_value == 2) {
            selected_item.addTravelledBy(GUIManager.getInstanceGUIManager().getCyclistAccount().getUsername());
            GUIManager.getInstanceGUIManager().addUserRouteHistory(selected_item);
            setDistanceChart();
            //TODO update selected_item in the database
            initUserRouteTable();
          }
        }
      }
    });
  }

  /**
   * Initialiser for the route history table in the user tab
   */
  public void initUserRouteTable() {
    //ObservableList<Route> oListUserRoutes = FXCollections.observableArrayList(getUserRouteHistory());
    ObservableList<Route> oListUserRoutes = FXCollections.observableArrayList(getRoutes());
    TableColumn<Route, Station> startStationCol = new TableColumn<>("Start Station");
    startStationCol
        .setCellValueFactory(new PropertyValueFactory<>("startStationName"));
    TableColumn<Route, Station> stopStationCol = new TableColumn<>("Stop Station");
    stopStationCol.setCellValueFactory(new PropertyValueFactory<>("stopStationName"));
    TableColumn<Route, Date> startDateTimeCol = new TableColumn<>("Start Time");
    startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
    TableColumn<Route, Date> endDateTimeCol = new TableColumn<>("Stop Time");
    endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("stopDate"));
    TableColumn<Route, Integer> durationCol = new TableColumn<>("Duration");
    durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));

    FilteredList<Route> fListUserRoutes = new FilteredList<>(oListUserRoutes);
        fListUserRoutes.setPredicate(Route -> {
      if(Route.travelledByContains(GUIManager.getInstanceGUIManager().getCyclistAccount().getUsername())) {
        return true;
      }
      return false;
    });

    RouteHistoryFilterSelector.getItems().clear();
    RouteHistoryFilterSelector.getItems().addAll(FXCollections.observableArrayList("Starts at:", "Ends at:", "Bike ID"));
    RouteHistoryFilterSelector.getSelectionModel().selectFirst();
    RouteHistoryFilterSelector.getSelectionModel().selectedIndexProperty().addListener(
        new ChangeListener<Number>() {
          @Override
          public void changed(ObservableValue<? extends Number> observable, Number oldValue,
              Number newValue) {
            filters.RouteHistorySelectedIndex = newValue.intValue();
          }
        });
    fListUserRoutes = filters.routeHistoryFilter(RouteHistoryFilterField, fListUserRoutes, GUIManager.getInstanceGUIManager().getCyclistAccount().getUsername());

    SortedList<Route> sListRoutes = new SortedList<>(fListUserRoutes);
    sListRoutes.comparatorProperty().bind(dataTableRoute.comparatorProperty());
    //simple: start and end stations, start and end times
    //sets up simple view
    dataTableRouteHistory.getColumns()
        .setAll(startStationCol, stopStationCol, startDateTimeCol, endDateTimeCol, durationCol);
    dataTableRouteHistory.setItems(sListRoutes);

    dataTableRouteHistory.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
          Route selected_item = dataTableRouteHistory.getSelectionModel().getSelectedItem();
          System.out.println(selected_item.getTravelledBy());
          System.out.println(selected_item.travelledByContains(GUIManager.getInstanceGUIManager().getCyclistAccount().getUsername()));
          tableOnClickPopup.create("Personal Route",  selected_item, false);
          if (tableOnClickPopup.return_value == 1) {
            try {

              displayRoute(selected_item.getStartStation().getLatitude(),
                  selected_item.getStartStation().getLongitude(),
                  selected_item.getStopStation().getLatitude(),
                  selected_item.getStopStation().getLongitude());
              viewMap();
            } catch (NullPointerException e) {
              System.out.println("Map not yet loaded");
            }
          } else if (tableOnClickPopup.return_value == 2) {
            selected_item.removeTravelledBy(GUIManager.getInstanceGUIManager().getCyclistAccount().getUsername());
            GUIManager.getInstanceGUIManager().removeUserRouteHistory(selected_item);
            //TODO update selected_item in the database
            initUserRouteTable();
          }
        }
      }
    });
  }

  /**
   * Launches the UserPOIForm window by telling GUIManager to launch that window
   */
  @FXML private void addUserPOI() throws Exception {
    GUIManager.getInstanceGUIManager().addPlace();
  }

  /* ACCOUNT TAB METHODS */

  /**
   * Launches the updateAccount window Tells the GUIManager to change to updateAccount window
   */
  public void updateAccount() throws Exception {
    GUIManager.getInstanceGUIManager().updateAccount();
  }

  /**
   * Tells GUIManager the user wants to log out
   */
  @FXML void logOut() throws Exception {
    //TODO make this clear any user data (route history)
    GUIManager.getInstanceGUIManager().logOut();
  }

  private void setChartLabels(PieChart chart) {
    final Label caption = new Label("");
    caption.setTextFill(Color.DARKORANGE);
    caption.setStyle("-fx-font: 24 arial;");

    for (final PieChart.Data data : chart.getData()) {
      data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
          new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
              caption.setTranslateX(e.getSceneX());
              caption.setTranslateY(e.getSceneY());
              caption.setText(String.valueOf(data.getPieValue()) + "%");
            }
          });
    }
  }

  /** Generates and populates the data for the user's distance chart *
   * The chart displays user distance travelled over the last four weeks
   * This week, last week, two weeks ago, and three weeks ago in a 100% pie chart
   */
  private void setDistanceChart() {
    ArrayList<String> durations = new ArrayList<>();
    ArrayList<Integer> durationCount = new ArrayList<>();

    int duration;
    String durationStr;
    for (Route route : GUIManager.getInstanceGUIManager().getUserRouteHistory()) {
      duration = route.getDuration();
      if (duration < 120) {
        durationStr = "< 2 minutes";
      } else if (duration < 300) {
        durationStr = "2-5 minutes";
      } else if (duration < 900) {
        durationStr = "5-15 minutes";
      } else if (duration < 1800) {
        durationStr = "15-30 minutes";
      } else {
        durationStr = "> 30 minutes";
      }

      if (durations.contains(durationStr)) {
        Integer count = durationCount.get(durations.indexOf(durationStr));
        durationCount.set(durations.indexOf(durationStr), count+1);
      } else {
        durations.add(durationStr);
        durationCount.add(1);
      }
    }

    ArrayList<Data> dataPoints = new ArrayList<>();
    for (int i=0; i<durations.size(); i++) {
      dataPoints.add(new PieChart.Data(durations.get(i), durationCount.get(i)));
    }

    ObservableList<Data> userRoutesChartData = FXCollections.observableArrayList(dataPoints);

    userRoutesChart.setData(userRoutesChartData);
    setChartLabels(userRoutesChart);
    // TODO set this to actual data @Kyle @Andrew

  }

  /**
   * Allows the user to select a file to import data from, at which case the Import Function is
   * called.
   */
  public void selectImportFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open CSV File");
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"));
    //fileChooser.setInitialDirectory(new File("~$USER")); //TODO Default directory
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
      importData(selectedFile.getPath());
    }
  }

  /**
   * Allows the user to export their routes to a custom csv file. Shows alert based on result
   */
  public void exportUserRoutes() {
    Alert alert = null;
    if (getUserRouteHistory().size() == 0) {
      alert = new Alert(AlertType.ERROR, "Route history is empty", ButtonType.OK);
      alert.showAndWait();
    } else {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Export CSV File");
      fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV FILES", "*.csv"));
      File saveFile = fileChooser.showSaveDialog(null);

      if (saveFile != null) {
        try {
          Writer writer = new Writer();
          if (saveFile.getPath().endsWith(".csv")) {
            writer.writeRoutesToFile(getUserRouteHistory(), saveFile.getPath());
          } else {
            writer.writeRoutesToFile(getUserRouteHistory(), saveFile.getPath() + ".csv");
          }
          alert = new Alert(AlertType.NONE, "Routes successfully exported", ButtonType.OK);

        } catch (IOException e) {
          alert = new Alert(AlertType.ERROR, "Error exporting routes", ButtonType.OK);
        } finally {
          alert.showAndWait();
        }
      }
    }
  }

  /**
   * Imports additional items from a csv file to the appropriate ArrayList based on the selected
   * datatype from the ChoiceBox
   */
  public void importData(String importFilePath) { //TODO expand for rest of data types, file path differences
    Reader reader = new Reader();
    int prevSize;
    Alert alert = null;
    if (importType.getValue().equals("User POI")) {
      try {
        ArrayList<UserPOI> userPOIsToAdd = reader
            .readUserPOIS(importFilePath, true);
        prevSize = getUserPOIs().size();
        GUIManager.getInstanceGUIManager().addUserPOIs(userPOIsToAdd);
        alert = new Alert(AlertType.NONE,
            getUserPOIs().size() - prevSize + " User POIs succesfully imported", ButtonType.OK);
        initUserPOITable();
      } catch (IOException| ArrayIndexOutOfBoundsException e) {
        System.out.println("Error loading user POIs");
      }
    } else {
      try {
        MySQL mysql = new MySQL();

        ArrayList<Route> routesToAdd = reader
            .readRoutes(importFilePath, getStations(), true);
        int size = routesToAdd.size();
        String username = GUIManager.getInstanceGUIManager().getCyclistAccount().getUsername();
        try {
          Connection conn = mysql.getConnection();
          for (int i = 0; i < size; i++) {
            mysql.insertRoute(conn,routesToAdd.get(i),username);
          }

        } catch (Exception e) {
          System.out.println(e);
        }


        prevSize = getRoutes().size();
        GUIManager.getInstanceGUIManager().addRoutes(routesToAdd);
        alert = new Alert(AlertType.NONE, getRoutes().size() - prevSize + " Routes succesfully imported",
            ButtonType.OK);
        initRouteTable();
      } catch (IOException| ArrayIndexOutOfBoundsException e) {
        System.out.println("Error loading routes");
      }
    }
    if (alert != null) {
      alert.showAndWait();
    }
  }

}