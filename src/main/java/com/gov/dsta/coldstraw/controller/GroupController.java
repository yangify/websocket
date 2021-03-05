package com.gov.dsta.coldstraw.controller;

import com.gov.dsta.coldstraw.assembler.GroupAssembler;
import com.gov.dsta.coldstraw.assembler.UserAssembler;
import com.gov.dsta.coldstraw.exception.Group.GroupNotFoundException;
import com.gov.dsta.coldstraw.exception.user.UserNotFoundException;
import com.gov.dsta.coldstraw.model.Group;
import com.gov.dsta.coldstraw.model.User;
import com.gov.dsta.coldstraw.service.GroupService;
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
@RequestMapping("/v1/groups")
public class GroupController {

    private final GroupService groupService;
    private final GroupAssembler groupAssembler;
    private final UserAssembler userAssembler;

    public GroupController(GroupService groupService, GroupAssembler groupAssembler, UserAssembler userAssembler) {
        this.groupService = groupService;
        this.groupAssembler = groupAssembler;
        this.userAssembler = userAssembler;
    }

    @GetMapping()
    public CollectionModel<EntityModel<Group>> getGroups() {
        List<EntityModel<Group>> groups = groupService.getGroups().stream()
                .map(groupAssembler::toModel)
                .collect(Collectors.toList());
        return groupAssembler.toCollectionModel(groups);
    }

    @GetMapping("/{groupId}")
    public EntityModel<Group> getGroup(@PathVariable Long groupId) {
        try {
            Group group = groupService.getGroup(groupId);
            return groupAssembler.toModel(group);
        } catch (GroupNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @GetMapping("/{groupId}/users")
    public CollectionModel<EntityModel<User>> getUsers(@PathVariable Long groupId) {
        List<EntityModel<User>> users = groupService.getUsers(groupId).stream()
                .map(userAssembler::toModel)
                .collect(Collectors.toList());
        return userAssembler.toCollectionModel(users);
    }

    @GetMapping("/{groupId}/users/{userId}")
    public EntityModel<User> getUser(@PathVariable Long groupId, @PathVariable Long userId) {
        try {
            User user = groupService.getUser(groupId, userId);
            return userAssembler.toModel(user);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<EntityModel<Group>> createGroup(@RequestBody Group group) {
        Group createdGroup = groupService.createGroup(group);
        EntityModel<Group> groupModel = groupAssembler.toModel(createdGroup);
        return ResponseEntity
                .created(groupModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(groupModel);
    }

    @PostMapping("/{groupId}/users")
    public Group addUserToGroup(@PathVariable Long groupId, @RequestBody Long userId) {
        return groupService.addUser(groupId, userId);
    }

    @PutMapping("/{groupId}")
    public Group updateGroup(@PathVariable String groupId, @RequestBody Group group) {
        return null;
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{groupId}/users")
    public ResponseEntity<Void> deleteAllUserFromGroup(@PathVariable Long groupId) {
        groupService.deleteAllUserFromGroup(groupId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{groupId}/users/{userId}")
    public ResponseEntity<Void> deleteUserFromGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        groupService.deleteUserFromGroup(groupId, userId);
        return ResponseEntity.noContent().build();
    }
}
