package model;

import java.time.LocalDate;

/**
 * The class Cyclist defines the type for users of type Cyclist
 */
public class Cyclist {

  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private int gender; /* User gender 0 - male 1 - female 2 - not specified */
  private double weight;
  private int height;
  /**
   * Contains only the date; no time
   */
  private LocalDate birthDate;

  /**
   * Default Constructor for Creating a Cyclist User
   *
   * @param username Username for Cyclist
   * @param password Password for Cyclist
   * @param birthDate Date of birth of Cyclist User. Uses java.time.LocalDate
   * @param gender Gender of Cyclist 0 Male, 1 Female, 2 Other
   * @param weight Weight of Cyclist user
   * @param height Height of user in inches
   */
  public Cyclist(String firstName, String lastName, String username, String password,
      LocalDate birthDate, int gender, double weight, int height) {

    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.birthDate = birthDate;
    this.gender = gender;
    this.weight = weight;
    this.height = height;
  }

  /**
   * Print method for Cyclist
   *
   * @return String
   */
  public String toString() {

    return "First name: " + firstName + ", Last name: " + lastName + ", Username: " + username +
        ", Password: " + password + ", Gender: " + gender + ", Weight: " + weight + ", Height: " +
        height + ", Birth date: " + birthDate;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
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
    return birthDate;
  }


  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public Double getBMI() {
    double heightM = height * 0.0254;
    double weightKG = weight * 0.45359237;
    return  weightKG / heightM;
  }
}
