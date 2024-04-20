package at.fhtw.carsharing.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * User class:
 * contains information about user, can be fleetmanager or customer (specified by "Roles role").
 */
@AllArgsConstructor
@Getter
@Setter
public class User {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String surname;
    private int age;
    private String drivingLicenseNumber;
    private String creditCardNumber;
    private Roles role;
    private boolean loggedIn;

    public User() {}
}
