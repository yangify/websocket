package com.gov.dsta.coldstraw.controller;

import com.gov.dsta.coldstraw.assembler.GroupAssembler;
import com.gov.dsta.coldstraw.assembler.UserAssembler;
import com.gov.dsta.coldstraw.exception.Group.GroupNotFoundException;
import com.gov.dsta.coldstraw.exception.user.UserNotFoundException;
import com.gov.dsta.coldstraw.model.Group;
import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.model.User;
import com.gov.dsta.coldstraw.service.GroupService;
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
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;
    private final UserAssembler userAssembler;
    private final GroupService groupService;
    private final GroupAssembler groupAssembler;

    public UserController(UserService userService, UserAssembler userAssembler, GroupService groupService, GroupAssembler groupAssembler) {
        this.userService = userService;
        this.userAssembler = userAssembler;
        this.groupService = groupService;
        this.groupAssembler = groupAssembler;
    }

    @GetMapping()
    public CollectionModel<EntityModel<User>> getUsers() {
        List<EntityModel<User>> users = userService.getUsers().stream()
                .map(userAssembler::toModel)
                .collect(Collectors.toList());
        return userAssembler.toCollectionModel(users);
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
    public CollectionModel<EntityModel<Group>> getGroups(@PathVariable Long userId) {
        List<EntityModel<Group>> groups = userService.getUserGroups(userId).stream()
                .map(groupAssembler::toModel)
                .collect(Collectors.toList());
        return groupAssembler.toCollectionModel(groups);
    }

    @GetMapping("/{userId}/groups/{groupId}")
    public EntityModel<Group> getGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        try {
            Group group = userService.getUserGroup(userId, groupId);
            return groupAssembler.toModel(group);
        } catch(GroupNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
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
        groupService.deleteUserFromGroups(userId);
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    //TODO
    @DeleteMapping("/{userId}/groups")
    public ResponseEntity<Void> deleteAllGroupFromUser(@PathVariable Long userId) {
        return ResponseEntity.noContent().build();
    }

    //TODO
    @DeleteMapping("/{userId}/groups/{groupId}")
    public ResponseEntity<Void> deleteGroupFromUser(@PathVariable Long userId, @PathVariable Long groupId) {
        return ResponseEntity.noContent().build();
    }
}
