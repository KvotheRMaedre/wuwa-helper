package com.wuwa.helper.controller;

import com.wuwa.helper.dto.UserDTO;
import com.wuwa.helper.entity.User;
import com.wuwa.helper.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO request){
        var userId = userService.createUser(request);
        return ResponseEntity.created(URI.create("/v1/user/" + userId.toString())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
        try {
            var user = userService.getUserByID(userId);
            return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        var listUsers = userService.getAllUsers();
        return ResponseEntity.ok(listUsers);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String userId){
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{userId}")
    public ResponseEntity<User> updateUserById(@PathVariable String userId, @RequestBody UserDTO request){
        try {
            if(userService.userExists(userId)){
                var user = userService.updateUserById(userId, request);
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}