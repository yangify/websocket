package com.gov.dsta.coldstraw.controller;

import com.gov.dsta.coldstraw.service.GroupService;
import com.gov.dsta.coldstraw.model.Group;
import com.gov.dsta.coldstraw.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping()
    public List<Group> getGroups() {
        // return all groups
        return groupService.getGroups();
    }

    @GetMapping("/{groupId}")
    public Group getGroup(@PathVariable Long groupId) {
        // return a single group
        return null;
    }

    @PostMapping()
    public Group createGroup(@RequestBody Group group) {
        // create a group
        return null;
    }

    @PutMapping("/{groupId}")
    public Group updateGroup(@RequestBody Group group) {
        // change name
        // change users
        return null;
    }

    @DeleteMapping("/{groupId}")
    public void deleteGroup(@PathVariable Long groupId) {
        // delete a single group
    }

    @GetMapping("/{groupId}/users")
    public List<User> getUsers(@PathVariable Long groupId) {
        // get all users of a single group
        return null;
    }

    @GetMapping("/{groupId}/users/{userId}")
    public User getUser(@PathVariable Long groupId, @PathVariable Long userId) {
        // get a single user from a single group, maybe null
        return null;
    }

    @PostMapping("/{groupId}/users")
    public Group addUser(@PathVariable Long groupId, @RequestBody User user) {
        // add a single user to a single group
        return null;
    }

    @DeleteMapping("/{groupId}/users/{userId}")
    public void deleteUser(@PathVariable Long groupId, @PathVariable Long userId) {
        // delete a single user from a single group
    }
}
