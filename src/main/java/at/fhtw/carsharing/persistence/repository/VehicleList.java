package at.fhtw.carsharing.persistence.repository;

import at.fhtw.carsharing.persistence.entity.Position;
import at.fhtw.carsharing.persistence.entity.State;
import at.fhtw.carsharing.persistence.entity.Vehicle;
import at.fhtw.carsharing.persistence.entity.VehicleStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class VehicleList {
    final private static List<Vehicle> vehicleList = new ArrayList<>();


    public static List<Vehicle> getVehicleList() {
        vehicleList.add(new Vehicle(1, "Ford1", "Fiesta2020", "1234", "t0k3n", new VehicleStatus(new Position(15.351, 46.312), new Date(), State.OCCUPIED, 2, 5, 3)));
        vehicleList.add(new Vehicle(2, "Ford2", "Focus2017", "1234", "t0k3n", new VehicleStatus(new Position(15.351, 46.312), new Date(), State.OCCUPIED, 3, 5, 3)));
        vehicleList.add(new Vehicle(3, "Opel1", "Corsa1999", "1234", "t0k3n", new VehicleStatus(new Position(15.351, 46.312), new Date(), State.OCCUPIED, 4, 5, 3)));

        return vehicleList;
    }
}
