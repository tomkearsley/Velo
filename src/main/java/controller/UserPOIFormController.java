package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.UserPOI;

public class UserPOIFormController {

  /* Window attributes */
  @FXML private TextField name;
  @FXML private TextArea description;
  @FXML private TextField buildingNumber;
  @FXML private TextField streetName;
  @FXML private TextField city;
  @FXML private TextField state;
  @FXML private TextField latitude;
  @FXML private TextField longitude;
  @FXML private Button cancel;
  @FXML private Button add;
  @FXML private Label loading;
  private UserPOI newUserPOI;


  /* Window methods */

  private String getName() {
    return name.getText();
  }

  private String getDescription() {
    return description.getText();
  }

  private String getBuildingNumber() {
    return buildingNumber.getText();
  }

  private String getStreetName() {
    return streetName.getText();
  }

  private String getCity() {
    return city.getText();
  }

  private String getState() {
    return state.getText();
  }

  private String getLatitude() {
    return latitude.getText();
  }

  private String getLongitude() {
    return longitude.getText();
  }

  public void addPlace() {

    newUserPOI.setName(getName());
    newUserPOI.setDescription(getDescription());

    // TODO Query Google to get latitude and longitude @Imas
//    newUserPOI.setLatitude(stuff);
//    newUserPOI.setLongitude(stuff);

    // TODO Add newUserPOI to Database

  }

  public void cancelAddPlace() throws Exception {
    loading.setVisible(true);
    Platform.runLater(() -> {
      try { GUIManager.getInstanceGUIManager().cancelAddPlace(); }
      catch (Exception e) { System.out.println("Cancel add place failed\n" + e); }
    } );
  }

}
