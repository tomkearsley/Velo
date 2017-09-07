package controller;

import filehandler.Reader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.Hotspot;
import model.Mappable;
import model.POI;
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
  Reader rdr = new Reader();

  ArrayList<Hotspot> hotspots = new ArrayList<Hotspot>();
  ArrayList<Retailer> retailers = new ArrayList<Retailer>();
  ArrayList<UserPOI> userPOIs = new ArrayList<UserPOI>();
  ArrayList<PublicPOI> publicPOIs = new ArrayList<PublicPOI>();
  ArrayList<Route> routes = new ArrayList<Route>();
  //TODO use reader to populate these ArrayLists

  private boolean populateArrayLists() {
    Reader rdr = new Reader();
    try{
      //hotspots = rdr.readHotspots("src/main/resources/InitialHotspots");
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
  public void initialize() {
    populateArrayLists(); //TODO fix reader so it doesn't explode
  }

  //menu button declarations
  @FXML
  private Button dataViewButton;
  @FXML
  private Button mapViewButton;
  @FXML
  private Button userViewButton;
  @FXML
  private Button importExportButton;
  //Data table
  @FXML
  private TableView rawDataTable;

  @FXML
  private SplitPane mainSplitPane; //background "holder" of everything

  //stackpane which the main views sit on
  @FXML
  private StackPane mainStackPane;

  //splitpane the TableView and buttons sit on. Children of the stackpane above.
  @FXML
  private SplitPane dataSplitPane;
  @FXML
  private Pane mapViewPane;




  /*
  Action handlers
   */
  public void viewData() {
    dataSplitPane.toFront();
  }
  public void viewMap() {
    mapViewPane.toFront();
  }

  public void dataViewHotspots() {
    ObservableList<Hotspot> oListHotspots = FXCollections.observableArrayList(hotspots);
    TableColumn<Hotspot, String> firstCol = new TableColumn<Hotspot, String>("Name");
    rawDataTable.getColumns().setAll(firstCol); //something something
    rawDataTable.setItems(oListHotspots);
  }

  public void dataViewRetailers() {
    ObservableList<Retailer>  oListRetailers = FXCollections.observableArrayList(retailers);
    TableColumn<Retailer, String> firstCol = new TableColumn<Retailer, String>("Name");
    firstCol.setCellValueFactory(new PropertyValueFactory<Retailer, String>("name"));
    rawDataTable.getColumns().setAll(firstCol);
    rawDataTable.setItems(oListRetailers);
  }
}