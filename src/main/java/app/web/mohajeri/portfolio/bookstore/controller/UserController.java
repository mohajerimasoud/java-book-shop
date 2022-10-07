package app.web.mohajeri.portfolio.bookstore.controller;

import app.web.mohajeri.portfolio.bookstore.model.User;
import app.web.mohajeri.portfolio.bookstore.repository.UserRepository;
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

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody User user, Errors error) {
        if (error.hasErrors()) {
            return ResponseEntity.status(400).body("errors : " + error);
        }
        var foundUser = userRepository.findUserByUsername(user.getUsername());
        if (foundUser != null) {
            return ResponseEntity.status(400).body("duplicated username");
        }

        var result = userRepository.save(user);

        return ResponseEntity.status(200).body( result);


    }

}
