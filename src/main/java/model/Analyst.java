package model;

/**
 * The class Analyst defines the type for users of type Analyst
 */
public class Analyst {

  private String username;

  private String password;

  public Analyst(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public String getUsername() {
    return username;
  }
}
