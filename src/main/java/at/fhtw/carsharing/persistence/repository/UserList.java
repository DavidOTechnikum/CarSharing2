package at.fhtw.carsharing.persistence.repository;

import at.fhtw.carsharing.persistence.entity.Roles;
import at.fhtw.carsharing.persistence.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserList {
    final private static List<User> userList = new ArrayList<>();


    public static List<User> getUserList() {
        userList.add(new User(1, "Admin", "1234", "Admin", "Admin", 20, "Test", "1234567891234567", Roles.FLEETMANAGER, true));
        userList.add(new User(2, "Bibi5", "1234", "Bibi", "Blocksberg", 18, "AB45648", "4125643789546154", Roles.CUSTOMER, true));
        userList.add(new User(3, "Hansl77", "1234", "Johann", "Alhamid", 65, "DE87954", "4351652345978156", Roles.CUSTOMER, false));


        return userList;
    }
}
