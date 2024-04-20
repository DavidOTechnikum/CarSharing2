package at.fhtw.carsharing.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * Vehicle class:
 * contains info about vehicle.
 */
@AllArgsConstructor
@Getter
@Setter
public class Vehicle {
    private int id;
    private String name;
    private String description;
    private String securityKey;
    private String jwtToken;
    private VehicleStatus vehicleStatus;
}
