package window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The class Login runs the login.fxml file to build the Login GUI window
 */
public class Login extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/gui/login.fxml"));
      primaryStage.setTitle("Vélo");
      primaryStage.setScene(new Scene(root, 500, 400));

//      MenuBar menuBar = new MenuBar();
//      final String os = System.getProperty ("os.name");
//      if (os != null && os.startsWith ("mac"))
//        menuBar.useSystemMenuBarProperty().set(true);
//
//      BorderPane borderPane = new BorderPane ();
//      borderPane.setTop(menuBar);
//
//      primaryStage.setScene(new Scene(borderPane));


      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
