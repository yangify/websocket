package com.gov.dsta.coldstraw.controller;

import com.gov.dsta.coldstraw.assembler.UserAssembler;
import com.gov.dsta.coldstraw.exception.user.UserNotFoundException;
import com.gov.dsta.coldstraw.model.Group;
import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.model.User;
import com.gov.dsta.coldstraw.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserAssembler userAssembler;

    public UserController(UserService userService, UserAssembler userAssembler) {
        this.userService = userService;
        this.userAssembler = userAssembler;
    }

    @GetMapping()
    public CollectionModel<EntityModel<User>> getUsers() {
        List<User> users = userService.getUsers();
        List<EntityModel<User>> roles = users.stream()
                .map(userAssembler::toModel)
                .collect(Collectors.toList());
        return userAssembler.toCollectionModel(roles);
    }

    @GetMapping("/{userId}")
    public EntityModel<User> getUser(@PathVariable Long userId) {
        try {
            User user = userService.getUser(userId);
            return userAssembler.toModel(user);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
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
    public ResponseEntity<EntityModel<User>> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        EntityModel<User> userModel = userAssembler.toModel(createdUser);
        return ResponseEntity
                .created(userModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(userModel);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<EntityModel<User>> updateUser(@PathVariable Long userId, @RequestBody User user) {
        User updatedRole = userService.updateUser(userId, user);
        EntityModel<User> roleModel = userAssembler.toModel(updatedRole);
        return ResponseEntity
                .created(roleModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(roleModel);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
