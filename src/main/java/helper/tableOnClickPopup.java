package helper;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import model.Mappable;

public class tableOnClickPopup {

  public static boolean return_value = false;

  public static void create(String title, Mappable clicked_item) {
    return_value = false;
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(clicked_item.toString());
    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);

    ButtonType showOnMapButton = new ButtonType("Show on Map");
    //other buttons go here
    ButtonType closeButton = new ButtonType("Close", ButtonData.CANCEL_CLOSE);

    alert.getButtonTypes().setAll(showOnMapButton, closeButton);

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == showOnMapButton) {
      //System.out.println("you clicked Show on Map!");
      return_value = true;
    } else {

      return_value = false;
    }
  }
  public static void create(String title, String message) {
    return_value = false;
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
    ButtonType closeButton = new ButtonType("Close", ButtonData.CANCEL_CLOSE);

    alert.getButtonTypes().setAll(closeButton);

    alert.showAndWait();

    return_value = false;

  }
}
