package at.fhtw.carsharing.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Transportation class containing username and password, necessary for log-in method.
 */
@AllArgsConstructor
@Getter
@Setter
public class LoginInfo {
    private String username;
    private String password;
}
