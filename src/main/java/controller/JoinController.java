package controller;

import java.time.LocalDate;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class JoinController {

  // Join window attributes

  @FXML private GridPane gridPane;
  @FXML private TextField firstName;
  @FXML private TextField lastName;
  @FXML private ToggleGroup gender;
  @FXML private RadioButton male;
  @FXML private RadioButton female;
  @FXML private RadioButton other;
  @FXML private DatePicker birthdate;
  @FXML private TextField height;
  @FXML private TextField weight;
  @FXML private TextField username;
  @FXML private TextField password;
  @FXML private Button continueB;


  // Methods
  /** Initialize the window
   *
   */
  public void initialize() {
//    gridPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
//      @Override
//      public void handle(KeyEvent keyEvent) {
//        if (keyEvent.getCode() == KeyCode.ENTER)  {
//          createUser();
//        }
//      }
//    });
  }

  /** Attempts to create a bicyclist user using form fields
   *
   */
  public void createUser() {

    // TODO Validate data fields using method below, throw warning window if format error
    boolean validFields = validateDataFields();
    if (validFields) {
      // TODO create the user

      // If user created successfully, tell GUIManager
      try {
        System.out.println("User created");
        GUIManager.getInstanceGUIManager().userCreated();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }

  /** Checks to make sure every field in the window has valid input
   * @return boolean True if details correct, False otherwise
   */
  private boolean validateDataFields() {

    boolean validNewFirstName = true;
    boolean validNewLastName = true;
    boolean validHeight = true;
    boolean validWeight = true;
    boolean validUsername = true;
    boolean availableUsername = true;
    boolean validPassword = true;

    String newFirstNameWarning = "First name must be only upper and lowercase letters";
    String newLastNameWarning = "Last name must be only upper and lowercase letters";
    String heightWarning = "Height must be in the format {numer}'{number}\"";
    String weightWarning = "Weight must be a number";
    String usernameWarning = "Username must be only upper and lower case letters";
    String usernameTakenWarning = "That username has already been taken";

    // Test first name
    // TODO Check for characters other than letters
    // TODO Format the string for title
    String newFirstName = firstName.getText();
    if (!newFirstName.matches("[A-Za-z]*")) {
      validNewFirstName = false;
    } else {
      newFirstName = toDisplayCase(newFirstName);
    }

    // Test last name
    // TODO Check for characters other than letters
    // TODO Format the string for title
    String newLastName = lastName.getText();
    if (!newLastName.matches("[A-Za-z]*")) {
      validNewLastName = false;
    } else {
      newLastName = toDisplayCase(newLastName);
    }

    // Test radio button
    String newGender = gender.getSelectedToggle().toString();
    int newGenderInt;
    switch (newGender) {
      case "male":
        newGenderInt = 0;
        break;
      case "female":
        newGenderInt = 1;
        break;
      case "other":
        newGenderInt = 2;
        break;
    }

    // Get birthdate
    LocalDate newBirthdate = birthdate.getValue();

    // Test height
    // TODO Check if format is int'int"
    // TODO Check if under 8'11"
    String newHeight = height.getText();
    if (!newLastName.matches("[0-9]'([0-9]|[0-1][0-2])]\"")) {
      validNewLastName = false;
    }

    // Test weight
    // TODO Check if format is simply an int
    // TODO Check if less than what 500lbs?
    double newWeight = Double.valueOf(weight.getText());

    // Test username
    // TODO make sure it's unique
    // TODO length limit
    String newUsername = username.getText();
    if (newUsername.matches("[A-Za-z]*")) {
      validUsername = false;
    }

    // Test password
    // TODO length limit
    String newPassword = password.getText();
    if (password.getLength() > 80) {
      validPassword = false;
    }


    // If anything is false, return false
    if (!(validNewFirstName && validNewLastName && validHeight && validWeight && validUsername && availableUsername && validPassword)) {

      Alert alert = new Alert(AlertType.WARNING, "Delete ?", ButtonType.OK);
      alert.showAndWait();

      if (alert.getResult() == ButtonType.OK) {
        //do stuff
      }

      return false;
    } else {
      return true;
    }

  }

  public static String toDisplayCase(String s) {

    final String ACTIONABLE_DELIMITERS = " '-/"; // these cause the character following
    // to be capitalized

    StringBuilder sb = new StringBuilder();
    boolean capNext = true;

    for (char c : s.toCharArray()) {
      c = (capNext)
          ? Character.toUpperCase(c)
          : Character.toLowerCase(c);
      sb.append(c);
      capNext = (ACTIONABLE_DELIMITERS.indexOf((int) c) >= 0); // explicit cast not needed
    }
    return sb.toString();
  }

}
