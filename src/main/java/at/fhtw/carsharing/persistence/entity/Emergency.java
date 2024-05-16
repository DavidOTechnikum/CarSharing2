package at.fhtw.carsharing.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Emergency {
    private VehicleStatus vehicleStatus;
    private int priority;
    private String description;
}
