package at.fhtw.carsharing.persistence.repository;

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
        return userList;
    }
}
