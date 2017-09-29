package model;

import java.time.LocalDate;

/**
 * The class Cyclist defines the type for users of type Cyclist
 */
public class Cyclist {

  private String username;
  private String password;
  /**
   * User gender 0 - male 1 - female 2 - not specified
   */
  private int gender;
  private double weight;
  private int height;
  private LocalDate DOB;

  /**
   * Default Constructor for Creating a Cyclist User
   * @param username Username for Cyclist
   * @param password Password for Cyclist
   * @param DOB Date of Birth of Cyclist User. Uses java.time.LocalDate
   * @param gender Gender of Cyclist 0 Male, 1 Female, 2 Other
   * @param weight Weight of Cyclist user
   * @param height Height of user in inches
   */
  public Cyclist(String username, String password, LocalDate DOB, int gender, double weight, int height){
    this.username = username;
    this.password = password;
    this.DOB = DOB;
    this.gender = gender;
    this.weight = weight;
    this.height = height;

  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }


  public double getWeight() {
    return weight;
  }

  public int getGender() {
    return gender;
  }

  public int getHeight() {
    return height;
  }

  public LocalDate getDOB() {
    return DOB;
  }
}
