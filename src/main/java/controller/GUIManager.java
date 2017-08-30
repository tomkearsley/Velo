package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.Hotspot;
import model.Mappable;

/**
 * Created by Tyler Kennedy on 29/8/17
 * TODO add temporary storage (reader, arrayLists
 * TODO conversion to ObservableList for tableView
 * TODO change bottom bar on tableView to radio buttons for different tables and a "GO" button to link here
 */

public class GUIManager {
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
  //public void dataViewHotspots() {rawDataTable.setItems(hotspotsObservable);}
}