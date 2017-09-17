package controller;

import filehandler.Reader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import model.Hotspot;
import model.PublicPOI;
import model.Retailer;
import model.Route;
import model.UserPOI;

/**
 * Created by Tyler Kennedy on 29/8/17
 *
 * TODO change bottom bar on tableView to radio buttons for different tables and a "GO" button to link here
 */

public class GUIManager {

  ArrayList<Hotspot> hotspots = new ArrayList<Hotspot>();
  ArrayList<Retailer> retailers = new ArrayList<Retailer>();
  ArrayList<UserPOI> userPOIs = new ArrayList<UserPOI>();
  ArrayList<PublicPOI> publicPOIs = new ArrayList<PublicPOI>();
  ArrayList<Route> routes = new ArrayList<Route>();
  //TODO use reader to populate these ArrayLists


  //Data table
  @FXML
  private TableView rawDataTable;
  @FXML
  private SplitPane dataSplitPane;
  @FXML
  private Pane mapViewPane;
  @FXML
  private ChoiceBox<DataType> dataTypeChoiceBox;

  @FXML
  private WebView mapWebView;



  public boolean populateArrayLists() {
    Reader rdr = new Reader();
    try{
      //TODO update sources when reader is fixed
      hotspots = rdr.readHotspots("src/test/testResources/TestInitialHotspots.csv");
      retailers = rdr.readRetailers("src/test/testResources/retailers.csv");

    }
    catch(FileNotFoundException e){
      System.out.println("File not found");
      e.printStackTrace();
      //TODO bring up warning window when that is implemented
    }
    return true;
  }


  /**
   * Runs at startup
   * Populates the model structure with data from .csv files
   * TODO adapt to using database primarily with csv as fallback
   */
  public void initialize(){
    populateArrayLists();
    dataViewRetailers(); /* some initial data so the table isn't empty on startup */
    dataTypeChoiceBox.getItems().setAll(DataType.values());

    File f = new File("src/main/resources/googleMaps.html");
    mapWebView.getEngine().load(f.toURI().toString());
  }

  /*
  Action handlers
   */
  public void viewData() {
    dataSplitPane.toFront();
  }
  public void viewMap() {
    mapViewPane.toFront();
  }

  /**
   * converts the arrayList of retailers to an observableList
   * creates columns and sets these columns and values to be displayed in rawDataTable
   */
  public void dataViewRetailers() {
    //converting the arraylist to an observable list
    ObservableList<Retailer>  oListRetailers = FXCollections.observableArrayList(retailers);
    //each 2 line section creates one table heading and set of values
    //TODO lat and long from address?
    TableColumn<Retailer, String> nameCol = new TableColumn<Retailer, String>("Name");//title to be written above column
    nameCol.setCellValueFactory(new PropertyValueFactory<Retailer, String>("name"));//looks for retailer.getName()
    TableColumn<Retailer, String> addressCol = new TableColumn<Retailer, String>("Address");
    addressCol.setCellValueFactory(new PropertyValueFactory<Retailer, String>("address"));
    TableColumn<Retailer, String> floorCol = new TableColumn<Retailer, String>("Floor");
    floorCol.setCellValueFactory(new PropertyValueFactory<Retailer, String>("floor"));
    TableColumn<Retailer, String> cityCol = new TableColumn<Retailer, String>("city");
    cityCol.setCellValueFactory(new PropertyValueFactory<Retailer, String>("city"));
    TableColumn<Retailer, String> zipcodeCol = new TableColumn<Retailer, String>("Zipcode");
    zipcodeCol.setCellValueFactory(new PropertyValueFactory<Retailer, String>("zipcode"));
    TableColumn<Retailer, String> stateCol = new TableColumn<Retailer, String>("State");
    stateCol.setCellValueFactory(new PropertyValueFactory<Retailer, String>("state"));
    TableColumn<Retailer, String> blockCol = new TableColumn<Retailer, String>("Block");
    blockCol.setCellValueFactory(new PropertyValueFactory<Retailer, String>("block"));
    TableColumn<Retailer, String> secondaryDescCol = new TableColumn<Retailer, String>("Secondary Description");
    secondaryDescCol.setCellValueFactory(new PropertyValueFactory<Retailer, String>("secondaryDescription"));

    rawDataTable.getColumns().setAll(nameCol, addressCol, floorCol, cityCol, zipcodeCol, stateCol, blockCol, secondaryDescCol);
    rawDataTable.setItems(oListRetailers);
  }
  public void dataViewHotspots() {
    ObservableList<Hotspot> oListHotspots = FXCollections.observableArrayList(hotspots);

    TableColumn<Hotspot, String> idCol = new TableColumn<Hotspot, String>("Name");
    idCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("name"));
    TableColumn<Hotspot, Double> latCol = new TableColumn<Hotspot, Double>("Latitude");
    latCol.setCellValueFactory(new PropertyValueFactory<Hotspot, Double>("latitude"));
    TableColumn<Hotspot, Double> longCol = new TableColumn<Hotspot, Double>("Longitude");
    longCol.setCellValueFactory(new PropertyValueFactory<Hotspot, Double>("longitude"));
    TableColumn<Hotspot, String> locAddressCol = new TableColumn<Hotspot, String>("Address");
    locAddressCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("locationAddress"));
    TableColumn<Hotspot, String> boroughCol = new TableColumn<Hotspot, String>("Borough");
    boroughCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("borough"));
    TableColumn<Hotspot, String> cityCol = new TableColumn<Hotspot, String>("City");
    cityCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("city"));
    TableColumn<Hotspot, String> postCodeCol = new TableColumn<Hotspot, String>("postcode");
    postCodeCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("postcode"));
    TableColumn<Hotspot, String> typeCol = new TableColumn<Hotspot, String>("Type");
    typeCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("type"));
    TableColumn<Hotspot, String> SSIDCol = new TableColumn<Hotspot, String>("SSID");
    SSIDCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("SSID"));
    TableColumn<Hotspot, String> nameCol = new TableColumn<Hotspot, String>("Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("name"));
    TableColumn<Hotspot, String> providerCol = new TableColumn<Hotspot, String>("Provider");
    providerCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("provider"));
    TableColumn<Hotspot, String> remarksCol = new TableColumn<Hotspot, String>("Remarks");
    remarksCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("remarks"));

    rawDataTable.getColumns().setAll(idCol, latCol, longCol, locAddressCol, boroughCol, cityCol, postCodeCol, typeCol, SSIDCol, providerCol, remarksCol); //something something
    rawDataTable.setItems(oListHotspots);
  }
  //TODO ask about POIs, which to view, how locations are being stored (lat/long together or not?)
  public void dataViewPublicPOIs() {
    //lat, long, name, description
    ObservableList<PublicPOI> oListPublicPOIs = FXCollections.observableArrayList(publicPOIs);
    TableColumn<PublicPOI, Double> latCol = new TableColumn<PublicPOI, Double>("Latitude");
    latCol.setCellValueFactory(new PropertyValueFactory<PublicPOI, Double>("latitude"));
    TableColumn<PublicPOI, Double> longCol = new TableColumn<PublicPOI, Double>("Longitude");
    longCol.setCellValueFactory(new PropertyValueFactory<PublicPOI, Double>("longitude"));
    TableColumn<PublicPOI, String>  nameCol = new TableColumn<PublicPOI, String>("Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<PublicPOI, String>("name"));
    TableColumn<PublicPOI, String> descriptionCol = new TableColumn<PublicPOI, String>("Description");
    descriptionCol.setCellValueFactory(new PropertyValueFactory<PublicPOI, String>("description"));

    rawDataTable.getColumns().setAll(nameCol, latCol, longCol, descriptionCol);
    rawDataTable.setItems(oListPublicPOIs);
  }
  //TODO fill out stubs
  public void dataViewUserPOIs() {
    //lat, long, name, description
    ObservableList<UserPOI> oListUserPOIs = FXCollections.observableArrayList(userPOIs);
    TableColumn<UserPOI, Double> latCol = new TableColumn<UserPOI, Double>("Latitude");
    latCol.setCellValueFactory(new PropertyValueFactory<UserPOI, Double>("latitude"));
    TableColumn<UserPOI, Double> longCol = new TableColumn<UserPOI, Double>("Longitude");
    longCol.setCellValueFactory(new PropertyValueFactory<UserPOI, Double>("longitude"));
    TableColumn<UserPOI, String>  nameCol = new TableColumn<UserPOI, String>("Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<UserPOI, String>("name"));
    TableColumn<UserPOI, String> descriptionCol = new TableColumn<UserPOI, String>("Description");
    descriptionCol.setCellValueFactory(new PropertyValueFactory<UserPOI, String>("description"));

    rawDataTable.getColumns().setAll(nameCol, latCol, longCol, descriptionCol);
    rawDataTable.setItems(oListUserPOIs);
  }

  public void dataViewStations() {

  }

  public void dataViewRoutes() {
    //TODO figure out how to show this
    //for point in route create column..?
  }
  public void dataViewSelected() {
    DataType selected = dataTypeChoiceBox.getValue();
    switch (selected) {
      case HOTSPOT:
        dataViewHotspots();
        break;
      case RETAILER:
        dataViewRetailers();
        break;
      case PUBLICPOI:
        dataViewPublicPOIs();
        break;
      case USERPOI:
        dataViewUserPOIs();
        break;
      case STATION:
        dataViewStations();
        break;
      case ROUTE:
        dataViewRoutes();
        break;
      }
    }

  /**
   * Fetches a specified attribute of the retailer instance. Used for filtering of retailers
   * using one function that can control the filter field through this parameter
   * @param retailer The retailer instance to fetch attributes from
   * @param field The field char to be filtered ('n' for name, 'a' for address, etc...)
   * @return The string value of that attribute
   */
  public String getRetailerField(Retailer retailer, char field) {
    switch(field) {
      case 'n':
        return retailer.getName();
      case 'a':
        return retailer.getAddress();
      case 'f':
        return retailer.getFloor();
      case 'c':
        return retailer.getCity();
      case 's':
        return retailer.getState();
      case 'z':
        return Integer.toString(retailer.getZipcode());
      case 'b':
        return retailer.getBlock();
      default:
        return "Invalid field name";
    }
  }

  /**
   * Filters the list of retailers to match a certain term in a given field
   * @param matchList The list of currently matched retailers (empty for filtering original list,
   *                  populated for adding additional filtering (ie filtering by name, then state)
   * @param field The field to be filtered on, standard is first character of field name ('n' for name)
   * @param term  The field to search against. If the attribute is a string, searches for attribute containing
   *              the term. If integer, searches for direct matches
   * @return  The filtered list of retailers
   */
  public ArrayList<Retailer> filterRetailers(ArrayList<Retailer> matchList, char field, String term) {
    boolean firstRun = matchList.size() == 0;
    if (firstRun) {
      for (Retailer retailer : retailers) {
        if (field == 'z') {
          if (getRetailerField(retailer, field).equals(term)) {
            matchList.add(retailer);
          }
        } else if (getRetailerField(retailer, field).contains(term)) {
          matchList.add(retailer);
        }
      }
    } else {
      for (Retailer retailer : matchList) {
        if (field == 'z') {
          if (!getRetailerField(retailer, field).equals(term)) {
            matchList.remove(retailer);
          }
        } else if (!getRetailerField(retailer, field).contains(term)) {
          matchList.remove(retailer);
        }
      }
    }
    return matchList;
  }

}