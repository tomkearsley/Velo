package helper;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import model.Mappable;
import model.POI;
import model.Retailer;

public class tableOnClickPopup {
  public static boolean return_value = false;
  public static void create(String title, String message, Mappable clicked_item) {
    return_value = false;
    //TODO may have to overload to change Mappable to POI and Route
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(clicked_item.toString());

    ButtonType showOnMapButton = new ButtonType("Show on Map");
    //other buttons go here
    ButtonType closeButton = new ButtonType("Close", ButtonData.CANCEL_CLOSE);

    alert.getButtonTypes().setAll(showOnMapButton, closeButton);

    Optional<ButtonType> result = alert.showAndWait();
    if(result.get() == showOnMapButton) {
      //TODO add functionality here
      System.out.println("you clicked Show on Map!");
      return_value = true;
    }
    else {

      return_value = false;
    }
    //change return type to Mappable, return the point or null if none is selected
    //in controller, if not null --> show on map
  }

}
