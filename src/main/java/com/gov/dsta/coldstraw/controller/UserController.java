package com.gov.dsta.coldstraw.controller;

import com.gov.dsta.coldstraw.model.Group;
import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.model.User;
import com.gov.dsta.coldstraw.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId) {
        return null;
    }

    @GetMapping("/{userId}/groups")
    public List<Group> getUserGroups(@PathVariable Long userId) {
        // return all the groups the user belongs to
        return null;
    }

    @GetMapping("/{userId}/groups/{groupId}")
    public Group getUserGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        // return a specific group that a user belongs to
        return null;
    }

    @GetMapping("/{userId}/notifications/sent")
    public List<Notification> getSentNotifications(@PathVariable Long userId) {
        // return all notifications sent by user
        return null;
    }

    @GetMapping("/{userId}/notifications/received")
    public List<Notification> getReceivedNotifications(@PathVariable Long userId) {
        // return all notifications received by user
        return null;
    }

    @PostMapping()
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId) {
        // update one user's details
        return null;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
