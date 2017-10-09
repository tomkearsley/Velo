package controller;

import filehandler.Google;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.UserPOI;

public class UserPOIFormController {

  /* Window attributes */
  @FXML private TextField name;
  @FXML private TextArea description;
  @FXML private TextField buildingNumber;
  @FXML private TextField streetName;
  @FXML private TextField city;
  @FXML private TextField state;
  @FXML private Label loading;


  /* Window methods */
  /** Initialize the window */
  public void initialize() {
    setElementToolTips();

    EventHandler<KeyEvent> listener = (keyEvent) -> {
      if (keyEvent.getCode() == KeyCode.ENTER)  {
        addPlace();
      }
    };
    name.setOnKeyPressed(listener);
    description.setOnKeyPressed(listener);
    buildingNumber.setOnKeyPressed(listener);
    streetName.setOnKeyPressed(listener);
    city.setOnKeyPressed(listener);
    state.setOnKeyPressed(listener);
  }

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

  /** Tests to see if the name textfield matches a regular expression for validity
   * @return Boolean true if valid, false otherwise
   */
  private Boolean isValidName() {
    return getName().matches("[A-Za-z ]*") && getName().length() <= 30 && !getName().isEmpty();
  }

  /** Tests to see if the description textfield matches a regular expression for validity
   * @return Boolean true if valid, false otherwise
   */
  private Boolean isValidDescription() {
    return getDescription().matches("[A-Za-z.,?!;:'\"()&%$/ ]*") && getDescription().length() <= 50 && !getDescription().isEmpty();
  }

  /** Tests to see if the building number matches a regular expression for validity
   * @return Boolean true if valid, false otherwise
   */
  private Boolean isValidBuildingNumber() {
    return getBuildingNumber().matches("[A-Za-z0-9 ]*") && getBuildingNumber().length() <= 20 && !getBuildingNumber().isEmpty();
  }

  /** Tests to see if the street name matches a regular expression for validity
   * @return Boolean true if valid, false otherwise
   */
  private Boolean isValidStreeName() {
    return getStreetName().matches("[A-Za-z0-9 ]*") && getStreetName().length() <= 30 && !getStreetName().isEmpty();
  }

  /** Tests to see if the city matches a regular expression for validity
   * @return Boolean true if valid, false otherwise
   */
  private Boolean isValidCity() {
    return getCity().matches("[A-Za-z ]*") && getCity().length() <= 30 && !getCity().isEmpty();
  }

  /** Tests to see if the state matches a regular expression for validity
   * @return Boolean true if valid, false otherwise
   */
  private Boolean isValidState() {
    return getState().matches("[A-Za-z ]*") && getState().length() <= 20 && !getState().isEmpty();
  }

  /** Tests if all the text fields contain valid input
   * @return Boolean true if valid, false otherwise
   */
  private Boolean areValidFields() {
    return (isValidName() && isValidDescription() && isValidBuildingNumber() && isValidStreeName() && isValidCity() && isValidState());
  }

  /** Sets the error style to the name textfield */
  private void setNameErrorStyle() {
    name.setStyle("-fx-background-color: #ffbbbb; -fx-border-color: #f00;");
  }

  /** Sets the error style to the description textfield */
  private void setDescriptionErrorStyle() {
    description.setStyle("-fx-background-color: #ffbbbb; -fx-border-color: #f00;");
  }

  /** Sets the error style to the building number textfield */
  private  void setBuildingNumberErrorStyle() {
    buildingNumber.setStyle("-fx-background-color: #ffbbbb; -fx-border-color: #f00;");
  }

  /** Sets the error style to the street name textfield */
  private void setStreetNameErrorStyle() {
    streetName.setStyle("-fx-background-color: #ffbbbb; -fx-border-color: #f00;");
  }

  /** Sets the error style to the city textfield */
  private void setCityErrorStyle() {
    city.setStyle("-fx-background-color: #ffbbbb; -fx-border-color: #f00;");
  }

  /** Sets the error style to the state textfield */
  private void setStateErrorStyle() {
    state.setStyle("-fx-background-color: #ffbbbb; -fx-border-color: #f00;");
  }

  /** Sets the erroneous fields to be in error style*/
  private void setErrnoeousFieldErrorStyle() {
    if (!isValidName()) {setNameErrorStyle();}
    if (!isValidDescription()) {setDescriptionErrorStyle();}
    if (!isValidBuildingNumber()) {setBuildingNumberErrorStyle();}
    if (!isValidStreeName()) {setStreetNameErrorStyle();}
    if (!isValidCity()) {setCityErrorStyle();}
    if (!isValidState()) {setStateErrorStyle();}
  }

  @FXML private void clearNameStyle() {
    name.setStyle("-fx-background-color: null; -fx-border-color: null");
  }

  @FXML private void clearDescriptionStyle() {
    description.setStyle("-fx-background-color: null; -fx-border-color: null");
  }

  @FXML private void clearBuildingNumberStyle() {
    buildingNumber.setStyle("-fx-background-color: null; -fx-border-color: null");
  }

  @FXML private void clearStreetNameStyle() {
    streetName.setStyle("-fx-background-color: null; -fx-border-color: null");
  }

  @FXML private void clearCityStyle() {
    city.setStyle("-fx-background-color: null; -fx-border-color: null");
  }

  @FXML private void clearStateStyle() {
    state.setStyle("-fx-background-color: null; -fx-border-color: null");
  }

  private void setElementToolTips() {
    name.setTooltip(new Tooltip("Up to 30 letters"));
    description.setTooltip(new Tooltip("Up to 50 letters and some special characters"));
    buildingNumber.setTooltip(new Tooltip("Up to 20 letters and numbers"));
    streetName.setTooltip(new Tooltip("Up to 30 letters and numbers"));
    city.setTooltip(new Tooltip("Up to 30 letters"));
    state.setTooltip(new Tooltip("Up to 20 letters"));
  }

  public void addPlace() {

    if (areValidFields()) {

      String newName = getName();
      String newDescription = getDescription();
      double[] coordinates = Google.stringToLocation(getBuildingNumber() + getStreetName() + getCity() + getState());
      double newLat = coordinates[0];
      double newLong = coordinates[1];
      UserPOI newUserPOI = new UserPOI(newLat, newLong, newName, newDescription);

      // TODO Add newUserPOI to Database @Tom

    } else {
      Alert alert = new Alert(AlertType.WARNING, "Invalid input. Hover over the erroneous field for help.", ButtonType.OK);
      alert.showAndWait();
      setErrnoeousFieldErrorStyle();
    }
  }

  public void cancelAddPlace() throws Exception {
    loading.setVisible(true);
    Platform.runLater(() -> {
      try { GUIManager.getInstanceGUIManager().cancelAddPlace(); }
      catch (Exception e) { System.out.println("Cancel add place failed\n" + e); }
    } );
  }

}
