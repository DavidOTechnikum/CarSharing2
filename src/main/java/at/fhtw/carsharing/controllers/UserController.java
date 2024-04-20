package at.fhtw.carsharing.controllers;

import at.fhtw.carsharing.persistence.entity.*;
import at.fhtw.carsharing.persistence.repository.UserList;
import at.fhtw.carsharing.security.WebSecurityConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static at.fhtw.carsharing.service.UserService.*;

/**
 * UserController
 * Contains endpoints for REST server dealing with user issues.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

//    public List<User> userList = new ArrayList<>();
    List<User> userList = UserList.getUserList();

    /**
     * Endpoint for registering new user.
     * Per default set to "customer" and not logged in.
     * @param user              New user, if fleetmanager, role must be updated later (updateUser()).
     * @return                  HttpStatus
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User  user) {
        if (!checkUserObject(user)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!usernameTaken(user, userList)){
                return new ResponseEntity<>("username taken", HttpStatus.CONFLICT);
        }

        addUserToList(user, userList);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Endpoint for users to log in.
     * @param loginInfo     Object made up of username and password.
     * @return              JWT token to be saved locally and used for any other request.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody LoginInfo loginInfo) {
        if (userList.isEmpty()) {
            userList.add(new User(1, "Admin", "1234", "Admin", "Admin", 0, "000", "000", Roles.FLEETMANAGER, false));

        }
        String jwtToken = userLogin(userList, loginInfo);
        if (jwtToken.equals("null")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(jwtToken, HttpStatus.OK);

    }


    /**
     * Endpoint for logging out.
     * @param usernameToken Object consisting of JWT token received at log-in
     *                      and username of the user to be logged off.
     * @return              HTTP status code
     */
    @PostMapping("/logout")
    public HttpStatus logout (@RequestBody UsernameToken usernameToken) {
        if (WebSecurityConfig.TokenVerifierCustomer(usernameToken.getJwtToken(), usernameToken.getUsername())){
            for (User u : userList) {
                if (u.getUsername().equals(usernameToken.getUsername())) {
                    u.setLoggedIn(false);
                    return HttpStatus.OK;
                }
            }
        }

    return HttpStatus.FORBIDDEN;
    }


//    /**
//     * Endpoint which returns list of registered users.
//     * @param usernameToken Object consisting of JWT token received at log-in
//     *      *                      and username of fleetmanager.
//     * @return              User list.
//     */
//    @GetMapping("/")
//    public ResponseEntity<List<User>> getUserList (@RequestBody UsernameToken usernameToken) {
//        if (TokenVerifierManager(usernameToken.getJwtToken(), usernameToken.getUsername())) {
//                        return new ResponseEntity<>(userList, HttpStatus.OK);
//            }
//        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//    }


    /**
     * Endpoint which returns list of registered users.
     * @param usernameToken Object consisting of JWT token received at log-in
     *      *                      and username of fleetmanager.
     * @return              User list.
     */
    @GetMapping("/")
    public ResponseEntity<List<User>> getUserList (@RequestHeader Map<String, String> usernameToken) {
        String username = usernameToken.get("username");
        String token = usernameToken.get("token");
        if (WebSecurityConfig.TokenVerifierManager(token, username)) {
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    /**
     * Endpoint to update user information, can be done to oneself; to others only as fleetmanager.
     * @param changeUsername            Path variable: username of user to be updated.
     * @param changeUserRequest         Object containing:
     *                                      Requesting user's username and token (UsernameToken)
     *                                      New user info (User)
     * @return                          New JWT token in case user changes their own username.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser (@PathVariable("id") String changeUsername, @RequestBody ChangeUserRequest changeUserRequest) {
        return userUpdate(changeUsername, changeUserRequest, userList);
    }
}