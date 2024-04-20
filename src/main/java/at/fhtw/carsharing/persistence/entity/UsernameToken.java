package at.fhtw.carsharing.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Transportation class for username and JWT token, necessary for authenticated methods.
 */
@AllArgsConstructor
@Getter
@Setter
public class UsernameToken {
    private String username;
    private String jwtToken;
}
