package com.gov.dsta.coldstraw.controller;

import com.gov.dsta.coldstraw.assembler.GroupAssembler;
import com.gov.dsta.coldstraw.assembler.UserAssembler;
import com.gov.dsta.coldstraw.model.Group;
import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.model.User;
import com.gov.dsta.coldstraw.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserAssembler userAssembler;
    private final GroupAssembler groupAssembler;

    public UserController(UserService userService, UserAssembler userAssembler, GroupAssembler groupAssembler) {
        this.userService = userService;
        this.userAssembler = userAssembler;
        this.groupAssembler = groupAssembler;
    }

    @GetMapping()
    public CollectionModel<EntityModel<User>> getUsers() {
        List<EntityModel<User>> users = userService
                .getUsers()
                .stream()
                .map(userAssembler::toModel)
                .collect(Collectors.toList());
        return userAssembler.toCollectionModel(users);
    }

    @GetMapping("/{userId}")
    public EntityModel<User> getUser(@PathVariable UUID userId) {
        User user = userService.getUser(userId);
        return userAssembler.toModel(user);
    }

    @GetMapping("/{userId}/groups")
    public CollectionModel<EntityModel<Group>> getGroups(@PathVariable UUID userId) {
        List<EntityModel<Group>> groups = userService
                .getUserGroups(userId)
                .stream()
                .map(groupAssembler::toModel)
                .collect(Collectors.toList());
        return groupAssembler.toCollectionModel(groups);
    }

    @GetMapping("/{userId}/groups/{groupId}")
    public EntityModel<Group> getGroup(@PathVariable UUID userId, @PathVariable UUID groupId) {
        Group group = userService.getUserGroup(userId, groupId);
        return groupAssembler.toModel(group);
    }

    @GetMapping("/{userId}/notifications/sent")
    public List<Notification> getSentNotifications(@PathVariable UUID userId) {
        // return all notifications sent by user
        return null;
    }

    @GetMapping("/{userId}/notifications/received")
    public List<Notification> getReceivedNotifications(@PathVariable UUID userId) {
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

    @PutMapping("/{userId}/groups")
    public ResponseEntity<EntityModel<User>> addGroup(@PathVariable UUID userId, @RequestParam UUID groupId) {
        User user = userService.addGroup(userId, groupId);
        EntityModel<User> userModel = userAssembler.toModel(user);
        return ResponseEntity
                .created(userModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(userModel);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<EntityModel<User>> updateUser(@PathVariable UUID userId, @RequestBody User user) {
        User updatedRole = userService.updateUser(userId, user);
        EntityModel<User> roleModel = userAssembler.toModel(updatedRole);
        return ResponseEntity
                .created(roleModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(roleModel);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}/groups")
    public ResponseEntity<Void> deleteUserGroups(@PathVariable UUID userId) {
        userService.deleteUserGroups(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}/groups/{groupId}")
    public ResponseEntity<Void> deleteUserGroup(@PathVariable UUID userId, @PathVariable UUID groupId) {
        userService.deleteUserGroup(userId, groupId);
        return ResponseEntity.noContent().build();
    }
}
