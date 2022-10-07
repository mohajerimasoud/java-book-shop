package app.web.mohajeri.portfolio.bookstore.controller;

import app.web.mohajeri.portfolio.bookstore.DTO.TokenDto;
import app.web.mohajeri.portfolio.bookstore.model.User;
import app.web.mohajeri.portfolio.bookstore.repository.UserRepository;
import app.web.mohajeri.portfolio.bookstore.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {


    UserRepository userRepository;
    JwtService jwtService;

    public UserController(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;

    }

    @PostMapping("/register")
    public ResponseEntity<TokenDto> registerUser(@Valid @RequestBody User user, Errors error) {
        try {
            if (error.hasErrors()) {
                throw  new Error("errors : " + error);
            }
            var foundUser = userRepository.findUserByUsername(user.getUsername());
            if (foundUser != null) {
                throw  new Error("duplicated username");
            }
            var result = userRepository.save(user);
            if (result == null) {
                throw  new Error("Error");
            }
            var jwt = jwtService.generateTokenFromUserId(result.getId());

            return ResponseEntity.status(200).body(jwt);
        } catch (Exception e) {
            log.error("Error in controller " + e);
//            return ResponseEntity.status(500).body("Error "+e);
            throw new Error(e);
        }
    }


    @PostMapping("/validate")
    public ResponseEntity<String> validateToken( @RequestParam String token) {
       var result = jwtService.validateJwt(token);
        return ResponseEntity.status(200).body(result ? "Token is valid" : "invalid token");
    }

}
