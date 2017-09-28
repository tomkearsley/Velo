package controller;

import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class JoinController {

  // Join window attributes

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

  /** Attempts to create a bicyclist user using form fields
   *
   */
  public void createUser() {

    // TODO Validate data fields using method below, throw warning window if format error


    // If user created successfully, tell GUIManager
    try {
      System.out.println("User created");
      GUIManager.getInstanceGUIManager().userCreated();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /** Checks to make sure every field in the window has valid input
   * @return boolean True if details correct, False otherwise
   */
  private boolean validateDataFields() {

    // Test first name
    // TODO Check for characters other than letters
    // TODO Format the string for title
    String newFirstName = firstName.getText();

    // Test first name
    // TODO Check for characters other than letters
    // TODO Format the string for title
    String newLastName = lastName.getText();

    // Test radio button
    String newGender = gender.getSelectedToggle().toString();

    // Get birthdate
    LocalDate newBirthdate = birthdate.getValue();

    // Test height
    // TODO Check if format is int'int"
    // TODO Check if under 8'

    // Test weight
    // TODO Check if format is simply an int
    // TODO Check if less than what 500lbs?
    int newWeight = Integer.valueOf(weight.getText());

    // Test username
    // TODO make sure it's unique
    // TODO length limit
    String newUsername = username.getText();

    // Test password
    // TODO length limit
    String newPassword = password.getText();


    return true;
  }

}
