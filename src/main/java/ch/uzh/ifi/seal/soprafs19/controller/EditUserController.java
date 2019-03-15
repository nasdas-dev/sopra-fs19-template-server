package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.service.EditService;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class EditUserController {

    private final EditService editService;
    private UserService userService;

    EditUserController(EditService editService, UserService userService) {
        this.editService = editService;
        this.userService = userService;
    }

    @PostMapping("/users/{id}/edit")
    Boolean canEditUser(@RequestHeader(value="token") String token, @RequestBody @PathVariable("id") Long id) {
        try {
            return this.editService.canEditUser(id, token);
        } catch (Exception ex) {
            if (ex instanceof ResponseStatusException) {
                throw ex;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        }

    }
}
