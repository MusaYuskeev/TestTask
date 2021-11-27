package controllers;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.UserService;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "getById")
    public ResponseEntity<User> getUser(@RequestParam(value = "id") Long id) {
        User user = userService.getById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "getUser")
    public ResponseEntity<User> getUser(@RequestParam(value = "login") String login,
                                        @RequestParam(value = "password") String password) {
        User user = userService.getByCredential(login, password);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "delete")
    public ResponseEntity<User> deleteUser(@RequestParam(value = "id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "create")
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        User user = userService.create(newUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(path = "update")
    public ResponseEntity<User> updateUser(@RequestBody User newUser) {
        User user = userService.update(newUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("userlist")
    public ResponseEntity<List<User>> getAll() {
        try {
            return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
