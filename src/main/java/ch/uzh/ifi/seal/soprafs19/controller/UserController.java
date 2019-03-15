package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.entity.UserLight;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    Iterable<UserLight> all() {
        List<UserLight> userLights = new ArrayList<>();
        this.service.getUsers().forEach(user -> {
            userLights.add(new UserLight(user));
        });

        return userLights;
    }

    @PostMapping("/users")
    String createUser(@RequestBody User newUser) {
        try{
            return "users/"+this.service.createUser(newUser).getId();
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
    }

    @GetMapping("/users/{id}")
    UserLight getUser(@PathVariable("id") long id){
        var user = this.service.getUserById(id);

        if (user != null) {
            return new UserLight(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @CrossOrigin(origins = {"http://localhost:3000", "https://sopra-fs19-safa-dario-client.herokuapp.com"})
    @PutMapping("/users/{id}")
    ResponseEntity<Void> updateUser(@RequestBody User updatedUser, @PathVariable("id") long id) {
        var oldUser = this.service.getUserById(id);
        if (oldUser != null) {
            try {
                this.service.updateUser(id, updatedUser);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

}
