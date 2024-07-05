package conexa.challenge.controller;

import conexa.challenge.model.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(value = "User Management System", tags = "Users", description = "Operations pertaining to users in User Management System")
public class UserController {

    @ApiOperation(value = "Login user and return JWT token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully logged in"),
            @ApiResponse(code = 400, message = "Invalid request parameters"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PostMapping("user")
    public UserDTO login(
            @ApiParam(value = "Username of the user", required = true)
            @RequestParam("user")
            @NotBlank(message = "Username cannot be blank") String username,
            @ApiParam(value = "Password of the user", required = true)
            @RequestParam("password")
            @NotBlank(message = "Password cannot be blank") String pwd) {

        String token = getJWTToken(username);
        UserDTO userDTO = new UserDTO();
        userDTO.setUser(username);
        userDTO.setToken(token);
        return userDTO;
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
