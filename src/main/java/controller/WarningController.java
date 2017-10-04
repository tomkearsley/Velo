package controller;

import java.awt.Button;
import java.awt.Label;
import javafx.fxml.FXML;

public class WarningController {

  //FXML attributes
  @FXML private Label description;
  @FXML private Button continueB;


  // Methods
  /** Sets the description label in the warning window
   *
   * @param newDescription the description to be set as the warning message
   */
  public void setDescription(String newDescription) {
    description.setText(newDescription);
  }

}
