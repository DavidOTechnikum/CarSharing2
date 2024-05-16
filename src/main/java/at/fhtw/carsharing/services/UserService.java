package at.fhtw.carsharing.services;

import at.fhtw.carsharing.persistence.entity.ChangeUserRequest;
import at.fhtw.carsharing.persistence.entity.LoginInfo;
import at.fhtw.carsharing.persistence.entity.Roles;
import at.fhtw.carsharing.persistence.entity.User;
import at.fhtw.carsharing.security.WebSecurityConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class UserService {

    public static boolean checkUserObject (User user) {
        if (user.getUsername() == null ||
                user.getPassword() == null ||
                user.getFirstName() == null ||
                user.getSurname() == null ||
                user.getAge() == 0 ||
                user.getDrivingLicenseNumber() == null ||
                user.getCreditCardNumber() == null) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param user
     * @param userList
     * @return true if Username is ok, false if Username is taken
     */
    public static boolean usernameTaken (User user, List<User> userList) {
        for (User u : userList) {
            if (u.getUsername().equals(user.getUsername())){
                return false;
            }
        }
        return true;
    }

    public static void addUserToList (User user, List<User> userList) {
        user.setId(userList.size()+1);
        user.setRole(Roles.CUSTOMER);
        user.setLoggedIn(false);

        userList.add(user);
    }

    public static String userLogin (List<User> userList, LoginInfo loginInfo) {
        for (User u : userList) {
            if (loginInfo.getUsername().equals(u.getUsername())) {
                if (loginInfo.getPassword().equals(u.getPassword())){
                    u.setLoggedIn(true);
                    String jwtToken = (WebSecurityConfig.TokenIssuer(u.getUsername(), String.valueOf(u.getRole())));
                    return jwtToken;
                }
            }
        }
        return "null";
    }


        public static ResponseEntity<String> userUpdate (String changeUsername, ChangeUserRequest changeUserRequest, List<User> userList) {

        if (changeUserRequest.getUsernameToken().getUsername().equals(changeUsername)) {

            if (WebSecurityConfig.TokenVerifierCustomer(changeUserRequest.getUsernameToken().getJwtToken(), changeUserRequest.getUsernameToken().getUsername())) {
                for (User u : userList) {
                    if (u.getUsername().equals(changeUserRequest.getUsernameToken().getUsername())) {
                        for (User us : userList) {
                            if ((us.getUsername().equals(changeUserRequest.getNewUser().getUsername())) && (!u.getUsername().equals(us.getUsername()))) {
                                return new ResponseEntity<>("username taken", HttpStatus.CONFLICT);
                            }
                        }
                        if (changeUserRequest.getNewUser().getUsername() != null) {
                            u.setUsername(changeUserRequest.getNewUser().getUsername());
                        }
                        if (changeUserRequest.getNewUser().getPassword() != null) {
                            u.setPassword(changeUserRequest.getNewUser().getPassword());
                        }
                        if (changeUserRequest.getNewUser().getFirstName() != null) {
                            u.setFirstName(changeUserRequest.getNewUser().getFirstName());
                        }
                        if (changeUserRequest.getNewUser().getSurname() != null) {
                            u.setSurname(changeUserRequest.getNewUser().getSurname());
                        }
                        if (changeUserRequest.getNewUser().getAge() != 0) {
                            u.setAge(changeUserRequest.getNewUser().getAge());
                        }
                        if (changeUserRequest.getNewUser().getDrivingLicenseNumber() != null) {
                            u.setDrivingLicenseNumber(changeUserRequest.getNewUser().getDrivingLicenseNumber());
                        }
                        if (changeUserRequest.getNewUser().getCreditCardNumber() != null) {
                            u.setCreditCardNumber(changeUserRequest.getNewUser().getCreditCardNumber());
                        }
                        if (changeUserRequest.getNewUser().getRole() != null && u.getRole().equals(Roles.FLEETMANAGER)) {
                            u.setRole(changeUserRequest.getNewUser().getRole());
                        }

                        String newJwtToken = WebSecurityConfig.TokenIssuer(u.getUsername(), String.valueOf(u.getRole()));
                        return new ResponseEntity<>(newJwtToken, HttpStatus.OK);

                    }
                }
            }
        } else {
            if (WebSecurityConfig.TokenVerifierManager(changeUserRequest.getUsernameToken().getJwtToken(), changeUserRequest.getUsernameToken().getUsername())) {
                for (User v : userList) {
                    if (v.getUsername().equals(changeUsername)) {
                        for (User vs : userList) {
                            if (vs.getUsername().equals(changeUserRequest.getNewUser().getUsername()) && (!v.getUsername().equals(changeUserRequest.getNewUser().getUsername()))) {
                                return new ResponseEntity<>("username taken", HttpStatus.CONFLICT);
                            }
                        }
                        if (changeUserRequest.getNewUser().getUsername() != null) {
                            v.setUsername(changeUserRequest.getNewUser().getUsername());
                        }
                        if (changeUserRequest.getNewUser().getPassword() != null) {
                            v.setPassword(changeUserRequest.getNewUser().getPassword());
                        }
                        if (changeUserRequest.getNewUser().getFirstName() != null) {
                            v.setFirstName(changeUserRequest.getNewUser().getFirstName());
                        }
                        if (changeUserRequest.getNewUser().getSurname() != null) {
                            v.setSurname(changeUserRequest.getNewUser().getSurname());
                        }
                        if (changeUserRequest.getNewUser().getAge() != 0) {
                            v.setAge(changeUserRequest.getNewUser().getAge());
                        }
                        if (changeUserRequest.getNewUser().getDrivingLicenseNumber() != null) {
                            v.setDrivingLicenseNumber(changeUserRequest.getNewUser().getDrivingLicenseNumber());
                        }
                        if (changeUserRequest.getNewUser().getCreditCardNumber() != null) {
                            v.setCreditCardNumber(changeUserRequest.getNewUser().getCreditCardNumber());
                        }
                        if (changeUserRequest.getNewUser().getRole() != null) {
                            v.setRole(changeUserRequest.getNewUser().getRole());
                        }
                        v.setLoggedIn(false);
                        return new ResponseEntity<>(HttpStatus.OK);
                    }
                }
            }
        }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }


}
