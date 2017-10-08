package helper;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import model.Mappable;
import model.Route;

public class tableOnClickPopup {

  public static int return_value = 0;

  public static void create(String title, Mappable clicked_item, boolean isRouteTableItem) {
    return_value = 0;
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(clicked_item.toString());
    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    alert.getDialogPane().setMinWidth(400);

    ButtonType showOnMapButton = new ButtonType("Show on Map");
    //other buttons go here
    ButtonType closeButton = new ButtonType("Close", ButtonData.CANCEL_CLOSE);

    ButtonType addRouteButton = new ButtonType("Add to history");
    ButtonType removeRouteButton = new ButtonType("Remove from history");

    if (isRouteTableItem) {
      alert.getButtonTypes().setAll(addRouteButton, showOnMapButton, closeButton);
    } else if (clicked_item instanceof Route) {
      alert.getButtonTypes().setAll(removeRouteButton, showOnMapButton, closeButton);
    } else {
      alert.getButtonTypes().setAll(showOnMapButton, closeButton);
    }

    Optional<ButtonType> result = alert.showAndWait();

    if (result.get() == showOnMapButton) {
      //System.out.println("you clicked Show on Map!");
      return_value = 1;
    } else if ((result.get() == addRouteButton) || (result.get() == removeRouteButton)) {
      return_value = 2;
    } else {
      return_value = 0;
    }

  }
  public static void create(String title, String message) {
    return_value = 0;
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
    ButtonType closeButton = new ButtonType("Close", ButtonData.CANCEL_CLOSE);

    alert.getButtonTypes().setAll(closeButton);

    alert.showAndWait();

    return_value = 0;

  }
}
