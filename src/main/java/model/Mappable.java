package model;

import javax.swing.ImageIcon;

/**
 * Thinking about making this a normal class, or maybe abstract because the methods can just be defined here instead of individually
 */

public interface Mappable {
  double focusLocation[] = new double[2];
  ImageIcon Logo = new ImageIcon();
  String info = "";

  /*
  double[] getLocation();
  void setLocation(double focusLocation[]);

  ImageIcon getLogo();
  void setLogo(ImageIcon Logo);

  String getInfo();
  void setInfo(String info);
  */
}
