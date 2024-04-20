package at.fhtw.carsharing.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Transportation class for username, JWT token and vehicle (used to update vehicle info).
 */
@AllArgsConstructor
@Getter
@Setter
public class VehicleUsernameToken {
    private UsernameToken usernameToken;
    private Vehicle vehicle;
}
