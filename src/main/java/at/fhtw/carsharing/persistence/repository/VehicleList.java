package at.fhtw.carsharing.persistence.repository;

import at.fhtw.carsharing.persistence.entity.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VehicleList {
    final private static List<Vehicle> vehicleList = new ArrayList<>();


    public static List<Vehicle> getVehicleList() {
        return vehicleList;
    }
}
