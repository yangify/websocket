package com.gov.dsta.coldstraw.service;

import com.gov.dsta.coldstraw.exception.Group.GroupNotFoundException;
import com.gov.dsta.coldstraw.exception.user.UserNotFoundException;
import com.gov.dsta.coldstraw.model.Group;
import com.gov.dsta.coldstraw.model.User;
import com.gov.dsta.coldstraw.repository.GroupRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final UserService userService;
    private final GroupRepository groupRepository;

    public GroupService(@Lazy UserService userService, GroupRepository groupRepository) {
        this.userService = userService;
        this.groupRepository = groupRepository;
    }

    // GET
    public List<Group> getGroups() {
        return (List<Group>) groupRepository.findAll();
    }

    public Group getGroup(UUID groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));
    }

    public Group getGroup(String groupName) {
        return groupRepository.findByName(groupName).orElseThrow(() -> new GroupNotFoundException(groupName));
    }

    public Set<User> getUsers(UUID groupId) {
        Group group = getGroup(groupId);
        return group.getUsers();
    }

    public User getUser(UUID groupId, UUID userId) {
        Set<User> users = getUsers(groupId);
        return users.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    // POST
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    // PUT
    public Group addUser(UUID groupId, UUID userId) {
        User user = userService.getUser(userId);
        Group group = getGroup(groupId);
        group.addUser(user);
        return groupRepository.save(group);
    }

    // DELETE
    public void deleteGroup(UUID groupId) {
        deleteGroupUsers(groupId);
        groupRepository.deleteById(groupId);
    }

    public void deleteGroupUsers(UUID groupId) {
        Group group = getGroup(groupId);
        Set<User> users = group.getUsers();
        users.forEach(user -> deleteGroupUser(groupId, user.getId()));
        groupRepository.save(group);
    }

    public void deleteGroupUser(UUID groupId, UUID userId) {
        Group group = getGroup(groupId);
        Set<User> users = group
                .getUsers()
                .stream()
                .filter(user -> !user.getId().equals(userId))
                .collect(Collectors.toSet());
        group.setUsers(users);
        groupRepository.save(group);
    }
}
