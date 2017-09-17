package model;

import javax.swing.ImageIcon;

/**
 * The interface Mappable
 */
public interface Mappable {
  double latitude = 0;
  double longitude = 0;
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
