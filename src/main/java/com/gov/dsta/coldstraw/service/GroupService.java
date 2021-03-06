package com.gov.dsta.coldstraw.service;

import com.gov.dsta.coldstraw.exception.Group.GroupNotFoundException;
import com.gov.dsta.coldstraw.exception.user.UserNotFoundException;
import com.gov.dsta.coldstraw.model.Group;
import com.gov.dsta.coldstraw.model.User;
import com.gov.dsta.coldstraw.repository.GroupRepository;
import com.gov.dsta.coldstraw.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public GroupService(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    // GET
    public List<Group> getGroups() {
        return (List<Group>) groupRepository.findAll();
    }

    public Group getGroup(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));
    }

    public Set<User> getUsers(Long groupId) {
        Group group = getGroup(groupId);
        return group.getUsers();
    }

    public User getUser(Long groupId, Long userId) {
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

    public Group addUser(Long groupId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Group group = getGroup(groupId);

        group.getUsers().add(user);
        user.getGroups().add(group);

        userRepository.save(user);
        return groupRepository.save(group);
    }

    // DELETE
    public void deleteGroup(Long groupId) {
        Group group = getGroup(groupId);
        Set<User> users = group.getUsers();
        users.forEach(user -> {
            Set<Group> groups = user
                    .getGroups()
                    .stream()
                    .filter(g -> !g.getId().equals(groupId))
                    .collect(Collectors.toSet());
            user.setGroups(groups);
        });
        groupRepository.save(group);
        groupRepository.deleteById(groupId);
    }

    public void deleteGroupUsers(Long groupId) {
        Group group = getGroup(groupId);
        group.setUsers(new HashSet<>());
        groupRepository.save(group);
    }

    public void deleteGroupUser(Long groupId, Long userId) {
        Group group = getGroup(groupId);
        Set<User> users = group.getUsers()
                .stream()
                .filter(user -> !user.getId().equals(userId))
                .collect(Collectors.toSet());
        group.setUsers(users);
        groupRepository.save(group);
    }
}
