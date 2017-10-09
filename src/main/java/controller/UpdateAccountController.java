package controller;

import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import model.Cyclist;

public class UpdateAccountController {

    // Window attributes
    @FXML private GridPane gridPane;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private ToggleGroup gender;
    @FXML private Toggle male;
    @FXML private Toggle female;
    @FXML private Toggle other;
    @FXML private DatePicker birthDate;
    @FXML private ComboBox heightFeet;
    @FXML private ComboBox heightInches;
    @FXML private TextField weight;
    @FXML private TextField username;
    @FXML private TextField password;


    // Methods
    /** Initialize the window*/
    public void initialize() {

      // Set helper tool tips
      firstName.setTooltip(new Tooltip("Upper and lowercase letters only"));
      lastName.setTooltip(new Tooltip("Upper and lowercase letters only"));
      birthDate.setTooltip(new Tooltip("Must be before today"));
      heightFeet.setTooltip(new Tooltip("Numbers only"));
      heightInches.setTooltip(new Tooltip("Numbers only"));
      weight.setTooltip(new Tooltip("Numbers with decimal points only"));
      username.setTooltip(new Tooltip("Username has already been set and cannot be changed"));
      password.setTooltip(new Tooltip("80 character limit"));

      Cyclist cyclist = GUIManager.getInstanceGUIManager().getCyclistAccount();
      firstName.setText(cyclist.getFirstName());
      lastName.setText(cyclist.getLastName());
      switch (cyclist.getGender()) {
        case 0:
          gender.selectToggle(male);
          break;
        case 1:
          gender.selectToggle(female);
          break;
        case 2:
          gender.selectToggle(other);
          break;
      }
      birthDate.setValue(cyclist.getDOB());
      int newHeightFeet = cyclist.getHeight() / 12;
      int newHeightInches = cyclist.getHeight() % 12;
      // TODO set heightFeet and heightInches
      heightFeet.setValue(Integer.toString(newHeightFeet));
      heightInches.setValue(Integer.toString(newHeightInches));
      weight.setText(String.valueOf(cyclist.getWeight()));
      username.setText(cyclist.getUsername());


      EventHandler<KeyEvent> listener = (keyEvent) -> {
        if (keyEvent.getCode() == KeyCode.ENTER)  {
          updateCyclist();
        }
      };
      firstName.setOnKeyPressed(listener);
      lastName.setOnKeyPressed(listener);
      birthDate.setOnKeyPressed(listener);
      weight.setOnKeyPressed(listener);
      username.setOnKeyPressed(listener);
      password.setOnKeyPressed(listener);

      ObservableList feet = FXCollections.observableArrayList(
          "1", "2", "3", "4", "5", "6", "7", "8"
      );
      ObservableList inches = FXCollections.observableArrayList(
          "0","1","2","3","4","5","6","7","8","9","10","11","12"
      );

      heightFeet.getItems().addAll(feet);
      heightInches.getItems().addAll(inches);

    }

    /** Attempts to create a bicyclist user using form fields */
    @FXML
    private void updateCyclist() {

      boolean validData = true;
      String newFirstName = "";
      String newLastName = "";
      String newUsername = "";
      String newPassword = "";
      LocalDate newBirthDate = LocalDate.now();
      int newGenderInt = 0;
      double newWeight = -1.0;
      int newHeight = -1;


      // Test first name
      if (isValidFirstName()) {
        newFirstName = toDisplayCase(firstName.getText());
      } else {
        validData = false;
        firstName.setStyle("-fx-background-color: #ffbbbb; -fx-border-color: #f00;");
        // TODO set border red
      }

      // Test last name
      if (isValidLastName()) {
        newLastName = toDisplayCase(lastName.getText());
      } else {
        validData = false;
        lastName.setStyle("-fx-background-color: #ffbbbb; -fx-border-color: #f00;");
        // TODO set border red
      }

      // Test radio button
      switch (gender.getSelectedToggle().toString()) {
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

      // Test birth date
      if (isValidBirthDate()) {
        newBirthDate = birthDate.getValue();
      } else {
        validData = false;
        birthDate.setStyle("-fx-background-color: #ffbbbb; -fx-border-color: #f00;");
      }

      // Test height
      if (isValidHeightFeet()) {
        int newHeightFeet = Integer.valueOf(heightFeet.getValue().toString());
        newHeight += (newHeightFeet * 12);
      } else {
        validData = false;
        heightFeet.setStyle("-fx-background-color: #ffbbbb; -fx-border-color: #f00;");
      }

      if (isValidHeightInches()) {
        int newHeightInches = Integer.valueOf(heightInches.getValue().toString());
        newHeight += newHeightInches;
      } else {
        validData = false;
        heightInches.setStyle("-fx-background-color: #ffbbbb; -fx-border-color: #f00;");
      }

      // Test weight
      if (isValidWeight()) {
        newWeight = Double.valueOf(weight.getText());
      } else {
        validData = false;
        weight.setStyle("-fx-background-color: #ffbbbb; -fx-border-color: #f00;");
      }

      // Test password
      if (isValidPassword()) {
        newPassword = password.getText();
      } else {
        validData = false;
        password.setStyle("-fx-background-color: #ffbbbb; -fx-border-color: #f00;");
      }


      if (validData) {

        // TODO update user cyclist account details with the values taken from the form @tom

        // If user created successfully, tell GUIManager
        try {
          System.out.println("User account updated");
          GUIManager.getInstanceGUIManager().accountUpdated();
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        Alert alert = new Alert(AlertType.WARNING, "Invalid input. Hover over the erroneous field for help.", ButtonType.OK);
        alert.showAndWait();
      }
    }

    /** Back button action
     * Tells the GUI manager that the user wants to cancel joining
     */
    @FXML private void accountUpdateCancelled() throws Exception {
      System.out.println("User cancelled joining.");
      GUIManager.getInstanceGUIManager().accountUpdateCancelled();
    }

    /** Determines if the firstName element's input is valid
     *
     * @return true if valid; else false
     */
    private boolean isValidFirstName() {
      return firstName.getText().matches("[A-Za-z]*") && !firstName.getText().isEmpty();
    }

    /** Determines if the lastName element's input is valid
     *
     * @return true if valid; else false
     */
    private boolean isValidLastName() {
      return lastName.getText().matches("[A-Za-z]*") && !lastName.getText().isEmpty();
    }

    /** Determines if the birthDate element's input is valid
     *
     * @return true if valid; else false
     */
    private boolean isValidBirthDate() {
      return birthDate.getValue() != null && birthDate.getValue().isBefore(LocalDate.now());
    }

    /** Determines if the heightFeet element's input is valid
     *
     * @return true if valid; else false
     */
    private boolean isValidHeightFeet() {
      return heightFeet.getValue() != null;
    }

    /** Determines if the heightInches element's input is valid
     *
     * @return true if valid; else false
     */
    private boolean isValidHeightInches() {
      return heightInches.getValue() != null;
    }

    /** Determines if the weight element's input is valid
     *
     * @return true if valid; else false
     */
    private boolean isValidWeight() {
      return weight.getText().matches("[0-9]*") && !weight.getText().isEmpty();
    }

    /** Determines if the password element's input is valid
     *
     * @return true if valid; else false
     */
    private boolean isValidPassword() {
      return password.getLength() < 80;
    }

    /** Clears the CSS style for the firstName element */
    @FXML private void clearFirstNameStyle() {
      firstName.setStyle("-fx-background-color: null");
      firstName.setStyle("-fx-border-color: null");
      System.out.println("Cleared the first name style");
    }

    /** Clears the CSS style for the lastName element */
    @FXML private void clearLastNameStyle() {
      lastName.setStyle("-fx-background-color: null");
      lastName.setStyle("-fx-border-color: null");
    }

    /** Clears the CSS style for the birthDate element */
    @FXML private void clearBirthDateStyle() {
      birthDate.setStyle("-fx-background-color: null");
      birthDate.setStyle("-fx-border-color: null");
    }

    /** Clears the CSS style for the heightFeet element */
    @FXML private void clearHeightFeetStyle() {
      heightFeet.setStyle("-fx-background-color: null");
      heightFeet.setStyle("-fx-border-color: null");
    }

    /** Clears the CSS style for the heightInches element */
    @FXML private void clearHeightInchesStyle() {
      heightInches.setStyle("-fx-background-color: null");
      heightInches.setStyle("-fx-border-color: null");
    }

    /** Clears the CSS style for the weight element */
    @FXML private void clearWeightStyle() {
      weight.setStyle("-fx-background-color: null");
      weight.setStyle("-fx-border-color: null");
    }

    /** Clears the CSS style for the username element */
    @FXML private void clearUsernameStyle() {
      username.setStyle("-fx-background-color: null");
      username.setStyle("-fx-border-color: null");
    }

    /** Clears the CSS style for the password element */
    @FXML private void clearPasswordStyle() {
      password.setStyle("-fx-background-color: null");
      password.setStyle("-fx-border-color: null");
    }

    /** Capitalises text in title or diplay format
     * The first letter is capitalised, all others lowercase
     * @param s the string to be capitalised
     * @return capitalised string
     */
    private static String toDisplayCase(String s) {

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
