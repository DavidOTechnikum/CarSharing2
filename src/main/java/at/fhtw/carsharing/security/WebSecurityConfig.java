package at.fhtw.carsharing.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import at.fhtw.carsharing.persistence.entity.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import java.util.UUID;

/**
 * Security configuration and token methods.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     *
     * @param http  Security for web-based http requests.
     * @return      HttpSecurity object.
     * @throws Exception in case HttpSecurity object cannot be created.
     */
    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .formLogin().disable()
                .securityMatcher("/**")
//                .authorizeHttpRequests(registry -> registry
//                        .requestMatchers("/api/users/login").permitAll()
//                        .anyRequest().authenticated())
        ;
        return http.build();

    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        http
//                .authorizeHttpRequests((requests) -> requests
////                        .requestMatchers("/index", "/", "/api/**").permitAll()
//                        .requestMatchers("/").permitAll()
//                        .anyRequest().authenticated())
//                .formLogin((form) -> form
//                        . loginPage("/login")
//                        .permitAll())
//                .logout((logout) -> logout.permitAll());
//
//        return http.build();
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }

    /**
     * Method issues JWT tokens.
     * @param username  Username to be included in token.
     * @param role      Role (fleetmanager/customer) to be included in token.
     * @return          JWT token as string.
     */
    public static String TokenIssuer(String username,
                                     String role) {
        Algorithm algorithm = Algorithm.HMAC256("fhtw");

        return JWT.create()
                .withIssuer("fhtw")
                .withClaim("username", username)
                .withClaim("role", role)
                .withJWTId(String.valueOf(UUID.randomUUID()))
                .sign(algorithm);
}

    /**
     * Method verifies token, checks username and role.
     * @param jwtToken  JWT token as string.
     * @param username  Username which was used to create token.
     * @return          True in case username fits and if user is fleetmanager.
     */
    public static boolean TokenVerifierManager (String jwtToken,
                                        String username) {
        Algorithm algorithm = Algorithm.HMAC256("fhtw");
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("fhtw")
                .build();
        DecodedJWT decodedJWT = verifier.verify(jwtToken);
        Claim nameClaim = decodedJWT.getClaim("username");
        Claim managerClaim = decodedJWT.getClaim("role");
        return nameClaim.asString().equals(username) && managerClaim.asString().equals(String.valueOf(Roles.FLEETMANAGER));
    }

    /**
     * Method verifies token, checks username.
     * @param jwtToken  JWT token as string.
     * @param username  Username which was used to create token.
     * @return          True in case username fits.
     */
    public static boolean TokenVerifierCustomer (String jwtToken,
                                                 String username) {
        Algorithm algorithm = Algorithm.HMAC256("fhtw");
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("fhtw")
                .build();
        DecodedJWT decodedJWT = verifier.verify(jwtToken);
        Claim nameClaim = decodedJWT.getClaim("username");
        return nameClaim.asString().equals(username);
    }

    /**
     * Method issues JWT tokens for vehicles.
     * @param id                ID number to be included in token.
     * @param securityKey      Security key to be included in token.
     * @return                  JWT token as string.
     */
    public static String TokenIssuerVehicle(int id,
                                     String securityKey) {
        Algorithm algorithm = Algorithm.HMAC256("fhtw");

        return JWT.create()
                .withIssuer("fhtw")
                .withClaim("id", String.valueOf(id))
                .withClaim("securityKey", securityKey)
                .withJWTId(String.valueOf(UUID.randomUUID()))
                .sign(algorithm);
    }


    /**
     * Method verifies vehicle JWT token, checks vehicle id and security key.
     * @param jwtToken      JWT token as string.
     * @param id            Vehicle ID number.
     * @param securityKey   Vehicle security key.
     * @return              True in case token is valid.
     */
    public static boolean TokenVerifierVehicle (String jwtToken,
                                                 int id,
                                                 String securityKey) {
        Algorithm algorithm = Algorithm.HMAC256("fhtw");
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("fhtw")
                .build();
        DecodedJWT decodedJWT = verifier.verify(jwtToken);
        Claim idClaim = decodedJWT.getClaim("id");
        Claim keyClaim = decodedJWT.getClaim("securityKey");
        return idClaim.asInt() == id && keyClaim.asString().equals(securityKey);
    }

}
