package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Created by Tyler Kennedy on 29/8/17
 * 
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
}