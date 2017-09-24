package sample;

import java.util.function.Consumer;

import javafx.concurrent.Worker.State;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;


public class Bridge {


  private JSObject window;

  public Bridge(WebEngine engine) {
    engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
      if (newState == State.SUCCEEDED) {
        window = (JSObject) engine.executeScript("window");
        window.setMember("application", this);
      }
    });
  }

  public void execute(String function, Object... args) {
    window.call(function, args);
  }
}