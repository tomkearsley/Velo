package window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Join extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/gui/join.fxml"));
      primaryStage.setTitle("Vélo");
      primaryStage.setScene(new Scene(root, 500, 400));
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
