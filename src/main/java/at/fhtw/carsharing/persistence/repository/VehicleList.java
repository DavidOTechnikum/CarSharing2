package at.fhtw.carsharing.persistence.repository;

import at.fhtw.carsharing.persistence.entity.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleList {
    final private static List<Vehicle> vehicleList = new ArrayList<>();


    public static List<Vehicle> getVehicleList() {
        return vehicleList;
    }
}
